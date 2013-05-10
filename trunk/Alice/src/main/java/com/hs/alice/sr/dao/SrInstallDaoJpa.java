package com.hs.alice.sr.dao;

// Generated 2013. 4. 29 오후 2:54:28 by Hibernate Tools 3.4.0.CR1

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.hs.alice.sr.domain.SrInstall;

/**
 * Home object for domain model class SrInstall.
 * @see com.hs.alice.sr.dao.SrInstall
 * @author Hibernate Tools
 */
@Repository
public class SrInstallDaoJpa implements SrInstallDao {

	private static final Log log = LogFactory.getLog(SrInstallDaoJpa.class);

	@PersistenceContext
	private EntityManager entityManager;

	public void persist(SrInstall transientInstance) {
		log.debug("persisting SrInstall instance");
		try {
			entityManager.persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(SrInstall persistentInstance) {
		log.debug("removing SrInstall instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public SrInstall merge(SrInstall detachedInstance) {
		log.debug("merging SrInstall instance");
		try {
			SrInstall result = entityManager.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public SrInstall findById(int id) {
		log.debug("getting SrInstall instance with id: " + id);
		try {
			SrInstall instance = entityManager.find(SrInstall.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	@Override
	public SrInstall findByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SrInstall> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
}
