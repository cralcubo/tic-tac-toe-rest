package com.chris.tictactoe.rest.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ws.rs.core.UriInfo;

import com.chris.tictactoe.rest.controller.factory.ResourcesFactory;
import com.chris.tictactoe.rest.controller.resources.LinkResource;
import com.chris.tictactoe.rest.model.Entity;

public class ControllersUtility {
	
	public <T extends LinkResource, U extends Entity> List<LinkResource> createReources(ResourcesFactory<T,U> factory, Collection<U> entities, UriInfo uriInfo, boolean expand){
		List<LinkResource> resources = new ArrayList<>();
		
		for(U entity : entities){
			LinkResource resource;
			
			if(expand){
				resource = factory.createResource(uriInfo, entity);
			} else {
				resource = new LinkResource(uriInfo, entity);
			}
			
			resources.add(resource);
		}
		
		return resources;
	}

}
