package com.chris.tictactoe.business;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chris.tictactoe.game.exceptions.GameOverException;
import com.chris.tictactoe.game.exceptions.NoPlayersRegisteredException;
import com.chris.tictactoe.game.exceptions.PositionOccupiedException;
import com.chris.tictactoe.game.exceptions.WaitYourTurnException;
import com.chris.tictactoe.game.exceptions.WrongShapeException;
import com.chris.tictactoe.rest.business.GameManagerImpl;
import com.chris.tictactoe.rest.exceptions.GameNotStartedException;
import com.chris.tictactoe.rest.exceptions.UnsupportedCoordinateException;
import com.chris.tictactoe.rest.exceptions.UnsupportedShapeException;
import com.chris.tictactoe.rest.model.Game;
import com.chris.tictactoe.rest.model.GamePlayer;
import com.chris.tictactoe.rest.model.GameStatus;
import com.chris.tictactoe.rest.model.PlayerStatus;


public class GameManagerImplTest {
	private static final Logger LOG = LoggerFactory.getLogger(GameManagerImplTest.class);
	
	private GameManagerImpl gameManager;
	
	@Before
	public void setUp(){
		gameManager = new GameManagerImpl(new Game());
	}
	
	@Test
	public void testStartGame(){
		gameManager.startGame();
		
		assertNotNull(gameManager.getGame());
		assertEquals(GameStatus.STARTED, gameManager.getGame().getStatus());
	}
	
	@Test
	public void testRegisterPlayer() throws UnsupportedShapeException, WrongShapeException, GameNotStartedException{
		gameManager.startGame();
		
		GamePlayer aPlayer = new GamePlayer();
		aPlayer.setShape("o");
		gameManager.registerPlayer(aPlayer);
		
		assertNotNull(gameManager.getCirclePlayer());
		assertNull(gameManager.getCrossPlayer());
		
		GamePlayer anotherPlayer = new GamePlayer();
		anotherPlayer.setShape("X");
		gameManager.registerPlayer(anotherPlayer );
		
		assertNotNull(gameManager.getCirclePlayer());
		assertNotNull(gameManager.getCrossPlayer());
	}
	
	@Test(expected = GameNotStartedException.class)
	public void testRegisterPlayer_noGameStarted() throws UnsupportedShapeException, WrongShapeException, GameNotStartedException{
		GamePlayer aPlayer = new GamePlayer();
		aPlayer.setShape("o");
		gameManager.registerPlayer(aPlayer );
	}
	
	@Test(expected = UnsupportedShapeException.class)
	public void testRegisterPlayer_wrongShape() throws UnsupportedShapeException, WrongShapeException, GameNotStartedException{
		gameManager.startGame();
		GamePlayer aPlayer = new GamePlayer();
		aPlayer.setShape("circle");
		gameManager.registerPlayer(aPlayer);
	}
	
	@Test
	public void testPlay() throws PositionOccupiedException, WaitYourTurnException, GameOverException, UnsupportedCoordinateException, UnsupportedShapeException, GameNotStartedException, NoPlayersRegisteredException{
		setGameReady();
		gameManager.playCircle("A1");
		
		assertEquals(PlayerStatus.PLAYING, gameManager.getCirclePlayer().getStatus());
		
		gameManager.playCross("b2");
		assertEquals(PlayerStatus.PLAYING, gameManager.getCrossPlayer().getStatus());
	}
	
	@Test(expected = PositionOccupiedException.class)
	public void testPlay_positionOcupied() throws PositionOccupiedException, WaitYourTurnException, GameOverException, UnsupportedCoordinateException, NoPlayersRegisteredException, UnsupportedShapeException, GameNotStartedException{
		setGameReady();
		gameManager.playCircle("A1");
		gameManager.playCross("A1");
		
	}
	
