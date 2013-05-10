package com.hs.alice.sr.dao;

// Generated 2013. 4. 29 오후 2:54:28 by Hibernate Tools 3.4.0.CR1

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.hs.alice.sr.domain.SrCompanyCodeDivision;

/**
 * Home object for domain model class SrCompanyCodeDivision.
 * @see com.hs.alice.sr.dao.SrCompanyCodeDivision
 * @author Hibernate Tools
 */
@Repository
public class SrCompanyCodeDivisionDaoJpa implements SrCompanyCodeDivisionDao {

	private static final Log log = LogFactory
			.getLog(SrCompanyCodeDivisionDaoJpa.class);

	@PersistenceContext
	private EntityManager entityManager;

	public void persist(SrCompanyCodeDivision transientInstance) {
		log.debug("persisting SrCompanyCodeDivision instance");
		try {
			entityManager.persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(SrCompanyCodeDivision persistentInstance) {
		log.debug("removing SrCompanyCodeDivision instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public SrCompanyCodeDivision merge(SrCompanyCodeDivision detachedInstance) {
		log.debug("merging SrCompanyCodeDivision instance");
		try {
			SrCompanyCodeDivision result = entityManager
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	@Override
	public SrCompanyCodeDivision findById(int id) {
		log.debug("getting SrCompanyCodeDivision instance with id: " + id);
		try {
			SrCompanyCodeDivision instance = entityManager.find(
					SrCompanyCodeDivision.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	@Override
	public SrCompanyCodeDivision findByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SrCompanyCodeDivision> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
}
