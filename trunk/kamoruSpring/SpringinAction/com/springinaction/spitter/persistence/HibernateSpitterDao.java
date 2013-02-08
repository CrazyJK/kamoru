package com.springinaction.spitter.persistence;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.springinaction.spitter.domain.Spitter;
import com.springinaction.spitter.domain.Spittle;

@Repository("spitterDao-hi")
public class HibernateSpitterDao implements SpitterDao {

//	@Autowired
	private SessionFactory sessionFactory;
	
	private Session currentSession() {
		return sessionFactory.getCurrentSession();
	}
	@Override
	public void addSpitter(Spitter spitter) {
		currentSession().save(spitter);
	}

	@Override
	public Spitter getSpitterById(long id) {
		return (Spitter)currentSession().get(Spitter.class, id);
	}

	@Override
	public void saveSpitter(Spitter spitter) {
		currentSession().update(spitter);
	}
	@Override
	public List<Spittle> getRecentSpittles(int spittlesPerPage) {
		// TODO Auto-generated method stub
		return null;
	}

}
