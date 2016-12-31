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
		// cfg.configure();可带参数指定配置文件.返回值还是一个configuration 但是其拥有了配置选项;
		SessionFactory sf = cfg.configure().buildSessionFactory();
		// 打开session;
		Session session = sf.openSession();

		// 开始事务;
		session.beginTransaction();
		// 参数是一个字符串,是HQL的查询语句.注意此时的的UserU为大写,为对象的,而不是表的.
		Query query = session.createQuery("from User");
		// 使用List方法.
		List<User> userList = query.list();
		// 迭代器去迭代.
		for (Iterator<User> iter = userList.iterator(); iter.hasNext();) {
			User user = (User) iter.next();
			System.out.println("id=" + user.getUserId() + "name=" + user.getUserName());
		}
		// 获取事务并提交;
		session.getTransaction().commit();

		session.close();
		sf.close();
		return userList;
	}
}
