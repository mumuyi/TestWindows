package com.nuaa.ai;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class UserTableWindows implements ActionListener {

	private JFrame frame = null;
	private JTable table = null;
	private MyTableModel model = null;
	private JScrollPane s_pan = null;

	// ��ͷ��Ϣ;
	private String[] Titles = { "�û�ID", "�û�����" };

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
	// ���ݿ��ѯ���;
	private List<User> list;

	// ���캯��;
	public UserTableWindows() {
		frame = new JFrame("Table Test");
		model = new MyTableModel(Titles, 20);
		table = new JTable(model);

		// ��ȡ���е���������;
		totalNum = UserHibernate.UserGetRecordNum();

		list = UserHibernate.UserQuery(0, maxLineATime);
		for (int i = 0; i < list.size(); i++) {
			model.addRow(list.get(i).getUserId(), list.get(i).getUserName());
		}

		List<Integer> specialrow = new ArrayList<>();

		// ����ѡ�е��е���ɫ;
		table.setSelectionBackground(new Color(189, 252, 201));
		// ��񱳾�ɫ
		// table.setBackground(Color.yellow);
		// ָ��ÿһ�е��и�50;
		table.setRowHeight(50);
		// jt.setRowHeight(2, 30);//ָ��2�еĸ߶�30

		// ���Ӳ�������,���Ա���ж���������ʾ�������ɫ;
		specialrow.add(0);
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
		pageLabel.setText("" + (currentPage + 1) + "/" + (totalNum / maxLineATime));
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
		// ��ʾ����;
		frame.setVisible(true);

		// ����JFrame ���ڹر��¼�;
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		// ����JFrame ���ڳ���λ��;������������ʾ����Ļ�м�;
		frame.setLocationRelativeTo(null);
	}

	public static void main(String args[]) {
		new UserTableWindows();
	}

	// ����������Կ��ǵ����ó�����Ϊһ�������ķ���;
	public static void makeFace(JTable table, List<Integer> specialrows) {
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
			updateTable();
		} else if (e.getActionCommand().equals("LastPage")) {
			System.out.println("���������Ϸ�ҳ����");
			currentPage -= 1;
			updateTable();
		}
	}

	// ���ݸ��²���;
	private void updateTable() {
		// ɾ��model�е�֮ǰ������;
		for (int i = 0; i < list.size(); i++) {
			// ����ÿ�ζ���ɾ����0��;��Ϊ�ײ�Ĵ�����ɾ��֮��������λ��;����ÿ�ζ�ɾ��һ��,һ��ɾ��list.size()�ξͺ���;
			model.removeRow(0);
		}
		// ���²�ѯ;
		list = UserHibernate.UserQuery(currentPage * maxLineATime, maxLineATime);
		// ��modelд���µ�����;
		for (int i = 0; i < list.size(); i++) {
			model.addRow(list.get(i).getUserId(), list.get(i).getUserName());
		}
		// ˢ�±��;
		table.updateUI();

		// ���°�ť;
		buttonClickableChange();

		// ����ҳ����Ϣ;
		pageLabel.setText("" + (currentPage + 1) + "/" + (totalNum / maxLineATime));
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
}
