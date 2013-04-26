package com.hs.alice.orm.hibernate.reverse.com.hs.alice.auth.dao.hibernate;

// Generated 2013. 4. 10 오전 10:19:22 by Hibernate Tools 3.4.0.CR1

import java.math.BigDecimal;
import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hs.alice.auth.dao.AuthGroupDao;
import com.hs.alice.domain.AuthGroup;
import com.hs.alice.web.AuthController;

import static org.hibernate.criterion.Example.create;

/**
 * Home object for domain model class AuthGroup.
 * @see com.hs.alice.domain.AuthGroup
 * @author Hibernate Tools
 */
@Repository
public class AuthGroupDaoHibernate implements AuthGroupDao {

	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
	
	private SessionFactory sessionFactory;

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void persist(AuthGroup transientInstance) {
		logger.debug("persisting AuthGroup instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	@Override
	public BigDecimal save(AuthGroup transientInstance) {
		logger.debug("saving AuthGroup instance");
		try {
			BigDecimal groupid = (BigDecimal)sessionFactory.getCurrentSession().save(transientInstance);
			logger.info("save successful. id=" + groupid);
			return groupid;
		} catch (RuntimeException re) {
			logger.error("save failed", re);
			throw re;
		}
	}

	@Override
	public void attachDirty(AuthGroup instance) {
		logger.debug("attaching dirty AuthGroup instance");
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
	public void attachClean(AuthGroup instance) {
		logger.debug("attaching clean AuthGroup instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			logger.debug("attach successful");
		} catch (RuntimeException re) {
			logger.error("attach failed", re);
			throw re;
		}
	}

	@Override
	public void delete(AuthGroup persistentInstance) {
		logger.debug("deleting AuthGroup instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			logger.debug("delete successful");
		} catch (RuntimeException re) {
			logger.error("delete failed", re);
			throw re;
		}
	}

	@Override
	public AuthGroup merge(AuthGroup detachedInstance) {
		logger.debug("merging AuthGroup instance");
		try {
			AuthGroup result = (AuthGroup) sessionFactory.getCurrentSession()
					.merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	@Override
	public AuthGroup findById(java.math.BigDecimal id) {
		logger.debug("getting AuthGroup instance with id: " + id);
		try {
			AuthGroup instance = (AuthGroup) sessionFactory.getCurrentSession()
					.get("com.hs.alice.domain.AuthGroup", id);
			if (instance == null) {
				logger.debug("get successful, no instance found");
			} else {
//				Hibernate.initialize(instance.getAuthRoleGroupMaps());
//				Hibernate.initialize(instance.getAuthUsers());
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
	public List<AuthGroup> findByExample(AuthGroup instance) {
		logger.debug("finding AuthGroup instance by example");
		try {
			List<AuthGroup> results = (List<AuthGroup>) sessionFactory
					.getCurrentSession()
					.createCriteria("com.hs.alice.domain.AuthGroup")
					.add(create(instance)).list();
			logger.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			logger.error("find by example failed", re);
			throw re;
		}
	}
}
