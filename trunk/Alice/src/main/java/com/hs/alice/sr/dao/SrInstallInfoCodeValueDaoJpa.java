package com.hs.alice.sr.dao;

// Generated 2013. 4. 29 오후 2:54:28 by Hibernate Tools 3.4.0.CR1

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.hs.alice.sr.domain.SrInstallInfoCodeValue;

/**
 * Home object for domain model class SrInstallInfoCodeValue.
 * @see com.hs.alice.sr.dao.SrInstallInfoCodeValue
 * @author Hibernate Tools
 */
@Repository
public class SrInstallInfoCodeValueDaoJpa implements SrInstallInfoCodeValueDao {

	private static final Log log = LogFactory
			.getLog(SrInstallInfoCodeValueDaoJpa.class);

	@PersistenceContext
	private EntityManager entityManager;

	public void persist(SrInstallInfoCodeValue transientInstance) {
		log.debug("persisting SrInstallInfoCodeValue instance");
		try {
			entityManager.persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(SrInstallInfoCodeValue persistentInstance) {
		log.debug("removing SrInstallInfoCodeValue instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public SrInstallInfoCodeValue merge(SrInstallInfoCodeValue detachedInstance) {
		log.debug("merging SrInstallInfoCodeValue instance");
		try {
			SrInstallInfoCodeValue result = entityManager
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public SrInstallInfoCodeValue findById(int id) {
		log.debug("getting SrInstallInfoCodeValue instance with id: " + id);
		try {
			SrInstallInfoCodeValue instance = entityManager.find(
					SrInstallInfoCodeValue.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	@Override
	public SrInstallInfoCodeValue findByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SrInstallInfoCodeValue> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
}
