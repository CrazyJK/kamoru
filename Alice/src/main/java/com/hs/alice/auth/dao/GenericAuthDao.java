package com.hs.alice.auth.dao;

import java.util.List;

public interface GenericAuthDao<T> {

	/**
	 * 도메인 객체를 저장한다.
	 * @param transientInstance
	 */
	void persist(T transientInstance);
	
	/**
	 * entity를 삭제 한다.
	 * @param persistentInstance
	 */
	void remove(T persistentInstance);
	
	/**
	 * entity를 병합한다.
	 * @param detachedInstance
	 * @return
	 */
	T merge(T detachedInstance);
	
	/**
	 * PK(id)로 entity를 찾는다. Lazy loading
	 * @param id
	 * @return
	 */
	T findById(Integer id);

	/**
	 * name으로 entity를 찾는다. Lazy loading
	 * @param name
	 * @return
	 */
	T findByName(String name);
	
	/**
	 * PK(id)로 entity를 찾는다. LEFT JOIN FETCH
	 * @param id
	 * @return
	 */
	T fetchById(Integer id);

	/**
	 * name으로 entity를 찾는다. LEFT JOIN FETCH
	 * @param name
	 * @return
	 */
	T fetchByName(String name);

	/**
	 * 전체 entity를 찾는다. Lazy loading
	 * @return
	 */
	List<T> findAll();

	/**
	 * 전체 entity를 찾는다. LEFT JOIN FETCH
	 * @return
	 */
	List<T> fetchAll();
}
