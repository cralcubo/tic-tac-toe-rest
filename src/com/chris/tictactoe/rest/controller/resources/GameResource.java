package com.chris.tictactoe.rest.controller.resources;

import javax.ws.rs.core.UriInfo;

import com.chris.tictactoe.service.model.Game;

public class GameResource extends LinkResource {
	private static final long serialVersionUID = -6005468771349444288L;
	
	@SuppressWarnings("unchecked")
	public GameResource(UriInfo info, Game game) {
		super(info, game);
		
		put("player1", new GamePlayerResource(info, game.getCrossPlayer()));
		put("player2", new GamePlayerResource(info, game.getCirclePlayer()));
		
		put("status", game.getStatus());
		put("matrix", game.getGameMatrix());
	}

}
