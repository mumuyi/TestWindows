package com.nuaa.ai;

import java.util.Vector;

import javax.swing.table.AbstractTableModel;

public class Table_Model extends AbstractTableModel {
	private static final long serialVersionUID = -3094977414157589758L;

	//���ڴ洢����е���ʾ������;
	private Vector content = null;

	//���ڴ洢���ı�ͷ;
	private String[] title_name = { "�û�ID","�û�����" };

	//���캯��;
	public Table_Model() {
		content = new Vector();
	}

	//���캯��;
	public Table_Model(int count) {
		content = new Vector(count);
	}

	//����һ����
	public void addRow(int row) {
		Vector v = new Vector(2);
		v.add(0, null);
		v.add(1, null);
		//v.add(2, null);
		content.add(row, v);
	}

	
	//����һ������
	public void addRow(String name, String age) {
		Vector v = new Vector(2);

		v.add(0, name);
		//v.add(1, new Boolean(sex)); // JCheckBox��Boolean��Ĭ����ʾ������������Ϊ�˿�Ч������ʵ��JComboBox��ʾ***������
		v.add(1, age); // ������ǰ���Ѿ����ó���JComboBox����������������ʲô�ַ�����û��ϵ

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
