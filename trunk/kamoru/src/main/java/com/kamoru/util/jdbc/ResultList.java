package com.kamoru.util.jdbc;

import java.util.ArrayList;
import java.util.HashMap;

public class ResultList {
	@SuppressWarnings("rawtypes")
	ArrayList list = null;
	
	@SuppressWarnings("rawtypes")
	public ResultList() {
		list = new ArrayList();
	}
	
	public String get(String key) {
		return get(key, 0);
	}
	@SuppressWarnings("rawtypes")
	public String get(String key, int idx) {
		HashMap data = (HashMap)list.get(idx);
		return (String)data.get(key);
	}
	
	@SuppressWarnings("rawtypes")
	public HashMap get(int idx) {
		return (HashMap)list.get(idx);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void add(HashMap map) {
		list.add(map);
	}
	public void clear() {
		list.clear();
	}
	public int size() {
		return list.size();
	}

}
