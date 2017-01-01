package com.nuaa.ai;

import java.util.List;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

public class MyTableModel extends AbstractTableModel {
	private static final long serialVersionUID = -3094977414157589758L;

	//用于存储表格中的显示的数据;
	private Vector content = null;

	//用于存储表格的表头;
	private String[] title_name;

	//构造函数;
	public MyTableModel() {
		content = new Vector();
	}

	//构造函数;
	//需要传入表头;
	public MyTableModel(String[] Titles,int count) {
		title_name=Titles;
		content = new Vector(count);
	}

	//加入一空行
	public void addRow(int row,List<String> list) {
		Vector v = new Vector(list.size());
		for(int i=0;i<list.size();i++)
			v.add(i, null);
		content.add(row, v);
	}

	
	//加入一行内容
	public void addRow(List<String> list) {
		Vector v = new Vector(list.size()+1);
		
		//加入其他数据;
		for(int i=0;i<list.size();i++){
			v.add(i, list.get(i));
		}
		//添加复选框,初始为未选中状态;
		v.add(list.size(),new Boolean(false));
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
