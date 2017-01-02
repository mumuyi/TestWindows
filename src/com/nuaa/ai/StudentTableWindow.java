package com.nuaa.ai;

import java.util.List;

public class StudentTableWindow implements TableWindows{
	// ���ݿ��ѯʱ,ÿ�β�ѯ���������;
	private int maxLineATime = 4;

	public void iniTable(){
		StudentTableWindow utw=new StudentTableWindow();
		String[] Titles = { "���","ID", "Name","ѡ��" };
		@SuppressWarnings("unchecked")
		List<Student> list = (List<Student>)MyHibernate.sqlQuery(0, 4,"from Student");
		TableWindowsFactory table=new TableWindowsFactory("Student Table",Titles,list,utw,MyHibernate.sqlGetRecordNum("select count(*) from Student"),maxLineATime);
		table.showTable();
	}
	
	public List<?> update(int from,int maxLineATime){
		return MyHibernate.sqlQuery(from, maxLineATime,"from Student");
	}
	
	public List<?> update(int from,int maxLineATime,int upordown,String orderBy){
		if(upordown==0)
			return MyHibernate.sqlQuery(from, maxLineATime,"from Student order by "+orderBy+" asc");
		else
			return MyHibernate.sqlQuery(from, maxLineATime,"from Student order by "+orderBy+" desc");			
	}
	
	public int getMaxLineATime() {
		return maxLineATime;
	}

	public void setMaxLineATime(int maxLineATime) {
		this.maxLineATime = maxLineATime;
	}
	
	
	public static void main(String[] args){
		StudentTableWindow stw=new StudentTableWindow();
		stw.iniTable();
	}
}
