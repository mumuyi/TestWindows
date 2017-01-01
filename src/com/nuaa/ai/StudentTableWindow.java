package com.nuaa.ai;

import java.util.List;

public class StudentTableWindow {
	
	public void iniTable(){
		StudentTableWindow utw=new StudentTableWindow();
		String[] Titles = { "ID", "Name" };
		@SuppressWarnings("unchecked")
		List<Student> list = (List<Student>)MyHibernate.UserQuery(0, 2,"from Student");
		TableWindowsFactory table=new TableWindowsFactory("Student Table",Titles,list,utw,MyHibernate.UserGetRecordNum("select count(*) from Student"));
		table.showTable();
	}
	
	@SuppressWarnings("unchecked")
	public List<Student> update(int from,int maxLineATime){
		return (List<Student>)MyHibernate.UserQuery(from, maxLineATime,"from Student");
	}

}
