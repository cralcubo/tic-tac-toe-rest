package com.chris.tictactoe.rest.controller;

import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.chris.tictactoe.rest.controller.factory.GameResourceFactory;
import com.chris.tictactoe.rest.controller.resources.CollectionResource;
import com.chris.tictactoe.rest.controller.resources.GameResource;
import com.chris.tictactoe.rest.controller.resources.LinkResource;
import com.chris.tictactoe.service.business.GameManager;
import com.chris.tictactoe.service.business.GameManagerImpl;
import com.chris.tictactoe.service.dao.DAO;
import com.chris.tictactoe.service.dao.GameManagerStaticRepository;
import com.chris.tictactoe.service.dao.GameStaticRepository;
import com.chris.tictactoe.service.dao.PlayerStaticRepository;
import com.chris.tictactoe.service.exceptions.GameNotStartedException;
import com.chris.tictactoe.service.exceptions.UnsupportedShapeException;
import com.chris.tictactoe.service.model.Game;
import com.chris.tictactoe.service.model.GamePlayer;
import com.chris.tictactoe.service.model.GameStatus;

@Path(LinkResource.GAMES)
public class GamesController extends BaseController {
	
	private DAO<Game> gameRepository;
	private DAO<GamePlayer> playerRepository;
	private DAO<GameManagerImpl> managerRepository;
	
	@Context
	private UriInfo uriInfo;
	
	public GamesController() {
		gameRepository = GameStaticRepository.getInstance();
		playerRepository = PlayerStaticRepository.getInstance();
		managerRepository = GameManagerStaticRepository.getInstance();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public CollectionResource getGames(@DefaultValue("false") @QueryParam("expand") boolean expand){
		List<LinkResource> resources = new ControllersUtility().createReources(new GameResourceFactory(), gameRepository.getAll(), uriInfo, expand);

		return new CollectionResource(uriInfo, LinkResource.GAMES, resources);
	}
	
	@Path("/{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public GameResource getGame(@PathParam("id") String id){
		Game game = gameRepository.get(id);
		
		if(game.getStatus() != null){
			GameManager manager = managerRepository.get(id);
			manager.checkGameMatrix();
			manager.checkResults();
		}
		
		return new GameResource(uriInfo, game);
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response create(){
		Game game = new Game();
		String id = gameRepository.save(game);
		game.setId(id);
		
		GamePlayer circlePlayer = new GamePlayer();
		circlePlayer.setId(String.format("circle-%s", id));
		circlePlayer.setShape("O");
		game.setCirclePlayer(circlePlayer);
		playerRepository.save(circlePlayer);
		
		GamePlayer crossPlayer = new GamePlayer();
		crossPlayer.setId(String.format("cross-%s", id));
		crossPlayer.setShape("X");
		game.setCrossPlayer(crossPlayer);
		playerRepository.save(crossPlayer);
		
		GameManagerImpl manager = new GameManagerImpl(game);
		manager.setId(id);
		managerRepository.save(manager);
		
		return createResponse(new GameResource(uriInfo, game));
	}
	
	@Path("{id}")
	@DELETE
	public void delete(@PathParam("id") String id){
		gameRepository.delete(id);
	}
	
	@Path("{id}")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateGame(@PathParam("id") String id, Map<String, String> body) throws UnsupportedShapeException, GameNotStartedException {
		GameManager manager = managerRepository.get(id);
		Game game = gameRepository.get(id);
		
		String gameStatus = body.get("status");
		
		String unsupportedMessage = String.format("Game status can be either %s or %s", GameStatus.STARTED, GameStatus.FINISHED);
		
		if(gameStatus == null){
			throw new UnsupportedOperationException(unsupportedMessage);
		}
		
		if(gameStatus.equalsIgnoreCase(GameStatus.STARTED.name())){
			manager.startGame();
			
			manager.registerPlayer(game.getCirclePlayer());
			manager.registerPlayer(game.getCrossPlayer());
		} else if(gameStatus.equalsIgnoreCase(GameStatus.FINISHED.name())){
			game.setStatus(GameStatus.FINISHED);
		} else {
			throw new UnsupportedOperationException(unsupportedMessage);
		}
		
		return modifiedResponse(new GameResource(uriInfo, game));
	}

}
