package com.nuaa.ai;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class TableTest {

	private JFrame frame = null;
	private JTable table = null;
	private Table_Model model = null;
	private JScrollPane s_pan = null;

	public TableTest() {
		frame = new JFrame("Table Test");

		model = new Table_Model(20);

		table = new JTable(model);

		/*
		 * String[] age = { "21", "22", "23", "24", "25", "26", "27", "28",
		 * "29", "30" }; JComboBox com = new JComboBox(age); TableColumnModel
		 * tcm = table.getColumnModel(); tcm.getColumn(2).setCellEditor(new
		 * DefaultCellEditor(com)); // 设置某列采用JComboBox组件
		 */
		
		
		//Hibernate 查询数据库中的数据;
        Configuration cfg = new Configuration();
        //cfg.configure();可带参数指定配置文件.返回值还是一个configuration 但是其拥有了配置选项;
        SessionFactory sf = cfg.configure().buildSessionFactory();
        //打开session;
        Session session = sf.openSession();
        
        //开始事务;
        session.beginTransaction();
        
        //参数是一个字符串,是HQL的查询语句.注意此时的的UserU为大写,为对象的,而不是表的.
        Query query = session.createQuery("from User");
        //使用List方法.
        List<User> userList = query.list();
        //迭代器去迭代.
        for(Iterator<User> iter=userList.iterator();iter.hasNext();)
        {
           User user =(User)iter.next();
           model.addRow(user.getUserId(),  user.getUserName());
           System.out.println("id="+user.getUserId() + "name="+user.getUserName());
        }
        
        //获取事务并提交;
        session.getTransaction().commit();
        
        session.close();
        sf.close();
        
        //设置选中的行的颜色;
        table.setSelectionBackground(new Color(189, 252, 201));
        // 表格背景色
        //table.setBackground(Color.yellow);
        //指定每一行的行高50;
        table.setRowHeight(50);
        //jt.setRowHeight(2, 30);//指定2行的高度30
        
        makeFace(table);
        
        
		s_pan = new JScrollPane(table);
		frame.getContentPane().add(s_pan, BorderLayout.CENTER);
		frame.setSize(600, 400);
		frame.setVisible(true);
	}

	public static void main(String args[]) {
		new TableTest();
	}
	
	
	public static void makeFace(JTable table) {  
        try {  
            DefaultTableCellRenderer tcr = new DefaultTableCellRenderer() {  
  
                public Component getTableCellRendererComponent(JTable table,  
                        Object value, boolean isSelected, boolean hasFocus,  
                        int row, int column) {  
                    if (row % 2 == 0) {  
                        setBackground(Color.white); //设置奇数行底色  
                    } else if (row % 2 == 1) {  
                        setBackground(new Color(206, 231, 255)); //设置偶数行底色  
                    }  
                    /*
                    if (Double.parseDouble(table.getValueAt(row, 1).toString()) > 0) {  
                        setBackground(Color.red);  
                    } 
                    */                 
                    //如果需要设置某一个Cell颜色，需要加上column过滤条件即可  
                    return super.getTableCellRendererComponent(table, value,  
                            isSelected, hasFocus, row, column);  
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
