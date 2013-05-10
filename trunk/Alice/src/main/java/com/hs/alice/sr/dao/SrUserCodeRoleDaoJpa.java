package com.hs.alice.sr.dao;

// Generated 2013. 4. 29 오후 2:54:28 by Hibernate Tools 3.4.0.CR1

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.hs.alice.sr.domain.SrUserCodeRole;

/**
 * Home object for domain model class SrUserCodeRole.
 * @see com.hs.alice.sr.dao.SrUserCodeRole
 * @author Hibernate Tools
 */
@Repository
public class SrUserCodeRoleDaoJpa implements SrUserCodeRoleDao {

	private static final Log log = LogFactory.getLog(SrUserCodeRoleDaoJpa.class);

	@PersistenceContext
	private EntityManager entityManager;

	public void persist(SrUserCodeRole transientInstance) {
		log.debug("persisting SrUserCodeRole instance");
		try {
			entityManager.persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(SrUserCodeRole persistentInstance) {
		log.debug("removing SrUserCodeRole instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public SrUserCodeRole merge(SrUserCodeRole detachedInstance) {
		log.debug("merging SrUserCodeRole instance");
		try {
			SrUserCodeRole result = entityManager.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public SrUserCodeRole findById(int id) {
		log.debug("getting SrUserCodeRole instance with id: " + id);
		try {
			SrUserCodeRole instance = entityManager.find(SrUserCodeRole.class,
					id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	@Override
	public SrUserCodeRole findByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SrUserCodeRole> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
}
