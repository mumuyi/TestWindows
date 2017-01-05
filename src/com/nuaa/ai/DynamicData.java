/* --------------------
 * DynamicDataDemo.java
 * --------------------
 * (C) Copyright 2002-2005, by Object Refinery Limited.
 *
 */

package com.nuaa.ai;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;

public class DynamicData implements ActionListener {
	//时间序列;
	private TimeSeries series;

	private double lastValue = 100.0;

	private JButton button;

	private JFrame frame;

	private MyThread myThread1;

	//初始化时间序列图和窗口Frame;
	private void iniDemoPanel(String title) {
		frame = new JFrame(title);
		
		series = new TimeSeries("Random Data", Millisecond.class);
		TimeSeriesCollection dataset = new TimeSeriesCollection(series);

		ChartPanel chartPanel = new ChartPanel(createChart(dataset));
		chartPanel.setPreferredSize(new java.awt.Dimension(600, 370));

		JPanel buttonPanel = new JPanel();
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
		button = new JButton("Add New Data Item");
		button.setActionCommand("ADD_DATA");
		button.addActionListener(this);
		buttonPanel.add(button);

		frame.add(chartPanel);
		frame.add(buttonPanel, BorderLayout.SOUTH);
	}
	
	//展示;
	public void showDynamicData(String title) {
		iniDemoPanel(title);
		frame.setVisible(true);
	}
	
	//获取一个时间序列图;
	public JFreeChart getJFreeChart() {
		series = new TimeSeries("Random Data", Millisecond.class);
		TimeSeriesCollection dataset = new TimeSeriesCollection(series);
		return createChart(dataset);
	}

	//测试;
	public void beginToshow(){
		myThread1 = new MyThread();
		myThread1.start();
	}
	
	
	//创建一个时间序列图;
	private JFreeChart createChart(XYDataset dataset) {
		JFreeChart result = ChartFactory.createTimeSeriesChart("Dynamic Data Demo", "Time", "Value", dataset, true,
				true, false);
		XYPlot plot = (XYPlot) result.getPlot();
		ValueAxis axis = plot.getDomainAxis();
		axis.setAutoRange(true);
		axis.setFixedAutoRange(10000.0); // 60 seconds
		axis = plot.getRangeAxis();
		axis.setRange(0.0, 200.0);
		return result;
	}

	//点击事件;
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("ADD_DATA")) {
			myThread1 = new MyThread();
			myThread1.start();
			button.setText("stop");
			button.setActionCommand("stop");
		} else if (e.getActionCommand().equals("stop")) {
			myThread1.stop();
		}
	}

	//添加数据的线程;
	class MyThread extends Thread {
		public void run() {
			while (true) {
				setDynamicData();
				try {
					this.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	//动态设置显示的数据;
	public void setDynamicData() {
		double factor = 0.90 + 0.2 * Math.random();
		this.lastValue = this.lastValue * factor;
		Millisecond now = new Millisecond();
		System.out.println("Now = " + now.toString());
		this.series.add(new Millisecond(), this.lastValue);
	}
}
