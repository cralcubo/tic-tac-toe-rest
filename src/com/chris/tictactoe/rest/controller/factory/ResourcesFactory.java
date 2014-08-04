package com.chris.tictactoe.rest.controller.factory;

import javax.ws.rs.core.UriInfo;

import com.chris.tictactoe.rest.controller.resources.LinkResource;
import com.chris.tictactoe.service.model.Entity;

public interface ResourcesFactory<T extends LinkResource, U extends Entity > {
	
	T createResource(UriInfo uriInfo, U entity);
	

}
