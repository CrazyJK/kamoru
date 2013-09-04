package com.hs.alice.auth.dao.jpa;

// Generated 2013. 4. 16 오후 4:38:05 by Hibernate Tools 3.4.0.CR1

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import com.hs.alice.auth.dao.AuthUserDao;
import com.hs.alice.auth.domain.AuthUser;

/**
 * Home object for domain model class AuthUser.
 * @see com.hs.alice.auth.domain.AuthUser
 * @author Hibernate Tools
 */
@Repository
public class AuthUserDaoJpa implements AuthUserDao{

	private static final Log logger = LogFactory.getLog(AuthUserDaoJpa.class);

	private static final String FROM = "select u from AuthUser u ";
	
	private static final String FETCH = "LEFT JOIN FETCH u.authRoleUserMaps ";

	@PersistenceContext(unitName = "alice")
	private EntityManager entityManager;

	@Override
	@CacheEvict(value = "com.hs.alice.auth.domain.AuthUser", allEntries=true)
	public void persist(AuthUser transientInstance) {
		entityManager.persist(transientInstance);
		logger.info(transientInstance);
	}

	@Override
	@CacheEvict(value = "com.hs.alice.auth.domain.AuthUser", allEntries=true)
	public void remove(AuthUser persistentInstance) {
		entityManager.remove(persistentInstance);
		logger.info(persistentInstance);
	}

	@Override
	@CacheEvict(value = "com.hs.alice.auth.domain.AuthUser", allEntries=true)
	public AuthUser merge(AuthUser detachedInstance) {
		logger.info(detachedInstance);
		return entityManager.merge(detachedInstance);
	}

	@Override
	@Cacheable(value="com.hs.alice.auth.domain.AuthUser")
	public AuthUser findById(Integer id) {
		logger.info(id);
		return entityManager.find(AuthUser.class, id);
	}

	@Override
	@Cacheable(value="com.hs.alice.auth.domain.AuthUser")
	public AuthUser findByName(String name) {
		logger.info(name);
		Query query = entityManager.createQuery(FROM + "where u.username like :username", AuthUser.class);
		query.setParameter("username", name);
		return (AuthUser) query.getSingleResult();
	}

	@Override
	@Cacheable(value="com.hs.alice.auth.domain.AuthUser")
	public List<AuthUser> findAll() {
		logger.info("");
		return entityManager.createQuery(FROM, AuthUser.class)
				.getResultList();
	}

	@Override
	@Cacheable(value="com.hs.alice.auth.domain.AuthUser")
	public List<AuthUser> fetchAll() {
		logger.info("");
		List<AuthUser> authUserList = entityManager.createQuery(FROM + FETCH, AuthUser.class).getResultList();
		for(AuthUser authUser : authUserList)
			authUser.fetch();
		return authUserList; 
	}

	@Override
	@Cacheable(value="com.hs.alice.auth.domain.AuthUser")
	public AuthUser fetchById(Integer userid) {
		logger.info(userid);
		return entityManager.createQuery(FROM + FETCH + "where u.userid = :userid", AuthUser.class)
				.setParameter("userid", userid)
				.getSingleResult()
				.fetch();
	}

	@Override
	@Cacheable(value="com.hs.alice.auth.domain.AuthUser")
	public AuthUser fetchByName(String name) {
		logger.info(name);
		return entityManager.createQuery(FROM + FETCH + "where u.username = :username", AuthUser.class)
				.setParameter("username", name)
				.getSingleResult()
				.fetch();
	}

}
