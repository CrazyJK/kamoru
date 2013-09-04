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

import com.hs.alice.auth.dao.AuthGroupDao;
import com.hs.alice.auth.domain.AuthGroup;

/**
 * Home object for domain model class AuthGroup.
 * @see com.hs.alice.auth.domain.AuthGroup
 * @author Hibernate Tools
 */
@Repository
public class AuthGroupDaoJpa implements AuthGroupDao {

	private static final Log logger = LogFactory.getLog(AuthGroupDaoJpa.class);

	private static final String FROM = "select g from AuthGroup g ";
	
	private static final String FETCH = "LEFT JOIN FETCH g.authRoleGroupMaps LEFT JOIN FETCH g.authUsers ";
	
	@PersistenceContext(unitName = "alice")
	private EntityManager entityManager;

	@Override
	@CacheEvict(value = "com.hs.alice.auth.domain.AuthGroup", allEntries=true)
	public void persist(AuthGroup transientInstance) {
		entityManager.persist(transientInstance);
		logger.info(transientInstance);
	}

	@Override
	@CacheEvict(value = "com.hs.alice.auth.domain.AuthGroup", allEntries=true)
	public void remove(AuthGroup persistentInstance) {
		logger.info(persistentInstance);
		entityManager.remove(persistentInstance);
	}

	@Override
	@CacheEvict(value = "com.hs.alice.auth.domain.AuthGroup", allEntries=true)
	public AuthGroup merge(AuthGroup detachedInstance) {
		logger.info(detachedInstance);
		return entityManager.merge(detachedInstance);
	}

	@Override
	@Cacheable(value="com.hs.alice.auth.domain.AuthGroup")
	public AuthGroup findById(Integer id) {
		logger.info(id);
		return entityManager.find(AuthGroup.class, id);
	}


	@Override
	@Cacheable(value="com.hs.alice.auth.domain.AuthGroup")
	public List<AuthGroup> findAll() {
		logger.info("");
		return entityManager.createQuery(FROM, AuthGroup.class)
				.getResultList();
	}

	@Override
	@Cacheable(value="com.hs.alice.auth.domain.AuthGroup")
	public AuthGroup findByName(String name) {
		logger.info(name);
		return entityManager.createQuery(FROM + "where g.groupname like :groupname", AuthGroup.class)
				.setParameter("groupname", name)
				.getSingleResult();
	}

	@Override
	@Cacheable(value="com.hs.alice.auth.domain.AuthGroup")
	public List<AuthGroup> fetchAll() {
		List<AuthGroup> authGroupList = entityManager.createQuery(FROM + FETCH, AuthGroup.class).getResultList();
		for(AuthGroup authGroup : authGroupList)
			authGroup.fetch();
		logger.info(authGroupList);
		return authGroupList;
	}

	@Override
	@Cacheable(value="com.hs.alice.auth.domain.AuthGroup")
	public AuthGroup fetchById(Integer id) {
		logger.info(id);
		return entityManager.createQuery(FROM + FETCH + "where g.groupid like :groupid", AuthGroup.class)
				.setParameter("groupid", id)
				.getSingleResult()
				.fetch();
	}

	@Override
	@Cacheable(value="com.hs.alice.auth.domain.AuthGroup")
	public AuthGroup fetchByName(String name) {
		logger.info(name);
		return entityManager.createQuery(FROM + FETCH + "where g.groupname like :groupname", AuthGroup.class)
				.setParameter("groupname", name)
				.getSingleResult()
				.fetch();
	}
}
