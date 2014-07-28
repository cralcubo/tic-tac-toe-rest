package com.chris.tictactoe.rest.business;

import com.chris.tictactoe.game.exceptions.GameOverException;
import com.chris.tictactoe.game.exceptions.NoPlayersRegisteredException;
import com.chris.tictactoe.game.exceptions.PositionOccupiedException;
import com.chris.tictactoe.game.exceptions.WaitYourTurnException;
import com.chris.tictactoe.rest.exceptions.GameNotStartedException;
import com.chris.tictactoe.rest.exceptions.UnsupportedCoordinateException;
import com.chris.tictactoe.rest.exceptions.UnsupportedShapeException;
import com.chris.tictactoe.rest.model.Game;
import com.chris.tictactoe.rest.model.GamePlayer;

public interface GameManager {
	
	void startGame();
	
	void registerPlayer(GamePlayer aPlayer) throws UnsupportedShapeException, GameNotStartedException;
	
	void playCircle(String aCoordinate) throws PositionOccupiedException, WaitYourTurnException, GameOverException, UnsupportedCoordinateException, NoPlayersRegisteredException, GameNotStartedException;
	
	void playCross(String aCoordinate) throws PositionOccupiedException, WaitYourTurnException, GameOverException, UnsupportedCoordinateException, NoPlayersRegisteredException, GameNotStartedException;
	
	String checkResults();
	
	Game getGame();
	
	GamePlayer getCirclePlayer();
	
	GamePlayer getCrossPlayer();

}
