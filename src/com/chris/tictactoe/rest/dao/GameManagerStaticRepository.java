package com.chris.tictactoe.rest.dao;

import java.util.HashMap;
import java.util.Map;

import com.chris.tictactoe.rest.business.GameManagerImpl;

public class GameManagerStaticRepository extends StaticRepository<GameManagerImpl> {
	
	private final static Map<String, GameManagerImpl> REPO = new HashMap<>();
	
	private static GameManagerStaticRepository staticRepo;
	

	public GameManagerStaticRepository(Map<String, GameManagerImpl> staticRepo) {
		super(staticRepo);
	}
	
	public static GameManagerStaticRepository getInstance(){
		if(staticRepo == null){
			staticRepo = new GameManagerStaticRepository(REPO);
		}
		
		return staticRepo;
	}

}
