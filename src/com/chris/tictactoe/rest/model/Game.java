package com.chris.tictactoe.rest.model;

import java.util.Map;

public class Game extends Entity {
	
	private GameStatus status;
	
	private GamePlayer crossPlayer;
	
	private GamePlayer circlePlayer;
	
	private Map<String, String> gameMatrix;

	public GameStatus getStatus() {
		return status;
	}

	public void setStatus(GameStatus status) {
		this.status = status;
	}

	public GamePlayer getCrossPlayer() {
		return crossPlayer;
	}

	public void setCrossPlayer(GamePlayer crossPlayer) {
		this.crossPlayer = crossPlayer;
	}

	public GamePlayer getCirclePlayer() {
		return circlePlayer;
	}

	public void setCirclePlayer(GamePlayer circlePlayer) {
		this.circlePlayer = circlePlayer;
	}

	public Map<String, String> getGameMatrix() {
		return gameMatrix;
	}

	public void setGameMatrix(Map<String, String> gameMatrix) {
		this.gameMatrix = gameMatrix;
	}
}
