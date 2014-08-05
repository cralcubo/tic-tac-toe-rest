package com.chris.tictactoe.rest.controller;

import java.util.List;
import java.util.NoSuchElementException;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chris.tictactoe.game.exceptions.GameOverException;
import com.chris.tictactoe.game.exceptions.NoPlayersRegisteredException;
import com.chris.tictactoe.game.exceptions.PositionOccupiedException;
import com.chris.tictactoe.game.exceptions.WaitYourTurnException;
import com.chris.tictactoe.rest.controller.factory.PlayerResourceFactory;
import com.chris.tictactoe.rest.controller.resources.CollectionResource;
import com.chris.tictactoe.rest.controller.resources.GamePlayerResource;
import com.chris.tictactoe.rest.controller.resources.LinkResource;
import com.chris.tictactoe.service.business.GameManager;
import com.chris.tictactoe.service.business.GameManagerImpl;
import com.chris.tictactoe.service.dao.DAO;
import com.chris.tictactoe.service.dao.GameManagerStaticRepository;
import com.chris.tictactoe.service.dao.PlayerStaticRepository;
import com.chris.tictactoe.service.exceptions.GameNotStartedException;
import com.chris.tictactoe.service.exceptions.UnsupportedCoordinateException;
import com.chris.tictactoe.service.model.GamePlayer;
import com.chris.tictactoe.service.model.PlayerShape;

@Path(LinkResource.PLAYERS)
public class PlayersController extends BaseController {
	private static final Logger LOG = LoggerFactory.getLogger(PlayersController.class);

	private DAO<GamePlayer> playerRepository = PlayerStaticRepository.getInstance();
	private DAO<GameManagerImpl> managerRepository = GameManagerStaticRepository.getInstance();

	@Context
	UriInfo uriInfo;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public CollectionResource getAllPlayers(
			@DefaultValue("false") @QueryParam("expand") boolean expand) {

		List<LinkResource> resources = new ControllersUtility().createReources(new PlayerResourceFactory(), playerRepository.getAll(), uriInfo, expand);

		return new CollectionResource(uriInfo, LinkResource.PLAYERS, resources);
	}
	
	@Path("{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public GamePlayerResource getPlayer(@PathParam("id") String id){
		
		GamePlayer player = playerRepository.get(id);
		if(player != null){
			return new GamePlayerResource(uriInfo, player);
		}else {
			throw new NoSuchElementException(String.format("Player with ID: %s, not registered in the game.", id));
		}
	}
	
	@Path("{id}/{coordinate}")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response play(@PathParam("id") String id, @PathParam("coordinate") String coordinate) throws UnsupportedCoordinateException, PositionOccupiedException, WaitYourTurnException, GameOverException, GameNotStartedException{
		GamePlayer player = playerRepository.get(id);
		
		String[] idSplitted = id.split("-");
		String gameId = idSplitted[1];
		GameManager manager = managerRepository.get(gameId);
		
		try{
			if(player.getShape().equals(PlayerShape.CIRCLE.getShape())){
				manager.playCircle(coordinate);
			}else{
				manager.playCross(coordinate);
			}
		} catch(NoPlayersRegisteredException e){
			LOG.error("Players need to be registered before the game is started, this is suppose to be done when a new game is created, check that method!", e);
		}
		
		return modifiedResponse(new GamePlayerResource(uriInfo, player));
	}

}
