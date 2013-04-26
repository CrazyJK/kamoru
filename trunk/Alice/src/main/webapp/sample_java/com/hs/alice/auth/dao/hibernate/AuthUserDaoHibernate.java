package com.hs.alice.orm.hibernate.reverse.com.hs.alice.auth.dao.hibernate;

// Generated 2013. 4. 10 오전 10:19:22 by Hibernate Tools 3.4.0.CR1

import java.math.BigDecimal;
import java.util.List;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hs.alice.auth.dao.AuthUserDao;
import com.hs.alice.domain.AuthUser;

import static org.hibernate.criterion.Example.create;

/**
 * Home object for domain model class AuthUser.
 * @see com.hs.alice.domain.AuthUser
 * @author Hibernate Tools
 */
@Repository
public class AuthUserDaoHibernate implements AuthUserDao {

	private static final Logger logger = LoggerFactory.getLogger(AuthUserDaoHibernate.class);
	
	private SessionFactory sessionFactory;

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void persist(AuthUser transientInstance) {
		logger.debug("persisting AuthUser instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	@Override
	public BigDecimal save(AuthUser transientInstance) {
		logger.debug("saving AuthUser instance");
		try {
			BigDecimal userid = (BigDecimal)sessionFactory.getCurrentSession().save(transientInstance);
			logger.debug("save successful");
			return userid;
		} catch (RuntimeException re) {
			logger.error("save failed", re);
			throw re;
		}
	}

	public void attachDirty(AuthUser instance) {
		logger.debug("attaching dirty AuthUser instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			logger.debug("attach successful");
		} catch (RuntimeException re) {
			logger.error("attach failed", re);
			throw re;
		}
	}

	@SuppressWarnings("deprecation")
	public void attachClean(AuthUser instance) {
		logger.debug("attaching clean AuthUser instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			logger.debug("attach successful");
		} catch (RuntimeException re) {
			logger.error("attach failed", re);
			throw re;
		}
	}

	public void delete(AuthUser persistentInstance) {
		logger.debug("deleting AuthUser instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			logger.debug("delete successful");
		} catch (RuntimeException re) {
			logger.error("delete failed", re);
			throw re;
		}
	}

	public AuthUser merge(AuthUser detachedInstance) {
		logger.debug("merging AuthUser instance");
		try {
			AuthUser result = (AuthUser) sessionFactory.getCurrentSession()
					.merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public AuthUser findById(java.math.BigDecimal id) {
		logger.debug("getting AuthUser instance with id: " + id);
		try {
			AuthUser instance = (AuthUser) sessionFactory.getCurrentSession()
					.get("com.hs.alice.domain.AuthUser", id);
			if (instance == null) {
				logger.debug("get successful, no instance found");
			} else {
				logger.debug("get successful, instance found");
			}
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public List<AuthUser> findByExample(AuthUser instance) {
		logger.debug("finding AuthUser instance by example");
		try {
			List<AuthUser> results = (List<AuthUser>) sessionFactory
					.getCurrentSession()
					.createCriteria("com.hs.alice.domain.AuthUser")
					.add(create(instance)).list();
			logger.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			logger.error("find by example failed", re);
			throw re;
		}
	}
}
