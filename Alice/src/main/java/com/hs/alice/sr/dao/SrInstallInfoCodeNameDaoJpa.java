package com.hs.alice.sr.dao;

// Generated 2013. 4. 29 오후 2:54:28 by Hibernate Tools 3.4.0.CR1

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.hs.alice.sr.domain.SrInstallInfoCodeName;

/**
 * Home object for domain model class SrInstallInfoCodeName.
 * @see com.hs.alice.sr.dao.SrInstallInfoCodeName
 * @author Hibernate Tools
 */
@Repository
public class SrInstallInfoCodeNameDaoJpa implements SrInstallInfoCodeNameDao {

	private static final Log log = LogFactory
			.getLog(SrInstallInfoCodeNameDaoJpa.class);

	@PersistenceContext
	private EntityManager entityManager;

	public void persist(SrInstallInfoCodeName transientInstance) {
		log.debug("persisting SrInstallInfoCodeName instance");
		try {
			entityManager.persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(SrInstallInfoCodeName persistentInstance) {
		log.debug("removing SrInstallInfoCodeName instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public SrInstallInfoCodeName merge(SrInstallInfoCodeName detachedInstance) {
		log.debug("merging SrInstallInfoCodeName instance");
		try {
			SrInstallInfoCodeName result = entityManager
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public SrInstallInfoCodeName findById(int id) {
		log.debug("getting SrInstallInfoCodeName instance with id: " + id);
		try {
			SrInstallInfoCodeName instance = entityManager.find(
					SrInstallInfoCodeName.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	@Override
	public SrInstallInfoCodeName findByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SrInstallInfoCodeName> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
}
