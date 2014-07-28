package com.chris.tictactoe.rest.dao;

import java.util.Collection;

import com.chris.tictactoe.rest.model.Entity;

public interface DAO<E extends Entity> {
	
	Collection<E> getAll();
	
	E get(String id);
	
	void delete(String id);
	
	void save(String id, E entity);
	
	String save(E entity);
	
	void update(E entity);

}
