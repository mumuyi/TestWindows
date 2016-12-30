package com.nuaa.ai;

import java.awt.Font;
import java.util.Iterator;
import java.util.List;

import javax.swing.JOptionPane;

import org.hibernate.Query;
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
        users2.setUserId("12154909090909");
        users2.setUserName("��Ǯ");
        users2.setUserPhone("010-12345878");
        
  
        Student student=new Student();
        student.setId("222222");
        student.setName("2111111");
        
        
        
        Configuration cfg = new Configuration();
        //cfg.configure();�ɴ�����ָ�������ļ�.����ֵ����һ��configuration ������ӵ��������ѡ��;
        SessionFactory sf = cfg.configure().buildSessionFactory();
        //��session;
        Session session = sf.openSession();
        
        /*
        //��ʼ����;
        session.beginTransaction();
        session.save(student);
        //��ȡ�����ύ;
        session.getTransaction().commit();
        
        
        //��ʼ����;
        session.beginTransaction();
        session.save(users2);
        //��ȡ�����ύ;
        session.getTransaction().commit();
        */
        
        //��ʼ����;
        session.beginTransaction();
        session.update(users2);
       

        
        //������һ���ַ���,��HQL�Ĳ�ѯ���.ע���ʱ�ĵ�UserUΪ��д,Ϊ�����,�����Ǳ��.
        Query query = session.createQuery("from User");
        //ʹ��List����.
        List<User> userList = query.list();
        //������ȥ����.
        for(Iterator<User> iter=userList.iterator();iter.hasNext();)
        {
           User user =(User)iter.next();
           System.out.println("id="+user.getUserId() + "name="+user.getUserName());
        }
        
        //��ȡ�����ύ;
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
