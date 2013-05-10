package com.hs.alice.sr.dao;

// Generated 2013. 4. 29 오후 2:54:28 by Hibernate Tools 3.4.0.CR1

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.hs.alice.sr.domain.SrRequestCodeStatus;

/**
 * Home object for domain model class SrRequestCodeStatus.
 * @see com.hs.alice.sr.dao.SrRequestCodeStatus
 * @author Hibernate Tools
 */
@Repository
public class SrRequestCodeStatusDaoJpa implements SrRequestCodeStatusDao {

	private static final Log log = LogFactory
			.getLog(SrRequestCodeStatusDaoJpa.class);

	@PersistenceContext
	private EntityManager entityManager;

	public void persist(SrRequestCodeStatus transientInstance) {
		log.debug("persisting SrRequestCodeStatus instance");
		try {
			entityManager.persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(SrRequestCodeStatus persistentInstance) {
		log.debug("removing SrRequestCodeStatus instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public SrRequestCodeStatus merge(SrRequestCodeStatus detachedInstance) {
		log.debug("merging SrRequestCodeStatus instance");
		try {
			SrRequestCodeStatus result = entityManager.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public SrRequestCodeStatus findById(int id) {
		log.debug("getting SrRequestCodeStatus instance with id: " + id);
		try {
			SrRequestCodeStatus instance = entityManager.find(
					SrRequestCodeStatus.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	@Override
	public SrRequestCodeStatus findByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SrRequestCodeStatus> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
}
