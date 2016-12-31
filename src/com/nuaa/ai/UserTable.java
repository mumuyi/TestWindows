package com.nuaa.ai;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class UserTable {

	private JFrame frame = null;
	private JTable table = null;
	private MyTableModel model = null;
	private JScrollPane s_pan = null;

	private String[] Titles= { "用户ID","用户名称" };
	
	public UserTable() {
		frame = new JFrame("Table Test");
		model = new MyTableModel(Titles,20);
		table = new JTable(model);
		
		List<User> list = HibernateTest.UserQuery();
		for (int i = 0; i < list.size(); i++) {
			model.addRow(list.get(i).getUserId(), list.get(i).getUserName());
		}

		List<Integer> specialrow=new ArrayList<>();
		
		// 设置选中的行的颜色;
		table.setSelectionBackground(new Color(189, 252, 201));
		// 表格背景色
		// table.setBackground(Color.yellow);
		// 指定每一行的行高50;
		table.setRowHeight(50);
		// jt.setRowHeight(2, 30);//指定2行的高度30

		specialrow.add(0);
		makeFace(table,specialrow);

		s_pan = new JScrollPane(table);
		frame.getContentPane().add(s_pan, BorderLayout.CENTER);
		frame.setSize(600, 400);
		frame.setVisible(true);
		
		//设置JFrame 窗口关闭事件;
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		//设置JFrame 窗口出现位置;这里设置其显示在屏幕中间;
		frame.setLocationRelativeTo(null);
	}

	public static void main(String args[]) {
		new UserTable();
	}

	
	//这个方法可以考虑单独拿出来作为一个公共的方法;
	public static void makeFace(JTable table,List<Integer> specialrows) {
		try {
			DefaultTableCellRenderer tcr = new DefaultTableCellRenderer() {

				List<Integer>specialrow =specialrows;
				public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
						boolean hasFocus, int row, int column) {
					//设置相邻两行的颜色不同,方便用户查看;
					if (row % 2 == 0) {
						setBackground(Color.white); // 设置奇数行底色
					} else if (row % 2 == 1) {
						setBackground(new Color(206, 231, 255)); // 设置偶数行底色
					}
					// 如果需要设置某一个Cell颜色，需要加上column过滤条件即可
					if(row==1&&column==1){
						setBackground(Color.YELLOW);
					}
					//设置特定行的颜色;将所有需要特殊化处理的行号加入specialrow 中,之后再判断当前处理的行号是否包含在specialrow 中即可;
					if(specialrow.contains(row)){
						setBackground(Color.RED);
					}
					//这是在干什么不是太清楚,但是感觉应该是有用的;
					/*
					 * if (Double.parseDouble(table.getValueAt(row,
					 * 1).toString()) > 0) { setBackground(Color.red); }
					 */
					return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				}
			};
			for (int i = 0; i < table.getColumnCount(); i++) {
				table.getColumn(table.getColumnName(i)).setCellRenderer(tcr);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}
}
