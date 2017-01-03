package com.nuaa.ai;

public class TableWindowTest{
	public static void main(String[] args){
		//用户表;
		String[] Titles = {"用户ID", "用户名称","电话"};
		TableWindowsFactory table=new TableWindowsFactory("User",Titles);
		table.setTableTitle("User Table");
		table.setMaxLineATime(4);
		table.setTableWindowSize(600, 400);
		table.showTable();
		
		//学生表;
		String[] Titles1 = {"ID", "名称"};
		TableWindowsFactory table1=new TableWindowsFactory("Student",Titles1);
		table1.setTableTitle("Student Table");
		table1.setMaxLineATime(4);
		table1.setTableWindowSize(600, 400);
		table1.showTable();
	}
}
