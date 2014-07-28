package com.chris.tictactoe.rest.controller.factory;

import javax.ws.rs.core.UriInfo;

import com.chris.tictactoe.rest.controller.resources.GameResource;
import com.chris.tictactoe.rest.model.Game;

public class GameResourceFactory implements ResourcesFactory<GameResource, Game> {

	@Override
	public GameResource createResource(UriInfo uriInfo, Game entity) {
		return new GameResource(uriInfo, entity);
	}

	

}
