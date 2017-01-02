package com.nuaa.ai;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

public class TableWindowsFactory implements ActionListener, MouseListener {

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
	private int maxLineATime = 4;
	// ��ǰҳ��;
	private int currentPage = 0;
	// ���е����ݵ��ܵ�����;
	private long totalNum = 0;
	// �ݴ����ÿһ�е�ֵ;
	private List<String> list;
	// ��ѯ���;
	private List<?> sqlList;
	// ���õ���;
	private Object object;
	// �������������;
	private TextField text;
	// ��������;
	private String searchCondition = null;
	// ����������;
	private List<Integer> specialrow = new ArrayList<>();
	// ��ͷ;
	private String[] TableTitles;
	// ��Ҫɾ��������;
	private List<Integer> delList = new ArrayList<Integer>();
	// ����ѡ���;
	private Choice choice;
	//����ʵ����ĳ�Ա����������,���ں��������ʱ�����²�ѯ;
	private List<String> entryClassMemberName=new ArrayList<String>();
	//��¼entryClassMemberName �Ƿ��ѱ���ʼ����;
	private Boolean isentryClassMemberName=false;
	//��¼���յڼ�������;
	private int OrderByNum=0;
	//��¼�����������������;
	private int[] orderDirection=new int[20];
	// ���캯��;
	/**
	 * TableTitle ������; TableTitles ��ͷ; objectList ���е�һҳ������; formerObject
	 * ʵ����������Ǹ���,�����ص���ȡ���µ�����; totalNum ���е���������;
	 * 
	 */
	public TableWindowsFactory(String TableTitle, String[] TableTitles, List<?> objectList, Object formerObject,
			long totalNum,int maxLineATime) {
		frame = new JFrame(TableTitle);
		model = new MyTableModel(TableTitles, 20);
		table = new JTable(model);

		object = formerObject;
		sqlList = objectList;
		this.totalNum = totalNum;
		this.TableTitles = TableTitles;
		this.maxLineATime=maxLineATime;

		// ��ʼ����ҳ����;
		for (int i = 0; i < sqlList.size(); i++) {
			try {
				list = getObjectValue(sqlList.get(i), i);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			model.addRow(list);
		}

		//��ʼ������ķ���;
		for(int i=0;i<TableTitle.length();i++){
			orderDirection[i]=0;
		}
		
		// ���õ�һ���кŵĿ��Ϊ�̶�ֵ;
		TableColumn firsetColumn = table.getColumnModel().getColumn(0);
		firsetColumn.setPreferredWidth(30);
		firsetColumn.setMaxWidth(30);
		firsetColumn.setMinWidth(30);

		// �������һ���кŵĿ��Ϊ�̶�ֵ;
		TableColumn lastColumn = table.getColumnModel().getColumn(TableTitles.length - 1);
		lastColumn.setPreferredWidth(50);
		lastColumn.setMaxWidth(50);
		lastColumn.setMinWidth(50);

		// ���Ӳ�������,���Ա���ж���������ʾ�������ɫ;
		// specialrow.add(0);

		// ����ѡ�е��е���ɫ;
		table.setSelectionBackground(new Color(189, 252, 201));
		// ��񱳾�ɫ
		// table.setBackground(Color.yellow);
		// ָ��ÿһ�е��и�50;
		table.setRowHeight(50);
		// table.setRowHeight(2, 30);//ָ��2�еĸ߶�30

		// �����������ɫ;
		makeFace(table, specialrow);

		// ��ӱ��Ԫ�����¼�;
		table.addMouseListener(this);

		//��ӱ�ͷ����¼�;
		//��������ӵ�ԭ����,�º͵�Ԫ�񵥻��¼�������ͻ;����table������TableHeader �޷�����ActionCommond;
        table.getTableHeader().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                //table.setRowSelectionAllowed(true);
            	OrderByNum=table.columnAtPoint(e.getPoint());
                System.out.println("TableHeader Click"+table.columnAtPoint(e.getPoint()));
                //���·���;
                orderDirection[OrderByNum]=(orderDirection[OrderByNum]+1)%2;
                //���±������;
                try {
					updateTable();
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
        });
		
		
		// �������봰����;
		s_pan = new JScrollPane(table);
		frame.add(s_pan);

		// ��Ӱ�ť��ҳ����Ϣ;
		addBotton();

		// ���������;
		addSearch();

		// ���ô��ڴ�С;
		frame.setSize(600, 400);

		// ����JFrame ���ڹر��¼�;
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		// ����JFrame ���ڳ���λ��;������������ʾ����Ļ�м�;
		frame.setLocationRelativeTo(null);
	}

	// ��ʾ����;
	public void showTable() {
		frame.setVisible(true);
	}

