package com.hs.alice.sr.dao;

// Generated 2013. 4. 29 오후 2:54:28 by Hibernate Tools 3.4.0.CR1

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.hs.alice.sr.domain.SrInstallCodeVersion;

/**
 * Home object for domain model class SrInstallCodeVersion.
 * @see com.hs.alice.sr.dao.SrInstallCodeVersion
 * @author Hibernate Tools
 */
@Repository
public class SrInstallCodeVersionDaoJpa implements SrInstallCodeVersionDao {

	private static final Log log = LogFactory
			.getLog(SrInstallCodeVersionDaoJpa.class);

	@PersistenceContext
	private EntityManager entityManager;

	public void persist(SrInstallCodeVersion transientInstance) {
		log.debug("persisting SrInstallCodeVersion instance");
		try {
			entityManager.persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(SrInstallCodeVersion persistentInstance) {
		log.debug("removing SrInstallCodeVersion instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public SrInstallCodeVersion merge(SrInstallCodeVersion detachedInstance) {
		log.debug("merging SrInstallCodeVersion instance");
		try {
			SrInstallCodeVersion result = entityManager.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public SrInstallCodeVersion findById(int id) {
		log.debug("getting SrInstallCodeVersion instance with id: " + id);
		try {
			SrInstallCodeVersion instance = entityManager.find(
					SrInstallCodeVersion.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	@Override
	public SrInstallCodeVersion findByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SrInstallCodeVersion> findAll() {
		// TODO Auto-generated method stub
		return null;
	}


}
