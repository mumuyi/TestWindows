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

	// 翻页按钮;
	private JButton nextPagebutton;
	private JButton lastPagebutton;
	// 页数信息标签;
	private JLabel pageLabel;
	// 搜索条件输入框;
	private TextField text;
	// 下拉选择框;
	private Choice choice;
	
	// 暂存表中每一列的值;
	private List<String> list;
	// 查询结果;
	private List<?> sqlList;
	// 特殊行数据;
	private List<Integer> specialrow = new ArrayList<>();
	// 需要删除的数据;
	private List<Integer> delList = new ArrayList<Integer>();
	// 具体实体类的成员变量的名字,用于后面的排序时的重新查询;
	private List<String> entryClassMemberName = new ArrayList<String>();

	// 表头;
	private String[] TableTitles;

	// 记录表头名称;
	private String TableTitle = "Test Table";
	// 具体实体类的名字;
	private String EntityClassName;
	// 搜索条件;
	private String searchCondition = null;
	
	// 表中的数据的总的数量;
	private long totalNum = 0;
	// 记录正向排序或逆向排序;
	private int[] orderDirection = new int[22];
	//窗口的宽;
	private int TableWindowsWidth=600;
	//窗口的高;
	private int TableWindowsHeight=400;
	// 数据库查询时,每次查询的最大数量;
	private int maxLineATime = 4;
	// 当前页数;
	private int currentPage = 0;
	// 记录按照第几个排序;
	private int OrderByNum = 0;
	// 构造函数;
	/**
	 * TableTitle 窗口名; TableTitles 表头; objectList 表中第一页的数据; formerObject
	 * 实例化本类的那个类,用来回调获取更新的数据; totalNum 表中的数据总数;
	 * 
	 */
	public TableWindowsFactory(String entityClassName,String [] tableTitles) {
		EntityClassName=entityClassName;
		TableTitles=new String[tableTitles.length+2];
		TableTitles[0]="编号";
		for(int i=0;i<tableTitles.length;i++)
			TableTitles[i+1] = tableTitles[i];
		TableTitles[tableTitles.length+1]="选择";
	}

	// 显示窗口;
	public Boolean showTable() {
		//判断类名是否为空;
		if(EntityClassName==null){
			JOptionPane.showMessageDialog(null,"查询失败", "具体实体类名为空",JOptionPane.ERROR_MESSAGE);
			return false;
		}else{
			//初始化数据;
			iniDate();
			// 初始化Frame;
			iniFrame();
			// 初始化表格;
			iniTable();
			// 添加按钮和页数信息;
			addBotton();
			// 添加搜索框;
			addSearch();
			//显示窗口;
			frame.setVisible(true);
			return true;
		}
	}
	
	//初始化首页数据;
	private void iniDate(){
		sqlList=MyHibernate.sqlQuery(0, maxLineATime,"from "+EntityClassName);
		totalNum=MyHibernate.sqlGetRecordNum("select count(*) from "+EntityClassName);
		//无数据;
		if(sqlList.size()==0){
			JOptionPane.showMessageDialog(null,"查询失败", "表中数据为空",JOptionPane.ERROR_MESSAGE);
			return;
		}
		entryClassMemberName=MyObjectUtils.getAttributeName(sqlList.get(0));
	}
	//初始化Frame;
	private void iniFrame() {
		frame = new JFrame(TableTitle);
		// 设置窗口大小;
		frame.setSize(TableWindowsWidth, TableWindowsHeight);

		// 设置JFrame 窗口关闭事件;
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		// 设置JFrame 窗口出现位置;这里设置其显示在屏幕中间;
		frame.setLocationRelativeTo(null);
	}
	//初始化Table;
	private void iniTable() {
		model = new MyTableModel(TableTitles, 22);
		table = new JTable(model);

		// 初始化首页数据;
		for (int i = 0; i < sqlList.size(); i++) {
			try {
				// 添加编号;
				list=new ArrayList<String>();
				list.add("" + (i + 1 + currentPage * maxLineATime));
				//获取一条数据;
				MyObjectUtils.getObjectValue(sqlList.get(i),list);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			model.addRow(list);
		}

		// 初始化排序的方向;
		for (int i = 0; i < TableTitles.length; i++) {
			orderDirection[i] = 0;
		}

		// 设置第一列行号的宽度为固定值;
		TableColumn firsetColumn = table.getColumnModel().getColumn(0);
		firsetColumn.setPreferredWidth(30);
		firsetColumn.setMaxWidth(30);
		firsetColumn.setMinWidth(30);

		// 设置最后一列行号的宽度为固定值;
		TableColumn lastColumn = table.getColumnModel().getColumn(TableTitles.length - 1);
		lastColumn.setPreferredWidth(50);
		lastColumn.setMaxWidth(50);
		lastColumn.setMinWidth(50);

		// 增加测试数据,测试表格中对特殊行显示特殊的颜色;
		// specialrow.add(0);

		// 设置选中的行的颜色;
		table.setSelectionBackground(new Color(189, 252, 201));
		// 表格背景色
		// table.setBackground(Color.yellow);
		// 指定每一行的行高50;
		table.setRowHeight(50);
		// table.setRowHeight(2, 30);//指定2行的高度30

		// 给表格增加颜色;
		makeFace(table, specialrow);

		// 添加表格单元格点击事件;
		table.addMouseListener(this);

		// 添加表头点击事件;
		// 在这里添加的原因是,怕和单元格单击事件发生冲突;而且table或者是TableHeader 无法设置ActionCommond;
		table.getTableHeader().addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				// table.setRowSelectionAllowed(true);
				OrderByNum = table.columnAtPoint(e.getPoint());
				System.out.println("TableHeader Click" + table.columnAtPoint(e.getPoint()));
				// 更新方向;
				orderDirection[OrderByNum] = (orderDirection[OrderByNum] + 1) % 2;
				// 更新表格数据;
				try {
					updateTable();
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		// 将表格加入窗口中;
		s_pan = new JScrollPane(table);
		frame.add(s_pan);
	}
	// 为表格上色;
	private void makeFace(JTable table, List<Integer> specialrows) {
		try {
			DefaultTableCellRenderer tcr = new DefaultTableCellRenderer() {
				private static final long serialVersionUID = -3876410206046533481L;

				List<Integer> specialrow = specialrows;

				public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
						boolean hasFocus, int row, int column) {
					// 设置cell 中的数据居中显示;
					setHorizontalAlignment((int) 0.5f);

					// 设置相邻两行的颜色不同,方便用户查看;
					if (row % 2 == 0) {
						setBackground(Color.white); // 设置奇数行底色
					} else if (row % 2 == 1) {
						setBackground(Color.GRAY); // 设置偶数行底色
					}
					// 搜索;
					// 当前单元格包含所有字段;除去了编号和复选框字段;
					// 如果搜索的条件是空的话,直接显示,不上特殊的颜色;
					if (searchCondition != null && !searchCondition.isEmpty() && column != 0
							&& column != TableTitles.length && value.toString().contains(searchCondition)) {
						// 列选条件;
						// System.out.println(""+choice.getSelectedIndex()+"
						// "+column);
						if (choice.getSelectedIndex() == 0 || choice.getSelectedIndex() == column)
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
			// 在这里不做最后一项,在用最开始的方法即可设置成可选的复选框,但是依旧是没有上色的.
			for (int i = 0; i < table.getColumnCount() - 1; i++) {
				table.getColumn(table.getColumnName(i)).setCellRenderer(tcr);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	// 添加按钮和页数;
	private void addBotton() {
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
		if (totalNum % maxLineATime == 0)
			pageLabel.setText("" + (currentPage + 1) + "/" + (totalNum / maxLineATime));
		else
			pageLabel.setText("" + (currentPage + 1) + "/" + (totalNum / maxLineATime + 1));
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

		// 如果只有一列,也要设置下一列按钮不可用;
		if ((currentPage + 1) * maxLineATime >= totalNum)
			nextPagebutton.setEnabled(false);
	}

	// 添加搜索输入框,搜索按钮,下拉选择框,删除按钮;
	private void addSearch() {
		// 创建JPanel;
		JPanel searchPanel = new JPanel();
		searchPanel.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));

		// 添加输入框;
		text = new TextField();
		// text.setBounds(0, 0, 200, 5);
		searchPanel.add(text);

		// 添加搜索按钮;
		JButton searchButton = new JButton("Search");
		searchButton.setActionCommand("Search");
		searchButton.addActionListener(this);
		searchPanel.add(searchButton);

		// 添加下拉选择框;
		choice = new Choice();
		choice.add("所有列");
		// 不添加第一列和最后一列;(其中第一列为编号,最后一列为复选框);
		for (int i = 1; i < TableTitles.length - 1; i++) {
			choice.add(TableTitles[i]);
		}
		searchPanel.add(choice);
		// choice.getSelectedItem()
		// choice.getSelectedIndex()
		// 添加删除按钮;
		JButton delButton = new JButton("Delect");
		delButton.setActionCommand("delect");
		delButton.addActionListener(this);
		searchPanel.add(delButton, BorderLayout.WEST);

		// 将JPanel 添加到窗口中;
		frame.add(searchPanel, BorderLayout.NORTH);
	}

	// 鼠标监听事件;
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getActionCommand().equals("NextPage")) {
			System.out.println("在这里向下翻页操作");
			currentPage += 1;
			try {
				updateTable();
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else if (e.getActionCommand().equals("LastPage")) {
			System.out.println("在这里向上翻页操作");
			currentPage -= 1;
			try {
				updateTable();
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else if (e.getActionCommand().equals("Search")) {
			System.out.println("在这里进行搜索");
			searchCondition = text.getText();
			// 给表格增加颜色;
			makeFace(table, specialrow);
			try {
				updateTable();
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else if (e.getActionCommand().equals("delect")) {
			System.out.println("在这里进行删除操作");

			// 添加一个确认框;
			int option = JOptionPane.showConfirmDialog(null, "确定删除选择的 " + delList.size() + " 条数据吗", "提示",
					JOptionPane.YES_NO_OPTION);
			if (option == 0) {
				// 删除delList中的对应的sqlList中的对象;
				for (int i = 0; i < delList.size(); i++) {
					MyHibernate.sqlDelete(sqlList.get(delList.get(i)));
					System.out.println("" + delList.get(i));
				}

				// 更新表格;
				try {
					updateTable();
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}

	// 单元格点击事件;
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		// 获取行列;
		int[] columns = table.getSelectedColumns();
		int column = columns[0];
		int row = table.getSelectedRow();
		// System.out.println("!!!!!!!!!!"+row+" "+column);
		// 改变复选框的选择状态,并添加或删除delList中的数据;
		if (column == TableTitles.length - 1) {
			// 选中->未选中;
			if ((Boolean) table.getValueAt(row, column)) {
				table.setValueAt(false, row, column);

				for (int i = 0; i < delList.size(); i++) {
					if (delList.get(i) == row)
						delList.remove(i);
				}

			}
			// 未选中->选中;
			else {
				table.setValueAt(true, row, column);
				delList.add(row);
			}
		}
	}

	// 数据更新操作;
	private void updateTable() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		// 删除model中的之前的数据;
		for (int i = 0; i < sqlList.size(); i++) {
			// 这里每次都是删除第0个;因为底层的代码在删除之后做了移位了;所以每次都删第一个,一共删除list.size()次就好了;
			model.removeRow(0);
		}

		// 重新查询;
		// 存储顺序;
		if (OrderByNum == 0 || OrderByNum == TableTitles.length - 1) {
			sqlList = update(currentPage * maxLineATime, maxLineATime);
		}
		// 自定义顺序;
		else {
			sqlList = update(currentPage * maxLineATime, maxLineATime, orderDirection[OrderByNum],
					entryClassMemberName.get(OrderByNum - 1));
		}

		// 给model写入新的数据;
		for (int i = 0; i < sqlList.size(); i++) {
			try {
				// 添加编号;
				list=new ArrayList<String>();
				list.add("" + (i + 1 + currentPage * maxLineATime));
				//获取一条数据;
				MyObjectUtils.getObjectValue(sqlList.get(i),list);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			model.addRow(list);
		}
		// 刷新表格;
		table.updateUI();

		// 更新按钮;
		buttonClickableChange();

		// 更新页数信息;
		if (totalNum % maxLineATime == 0)
			pageLabel.setText("" + (currentPage + 1) + "/" + (totalNum / maxLineATime));
		else
			pageLabel.setText("" + (currentPage + 1) + "/" + (totalNum / maxLineATime + 1));
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

	

	//数据库查询操作;
	public List<?> update(int from, int maxLineATime) {
		return MyHibernate.sqlQuery(from, maxLineATime, "from " + EntityClassName);
	}
	//带排序的数据库查询操作;
	public List<?> update(int from, int maxLineATime, int upordown, String orderBy) {
		if (upordown == 0)
			return MyHibernate.sqlQuery(from, maxLineATime,
					"from " + EntityClassName + " order by " + orderBy + " asc");
		else
			return MyHibernate.sqlQuery(from, maxLineATime,
					"from " + EntityClassName + " order by " + orderBy + " desc");
	}

	//获取窗口的标题;
	public String getTableTitle() {
		return TableTitle;
	}
	//设置窗口的标题;	
	public void setTableTitle(String tableTitle) {
		TableTitle = tableTitle;
	}
	//设置窗口的大小;
	public void setTableWindowSize(int width,int height){
		TableWindowsWidth=width;
		TableWindowsHeight=height;
	}
	//获取每次查询最大条数,也是每一页显示条数;
	public int getMaxLineATime() {
		return maxLineATime;
	}
	//设置每次查询最大条数,也是每一页显示条数;
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