	// Ϊ�����ɫ;
	private void makeFace(JTable table, List<Integer> specialrows) {
		try {
			DefaultTableCellRenderer tcr = new DefaultTableCellRenderer() {
				private static final long serialVersionUID = -3876410206046533481L;

				List<Integer> specialrow = specialrows;

				public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
						boolean hasFocus, int row, int column) {
					// ����cell �е����ݾ�����ʾ;
					setHorizontalAlignment((int) 0.5f);

					// �����������е���ɫ��ͬ,�����û��鿴;
					if (row % 2 == 0) {
						setBackground(Color.white); // ���������е�ɫ
					} else if (row % 2 == 1) {
						setBackground(Color.GRAY); // ����ż���е�ɫ
					}
					// ����;
					// ��ǰ��Ԫ����������ֶ�;��ȥ�˱�ź͸�ѡ���ֶ�;
					// ��������������ǿյĻ�,ֱ����ʾ,�����������ɫ;
					if (searchCondition != null && !searchCondition.isEmpty() 
							&& column != 0&& column != TableTitles.length 
							&& value.toString().contains(searchCondition)) {
						// ��ѡ����;
						// System.out.println(""+choice.getSelectedIndex()+"
						// "+column);
						if (choice.getSelectedIndex() == 0 || choice.getSelectedIndex() == column)
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
			// �����ﲻ�����һ��,�����ʼ�ķ����������óɿ�ѡ�ĸ�ѡ��,����������û����ɫ��.
			for (int i = 0; i < table.getColumnCount() - 1; i++) {
				table.getColumn(table.getColumnName(i)).setCellRenderer(tcr);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	// ��Ӱ�ť��ҳ��;
	private void addBotton() {
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
		if (totalNum % maxLineATime == 0)
			pageLabel.setText("" + (currentPage + 1) + "/" + (totalNum / maxLineATime));
		else
			pageLabel.setText("" + (currentPage + 1) + "/" + (totalNum / maxLineATime + 1));
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

		// ���ֻ��һ��,ҲҪ������һ�а�ť������;
		if ((currentPage + 1) * maxLineATime >= totalNum)
			nextPagebutton.setEnabled(false);
	}

	//������������,������ť,����ѡ���,ɾ����ť;
	private void addSearch() {
		// ����JPanel;
		JPanel searchPanel = new JPanel();
		searchPanel.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));

		// ��������;
		text = new TextField();
		// text.setBounds(0, 0, 200, 5);
		searchPanel.add(text);

		// ���������ť;
		JButton searchButton = new JButton("Search");
		searchButton.setActionCommand("Search");
		searchButton.addActionListener(this);
		searchPanel.add(searchButton);

		// �������ѡ���;
		choice = new Choice();
		choice.add("������");
		// ����ӵ�һ�к����һ��;(���е�һ��Ϊ���,���һ��Ϊ��ѡ��);
		for (int i = 1; i < TableTitles.length - 1; i++) {
			choice.add(TableTitles[i]);
		}
		searchPanel.add(choice);
		// choice.getSelectedItem()
		// choice.getSelectedIndex()
		// ���ɾ����ť;
		JButton delButton = new JButton("Delect");
		delButton.setActionCommand("delect");
		delButton.addActionListener(this);
		searchPanel.add(delButton, BorderLayout.WEST);

		// ��JPanel ��ӵ�������;
		frame.add(searchPanel, BorderLayout.NORTH);
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
		} else if (e.getActionCommand().equals("Search")) {
			System.out.println("�������������");
			searchCondition = text.getText();
			// �����������ɫ;
			makeFace(table, specialrow);
			try {
				updateTable();
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else if (e.getActionCommand().equals("delect")) {
			System.out.println("���������ɾ������");

			//���һ��ȷ�Ͽ�;
			int option=JOptionPane.showConfirmDialog( null , "��ȷ��Ҫɾ����ѡ���������" , "��ʾ", JOptionPane.YES_NO_OPTION ) ;
			if(option==0){
				// ɾ��delList�еĶ�Ӧ��sqlList�еĶ���;
				for (int i = 0; i < delList.size(); i++) {
					MyHibernate.sqlDelete(sqlList.get(delList.get(i)));
					System.out.println("" + delList.get(i));
				}
	
				// ���±��;
				try {
					updateTable();
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}			
		}
	}

	// ��Ԫ�����¼�;
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		// ��ȡ����;
		int[] columns = table.getSelectedColumns();
		int column = columns[0];
		int row = table.getSelectedRow();
		//System.out.println("!!!!!!!!!!"+row+" "+column);
		// �ı临ѡ���ѡ��״̬,����ӻ�ɾ��delList�е�����;
		if (column == TableTitles.length - 1) {
			// ѡ��->δѡ��;
			if ((Boolean) table.getValueAt(row, column)) {
				table.setValueAt(false, row, column);

				for (int i = 0; i < delList.size(); i++) {
					if (delList.get(i) == row)
						delList.remove(i);
				}

			}
			// δѡ��->ѡ��;
			else {
				table.setValueAt(true, row, column);
				delList.add(row);
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
		// �洢˳��;
		if(OrderByNum==0||OrderByNum==TableTitles.length-1){
			try {
				Method m = (Method) object.getClass().getMethod("update", int.class, int.class);
				sqlList = (List<?>) m.invoke(object, currentPage * maxLineATime, maxLineATime);
			} catch (NoSuchMethodException | SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//�Զ���˳��;
		else{
			try {
				Method m = (Method) object.getClass().getMethod("update", int.class, int.class,int.class,String.class);
				sqlList = (List<?>) m.invoke(object, currentPage * maxLineATime, maxLineATime,orderDirection[OrderByNum],entryClassMemberName.get(OrderByNum-1));
			} catch (NoSuchMethodException | SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		
		// ��modelд���µ�����;
		for (int i = 0; i < sqlList.size(); i++) {
			try {
				list = getObjectValue(sqlList.get(i), i);
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
		if (totalNum % maxLineATime == 0)
			pageLabel.setText("" + (currentPage + 1) + "/" + (totalNum / maxLineATime));
		else
			pageLabel.setText("" + (currentPage + 1) + "/" + (totalNum / maxLineATime + 1));
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

	// ͨ�����ػ�ȡObject �ľ���ֵ;
	private List<String> getObjectValue(Object object, int i) throws Exception {
		List<String> list = new ArrayList<>();
		if (object != null) {

			// ��ӱ��;
			list.add("" + (i + 1 + currentPage * maxLineATime));

			// �õ�����
			Class<?> clz = object.getClass();
			// ��ȡʵ������������ԣ�����Field����
			Field[] fields = clz.getDeclaredFields();

			for (Field field : fields) {
				// System.out.println(field.getGenericType());// ��ӡ�����������������
				//��¼ÿһ�����Ե�����,����������������ѯ;
				if(!isentryClassMemberName){
					entryClassMemberName.add(field.getName());
				}
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
						// System.out.println("String type:" + val);
						list.add(val);
					} else {
						list.add("");
					}

				}

				// ���������Integer
				if (field.getGenericType().toString().equals("class java.lang.Integer")) {
					Method m = (Method) object.getClass().getMethod("get" + getMethodName(field.getName()));
					Integer val = (Integer) m.invoke(object);
					if (val != null) {
						// System.out.println("Integer type:" + val);
						list.add(val.toString());
					} else {
						list.add("");
					}

				}

				// ���������Double
				if (field.getGenericType().toString().equals("class java.lang.Double")) {
					Method m = (Method) object.getClass().getMethod("get" + getMethodName(field.getName()));
					Double val = (Double) m.invoke(object);
					if (val != null) {
						// System.out.println("Double type:" + val);
						list.add(val.toString());
					} else {
						list.add("");
					}

				}

				// ���������Boolean �Ƿ�װ��
				if (field.getGenericType().toString().equals("class java.lang.Boolean")) {
					Method m = (Method) object.getClass().getMethod(field.getName());
					Boolean val = (Boolean) m.invoke(object);
					if (val != null) {
						// System.out.println("Boolean type:" + val);
						list.add(val.toString());
					} else {
						list.add("");
					}

				}

				// ���������boolean �����������Ͳ�һ�� �����е�˵������������� isXXX�� �Ǿ�ȫ����isXXX��
				// �����Ҳ���getter�ľ�����
				if (field.getGenericType().toString().equals("boolean")) {
					Method m = (Method) object.getClass().getMethod(field.getName());
					Boolean val = (Boolean) m.invoke(object);
					if (val != null) {
						// System.out.println("boolean type:" + val);
						list.add(val.toString());
					} else {
						list.add("");
					}

				}
				// ���������Date
				if (field.getGenericType().toString().equals("class java.util.Date")) {
					Method m = (Method) object.getClass().getMethod("get" + getMethodName(field.getName()));
					Date val = (Date) m.invoke(object);
					if (val != null) {
						// System.out.println("Date type:" + val);
						list.add(val.toString());
					} else {
						list.add("");
					}

				}
				// ���������Short
				if (field.getGenericType().toString().equals("class java.lang.Short")) {
					Method m = (Method) object.getClass().getMethod("get" + getMethodName(field.getName()));
					Short val = (Short) m.invoke(object);
					if (val != null) {
						// System.out.println("Short type:" + val);
						list.add(val.toString());
					} else {
						list.add("");
					}
				}
				// �������Ҫ�������������Լ�����չ
			}
			isentryClassMemberName=true;
		} else {
			System.err.println("object is null");
		}
		return list;
	}

	// ��һ���ַ����ĵ�һ����ĸ��д;
	private static String getMethodName(String fildeName) throws Exception {
		if (fildeName.charAt(0) >= 'a' && fildeName.charAt(0) <= 'z') {
			byte[] items = fildeName.getBytes();
			items[0] = (byte) ((char) items[0] - 'a' + 'A');
			return new String(items);
		} else {
			return fildeName;
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
