package com.chris.tictactoe.rest.controller.resources;

import java.util.LinkedHashMap;

import javax.ws.rs.core.UriInfo;

import com.chris.tictactoe.service.model.Entity;


@SuppressWarnings({ "rawtypes", "serial" })
public class LinkResource extends LinkedHashMap {
	private static final String PATH_SEPARATOR = "/";
	
	public static final String HREF = "href";
	
	public final static String GAMES = PATH_SEPARATOR + "games";

	public final static String PLAYERS = PATH_SEPARATOR + "players";
	
	public LinkResource(UriInfo info, Entity entity) {
        this(getFullyQualifiedContextPath(info), entity);
    }
	
	@SuppressWarnings("unchecked")
	public LinkResource(String fqBasePath, Entity entity){
		String href = createHref(fqBasePath, entity);
        put(HREF, href);
	}
	
	@SuppressWarnings("unchecked")
	public LinkResource(String fullyQualifiedBasePath, String subPath) {
		put(HREF, fullyQualifiedBasePath + subPath);
	}
	
	public LinkResource(UriInfo info, String subPath){
		 this(getFullyQualifiedContextPath(info), subPath);
	}

	private String createHref(String fqBasePath, Entity entity) {
		StringBuilder sb = new StringBuilder(fqBasePath);
        
		ResourcePath path = ResourcePath.getResourcePath(entity.getClass());
        sb.append(path.getPath()).append(PATH_SEPARATOR).append(entity.getId());
        
        return sb.toString();
	}

	private static String getFullyQualifiedContextPath(UriInfo info){
		String base = info.getBaseUri().toString();
		
		if(base.endsWith(PATH_SEPARATOR)){
			return base.substring(0, base.length() - 1);
		}
		
		return base;
	}

}
