package com.springinaction.spitter.service;

import java.util.List;

import com.springinaction.spitter.domain.Spitter;
import com.springinaction.spitter.domain.Spittle;

public interface SpitterService {
	void addSpitter(Spitter spitter);
	void saveSpitter(Spitter spitter);
	List<Spittle> getRecentSpittles(int spittlesPerPage);
	List<Spittle> getSpittlesForSpitter(String username);
	Spitter getSpitterById(long id);
	Spitter getSpitter(String username);
	Spittle getSpittleById(long id);
	void saveSpittle(Spittle spittle);
	void deleteSpittle(long id);

}
