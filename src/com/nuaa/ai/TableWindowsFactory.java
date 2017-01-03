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
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
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
	// ҳ����Ϣ��ǩ;
	private JLabel pageLabel;
	// �������������;
	private TextField text;
	// ����ѡ���;
	private Choice choice;
	
	// �ݴ����ÿһ�е�ֵ;
	private List<String> list;
	// ��ѯ���;
	private List<?> sqlList;
	// ����������;
	private List<Integer> specialrow = new ArrayList<>();
	// ��Ҫɾ��������;
	private List<Integer> delList = new ArrayList<Integer>();
	// ����ʵ����ĳ�Ա����������,���ں��������ʱ�����²�ѯ;
	private List<String> entryClassMemberName = new ArrayList<String>();

	// ��ͷ;
	private String[] TableTitles;

	// ��¼��ͷ����;
	private String TableTitle = "Test Table";
	// ����ʵ���������;
	private String EntityClassName;
	// ��������;
	private String searchCondition = null;
	
	// ���е����ݵ��ܵ�����;
	private long totalNum = 0;
	// ��¼�����������������;
	private int[] orderDirection = new int[22];
	//���ڵĿ�;
	private int TableWindowsWidth=600;
	//���ڵĸ�;
	private int TableWindowsHeight=400;
	// ���ݿ��ѯʱ,ÿ�β�ѯ���������;
	private int maxLineATime = 4;
	// ��ǰҳ��;
	private int currentPage = 0;
	// ��¼���յڼ�������;
	private int OrderByNum = 0;
	// ���캯��;
	/**
	 * TableTitle ������; TableTitles ��ͷ; objectList ���е�һҳ������; formerObject
	 * ʵ����������Ǹ���,�����ص���ȡ���µ�����; totalNum ���е���������;
	 * 
	 */
	public TableWindowsFactory(String entityClassName,String [] tableTitles) {
		EntityClassName=entityClassName;
		TableTitles=new String[tableTitles.length+2];
		TableTitles[0]="���";
		for(int i=0;i<tableTitles.length;i++)
			TableTitles[i+1] = tableTitles[i];
		TableTitles[tableTitles.length+1]="ѡ��";
	}

	// ��ʾ����;
	public Boolean showTable() {
		//�ж������Ƿ�Ϊ��;
		if(EntityClassName==null){
			JOptionPane.showMessageDialog(null,"��ѯʧ��", "����ʵ������Ϊ��",JOptionPane.ERROR_MESSAGE);
			return false;
		}else{
			//��ʼ������;
			iniDate();
			// ��ʼ��Frame;
			iniFrame();
			// ��ʼ�����;
			iniTable();
			// ��Ӱ�ť��ҳ����Ϣ;
			addBotton();
			// ���������;
			addSearch();
			//��ʾ����;
			frame.setVisible(true);
			return true;
		}
	}
	
	//��ʼ����ҳ����;
	private void iniDate(){
		sqlList=MyHibernate.sqlQuery(0, maxLineATime,"from "+EntityClassName);
		totalNum=MyHibernate.sqlGetRecordNum("select count(*) from "+EntityClassName);
		//������;
		if(sqlList.size()==0){
			JOptionPane.showMessageDialog(null,"��ѯʧ��", "��������Ϊ��",JOptionPane.ERROR_MESSAGE);
			return;
		}
		entryClassMemberName=MyObjectUtils.getAttributeName(sqlList.get(0));
	}
	//��ʼ��Frame;
	private void iniFrame() {
		frame = new JFrame(TableTitle);
		// ���ô��ڴ�С;
		frame.setSize(TableWindowsWidth, TableWindowsHeight);

		// ����JFrame ���ڹر��¼�;
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		// ����JFrame ���ڳ���λ��;������������ʾ����Ļ�м�;
		frame.setLocationRelativeTo(null);
	}
	//��ʼ��Table;
	private void iniTable() {
		model = new MyTableModel(TableTitles, 22);
		table = new JTable(model);

		// ��ʼ����ҳ����;
		for (int i = 0; i < sqlList.size(); i++) {
			try {
				// ��ӱ��;
				list=new ArrayList<String>();
				list.add("" + (i + 1 + currentPage * maxLineATime));
				//��ȡһ������;
				MyObjectUtils.getObjectValue(sqlList.get(i),list);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			model.addRow(list);
		}

		// ��ʼ������ķ���;
		for (int i = 0; i < TableTitles.length; i++) {
			orderDirection[i] = 0;
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

		// ��ӱ�ͷ����¼�;
		// ��������ӵ�ԭ����,�º͵�Ԫ�񵥻��¼�������ͻ;����table������TableHeader �޷�����ActionCommond;
		table.getTableHeader().addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				// table.setRowSelectionAllowed(true);
				OrderByNum = table.columnAtPoint(e.getPoint());
				System.out.println("TableHeader Click" + table.columnAtPoint(e.getPoint()));
				// ���·���;
				orderDirection[OrderByNum] = (orderDirection[OrderByNum] + 1) % 2;
				// ���±������;
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
					if (searchCondition != null && !searchCondition.isEmpty() && column != 0
							&& column != TableTitles.length && value.toString().contains(searchCondition)) {
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

	// ������������,������ť,����ѡ���,ɾ����ť;
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

			// ���һ��ȷ�Ͽ�;
			int option = JOptionPane.showConfirmDialog(null, "ȷ��ɾ��ѡ��� " + delList.size() + " ��������", "��ʾ",
					JOptionPane.YES_NO_OPTION);
			if (option == 0) {
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
		// System.out.println("!!!!!!!!!!"+row+" "+column);
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
		if (OrderByNum == 0 || OrderByNum == TableTitles.length - 1) {
			sqlList = update(currentPage * maxLineATime, maxLineATime);
		}
		// �Զ���˳��;
		else {
			sqlList = update(currentPage * maxLineATime, maxLineATime, orderDirection[OrderByNum],
					entryClassMemberName.get(OrderByNum - 1));
		}

		// ��modelд���µ�����;
		for (int i = 0; i < sqlList.size(); i++) {
			try {
				// ��ӱ��;
				list=new ArrayList<String>();
				list.add("" + (i + 1 + currentPage * maxLineATime));
				//��ȡһ������;
				MyObjectUtils.getObjectValue(sqlList.get(i),list);
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

	

	//���ݿ��ѯ����;
	public List<?> update(int from, int maxLineATime) {
		return MyHibernate.sqlQuery(from, maxLineATime, "from " + EntityClassName);
	}
	//����������ݿ��ѯ����;
	public List<?> update(int from, int maxLineATime, int upordown, String orderBy) {
		if (upordown == 0)
			return MyHibernate.sqlQuery(from, maxLineATime,
					"from " + EntityClassName + " order by " + orderBy + " asc");
		else
			return MyHibernate.sqlQuery(from, maxLineATime,
					"from " + EntityClassName + " order by " + orderBy + " desc");
	}

	//��ȡ���ڵı���;
	public String getTableTitle() {
		return TableTitle;
	}
	//���ô��ڵı���;	
	public void setTableTitle(String tableTitle) {
		TableTitle = tableTitle;
	}
	//���ô��ڵĴ�С;
	public void setTableWindowSize(int width,int height){
		TableWindowsWidth=width;
		TableWindowsHeight=height;
	}
	//��ȡÿ�β�ѯ�������,Ҳ��ÿһҳ��ʾ����;
	public int getMaxLineATime() {
		return maxLineATime;
	}
	//����ÿ�β�ѯ�������,Ҳ��ÿһҳ��ʾ����;
	public void setMaxLineATime(int maxLineATime) {
		this.maxLineATime = maxLineATime;
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
