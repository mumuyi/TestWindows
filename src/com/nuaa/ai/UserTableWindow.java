package com.nuaa.ai;

import java.util.List;

public class UserTableWindow {
	
	public void iniTable(){
		UserTableWindow utw=new UserTableWindow();
		String[] Titles = { "用户ID", "用户名称" };
		@SuppressWarnings("unchecked")
		List<User> list = (List<User>)MyHibernate.sqlQuery(0, 2,"from User");
		TableWindowsFactory table=new TableWindowsFactory("User Table",Titles,list,utw,MyHibernate.sqlGetRecordNum("select count(*) from User"));
		table.showTable();
	}
	
	@SuppressWarnings("unchecked")
	public List<User> update(int from,int maxLineATime){
		return (List<User>)MyHibernate.sqlQuery(from, maxLineATime,"from User");
	}
}
