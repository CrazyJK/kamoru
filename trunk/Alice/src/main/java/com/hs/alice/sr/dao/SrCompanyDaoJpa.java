package com.hs.alice.sr.dao;

// Generated 2013. 4. 29 오후 2:54:28 by Hibernate Tools 3.4.0.CR1

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.hs.alice.sr.domain.SrCompany;

/**
 * Home object for domain model class SrCompany.
 * @see com.hs.alice.sr.dao.SrCompany
 * @author Hibernate Tools
 */
@Repository
public class SrCompanyDaoJpa implements SrCompanyDao {

	private static final Log log = LogFactory.getLog(SrCompanyDaoJpa.class);

	@PersistenceContext
	private EntityManager entityManager;

	public void persist(SrCompany transientInstance) {
		log.debug("persisting SrCompany instance");
		try {
			entityManager.persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(SrCompany persistentInstance) {
		log.debug("removing SrCompany instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public SrCompany merge(SrCompany detachedInstance) {
		log.debug("merging SrCompany instance");
		try {
			SrCompany result = entityManager.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public SrCompany findById(int id) {
		log.debug("getting SrCompany instance with id: " + id);
		try {
			SrCompany instance = entityManager.find(SrCompany.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	@Override
	public SrCompany findByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SrCompany> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
}
