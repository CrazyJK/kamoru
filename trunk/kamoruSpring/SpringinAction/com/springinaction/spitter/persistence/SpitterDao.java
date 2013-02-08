package com.springinaction.spitter.persistence;

import java.util.List;

import com.springinaction.spitter.domain.Spitter;
import com.springinaction.spitter.domain.Spittle;

public interface SpitterDao {
	void addSpitter(Spitter spitter);
	Spitter getSpitterById(long id);
	void saveSpitter(Spitter spitter);
	List<Spittle> getRecentSpittles(int spittlesPerPage);
}
