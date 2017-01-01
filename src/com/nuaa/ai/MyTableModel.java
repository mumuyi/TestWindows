package com.nuaa.ai;

import java.util.List;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

public class MyTableModel extends AbstractTableModel {
	private static final long serialVersionUID = -3094977414157589758L;

	//���ڴ洢����е���ʾ������;
	private Vector content = null;

	//���ڴ洢���ı�ͷ;
	private String[] title_name;

	//���캯��;
	public MyTableModel() {
		content = new Vector();
	}

	//���캯��;
	//��Ҫ�����ͷ;
	public MyTableModel(String[] Titles,int count) {
		title_name=Titles;
		content = new Vector(count);
	}

	//����һ����
	public void addRow(int row,List<String> list) {
		Vector v = new Vector(list.size());
		for(int i=0;i<list.size();i++)
			v.add(i, null);
		content.add(row, v);
	}

	
	//����һ������
	public void addRow(List<String> list) {
		Vector v = new Vector(list.size()+1);
		
		//������������;
		for(int i=0;i<list.size();i++){
			v.add(i, list.get(i));
		}
		//��Ӹ�ѡ��,��ʼΪδѡ��״̬;
		v.add(list.size(),new Boolean(false));
		content.add(v);
	}

	//ɾ��ĳһ��;
	public void removeRow(int row) {
		content.remove(row);
	}

	// ��Ԫ���Ƿ�ɱ༭;
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		/*
		 * if (rowIndex == 2) { return false; } return true;
		 */
		return false;
	}

	//���µ�Ԫ����Ϣ;
	public void setValueAt(Object value, int row, int col) {
		((Vector) content.get(row)).remove(col);
		((Vector) content.get(row)).add(col, value);
		this.fireTableCellUpdated(row, col);
	}

	//��ȡĳһ�е�����;
	public String getColumnName(int col) {
		return title_name[col];
	}

	//��ȡһ���ж�����;
	public int getColumnCount() {
		return title_name.length;
	}

	//��ȡһ���ж�����;
	public int getRowCount() {
		return content.size();
	}

	//��ȡĳһ��Ԫ�������;
	public Object getValueAt(int row, int col) {
		return ((Vector) content.get(row)).get(col);
	}

	//��ȡһ�е���Ϣ;
	public Class getColumnClass(int col) {
		return getValueAt(0, col).getClass();
	}
}
