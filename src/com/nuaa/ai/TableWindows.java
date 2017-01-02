package com.nuaa.ai;

import java.util.List;

public interface TableWindows {
	public void iniTable();
	public List<?> update(int from,int maxLineATime);
	public List<?> update(int from,int maxLineATime,int upordown,String orderBy);
}
