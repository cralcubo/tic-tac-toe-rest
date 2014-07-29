package com.chris.tictactoe.rest.business;

import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chris.tictactoe.game.GameCoordinates;
import com.chris.tictactoe.game.TicTacToe;
import com.chris.tictactoe.game.TicTacToeImpl;
import com.chris.tictactoe.game.exceptions.GameOverException;
import com.chris.tictactoe.game.exceptions.NoPlayersRegisteredException;
import com.chris.tictactoe.game.exceptions.PositionOccupiedException;
import com.chris.tictactoe.game.exceptions.WaitYourTurnException;
import com.chris.tictactoe.game.exceptions.WrongShapeException;
import com.chris.tictactoe.game.model.Player;
import com.chris.tictactoe.game.model.shapes.Circle;
import com.chris.tictactoe.game.model.shapes.Cross;
import com.chris.tictactoe.game.model.shapes.TicTacToeShape;
import com.chris.tictactoe.rest.exceptions.GameNotStartedException;
import com.chris.tictactoe.rest.exceptions.UnsupportedCoordinateException;
import com.chris.tictactoe.rest.exceptions.UnsupportedShapeException;
import com.chris.tictactoe.rest.model.Entity;
import com.chris.tictactoe.rest.model.Game;
import com.chris.tictactoe.rest.model.GamePlayer;
import com.chris.tictactoe.rest.model.GameStatus;
import com.chris.tictactoe.rest.model.PlayerShape;
import com.chris.tictactoe.rest.model.PlayerStatus;

public class GameManagerImpl extends Entity implements GameManager  {
	
	private static final Logger LOG = LoggerFactory.getLogger(GameManagerImpl.class);

	private static final String EMPTY_STR = "EMPTY";
	
	private TicTacToe ticTacToe;
	private Player circlePlayer;
	private Player crossPlayer;
	
	private Game game;
	private GamePlayer circle;
	private GamePlayer cross;
	
	public GameManagerImpl(Game game) {
		this.game = game;
	}
	
	public void startGame(){
		ticTacToe = new TicTacToeImpl();
		game.setStatus(GameStatus.STARTED);
	}
	
	public void registerPlayer(GamePlayer aPlayer) throws UnsupportedShapeException, GameNotStartedException{
		gameStartedChecker();
		
		PlayerShape shape = PlayerShape.getPlayerShape(aPlayer.getShape());
		aPlayer.setStatus(PlayerStatus.PLAYING);
		
		if(shape == PlayerShape.CIRCLE){
			circlePlayer = new Player(new Circle());
			game.setCirclePlayer(aPlayer);
			circle = aPlayer;
		} else {
			crossPlayer = new Player(new Cross());
			game.setCrossPlayer(aPlayer);
			cross = aPlayer;
		}
		
		if(circlePlayer != null & crossPlayer != null){
			try {
				ticTacToe.registerPlayers(circlePlayer, crossPlayer);
			} catch (WrongShapeException e) {
				LOG.error("Something really weird happened registering the players.", e);
			}
			
		}
	}
	
	public void playCircle(String aCoordinate) throws PositionOccupiedException, WaitYourTurnException, GameOverException, UnsupportedCoordinateException, NoPlayersRegisteredException, GameNotStartedException{
		play(aCoordinate, PlayerShape.CIRCLE);
	}
	
	public void playCross(String aCoordinate) throws UnsupportedCoordinateException, PositionOccupiedException, WaitYourTurnException, GameOverException, NoPlayersRegisteredException, GameNotStartedException{
		play(aCoordinate, PlayerShape.CROSS);
	}
	
	private void play(String aCoordinate, PlayerShape playerShape) throws GameNotStartedException, UnsupportedCoordinateException, PositionOccupiedException, WaitYourTurnException, GameOverException, NoPlayersRegisteredException{
		gameStartedChecker();
		
		game.setStatus(GameStatus.IN_PROGRESS);
		
		GameCoordinates coordinate = getCoordinates(aCoordinate);
		GamePlayer player;
		if(playerShape == PlayerShape.CIRCLE){
			ticTacToe.playCircle(coordinate);
			player = circle;
		} else {
			ticTacToe.playCross(coordinate);
			player = cross;
		}
		
		player.setStatus(PlayerStatus.PLAYING);
	}
	
	public String checkResults(){
		Player player = ticTacToe.checkWinner();
		if(player != null){
			game.setStatus(GameStatus.FINISHED);
			if(player.getShape() instanceof Circle){
				circle.setStatus(PlayerStatus.WINNER);
				cross.setStatus(PlayerStatus.LOSER);
			} else {
				cross.setStatus(PlayerStatus.WINNER);
				circle.setStatus(PlayerStatus.LOSER);
			}
			
			return String.format("There is a winner! Winner is: %s", player.getShape().toString());
		} else if(ticTacToe.isTie()){
			game.setStatus(GameStatus.FINISHED);
			cross.setStatus(PlayerStatus.TIE);
			circle.setStatus(PlayerStatus.TIE);
			
			return "There is a tie!";
		} else {
			game.setStatus(GameStatus.IN_PROGRESS);
			cross.setStatus(PlayerStatus.PLAYING);
			circle.setStatus(PlayerStatus.PLAYING);
			
			return "No winner yet, keep playing!"; 
		}
		
	}
	
	public void checkGameMatrix(){
		Map<String, String> matrix = new TreeMap<String, String>();
		
		if(ticTacToe != null){
			Map<GameCoordinates, TicTacToeShape> tictactoeMatrix =  ticTacToe.getGameMatrix();
			for(GameCoordinates coordinates : tictactoeMatrix.keySet()){
				String shape = (tictactoeMatrix.get(coordinates) != null) ? tictactoeMatrix.get(coordinates).toString() : EMPTY_STR;
				matrix.put(coordinates.name(), shape );
			}
		}
		
		game.setGameMatrix(matrix);
	}

	private GameCoordinates getCoordinates(String aCoordinate) {
		for(GameCoordinates coordinate : GameCoordinates.values()){
			if(coordinate.name().equalsIgnoreCase(aCoordinate)){
				return coordinate;
			}
		}
		
		throw new UnsupportedCoordinateException();
	}

	private void gameStartedChecker() throws GameNotStartedException {
		if(ticTacToe == null ){
			throw new GameNotStartedException();
		}
		
	}

	public Game getGame(){
		return game;
	}

	public GamePlayer getCirclePlayer() {
		return circle;
	}

	public GamePlayer getCrossPlayer() {
		return cross;
	}
	
}
