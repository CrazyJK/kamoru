package com.hs.alice.sr.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.hs.alice.sr.domain.RndRqst;

//@Repository
public class RqstDaoJpa implements RqstDao {

	private static final Log logger = LogFactory.getLog(RqstDaoJpa.class);

	private static final String FROM = "select r from RndRqst r ";
	
	private static final String FETCH = "LEFT JOIN FETCH u.authRoleUserMaps ";

	@PersistenceContext(unitName = "csd")
	private EntityManager entityManager;

	
	@Override
	public void persist(RndRqst transientInstance) {
		// TODO Auto-generated method stub

	}

	@Override
	public void remove(RndRqst persistentInstance) {
		// TODO Auto-generated method stub

	}

	@Override
	public RndRqst merge(RndRqst detachedInstance) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RndRqst findById(int id) {
		logger.info(id);
		return this.entityManager.find(RndRqst.class, id);
	}

	@Override
	public RndRqst findByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RndRqst> findAll() {
		logger.info("");
		return this.entityManager.createQuery(FROM + "ORDER BY r.rqstid DESC", RndRqst.class)
				.setFirstResult(10).setMaxResults(5)
				.getResultList();
	}

}
