package com.hs.alice.sr.dao;

// Generated 2013. 4. 29 오후 2:54:28 by Hibernate Tools 3.4.0.CR1

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.hs.alice.sr.domain.SrInstallCodeProduct;

/**
 * Home object for domain model class SrInstallCodeProduct.
 * @see com.hs.alice.sr.dao.SrInstallCodeProduct
 * @author Hibernate Tools
 */
@Repository
public class SrInstallCodeProductDaoJpa implements SrInstallCodeProductDao {

	private static final Log log = LogFactory
			.getLog(SrInstallCodeProductDaoJpa.class);

	@PersistenceContext
	private EntityManager entityManager;

	public void persist(SrInstallCodeProduct transientInstance) {
		log.debug("persisting SrInstallCodeProduct instance");
		try {
			entityManager.persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(SrInstallCodeProduct persistentInstance) {
		log.debug("removing SrInstallCodeProduct instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public SrInstallCodeProduct merge(SrInstallCodeProduct detachedInstance) {
		log.debug("merging SrInstallCodeProduct instance");
		try {
			SrInstallCodeProduct result = entityManager.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public SrInstallCodeProduct findById(int id) {
		log.debug("getting SrInstallCodeProduct instance with id: " + id);
		try {
			SrInstallCodeProduct instance = entityManager.find(
					SrInstallCodeProduct.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	@Override
	public SrInstallCodeProduct findByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SrInstallCodeProduct> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
}
