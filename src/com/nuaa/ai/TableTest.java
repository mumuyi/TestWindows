package com.nuaa.ai;

import java.awt.BorderLayout;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumnModel;

public class TableTest {

	private JFrame frame = null;
	private JTable table = null;
	private Table_Model model = null;
	private JScrollPane s_pan = null;

	public TableTest() {
		frame = new JFrame("Table Test");

		model = new Table_Model(20);

		table = new JTable(model);

		/*
		 * String[] age = { "21", "22", "23", "24", "25", "26", "27", "28",
		 * "29", "30" }; JComboBox com = new JComboBox(age); TableColumnModel
		 * tcm = table.getColumnModel(); tcm.getColumn(2).setCellEditor(new
		 * DefaultCellEditor(com)); // 设置某列采用JComboBox组件
		 */

		model.addRow("11111",  "11");
		model.addRow("22222", "22");
		model.addRow("33333", "33");

		s_pan = new JScrollPane(table);

		frame.getContentPane().add(s_pan, BorderLayout.CENTER);

		frame.setSize(600, 400);
		frame.setVisible(true);

		//model.addRow(2); // 在某处插入一空行

		table.updateUI(); // 刷新

	}

	public static void main(String args[]) {
		new TableTest();
	}

}
