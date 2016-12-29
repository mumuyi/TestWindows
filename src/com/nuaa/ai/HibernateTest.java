package com.nuaa.ai;

import java.awt.Font;

import javax.swing.JOptionPane;

import org.hibernate.Session;
import org.hibernate.cfg.Configuration;
import org.hibernate.SessionFactory;

public class HibernateTest {
    public static void main(String[] args) {
        //向Users对象中添加数据
        User users = new User();
        users.setUserId("12345");
        users.setUserName("王五");
        users.setUserPhone("010-12345678");
        //向Users对象中添加数据
        User users2 = new User();
        users2.setUserId("12154");
        users2.setUserName("孙钱");
        users2.setUserPhone("010-12345878");
        
  
        
        Configuration cfg = new Configuration();
        SessionFactory sf = cfg.configure().buildSessionFactory();
        Session session = sf.openSession();
        session.beginTransaction();
        session.save(users);
        session.getTransaction().commit();
        session.close();
        sf.close();
        
        
        /*
        //开启一个Hibernate会话
        Session session = HibernateSessionFactory.getSession();
        //开启一个事物
        Transaction trans = session.beginTransaction();
        session.persist(users);
        session.persist(users2);
        
        //提交事物
        trans.commit();
        
        //输出添加的数据结果
        StringBuffer result = new StringBuffer();
        result.append("添加成功！往数据库中添加了如下数据：\n");
        result.append("编号    "+"地址      "+"姓名  "+"年龄  "+"联系电话\t\r\n\r\n");
        result.append(users.getUserId()+"  ");
        result.append(users.getUserName()+"  ");
        result.append(users.getUserPhone()+"\t\n\r");

        result.append(users2.getUserId()+"  ");
        result.append(users2.getUserName()+"  ");
        result.append(users2.getUserPhone()+"\t\n\r");

        //关闭会话
        session.close();
        //用图形界面技术显示查询结果
        JOptionPane.getRootFrame().setFont(new Font("Arial", Font.BOLD, 14));
        JOptionPane.showMessageDialog(null, result.toString());
        */
    }
}
