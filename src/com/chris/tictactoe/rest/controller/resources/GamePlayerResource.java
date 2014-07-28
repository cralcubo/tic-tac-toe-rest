package com.chris.tictactoe.rest.controller.resources;

import javax.ws.rs.core.UriInfo;

import com.chris.tictactoe.rest.model.GamePlayer;

@SuppressWarnings("serial")
public class GamePlayerResource extends LinkResource {

	@SuppressWarnings("unchecked")
	public GamePlayerResource(UriInfo info, GamePlayer player) {
		super(info, player);
		
		put("shape", player.getShape());
		put("status", player.getStatus());
	}
	
	

}
