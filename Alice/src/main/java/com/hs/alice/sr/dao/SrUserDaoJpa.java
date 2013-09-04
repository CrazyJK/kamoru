package com.hs.alice.sr.dao;

// Generated 2013. 4. 29 오후 2:54:28 by Hibernate Tools 3.4.0.CR1

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.hs.alice.sr.domain.SrUser;

/**
 * Home object for domain model class SrUser.
 * @see com.hs.alice.sr.dao.SrUser
 * @author Hibernate Tools
 */
@Repository
public class SrUserDaoJpa implements SrUserDao {

	private static final Log log = LogFactory.getLog(SrUserDaoJpa.class);

	@PersistenceContext
	private EntityManager entityManager;

	public void persist(SrUser transientInstance) {
		log.debug("persisting SrUser instance");
		try {
			entityManager.persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(SrUser persistentInstance) {
		log.debug("removing SrUser instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public SrUser merge(SrUser detachedInstance) {
		log.debug("merging SrUser instance");
		try {
			SrUser result = entityManager.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public SrUser findById(int id) {
		log.debug("getting SrUser instance with id: " + id);
		try {
			SrUser instance = entityManager.find(SrUser.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	@Override
	public SrUser findByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SrUser> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
}
