package com.nuaa.ai;

import java.awt.Font;

import javax.swing.JOptionPane;

import org.hibernate.Session;
import org.hibernate.cfg.Configuration;
import org.hibernate.SessionFactory;

public class HibernateTest {
    public static void main(String[] args) {
        //��Users�������������
        User users = new User();
        users.setUserId("12345");
        users.setUserName("����");
        users.setUserPhone("010-12345678");
        //��Users�������������
        User users2 = new User();
        users2.setUserId("12154");
        users2.setUserName("��Ǯ");
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
        //����һ��Hibernate�Ự
        Session session = HibernateSessionFactory.getSession();
        //����һ������
        Transaction trans = session.beginTransaction();
        session.persist(users);
        session.persist(users2);
        
        //�ύ����
        trans.commit();
        
        //�����ӵ����ݽ��
        StringBuffer result = new StringBuffer();
        result.append("��ӳɹ��������ݿ���������������ݣ�\n");
        result.append("���    "+"��ַ      "+"����  "+"����  "+"��ϵ�绰\t\r\n\r\n");
        result.append(users.getUserId()+"  ");
        result.append(users.getUserName()+"  ");
        result.append(users.getUserPhone()+"\t\n\r");

        result.append(users2.getUserId()+"  ");
        result.append(users2.getUserName()+"  ");
        result.append(users2.getUserPhone()+"\t\n\r");

        //�رջỰ
        session.close();
        //��ͼ�ν��漼����ʾ��ѯ���
        JOptionPane.getRootFrame().setFont(new Font("Arial", Font.BOLD, 14));
        JOptionPane.showMessageDialog(null, result.toString());
        */
    }
}