	@Test(expected = WaitYourTurnException.class)
	public void testPlay_waitYourTurn() throws UnsupportedShapeException, GameNotStartedException, PositionOccupiedException, WaitYourTurnException, GameOverException, UnsupportedCoordinateException, NoPlayersRegisteredException{
		setGameReady();
		gameManager.playCircle("A1");
		gameManager.playCircle("A1");
	}
	
	@Test(expected = GameOverException.class)
	public void testPlay_gameOver() throws UnsupportedShapeException, GameNotStartedException, PositionOccupiedException, WaitYourTurnException, GameOverException, UnsupportedCoordinateException, NoPlayersRegisteredException{
		setGameReady();
		
		gameManager.playCircle("A1");
		gameManager.playCross("B1");
		
		gameManager.playCircle("A2");
		gameManager.playCross("B2");
		
		gameManager.playCircle("A3");
		gameManager.playCross("B3");
		
	}
	
	@Test(expected = UnsupportedCoordinateException.class)
	public void testPlay_unsupportedCoordinate() throws UnsupportedShapeException, GameNotStartedException, PositionOccupiedException, WaitYourTurnException, GameOverException, UnsupportedCoordinateException, NoPlayersRegisteredException{
		setGameReady();
		
		gameManager.playCircle("A0");
	}
	
	@Test(expected = NoPlayersRegisteredException.class)
	public void testPlay_noPlayers() throws PositionOccupiedException, WaitYourTurnException, GameOverException, UnsupportedCoordinateException, NoPlayersRegisteredException, GameNotStartedException{
		gameManager.startGame();
		gameManager.playCircle("A2");
	}
	
	@Test
	public void testCheckResults() throws UnsupportedShapeException, GameNotStartedException, UnsupportedCoordinateException, PositionOccupiedException, WaitYourTurnException, NoPlayersRegisteredException{
		setGameReady();
		String result = gameManager.checkResults();
		
		LOG.info("Game result: {}", result);
		assertEquals(GameStatus.IN_PROGRESS, gameManager.getGame().getStatus());
		
		try {
			gameManager.playCircle("A1");
			gameManager.playCross("B1");
			
			gameManager.playCircle("A2");
			gameManager.playCross("B2");
			
			gameManager.playCircle("A3");
			gameManager.playCross("B3");
		} catch (GameOverException e) {
			result = gameManager.checkResults();
			
			LOG.info("Game result: {}", result);
			
			assertEquals(GameStatus.FINISHED, gameManager.getGame().getStatus());
			assertEquals(PlayerStatus.WINNER, gameManager.getCirclePlayer().getStatus());
			assertEquals(PlayerStatus.LOSER, gameManager.getCrossPlayer().getStatus());
		}
	}
	
	@Test
	public void testResultsTie() throws UnsupportedShapeException, GameNotStartedException, PositionOccupiedException, WaitYourTurnException, UnsupportedCoordinateException, NoPlayersRegisteredException, GameOverException{
		setGameReady();
		
		gameManager.playCircle("A1");
		gameManager.playCross("c1");
		
		gameManager.playCircle("a3");
		gameManager.playCross("a2");
		
		gameManager.playCircle("b2");
		gameManager.playCross("c3");
		
		gameManager.playCircle("b1");
		gameManager.playCross("b3");
		
		gameManager.playCircle("c2");
		
		String result = gameManager.checkResults();
		
		LOG.info("Game result: {}", result);
		
		assertEquals(GameStatus.FINISHED, gameManager.getGame().getStatus());
		assertEquals(PlayerStatus.TIE, gameManager.getCirclePlayer().getStatus());
		assertEquals(PlayerStatus.TIE, gameManager.getCrossPlayer().getStatus());
		
	}
	
	private void setGameReady() throws UnsupportedShapeException, GameNotStartedException{
		gameManager.startGame();
		
		GamePlayer circle = new GamePlayer();
		circle.setShape("o");
		gameManager.registerPlayer(circle );
		
		GamePlayer cross = new GamePlayer();
		cross.setShape("x");
		gameManager.registerPlayer(cross);
	}
	
	


}
