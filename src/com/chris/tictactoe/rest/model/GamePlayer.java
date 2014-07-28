package com.chris.tictactoe.rest.model;

public class GamePlayer extends Entity {
	
	private String shape;
	
	private PlayerStatus status;

	public String getShape() {
		return shape;
	}

	public void setShape(String shape) {
		this.shape = shape;
	}

	public PlayerStatus getStatus() {
		return status;
	}

	public void setStatus(PlayerStatus status) {
		this.status = status;
	}

}
