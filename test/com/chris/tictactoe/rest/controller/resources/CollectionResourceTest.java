package com.chris.tictactoe.rest.controller.resources;

import static org.junit.Assert.assertEquals;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;

import javax.ws.rs.core.UriInfo;

import org.easymock.EasyMock;
import org.junit.Test;

import com.chris.tictactoe.service.model.Game;
import com.chris.tictactoe.service.model.GamePlayer;
import com.chris.tictactoe.service.model.GameStatus;
import com.chris.tictactoe.service.model.PlayerStatus;

public class CollectionResourceTest {
	
	private CollectionResource collectionResource;
	
	@Test
	public void testConstructor() throws URISyntaxException{
		UriInfo info = EasyMock.createMock(UriInfo.class);
		String myUri = "http://localhost:8080/";
		URI baseUri = new URI(myUri);
		EasyMock.expect(info.getBaseUri()).andReturn(baseUri).times(4);
		EasyMock.replay(info);
		
		String subPath = LinkResource.GAMES;
		
		Collection<GameResource> collection = new ArrayList<>();
		
		Game game = new Game();
		game.setId(String.valueOf(12));

		GamePlayer circlePlayer = new GamePlayer();
		circlePlayer.setId(String.valueOf("1"));
		circlePlayer.setShape("O");
		circlePlayer.setStatus(PlayerStatus.PLAYING);
		
		game.setCirclePlayer(circlePlayer );
		
		
		GamePlayer crossPlayer = new GamePlayer();
		crossPlayer.setId(String.valueOf("2"));
		crossPlayer.setShape("X");
		crossPlayer.setStatus(PlayerStatus.PLAYING);
		game.setCrossPlayer(crossPlayer);
		
		game.setStatus(GameStatus.IN_PROGRESS);
		
		GameResource gameResource = new GameResource(info, game );
		collection.add(gameResource );
		
		collectionResource = new CollectionResource(info, subPath, collection);
		
		assertEquals(collection, collectionResource.get("items"));
		
	}

}
