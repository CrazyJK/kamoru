package kamoru.sql;

import java.util.ArrayList;
import java.util.HashMap;

public class ResultList {
	ArrayList list = null;
	
	public ResultList() {
		list = new ArrayList();
	}
	
	public String get(String key) {
		return get(key, 0);
	}
	public String get(String key, int idx) {
		HashMap data = (HashMap)list.get(idx);
		return (String)data.get(key);
	}
	
	public HashMap get(int idx) {
		return (HashMap)list.get(idx);
	}
	
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
