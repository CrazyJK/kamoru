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

import com.hs.alice.auth.dao.AuthRoleDao;
import com.hs.alice.domain.AuthRole;

import static org.hibernate.criterion.Example.create;

/**
 * Home object for domain model class AuthRole.
 * @see com.hs.alice.domain.AuthRole
 * @author Hibernate Tools
 */
@Repository
public class AuthRoleDaoHibernate implements AuthRoleDao {

	private static final Logger logger = LoggerFactory.getLogger(AuthRoleDaoHibernate.class);
	
	private SessionFactory sessionFactory;

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void persist(AuthRole transientInstance) {
		logger.debug("persisting AuthRole instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	@Override
	public BigDecimal save(AuthRole transientInstance) {
		logger.debug("saving AuthRole instance");
		try {
			BigDecimal roleid = (BigDecimal)sessionFactory.getCurrentSession().save(transientInstance);
			logger.debug("save successful");
			return roleid;
		} catch (RuntimeException re) {
			logger.error("save failed", re);
			throw re;
		}
	}

	@Override
	public void attachDirty(AuthRole instance) {
		logger.debug("attaching dirty AuthRole instance");
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
	public void attachClean(AuthRole instance) {
		logger.debug("attaching clean AuthRole instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			logger.debug("attach successful");
		} catch (RuntimeException re) {
			logger.error("attach failed", re);
			throw re;
		}
	}

	@Override
	public void delete(AuthRole persistentInstance) {
		logger.debug("deleting AuthRole instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			logger.debug("delete successful");
		} catch (RuntimeException re) {
			logger.error("delete failed", re);
			throw re;
		}
	}

	@Override
	public AuthRole merge(AuthRole detachedInstance) {
		logger.debug("merging AuthRole instance");
		try {
			AuthRole result = (AuthRole) sessionFactory.getCurrentSession()
					.merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	@Override
	public AuthRole findById(java.math.BigDecimal id) {
		logger.debug("getting AuthRole instance with id: " + id);
		try {
			AuthRole instance = (AuthRole) sessionFactory.getCurrentSession()
					.get("com.hs.alice.domain.AuthRole", id);
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
	public List<AuthRole> findByExample(AuthRole instance) {
		logger.debug("finding AuthRole instance by example");
		try {
			List<AuthRole> results = (List<AuthRole>) sessionFactory
					.getCurrentSession()
					.createCriteria("com.hs.alice.domain.AuthRole")
					.add(create(instance)).list();
			logger.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			logger.error("find by example failed", re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AuthRole> findAll() {
		logger.debug("finding AuthRole All");
		try {
			List<AuthRole> results = (List<AuthRole>) sessionFactory
					.getCurrentSession()
					.createCriteria("com.hs.alice.domain.AuthRole")
					.list();
			logger.debug("find all successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			logger.error("find all failed", re);
			throw re;
		}
	}
}
