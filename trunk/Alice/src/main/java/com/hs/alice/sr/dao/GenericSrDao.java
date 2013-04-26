package com.hs.alice.sr.dao;

import java.util.List;

public interface GenericSrDao<T> {

	void persist(T transientInstance);
	
	void remove(T persistentInstance);
	
	T merge(T detachedInstance);
	
	T findById(Integer id);

	T findByName(String name);
	
	List<T> findAll();

}
