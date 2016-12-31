package com.nuaa.ai;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;
import org.hibernate.SessionFactory;

public class HibernateTest {

	public static List<User> UserQuery() {

		Configuration cfg = new Configuration();
		// cfg.configure();�ɴ�����ָ�������ļ�.����ֵ����һ��configuration ������ӵ��������ѡ��;
		SessionFactory sf = cfg.configure().buildSessionFactory();
		// ��session;
		Session session = sf.openSession();

		// ��ʼ����;
		session.beginTransaction();
		// ������һ���ַ���,��HQL�Ĳ�ѯ���.ע���ʱ�ĵ�UserUΪ��д,Ϊ�����,�����Ǳ��.
		Query query = session.createQuery("from User");
		// ʹ��List����.
		List<User> userList = query.list();
		// ������ȥ����.
		for (Iterator<User> iter = userList.iterator(); iter.hasNext();) {
			User user = (User) iter.next();
			System.out.println("id=" + user.getUserId() + "name=" + user.getUserName());
		}
		// ��ȡ�����ύ;
		session.getTransaction().commit();

		session.close();
		sf.close();
		return userList;
	}
}
