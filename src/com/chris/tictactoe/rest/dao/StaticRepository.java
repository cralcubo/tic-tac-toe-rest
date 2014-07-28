package com.chris.tictactoe.rest.dao;

import java.util.Collection;
import java.util.Map;
import java.util.NoSuchElementException;

import com.chris.tictactoe.rest.model.Entity;


public class StaticRepository<E extends Entity> implements DAO<E> {
	
	private final Map<String, E> staticRepo;
	
	public StaticRepository(Map<String, E> staticRepo) {
		this.staticRepo = staticRepo;
	}
	
	@Override
	public Collection<E> getAll() {
		return staticRepo.values();
	}

	@Override
	public E get(String id) {
		E entity = staticRepo.get(id);
		if(entity != null){
			return entity;
		}
		
		throw new NoSuchElementException(String.format("The object with ID: '%s' does not exist.", id));
	}

	@Override
	public void delete(String id) {
		E delted = staticRepo.remove(id);
		if(delted == null){
			throw new NoSuchElementException(String.format("The object with ID: '%s' could not be deleted becuase it does not exist.", id));
		}
	}

	@Override
	public void update(E entity) {
		String key = entity.getId();
		if(staticRepo.containsKey(key)){
			staticRepo.put(key, entity);
		} else {
			throw new NoSuchElementException(String.format("The object with ID: '%s' could not be updated becuase it does not exist.", key));
		}
		
	}

	@Override
	public void save(String id, E entity) {
		staticRepo.put(id, entity);
	}

	@Override
	public String save(E entity) {
		staticRepo.put(entity.getId(), entity);
		
		return entity.getId();
	}

}
