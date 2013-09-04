package com.hs.alice.auth.dao.jpa;

// Generated 2013. 4. 16 오후 4:38:05 by Hibernate Tools 3.4.0.CR1

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import com.hs.alice.auth.dao.AuthRoleDao;
import com.hs.alice.auth.domain.AuthRole;

/**
 * Home object for domain model class AuthRole.
 * @see com.hs.alice.auth.domain.AuthRole
 * @author Hibernate Tools
 */
@Repository
public class AuthRoleDaoJpa implements AuthRoleDao {

	private static final Log logger = LogFactory.getLog(AuthRoleDaoJpa.class);

	private static final String FROM = "select r from AuthRole r ";
	
	private static final String FETCH = "LEFT JOIN FETCH r.authRoleUserMaps LEFT JOIN FETCH r.authRoleGroupMaps ";

	@PersistenceContext(unitName = "alice")
	private EntityManager entityManager;

	@Override
	@CacheEvict(value = "com.hs.alice.auth.domain.AuthRole", allEntries=true)
	public void persist(AuthRole transientInstance) {
		entityManager.persist(transientInstance);
		logger.info(transientInstance);
	}

	@Override
	@CacheEvict(value = "com.hs.alice.auth.domain.AuthRole", allEntries=true)
	public void remove(AuthRole persistentInstance) {
		logger.info(persistentInstance);
		entityManager.remove(persistentInstance);
	}

	@Override
	@CacheEvict(value = "com.hs.alice.auth.domain.AuthRole", allEntries=true)
	public AuthRole merge(AuthRole detachedInstance) {
		logger.info(detachedInstance);
		return entityManager.merge(detachedInstance);
	}

	@Override
	@Cacheable(value="com.hs.alice.auth.domain.AuthRole")
	public AuthRole findById(Integer id) {
		logger.info(id);
		return entityManager.find(AuthRole.class, id);
	}

	@Override
	@Cacheable(value="com.hs.alice.auth.domain.AuthRole")
	public List<AuthRole> findAll() {
		logger.info("");
		return entityManager.createQuery(FROM, AuthRole.class).getResultList();
	}

	@Override
	@Cacheable(value="com.hs.alice.auth.domain.AuthRole")
	public AuthRole findByName(String name) {
		logger.info(name);
		return entityManager.createQuery(FROM + "where r.rolename like :rolename", AuthRole.class)
				.setParameter("rolename", name)
				.getSingleResult();
	}

	@Override
	@Cacheable(value="com.hs.alice.auth.domain.AuthRole")
	public AuthRole fetchById(Integer id) {
		logger.info(id);
		return entityManager.createQuery(FROM + FETCH + "where r.roleid like :roleid", AuthRole.class)
				.setParameter("roleid", id)
				.getSingleResult()
				.fetch();
	}

	@Override
	@Cacheable(value="com.hs.alice.auth.domain.AuthRole")
	public AuthRole fetchByName(String name) {
		logger.info(name);
		return entityManager.createQuery(FROM + FETCH + "where r.rolename like :rolename", AuthRole.class)
				.setParameter("rolename", name)
				.getSingleResult()
				.fetch();
	}

	@Override
	@Cacheable(value="com.hs.alice.auth.domain.AuthRole")
	public List<AuthRole> fetchAll() {
		List<AuthRole> authRoleList = entityManager.createQuery(FROM + FETCH, AuthRole.class).getResultList();
		for(AuthRole authRole : authRoleList)
			authRole.fetch();
		logger.info(authRoleList);
		return authRoleList;
	}
}
