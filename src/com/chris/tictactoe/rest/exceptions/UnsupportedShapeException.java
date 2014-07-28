package com.chris.tictactoe.rest.exceptions;

public class UnsupportedShapeException extends IllegalArgumentException {

	private static final long serialVersionUID = -5389803297859222602L;
	private static final String MESSAGE = "Unsupported shape, shape can be either 'O' or 'X'";
	
	public UnsupportedShapeException() {
		super(MESSAGE);
	}

}
