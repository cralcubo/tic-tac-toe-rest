package com.chris.tictactoe.rest.jersey;

import java.util.NoSuchElementException;

import com.chris.tictactoe.game.exceptions.GameOverException;
import com.chris.tictactoe.game.exceptions.NoPlayersRegisteredException;
import com.chris.tictactoe.game.exceptions.PositionOccupiedException;
import com.chris.tictactoe.game.exceptions.WaitYourTurnException;
import com.chris.tictactoe.rest.exceptions.GameNotStartedException;
import com.chris.tictactoe.rest.exceptions.UnsupportedCoordinateException;
import com.chris.tictactoe.rest.exceptions.UnsupportedShapeException;
import com.sun.jersey.api.NotFoundException;

public enum ErrorEnum {
	UNSUPPORTED_OPERATION(UnsupportedOperationException.class, 500, "Internal Error (Method tried to be excecuted is not supported)"),
	NO_SUCH_ELEMENT(NoSuchElementException.class, 404, "Not Found"),
	NULL_POINTER(NullPointerException.class, 500, "Internal Error (Null Pointer Exception)"),
	JERSEY_NOT_FOUND(NotFoundException.class, 404, "Not Found method"),
	UNSUPPORTED_SHAPE(UnsupportedShapeException.class, 500, "Internal Game Exception"),
	UNSUPPORTED_COORDINATE(UnsupportedCoordinateException.class, 500, "Internal Game Exception"),
	POSITION_OCCUPIED(PositionOccupiedException.class, 500, "Internal Game Exception"),
	WAIT_YOUR_TURN(WaitYourTurnException.class, 500, "Internal Game Exception"),
	GAME_OVER(GameOverException.class, 500, "Internal Game Exception"),
	NO_PLAYERS(NoPlayersRegisteredException.class, 500, "Internal Game Error"),
	NO_STARTED(GameNotStartedException.class, 500, "Internal Game Error");
	
	private Class<? extends Exception> clazz;
	private String status;
	private int code;
	
	private ErrorEnum(Class<? extends Exception> clazz, int code, String status) {
		this.clazz = clazz;
		this.status = status;
		this.code = code;
	}
	
	public static ErrorEnum getErrorEnum(Exception e){
		for(ErrorEnum error : values()){
			if(error.clazz.isAssignableFrom(e.getClass())){
				return error;
			}
		}
		
		return null;
	}

	public String getStatus() {
		return status;
	}

	public int getCode() {
		return code;
	}

}
