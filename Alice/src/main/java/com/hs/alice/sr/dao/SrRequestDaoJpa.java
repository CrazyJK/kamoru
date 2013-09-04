package com.hs.alice.sr.dao;

// Generated 2013. 4. 29 오후 2:54:28 by Hibernate Tools 3.4.0.CR1

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.hs.alice.sr.domain.SrRequest;

/**
 * Home object for domain model class SrRequest.
 * @see com.hs.alice.sr.dao.SrRequest
 * @author Hibernate Tools
 */
@Repository
public class SrRequestDaoJpa implements SrRequestDao {

	private static final Log log = LogFactory.getLog(SrRequestDaoJpa.class);

	@PersistenceContext
	private EntityManager entityManager;

	public void persist(SrRequest transientInstance) {
		log.debug("persisting SrRequest instance");
		try {
			entityManager.persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(SrRequest persistentInstance) {
		log.debug("removing SrRequest instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public SrRequest merge(SrRequest detachedInstance) {
		log.debug("merging SrRequest instance");
		try {
			SrRequest result = entityManager.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public SrRequest findById(int id) {
		log.debug("getting SrRequest instance with id: " + id);
		try {
			SrRequest instance = entityManager.find(SrRequest.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	@Override
	public SrRequest findByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SrRequest> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
}
