package com.chris.tictactoe.rest.controller.resources;

import java.util.Collection;
import java.util.Collections;

import javax.ws.rs.core.UriInfo;

public class CollectionResource extends LinkResource {
	private static final long serialVersionUID = 8233746078932888998L;
	
	private static final int COLLECTION_LIMIT = 25;
	
	public CollectionResource(UriInfo info, String subPath, Collection<? extends LinkResource> collection) {
        this(info, subPath, collection, 0, getLimit(collection));
    }
	
	@SuppressWarnings("unchecked")
	CollectionResource(UriInfo info, String subPath, Collection<? extends LinkResource> collection, int offset, int limit) {
        super(info, subPath);
        put("offset", offset);
        put("limit", getLimit(limit));
        put("items", collection != null ? collection : Collections.emptyList());
    }

	private static int getLimit(Collection<?> collection) {
		return collection != null ? getLimit(collection.size()) : 0;
	}

	private static int getLimit(int size) {
		return Math.max(COLLECTION_LIMIT, size);
	}

	

}
