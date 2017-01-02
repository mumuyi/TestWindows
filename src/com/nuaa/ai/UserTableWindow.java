package com.nuaa.ai;

import java.util.List;

public class UserTableWindow implements TableWindows{
	// 数据库查询时,每次查询的最大数量;
	private int maxLineATime = 4;
	
	public void iniTable(){
		UserTableWindow utw=new UserTableWindow();
		String[] Titles = {"编号","用户ID", "用户名称","电话","选择"};
		List<?> list = (List<?>)MyHibernate.sqlQuery(0, 4,"from User");
		TableWindowsFactory table=new TableWindowsFactory("User Table",Titles,list,utw,MyHibernate.sqlGetRecordNum("select count(*) from User"),maxLineATime);
		table.showTable();
	}
	
	public List<?> update(int from,int maxLineATime){
		return MyHibernate.sqlQuery(from, maxLineATime,"from User");
	}
	public List<?> update(int from,int maxLineATime,int upordown,String orderBy){
		if(upordown==0)
			return MyHibernate.sqlQuery(from, maxLineATime,"from User order by "+orderBy+" asc");
		else
			return MyHibernate.sqlQuery(from, maxLineATime,"from User order by "+orderBy+" desc");			
	}
	
	public static void main(String[] args){
		UserTableWindow utw=new UserTableWindow();
		utw.iniTable();
	}
	
	public int getMaxLineATime() {
		return maxLineATime;
	}

	public void setMaxLineATime(int maxLineATime) {
		this.maxLineATime = maxLineATime;
	}
}
