package com.nuaa.ai;

import java.util.Vector;

import javax.swing.table.AbstractTableModel;

public class Table_Model extends AbstractTableModel {
	private static final long serialVersionUID = -3094977414157589758L;

	//用于存储表格中的显示的数据;
	private Vector content = null;

	//用于存储表格的表头;
	private String[] title_name = { "用户ID","用户名称" };

	//构造函数;
	public Table_Model() {
		content = new Vector();
	}

	//构造函数;
	public Table_Model(int count) {
		content = new Vector(count);
	}

	//加入一空行
	public void addRow(int row) {
		Vector v = new Vector(2);
		v.add(0, null);
		v.add(1, null);
		//v.add(2, null);
		content.add(row, v);
	}

	
	//加入一行内容
	public void addRow(String name, String age) {
		Vector v = new Vector(2);

		v.add(0, name);
		//v.add(1, new Boolean(sex)); // JCheckBox是Boolean的默认显示组件，这里仅仅为了看效果，其实用JComboBox显示***更合适
		v.add(1, age); // 本列在前面已经设置成了JComboBox组件，这里随便输入什么字符串都没关系

		content.add(v);
	}

	//删除某一行;
	public void removeRow(int row) {
		content.remove(row);
	}

	// 单元格是否可编辑;
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		/*
		 * if (rowIndex == 2) { return false; } return true;
		 */
		return false;
	}

	//更新单元格信息;
	public void setValueAt(Object value, int row, int col) {
		((Vector) content.get(row)).remove(col);
		((Vector) content.get(row)).add(col, value);
		this.fireTableCellUpdated(row, col);
	}

	//获取某一列的名称;
	public String getColumnName(int col) {
		return title_name[col];
	}

	//获取一共有多少列;
	public int getColumnCount() {
		return title_name.length;
	}

	//获取一共有多少行;
	public int getRowCount() {
		return content.size();
	}

	//获取某一单元格的数据;
	public Object getValueAt(int row, int col) {
		return ((Vector) content.get(row)).get(col);
	}

	//获取一行的信息;
	public Class getColumnClass(int col) {
		return getValueAt(0, col).getClass();
	}
}
