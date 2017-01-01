package com.nuaa.ai;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;
import org.hibernate.SessionFactory;

public class MyHibernate {

	public static List<?> sqlQuery(int from,int max,String com) {

		Configuration cfg = new Configuration();
		// cfg.configure();�ɴ�����ָ�������ļ�.����ֵ����һ��configuration ������ӵ��������ѡ��;
		SessionFactory sf = cfg.configure().buildSessionFactory();
		// ��session;
		Session session = sf.openSession();

		// ��ʼ����;
		session.beginTransaction();
		// ������һ���ַ���,��HQL�Ĳ�ѯ���.ע���ʱ�ĵ�UserUΪ��д,Ϊ�����,�����Ǳ��.
		Query query = session.createQuery(com);
		
        //�ӵ�һ����ʼ����.�������ôӵڼ�������;
        query.setFirstResult(from);
        //�������;
        query.setMaxResults(max);
		
		// ʹ��List����.
		List<?> userList = query.list();
		/*
		// ������ȥ����.
		for (Iterator<?> iter = userList.iterator(); iter.hasNext();) {
			User user = (User) iter.next();
			System.out.println("id=" + user.getUserId() + "name=" + user.getUserName());
		}
		*/
		// ��ȡ�����ύ;
		session.getTransaction().commit();

		session.close();
		sf.close();
		return userList;
	}
	
	public static long sqlGetRecordNum(String com){
		Configuration cfg = new Configuration();
		// cfg.configure();�ɴ�����ָ�������ļ�.����ֵ����һ��configuration ������ӵ��������ѡ��;
		SessionFactory sf = cfg.configure().buildSessionFactory();
		// ��session;
		Session session = sf.openSession();
		// ��ʼ����;
		session.beginTransaction();
		Query query = session.createQuery(com);
		// ��ȡ�����ύ;
		session.getTransaction().commit();
		//��ȡ����;
		long sum=(long) query.uniqueResult();
		session.close();
		sf.close();
		return sum;
		//Criteria criteria = session.createCriteria(User.class);
		//criteria.setProjection(Projections.rowCount());
		//return criteria.list().size();
	}
	
	public static void sqlSaveOrUpdate(Object object){
		Configuration cfg = new Configuration();
		// cfg.configure();�ɴ�����ָ�������ļ�.����ֵ����һ��configuration ������ӵ��������ѡ��;
		SessionFactory sf = cfg.configure().buildSessionFactory();
		// ��session;
		Session session = sf.openSession();
		// ��ʼ����;
		session.beginTransaction();
		session.saveOrUpdate(object);
		// ��ȡ�����ύ;
		session.getTransaction().commit();
		//�ر�;
		session.close();
		sf.close();
	}
	
	public static void sqlDelete(Object object){
		Configuration cfg = new Configuration();
		// cfg.configure();�ɴ�����ָ�������ļ�.����ֵ����һ��configuration ������ӵ��������ѡ��;
		SessionFactory sf = cfg.configure().buildSessionFactory();
		// ��session;
		Session session = sf.openSession();
		// ��ʼ����;
		session.beginTransaction();
		session.delete(object);
		// ��ȡ�����ύ;
		session.getTransaction().commit();
		//�ر�;
		session.close();
		sf.close();
	} 
}
