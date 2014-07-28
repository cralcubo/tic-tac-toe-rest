package com.chris.tictactoe.rest.model;

public class Game extends Entity {
	
	private GameStatus status;
	
	private GamePlayer crossPlayer;
	
	private GamePlayer circlePlayer;

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
}
