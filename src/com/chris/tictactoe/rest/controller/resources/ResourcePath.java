package com.chris.tictactoe.rest.controller.resources;

import com.chris.tictactoe.rest.model.Entity;
import com.chris.tictactoe.rest.model.Game;
import com.chris.tictactoe.rest.model.GamePlayer;

public enum ResourcePath {
	
	GAMES(LinkResource.GAMES, Game.class),
	PLAYERS(LinkResource.PLAYERS, GamePlayer.class);
	
	private String path;
	
	private Class<? extends Entity> clazz;
	
	private ResourcePath(String path, Class<? extends Entity> clazz) {
		this.path = path;
		this.clazz = clazz;
	}
	
	public static ResourcePath getResourcePath(Class<? extends Entity> entity){
		for(ResourcePath resourcePath : values()){
			if(resourcePath.clazz.isAssignableFrom(entity)){
				return resourcePath;
			}
		}
		
		throw new IllegalArgumentException(String.format("There is no ResourcePath for the class: %s", entity.getName()));
	}
	
	public String getPath(){
		return path;
	}

}
