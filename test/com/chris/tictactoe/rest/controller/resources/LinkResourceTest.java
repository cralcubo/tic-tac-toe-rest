package com.chris.tictactoe.rest.controller.resources;

import static org.junit.Assert.assertEquals;

import java.net.URI;
import java.net.URISyntaxException;

import javax.ws.rs.core.UriInfo;

import org.easymock.EasyMock;
import org.junit.Test;

import com.chris.tictactoe.service.model.Entity;
import com.chris.tictactoe.service.model.Game;

public class LinkResourceTest {
	
	private LinkResource linkResource;
	
	@Test
	public void testLink_uriEntity() throws URISyntaxException{
		UriInfo info = EasyMock.createMock(UriInfo.class);
		Entity entity = new Game();
		entity.setId(String.valueOf(1));
		
		String myUri = "http://localhost:8080/";
		URI baseUri = new URI(myUri);
		EasyMock.expect(info.getBaseUri()).andReturn(baseUri);
		EasyMock.replay(info);
		
		linkResource = new LinkResource(info, entity);
		
		String hrefExpected = String.format("%sgames/1", baseUri);
		
		assertEquals(hrefExpected, linkResource.get("href"));
	}
	
	@Test
	public void testLink_basePath_Entity(){
		String fqBasePath = "http://localhost:8080";
		
		Entity entity = new Game();
		entity.setId(String.valueOf("10"));
		
		linkResource = new LinkResource(fqBasePath, entity);
		
		String hrefExpected = String.format("%s/games/10", fqBasePath);
		
		assertEquals(hrefExpected, linkResource.get("href"));
	}
	
	@Test
	public void testLink_basePath_subPath(){
		String fullyQualifiedBasePath = "http://localhost:8080";
		String subPath = "/games";
		
		linkResource = new LinkResource(fullyQualifiedBasePath, subPath);
		
		String hrefExpected = String.format("%s%s", fullyQualifiedBasePath, subPath);
		assertEquals(hrefExpected, linkResource.get("href"));
	}
	
	@Test
	public void testLink_uri_subPath() throws URISyntaxException{
		String subPath = "/games";
		
		UriInfo info = EasyMock.createMock(UriInfo.class);
		String myUri = "http://localhost:8080/";
		URI baseUri = new URI(myUri);
		EasyMock.expect(info.getBaseUri()).andReturn(baseUri);
		EasyMock.replay(info);
		
		linkResource = new LinkResource(info, subPath);
		
		String hrefExpected = String.format("%sgames", myUri);
		assertEquals(hrefExpected, linkResource.get("href"));
	}
	
	

}
