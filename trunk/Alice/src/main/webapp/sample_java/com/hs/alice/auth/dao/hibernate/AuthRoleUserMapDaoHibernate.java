package com.hs.alice.orm.hibernate.reverse.com.hs.alice.auth.dao.hibernate;

// Generated 2013. 4. 10 오전 10:19:22 by Hibernate Tools 3.4.0.CR1

import java.math.BigDecimal;
import java.util.List;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Property;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hs.alice.auth.dao.AuthRoleUserMapDao;
import com.hs.alice.domain.AuthRole;
import com.hs.alice.domain.AuthRoleUserMap;
import com.hs.alice.domain.AuthUser;

import static org.hibernate.criterion.Example.create;

/**
 * Home object for domain model class AuthRoleUserMap.
 * @see com.hs.alice.domain.AuthRoleUserMap
 * @author Hibernate Tools
 */
@Repository
public class AuthRoleUserMapDaoHibernate implements AuthRoleUserMapDao {

	private static final Logger logger = LoggerFactory.getLogger(AuthRoleUserMapDaoHibernate.class);
	
	private SessionFactory sessionFactory;

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void persist(AuthRoleUserMap transientInstance) {
		logger.debug("persisting AuthRoleUserMap instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			logger.debug("persist successful");
		} catch (RuntimeException re) {
			logger.error("persist failed", re);
			throw re;
		}
	}

	@Override
	public BigDecimal save(AuthRoleUserMap transientInstance) {
		logger.debug("saving AuthRoleUserMap instance");
		try {
			BigDecimal id = (BigDecimal)sessionFactory.getCurrentSession().save(transientInstance);
			logger.debug("save successful");
			return id;
		} catch (RuntimeException re) {
			logger.error("save failed", re);
			throw re;
		}
	}

	public void attachDirty(AuthRoleUserMap instance) {
		logger.debug("attaching dirty AuthRoleUserMap instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			logger.debug("attach successful");
		} catch (RuntimeException re) {
			logger.error("attach failed", re);
			throw re;
		}
	}

	@SuppressWarnings("deprecation")
	public void attachClean(AuthRoleUserMap instance) {
		logger.debug("attaching clean AuthRoleUserMap instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			logger.debug("attach successful");
		} catch (RuntimeException re) {
			logger.error("attach failed", re);
			throw re;
		}
	}

	public void delete(AuthRoleUserMap persistentInstance) {
		logger.debug("deleting AuthRoleUserMap instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			logger.debug("delete successful");
		} catch (RuntimeException re) {
			logger.error("delete failed", re);
			throw re;
		}
	}

	public AuthRoleUserMap merge(AuthRoleUserMap detachedInstance) {
		logger.debug("merging AuthRoleUserMap instance");
		try {
			AuthRoleUserMap result = (AuthRoleUserMap) sessionFactory
					.getCurrentSession().merge(detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	public AuthRoleUserMap findById(java.math.BigDecimal id) {
		logger.debug("getting AuthRoleUserMap instance with id: " + id);
		try {
			AuthRoleUserMap instance = (AuthRoleUserMap) sessionFactory
					.getCurrentSession().get(
							"com.hs.alice.domain.AuthRoleUserMap", id);
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
	public List<AuthRoleUserMap> findByExample(AuthRoleUserMap instance) {
		logger.debug("finding AuthRoleUserMap instance by example");
		try {
			List<AuthRoleUserMap> results = (List<AuthRoleUserMap>) sessionFactory
					.getCurrentSession()
					.createCriteria("com.hs.alice.domain.AuthRoleUserMap")
					.add(create(instance)).list();
			logger.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			logger.error("find by example failed", re);
			throw re;
		}
	}

	@Override
	public AuthRoleUserMap findByUserAndRole(AuthUser user, AuthRole role) {
		logger.debug("finding AuthRoleUserMap instance by userid, roleid");
		try {
			AuthRoleUserMap results = (AuthRoleUserMap) sessionFactory
					.getCurrentSession()
					.createCriteria("com.hs.alice.domain.AuthRoleUserMap")
					.add(Property.forName("authUser").eq(user))
					.add(Property.forName("authRole").eq(role))
					.uniqueResult();
			logger.debug("find by example successful, result : " + results);
			return results;
		} catch (RuntimeException re) {
			logger.error("find by example failed", re);
			throw re;
		}
	}
}
