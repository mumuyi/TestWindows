package com.nuaa.ai;

import java.util.List;

public class UserTableWindow {
	
	public void iniTable(){
		UserTableWindow utw=new UserTableWindow();
		String[] Titles = {"���","�û�ID", "�û�����","�绰","ѡ��"};
		@SuppressWarnings("unchecked")
		List<User> list = (List<User>)MyHibernate.sqlQuery(0, 4,"from User");
		TableWindowsFactory table=new TableWindowsFactory("User Table",Titles,list,utw,MyHibernate.sqlGetRecordNum("select count(*) from User"));
		table.showTable();
	}
	
	@SuppressWarnings("unchecked")
	public List<User> update(int from,int maxLineATime){
		return (List<User>)MyHibernate.sqlQuery(from, maxLineATime,"from User");
	}
	
	public static void main(String[] args){
		UserTableWindow utw=new UserTableWindow();
		utw.iniTable();
	}
}
