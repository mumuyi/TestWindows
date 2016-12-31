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
		 * DefaultCellEditor(com)); // ����ĳ�в���JComboBox���
		 */
		
		
		//Hibernate ��ѯ���ݿ��е�����;
        Configuration cfg = new Configuration();
        //cfg.configure();�ɴ�����ָ�������ļ�.����ֵ����һ��configuration ������ӵ��������ѡ��;
        SessionFactory sf = cfg.configure().buildSessionFactory();
        //��session;
        Session session = sf.openSession();
        
        //��ʼ����;
        session.beginTransaction();
        
        //������һ���ַ���,��HQL�Ĳ�ѯ���.ע���ʱ�ĵ�UserUΪ��д,Ϊ�����,�����Ǳ��.
        Query query = session.createQuery("from User");
        //ʹ��List����.
        List<User> userList = query.list();
        //������ȥ����.
        for(Iterator<User> iter=userList.iterator();iter.hasNext();)
        {
           User user =(User)iter.next();
           model.addRow(user.getUserId(),  user.getUserName());
           System.out.println("id="+user.getUserId() + "name="+user.getUserName());
        }
        
        //��ȡ�����ύ;
        session.getTransaction().commit();
        
        session.close();
        sf.close();
        
        //����ѡ�е��е���ɫ;
        table.setSelectionBackground(new Color(189, 252, 201));
        // ��񱳾�ɫ
        //table.setBackground(Color.yellow);
        //ָ��ÿһ�е��и�50;
        table.setRowHeight(50);
        //jt.setRowHeight(2, 30);//ָ��2�еĸ߶�30
        
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
                        setBackground(Color.white); //���������е�ɫ  
                    } else if (row % 2 == 1) {  
                        setBackground(new Color(206, 231, 255)); //����ż���е�ɫ  
                    }  
                    /*
                    if (Double.parseDouble(table.getValueAt(row, 1).toString()) > 0) {  
                        setBackground(Color.red);  
                    } 
                    */                 
                    //�����Ҫ����ĳһ��Cell��ɫ����Ҫ����column������������  
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
