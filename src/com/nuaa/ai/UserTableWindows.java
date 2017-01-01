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

	// 表头信息;
	private String[] Titles = { "用户ID", "用户名称" };

	// 翻页按钮;
	private JButton nextPagebutton;
	private JButton lastPagebutton;
	// 页数信息;
	private JLabel pageLabel;

	// 数据库查询时,每次查询的最大数量;
	private int maxLineATime = 2;
	// 当前页数;
	private int currentPage = 0;
	// 表中的数据的总的数量;
	private long totalNum = 0;
	// 数据库查询结果;
	private List<User> list;

	// 构造函数;
	public UserTableWindows() {
		frame = new JFrame("Table Test");
		model = new MyTableModel(Titles, 20);
		table = new JTable(model);

		// 获取表中的数据总数;
		totalNum = UserHibernate.UserGetRecordNum();

		list = UserHibernate.UserQuery(0, maxLineATime);
		for (int i = 0; i < list.size(); i++) {
			model.addRow(list.get(i).getUserId(), list.get(i).getUserName());
		}

		List<Integer> specialrow = new ArrayList<>();

		// 设置选中的行的颜色;
		table.setSelectionBackground(new Color(189, 252, 201));
		// 表格背景色
		// table.setBackground(Color.yellow);
		// 指定每一行的行高50;
		table.setRowHeight(50);
		// jt.setRowHeight(2, 30);//指定2行的高度30

		// 增加测试数据,测试表格中对特殊行显示特殊的颜色;
		specialrow.add(0);
		// 给表格增加颜色;
		makeFace(table, specialrow);

		// 将表格加入窗口中;
		s_pan = new JScrollPane(table);
		frame.add(s_pan);

		// 创建JPanel;
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
		// 添加上一页按钮;
		lastPagebutton = new JButton("Last Page");
		lastPagebutton.setActionCommand("LastPage");
		lastPagebutton.addActionListener(this);
		buttonPanel.add(lastPagebutton, BorderLayout.WEST);
		// 添加页数信息;
		pageLabel = new JLabel();
		pageLabel.setFont(new Font(Font.DIALOG, Font.BOLD, 13));
		pageLabel.setText("" + (currentPage + 1) + "/" + (totalNum / maxLineATime));
		buttonPanel.add(pageLabel, BorderLayout.WEST);
		// 添加下一页按钮;
		nextPagebutton = new JButton("Next Page");
		nextPagebutton.setActionCommand("NextPage");
		nextPagebutton.addActionListener(this);
		buttonPanel.add(nextPagebutton, BorderLayout.EAST);
		// 将JPanel 添加到窗口中;
		frame.add(buttonPanel, BorderLayout.SOUTH);

		// 第一页时设置上一页按钮不可用;
		lastPagebutton.setEnabled(false);

		// 设置窗口大小;
		frame.setSize(600, 400);
		// 显示窗口;
		frame.setVisible(true);

		// 设置JFrame 窗口关闭事件;
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		// 设置JFrame 窗口出现位置;这里设置其显示在屏幕中间;
		frame.setLocationRelativeTo(null);
	}

	public static void main(String args[]) {
		new UserTableWindows();
	}

	// 这个方法可以考虑单独拿出来作为一个公共的方法;
	public static void makeFace(JTable table, List<Integer> specialrows) {
		try {
			DefaultTableCellRenderer tcr = new DefaultTableCellRenderer() {
				private static final long serialVersionUID = -3876410206046533481L;

				List<Integer> specialrow = specialrows;

				public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
						boolean hasFocus, int row, int column) {
					// 设置相邻两行的颜色不同,方便用户查看;
					if (row % 2 == 0) {
						setBackground(Color.white); // 设置奇数行底色
					} else if (row % 2 == 1) {
						setBackground(new Color(206, 231, 255)); // 设置偶数行底色
					}
					// 如果需要设置某一个Cell颜色，需要加上column过滤条件即可
					if (row == 1 && column == 1) {
						setBackground(Color.YELLOW);
					}
					// 设置特定行的颜色;将所有需要特殊化处理的行号加入specialrow
					// 中,之后再判断当前处理的行号是否包含在specialrow 中即可;
					if (specialrow.contains(row)) {
						setBackground(Color.RED);
					}
					// 这是在干什么不是太清楚,但是感觉应该是有用的;
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

	// 鼠标监听事件;
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getActionCommand().equals("NextPage")) {
			System.out.println("在这里向下翻页操作");
			currentPage += 1;
			updateTable();
		} else if (e.getActionCommand().equals("LastPage")) {
			System.out.println("在这里向上翻页操作");
			currentPage -= 1;
			updateTable();
		}
	}

	// 数据更新操作;
	private void updateTable() {
		// 删除model中的之前的数据;
		for (int i = 0; i < list.size(); i++) {
			// 这里每次都是删除第0个;因为底层的代码在删除之后做了移位了;所以每次都删第一个,一共删除list.size()次就好了;
			model.removeRow(0);
		}
		// 重新查询;
		list = UserHibernate.UserQuery(currentPage * maxLineATime, maxLineATime);
		// 给model写入新的数据;
		for (int i = 0; i < list.size(); i++) {
			model.addRow(list.get(i).getUserId(), list.get(i).getUserName());
		}
		// 刷新表格;
		table.updateUI();

		// 更新按钮;
		buttonClickableChange();

		// 更新页数信息;
		pageLabel.setText("" + (currentPage + 1) + "/" + (totalNum / maxLineATime));
	}

	// 设置翻页按钮是否可点击;
	private void buttonClickableChange() {
		// 下一页;
		if ((currentPage + 1) * maxLineATime >= totalNum) {
			nextPagebutton.setEnabled(false);
		} else {
			nextPagebutton.setEnabled(true);

		}

		// 上一页;
		if (currentPage == 0) {
			lastPagebutton.setEnabled(false);
		} else {
			lastPagebutton.setEnabled(true);
		}
	}
}
