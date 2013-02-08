package com.springinaction.spitter.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.springinaction.spitter.domain.Spitter;
import com.springinaction.spitter.domain.Spittle;
import com.springinaction.spitter.persistence.SpitterDao;

@Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
public class SpitterServiceImpl implements SpitterService {

	private SpitterDao spitterDao;
	
	public void setSpitterDao(SpitterDao spitterDao) {
		this.spitterDao = spitterDao;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public void addSpitter(Spitter spitter) {
		spitterDao.addSpitter(spitter);
	}

	@Override
	public Spitter getSpitterById(long id) {
		return spitterDao.getSpitterById(id);
	}

	@Override
	public void saveSpitter(Spitter spitter) {
		spitterDao.saveSpitter(spitter);
	}

	@Override
	public List<Spittle> getRecentSpittles(int spittlesPerPage) {
		
		return spitterDao.getRecentSpittles(spittlesPerPage);
	}

	@Override
	public List<Spittle> getSpittlesForSpitter(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Spitter getSpitter(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Spittle getSpittleById(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveSpittle(Spittle spittle) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteSpittle(long id) {
		// TODO Auto-generated method stub
		
	}

}
