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

import com.hs.alice.auth.dao.AuthRoleGroupMapDao;
import com.hs.alice.domain.AuthRoleGroupMap;

import static org.hibernate.criterion.Example.create;

/**
 * Home object for domain model class AuthRoleGroupMap.
 * @see com.hs.alice.domain.AuthRoleGroupMap
 * @author Hibernate Tools
 */
@Repository
public class AuthRoleGroupMapDaoHibernate implements AuthRoleGroupMapDao {

	private static final Logger logger = LoggerFactory.getLogger(AuthRoleGroupMapDaoHibernate.class);
	
	private SessionFactory sessionFactory;

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void persist(AuthRoleGroupMap transientInstance) {
		logger.debug("persisting AuthRoleGroupMap instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	@Override
	public BigDecimal save(AuthRoleGroupMap transientInstance) {
		logger.debug("saving AuthRoleGroupMap instance");
		try {
			BigDecimal id = (BigDecimal)sessionFactory.getCurrentSession().save(transientInstance);
			logger.debug("save successful");
			return id;
		} catch (RuntimeException re) {
			logger.error("save failed", re);
			throw re;
		}
	}

	@Override
	public void attachDirty(AuthRoleGroupMap instance) {
		logger.debug("attaching dirty AuthRoleGroupMap instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			logger.debug("attach successful");
		} catch (RuntimeException re) {
			logger.error("attach failed", re);
			throw re;
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public void attachClean(AuthRoleGroupMap instance) {
		logger.debug("attaching clean AuthRoleGroupMap instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			logger.debug("attach successful");
		} catch (RuntimeException re) {
			logger.error("attach failed", re);
			throw re;
		}
	}

	@Override
	public void delete(AuthRoleGroupMap persistentInstance) {
		logger.debug("deleting AuthRoleGroupMap instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			logger.debug("delete successful");
		} catch (RuntimeException re) {
			logger.error("delete failed", re);
			throw re;
		}
	}

	@Override
	public AuthRoleGroupMap merge(AuthRoleGroupMap detachedInstance) {
		logger.debug("merging AuthRoleGroupMap instance");
		try {
			AuthRoleGroupMap result = (AuthRoleGroupMap) sessionFactory
					.getCurrentSession().merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	@Override
	public AuthRoleGroupMap findById(java.math.BigDecimal id) {
		logger.debug("getting AuthRoleGroupMap instance with id: " + id);
		try {
			AuthRoleGroupMap instance = (AuthRoleGroupMap) sessionFactory
					.getCurrentSession().get(
							"com.hs.alice.domain.AuthRoleGroupMap", id);
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
	@Override
	public List<AuthRoleGroupMap> findByExample(AuthRoleGroupMap instance) {
		logger.debug("finding AuthRoleGroupMap instance by example");
		try {
			List<AuthRoleGroupMap> results = (List<AuthRoleGroupMap>) sessionFactory
					.getCurrentSession()
					.createCriteria("com.hs.alice.domain.AuthRoleGroupMap")
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
