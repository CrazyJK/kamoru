package com.hs.alice.auth.dao.jpa;

// Generated 2013. 4. 16 오후 4:38:05 by Hibernate Tools 3.4.0.CR1

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.hs.alice.auth.dao.AuthRoleUserMapDao;
import com.hs.alice.auth.domain.AuthRoleUserMap;

/**
 * Home object for domain model class AuthRoleUserMap.
 * @see com.hs.alice.auth.domain.AuthRoleUserMap
 * @author Hibernate Tools
 */
@Repository
public class AuthRoleUserMapDaoJpa implements AuthRoleUserMapDao {

	private static final Log logger = LogFactory.getLog(AuthRoleUserMapDaoJpa.class);

	@PersistenceContext(unitName = "alice")
	private EntityManager entityManager;

	@Override
	public void persist(AuthRoleUserMap transientInstance) {
		entityManager.persist(transientInstance);
		logger.info(transientInstance);
	}

	@Override
	public void remove(AuthRoleUserMap persistentInstance) {
		entityManager.remove(persistentInstance);
		logger.info(persistentInstance);
	}

	@Override
	public AuthRoleUserMap merge(AuthRoleUserMap detachedInstance) {
		logger.info(detachedInstance);
		return entityManager.merge(detachedInstance);
	}

	@Override
	public AuthRoleUserMap findById(Integer id) {
		logger.info(id);
		return entityManager.find(AuthRoleUserMap.class, id);
	}

	@Override
	public AuthRoleUserMap findByName(String name) {
		throw new RuntimeException("Not implemented or not necessary");
	}

	@Override
	public AuthRoleUserMap fetchById(Integer id) {
		throw new RuntimeException("Not implemented or not necessary");
	}

	@Override
	public AuthRoleUserMap fetchByName(String name) {
		throw new RuntimeException("Not implemented or not necessary");
	}

	@Override
	public List<AuthRoleUserMap> findAll() {
		throw new RuntimeException("Not implemented or not necessary");
	}

	@Override
	public List<AuthRoleUserMap> fetchAll() {
		throw new RuntimeException("Not implemented or not necessary");
	}
}
