package com.nuaa.ai;

public class TableWindowTest{
	public static void main(String[] args){
		//�û���;
		String[] Titles = {"�û�ID", "�û�����","�绰"};
		TableWindowsFactory table=new TableWindowsFactory("User",Titles);
		table.setTableTitle("User Table");
		table.setMaxLineATime(4);
		table.setTableWindowSize(600, 400);
		table.showTable();
		
		//ѧ����;
		String[] Titles1 = {"ID", "����"};
		TableWindowsFactory table1=new TableWindowsFactory("Student",Titles1);
		table1.setTableTitle("Student Table");
		table1.setMaxLineATime(4);
		table1.setTableWindowSize(600, 400);
		table1.showTable();
	}
}
