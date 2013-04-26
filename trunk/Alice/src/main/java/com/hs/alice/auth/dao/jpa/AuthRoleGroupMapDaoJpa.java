package com.hs.alice.auth.dao.jpa;

// Generated 2013. 4. 16 오후 4:38:05 by Hibernate Tools 3.4.0.CR1

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.hs.alice.auth.dao.AuthRoleGroupMapDao;
import com.hs.alice.auth.domain.AuthRoleGroupMap;

/**
 * Home object for domain model class AuthRoleGroupMap.
 * 
 * @see com.hs.alice.auth.domain.AuthRoleGroupMap
 * @author Hibernate Tools
 */
@Repository
public class AuthRoleGroupMapDaoJpa implements AuthRoleGroupMapDao{

	private static final Log logger = LogFactory.getLog(AuthRoleGroupMapDaoJpa.class);

	@PersistenceContext(unitName = "alice")
	private EntityManager entityManager;

	@Override
	public void persist(AuthRoleGroupMap transientInstance) {
		logger.info(transientInstance);
		entityManager.persist(transientInstance);
	}

	@Override
	public void remove(AuthRoleGroupMap persistentInstance) {
		logger.info(persistentInstance);
		entityManager.remove(persistentInstance);
	}

	@Override
	public AuthRoleGroupMap merge(AuthRoleGroupMap detachedInstance) {
		logger.info(detachedInstance);
		return entityManager.merge(detachedInstance);
	}

	@Override
	public AuthRoleGroupMap findById(Integer id) {
		logger.info(id);
		return entityManager.find(AuthRoleGroupMap.class, id);
	}

	@Override
	public AuthRoleGroupMap findByName(String name) {
		throw new RuntimeException("Not implemented or not necessary");
	}

	@Override
	public AuthRoleGroupMap fetchById(Integer id) {
		throw new RuntimeException("Not implemented or not necessary");
	}

	@Override
	public AuthRoleGroupMap fetchByName(String name) {
		throw new RuntimeException("Not implemented or not necessary");
	}

	@Override
	public List<AuthRoleGroupMap> findAll() {
		throw new RuntimeException("Not implemented or not necessary");
	}

	@Override
	public List<AuthRoleGroupMap> fetchAll() {
		throw new RuntimeException("Not implemented or not necessary");
	}
}
