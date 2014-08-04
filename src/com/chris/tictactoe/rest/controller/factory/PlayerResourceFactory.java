package com.chris.tictactoe.rest.controller.factory;

import javax.ws.rs.core.UriInfo;

import com.chris.tictactoe.rest.controller.resources.GamePlayerResource;
import com.chris.tictactoe.service.model.GamePlayer;

public class PlayerResourceFactory implements ResourcesFactory<GamePlayerResource, GamePlayer>{

	@Override
	public GamePlayerResource createResource(UriInfo uriInfo, GamePlayer entity) {
		return new GamePlayerResource(uriInfo, entity);
	}

}
