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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

/**
 * A demonstration application showing a time series chart where you can
 * dynamically add (random) data by clicking on a button.
 */
public class DynamicData extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1787141432958166577L;
	private static DemoPanel demoPanel;

	/**
	 * Constructs a new demonstration application.
	 *
	 * @param title
	 *            the frame title.
	 */
	public DynamicData(String title) {
		super(title);
		demoPanel = new DemoPanel();
		setContentPane(demoPanel);
		
		/*
		setDefaultCloseOperation(ApplicationFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.out.println("click happen here");
			}
		});
		*/
		
		//setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	static class DemoPanel extends JPanel implements ActionListener {

		/**
		 * 
		 */
		private static final long serialVersionUID = 3280064875986207615L;

		/** The time series data. */
		private TimeSeries series;

		/** The most recent value added. */
		private double lastValue = 100.0;

		
		private JButton button;
		
		private MyThread myThread1;
		/**
		 * Creates a new instance.
		 */
		public DemoPanel() {
			super(new BorderLayout());
			this.series = new TimeSeries("Random Data", Millisecond.class);
			TimeSeriesCollection dataset = new TimeSeriesCollection(this.series);
			ChartPanel chartPanel = new ChartPanel(createChart(dataset));
			chartPanel.setPreferredSize(new java.awt.Dimension(600, 370));

			JPanel buttonPanel = new JPanel();
			buttonPanel.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
			button = new JButton("Add New Data Item");
			button.setActionCommand("ADD_DATA");
			button.addActionListener(this);
			buttonPanel.add(button);

			add(chartPanel);
			add(buttonPanel, BorderLayout.SOUTH);
		}

		/**
		 * Creates a sample chart.
		 * 
		 * @param dataset
		 *            the dataset.
		 * 
		 * @return A sample chart.
		 */
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

		/**
		 * Handles a click on the button by adding new (random) data.
		 *
		 * @param e
		 *            the action event.
		 */
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals("ADD_DATA")) {
				myThread1 = new MyThread();
				myThread1.start();
				button.setText("stop");
				button.setActionCommand("stop");
			}else if(e.getActionCommand().equals("stop")){
				myThread1.stop();
			}
		}

		public void setDynamicData() {
			double factor = 0.90 + 0.2 * Math.random();
			this.lastValue = this.lastValue * factor;
			Millisecond now = new Millisecond();
			System.out.println("Now = " + now.toString());
			this.series.add(new Millisecond(), this.lastValue);
		}

		class MyThread extends Thread {
			public void run() {
				while (true) {
					demoPanel.setDynamicData();
					try {
						this.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}

	/**
	 * Creates a panel for the demo (used by SuperDemo.java).
	 * 
	 * @return A panel.
	 */
	public static JPanel createDemoPanel() {
		return new DynamicData.DemoPanel();
	}

	/**
	 * Starting point for the demonstration application.
	 *
	 * @param args
	 *            ignored.
	 */
	public static void showDynamicData() {

		DynamicData demo = new DynamicData("Dynamic Data Demo");
		demo.pack();
		RefineryUtilities.centerFrameOnScreen(demo);
		demo.setVisible(true);
	}
}
