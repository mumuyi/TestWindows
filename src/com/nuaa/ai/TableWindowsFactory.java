package com.nuaa.ai;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class TableWindowsFactory implements ActionListener {

	private JFrame frame = null;
	private JTable table = null;
	private MyTableModel model = null;
	private JScrollPane s_pan = null;
	
	// ��ҳ��ť;
	private JButton nextPagebutton;
	private JButton lastPagebutton;
	// ҳ����Ϣ;
	private JLabel pageLabel;

	// ���ݿ��ѯʱ,ÿ�β�ѯ���������;
	private int maxLineATime = 2;
	// ��ǰҳ��;
	private int currentPage = 0;
	// ���е����ݵ��ܵ�����;
	private long totalNum = 0;
	// �ݴ����ÿһ�е�ֵ;
	private List<String> list;
	//��ѯ���;
	private List<?> sqlList;
	//���õ���;
	private Object object;
	// ���캯��;
	public TableWindowsFactory(String TableTitle,String[] TableTitles,List<?> objectList,Object formerObject,long totalnum) {
		frame = new JFrame(TableTitle);
		model = new MyTableModel(TableTitles, 20);
		table = new JTable(model);
		
		object=formerObject;
		sqlList=objectList;
		totalNum = totalnum;
		
		//��ʼ����ҳ����;
		for (int i = 0; i < sqlList.size(); i++) {
			try {
				list=getObjectValue(sqlList.get(i));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			model.addRow(list);
		}

		//����������;
		List<Integer> specialrow = new ArrayList<>();
		// ���Ӳ�������,���Ա���ж���������ʾ�������ɫ;
		specialrow.add(0);
		
		// ����ѡ�е��е���ɫ;
		table.setSelectionBackground(new Color(189, 252, 201));
		// ��񱳾�ɫ
		// table.setBackground(Color.yellow);
		// ָ��ÿһ�е��и�50;
		table.setRowHeight(50);
		// jt.setRowHeight(2, 30);//ָ��2�еĸ߶�30


		// �����������ɫ;
		makeFace(table, specialrow);

		// �������봰����;
		s_pan = new JScrollPane(table);
		frame.add(s_pan);

		// ����JPanel;
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
		// �����һҳ��ť;
		lastPagebutton = new JButton("Last Page");
		lastPagebutton.setActionCommand("LastPage");
		lastPagebutton.addActionListener(this);
		buttonPanel.add(lastPagebutton, BorderLayout.WEST);
		// ���ҳ����Ϣ;
		pageLabel = new JLabel();
		pageLabel.setFont(new Font(Font.DIALOG, Font.BOLD, 13));
		if(totalNum % maxLineATime==0)
			pageLabel.setText("" + (currentPage + 1) + "/" + (totalNum / maxLineATime));
		else
			pageLabel.setText("" + (currentPage + 1) + "/" + (totalNum / maxLineATime+1));
		buttonPanel.add(pageLabel, BorderLayout.WEST);
		// �����һҳ��ť;
		nextPagebutton = new JButton("Next Page");
		nextPagebutton.setActionCommand("NextPage");
		nextPagebutton.addActionListener(this);
		buttonPanel.add(nextPagebutton, BorderLayout.EAST);
		// ��JPanel ��ӵ�������;
		frame.add(buttonPanel, BorderLayout.SOUTH);

		// ��һҳʱ������һҳ��ť������;
		lastPagebutton.setEnabled(false);

		// ���ô��ڴ�С;
		frame.setSize(600, 400);

		// ����JFrame ���ڹر��¼�;
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		// ����JFrame ���ڳ���λ��;������������ʾ����Ļ�м�;
		frame.setLocationRelativeTo(null);
	}

	// ��ʾ����;
	public void showTable(){
		frame.setVisible(true);
	}
	
	
	// ����������Կ��ǵ����ó�����Ϊһ�������ķ���;
	private void makeFace(JTable table, List<Integer> specialrows) {
		try {
			DefaultTableCellRenderer tcr = new DefaultTableCellRenderer() {
				private static final long serialVersionUID = -3876410206046533481L;

				List<Integer> specialrow = specialrows;

				public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
						boolean hasFocus, int row, int column) {
					// �����������е���ɫ��ͬ,�����û��鿴;
					if (row % 2 == 0) {
						setBackground(Color.white); // ���������е�ɫ
					} else if (row % 2 == 1) {
						setBackground(new Color(206, 231, 255)); // ����ż���е�ɫ
					}
					// �����Ҫ����ĳһ��Cell��ɫ����Ҫ����column������������
					if (row == 1 && column == 1) {
						setBackground(Color.YELLOW);
					}
					// �����ض��е���ɫ;��������Ҫ���⻯������кż���specialrow
					// ��,֮�����жϵ�ǰ������к��Ƿ������specialrow �м���;
					if (specialrow.contains(row)) {
						setBackground(Color.RED);
					}
					// �����ڸ�ʲô����̫���,���Ǹо�Ӧ�������õ�;
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

	// �������¼�;
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getActionCommand().equals("NextPage")) {
			System.out.println("���������·�ҳ����");
			currentPage += 1;
			try {
				updateTable();
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else if (e.getActionCommand().equals("LastPage")) {
			System.out.println("���������Ϸ�ҳ����");
			currentPage -= 1;
			try {
				updateTable();
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	// ���ݸ��²���;
	private void updateTable() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		// ɾ��model�е�֮ǰ������;
		for (int i = 0; i < sqlList.size(); i++) {
			// ����ÿ�ζ���ɾ����0��;��Ϊ�ײ�Ĵ�����ɾ��֮��������λ��;����ÿ�ζ�ɾ��һ��,һ��ɾ��list.size()�ξͺ���;
			model.removeRow(0);
		}
		// ���²�ѯ;
		try {
			Method m = (Method) object.getClass().getMethod("update",int.class,int.class);
			sqlList=(List<?>)m.invoke(object,currentPage * maxLineATime, maxLineATime);
		} catch (NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// ��modelд���µ�����;
		for (int i = 0; i < sqlList.size(); i++) {
			try {
				list=getObjectValue(sqlList.get(i));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			model.addRow(list);
		}
		// ˢ�±��;
		table.updateUI();

		// ���°�ť;
		buttonClickableChange();

		// ����ҳ����Ϣ;
		if(totalNum % maxLineATime==0)
			pageLabel.setText("" + (currentPage + 1) + "/" + (totalNum / maxLineATime));
		else
			pageLabel.setText("" + (currentPage + 1) + "/" + (totalNum / maxLineATime+1));
	}

	// ���÷�ҳ��ť�Ƿ�ɵ��;
	private void buttonClickableChange() {
		// ��һҳ;
		if ((currentPage + 1) * maxLineATime >= totalNum) {
			nextPagebutton.setEnabled(false);
		} else {
			nextPagebutton.setEnabled(true);

		}

		// ��һҳ;
		if (currentPage == 0) {
			lastPagebutton.setEnabled(false);
		} else {
			lastPagebutton.setEnabled(true);
		}
	}

	private List<String> getObjectValue(Object object) throws Exception {
		List<String> list=new ArrayList<>();
		if (object != null) {
			// �õ�����
			Class<?> clz = object.getClass();
			// ��ȡʵ������������ԣ�����Field����
			Field[] fields = clz.getDeclaredFields();
			
			for (Field field : fields) {
				//System.out.println(field.getGenericType());// ��ӡ�����������������

				// ���������String
				if (field.getGenericType().toString().equals("class java.lang.String")) { // ���type�������ͣ���ǰ�����"class
																							// "�����������
					// �õ������Ե�gettet����
					/**
					 * ������Ҫ˵��һ�£����Ǹ���ƴ�յ��ַ�������д��getter������
					 * ��Booleanֵ��ʱ����isXXX��Ĭ��ʹ��ide����getter�Ķ���isXXX��
					 * �������NoSuchMethod�쳣 ��˵�����Ҳ����Ǹ�gettet���� ��Ҫ�����淶
					 */
					Method m = (Method) object.getClass().getMethod("get" + getMethodName(field.getName()));

					String val = (String) m.invoke(object);// ����getter������ȡ����ֵ
					if (val != null) {
						//System.out.println("String type:" + val);
						list.add(val);
					}else{
						list.add("");
					}
					
				}

				// ���������Integer
				if (field.getGenericType().toString().equals("class java.lang.Integer")) {
					Method m = (Method) object.getClass().getMethod("get" + getMethodName(field.getName()));
					Integer val = (Integer) m.invoke(object);
					if (val != null) {
						//System.out.println("Integer type:" + val);
						list.add(val.toString());
					}else{
						list.add("");
					}

				}

				// ���������Double
				if (field.getGenericType().toString().equals("class java.lang.Double")) {
					Method m = (Method) object.getClass().getMethod("get" + getMethodName(field.getName()));
					Double val = (Double) m.invoke(object);
					if (val != null) {
						//System.out.println("Double type:" + val);
						list.add(val.toString());
					}else{
						list.add("");
					}

				}

				// ���������Boolean �Ƿ�װ��
				if (field.getGenericType().toString().equals("class java.lang.Boolean")) {
					Method m = (Method) object.getClass().getMethod(field.getName());
					Boolean val = (Boolean) m.invoke(object);
					if (val != null) {
						//System.out.println("Boolean type:" + val);
						list.add(val.toString());
					}else{
						list.add("");
					}

				}

				// ���������boolean �����������Ͳ�һ�� �����е�˵������������� isXXX�� �Ǿ�ȫ����isXXX��
				// �����Ҳ���getter�ľ�����
				if (field.getGenericType().toString().equals("boolean")) {
					Method m = (Method) object.getClass().getMethod(field.getName());
					Boolean val = (Boolean) m.invoke(object);
					if (val != null) {
						//System.out.println("boolean type:" + val);
						list.add(val.toString());
					}else{
						list.add("");
					}

				}
				// ���������Date
				if (field.getGenericType().toString().equals("class java.util.Date")) {
					Method m = (Method) object.getClass().getMethod("get" + getMethodName(field.getName()));
					Date val = (Date) m.invoke(object);
					if (val != null) {
						//System.out.println("Date type:" + val);
						list.add(val.toString());
					}else{
						list.add("");
					}

				}
				// ���������Short
				if (field.getGenericType().toString().equals("class java.lang.Short")) {
					Method m = (Method) object.getClass().getMethod("get" + getMethodName(field.getName()));
					Short val = (Short) m.invoke(object);
					if (val != null) {
						//System.out.println("Short type:" + val);
						list.add(val.toString());
					}else{
						list.add("");
					}
				}
				// �������Ҫ�������������Լ�����չ
			}
		}else{
			System.err.println("object is null");
		}
		return list;
	}

	// ��һ���ַ����ĵ�һ����ĸ��д��Ч������ߵġ�
	private static String getMethodName(String fildeName) throws Exception {
		if(fildeName.charAt(0)>='a'&&fildeName.charAt(0)<='z'){
			byte[] items = fildeName.getBytes();
			items[0] = (byte) ((char) items[0] - 'a' + 'A');
			return new String(items);
		}else{
			return fildeName;
		}
	}
}
