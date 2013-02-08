package com.springinaction.spitter.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.springinaction.spitter.domain.Spitter;
import com.springinaction.spitter.domain.Spittle;

@Repository("spitterDao-jpa")
public class JpaSpitterDao implements SpitterDao {

//	@PersistenceContext
	private EntityManager em;
	
	@Override
	public void addSpitter(Spitter spitter) {
		em.persist(spitter);
	}

	@Override
	public Spitter getSpitterById(long id) {
		return em.find(Spitter.class, id);
	}

	@Override
	public void saveSpitter(Spitter spitter) {
		em.merge(spitter);
	}

	@Override
	public List<Spittle> getRecentSpittles(int spittlesPerPage) {
		// TODO Auto-generated method stub
		return null;
	}

}
