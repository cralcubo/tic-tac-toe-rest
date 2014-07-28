package com.chris.tictactoe.rest.controller;

import java.net.URI;

import javax.ws.rs.core.Response;

import com.chris.tictactoe.rest.controller.resources.LinkResource;

public abstract class BaseController {
	
	protected Response createResponse(LinkResource resource){
		String href = (String) resource.get(LinkResource.HREF);
		URI uri = URI.create(href);
		return Response.created(uri).entity(resource).build();
	}
	
	protected Response modifiedResponse(LinkResource resource){
		return Response.ok().entity(resource).build();
	}

}
