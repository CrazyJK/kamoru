package com.hs.alice.sr.dao;

// Generated 2013. 4. 29 오후 2:54:28 by Hibernate Tools 3.4.0.CR1

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.hs.alice.sr.domain.SrInstallInfo;

/**
 * Home object for domain model class SrInstallInfo.
 * @see com.hs.alice.sr.dao.SrInstallInfo
 * @author Hibernate Tools
 */
@Repository
public class SrInstallInfoDaoJpa implements SrInstallInfoDao {

	private static final Log log = LogFactory.getLog(SrInstallInfoDaoJpa.class);

	@PersistenceContext
	private EntityManager entityManager;

	public void persist(SrInstallInfo transientInstance) {
		log.debug("persisting SrInstallInfo instance");
		try {
			entityManager.persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(SrInstallInfo persistentInstance) {
		log.debug("removing SrInstallInfo instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public SrInstallInfo merge(SrInstallInfo detachedInstance) {
		log.debug("merging SrInstallInfo instance");
		try {
			SrInstallInfo result = entityManager.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public SrInstallInfo findById(int id) {
		log.debug("getting SrInstallInfo instance with id: " + id);
		try {
			SrInstallInfo instance = entityManager
					.find(SrInstallInfo.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	@Override
	public SrInstallInfo findByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SrInstallInfo> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
}
