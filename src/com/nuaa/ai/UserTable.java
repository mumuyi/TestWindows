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

	private String[] Titles= { "�û�ID","�û�����" };
	
	public UserTable() {
		frame = new JFrame("Table Test");
		model = new MyTableModel(Titles,20);
		table = new JTable(model);
		
		List<User> list = HibernateTest.UserQuery();
		for (int i = 0; i < list.size(); i++) {
			model.addRow(list.get(i).getUserId(), list.get(i).getUserName());
		}

		List<Integer> specialrow=new ArrayList<>();
		
		// ����ѡ�е��е���ɫ;
		table.setSelectionBackground(new Color(189, 252, 201));
		// ��񱳾�ɫ
		// table.setBackground(Color.yellow);
		// ָ��ÿһ�е��и�50;
		table.setRowHeight(50);
		// jt.setRowHeight(2, 30);//ָ��2�еĸ߶�30

		specialrow.add(0);
		makeFace(table,specialrow);

		s_pan = new JScrollPane(table);
		frame.getContentPane().add(s_pan, BorderLayout.CENTER);
		frame.setSize(600, 400);
		frame.setVisible(true);
		
		//����JFrame ���ڹر��¼�;
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		//����JFrame ���ڳ���λ��;������������ʾ����Ļ�м�;
		frame.setLocationRelativeTo(null);
	}

	public static void main(String args[]) {
		new UserTable();
	}

	
	//����������Կ��ǵ����ó�����Ϊһ�������ķ���;
	public static void makeFace(JTable table,List<Integer> specialrows) {
		try {
			DefaultTableCellRenderer tcr = new DefaultTableCellRenderer() {

				List<Integer>specialrow =specialrows;
				public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
						boolean hasFocus, int row, int column) {
					//�����������е���ɫ��ͬ,�����û��鿴;
					if (row % 2 == 0) {
						setBackground(Color.white); // ���������е�ɫ
					} else if (row % 2 == 1) {
						setBackground(new Color(206, 231, 255)); // ����ż���е�ɫ
					}
					// �����Ҫ����ĳһ��Cell��ɫ����Ҫ����column������������
					if(row==1&&column==1){
						setBackground(Color.YELLOW);
					}
					//�����ض��е���ɫ;��������Ҫ���⻯������кż���specialrow ��,֮�����жϵ�ǰ������к��Ƿ������specialrow �м���;
					if(specialrow.contains(row)){
						setBackground(Color.RED);
					}
					//�����ڸ�ʲô����̫���,���Ǹо�Ӧ�������õ�;
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
