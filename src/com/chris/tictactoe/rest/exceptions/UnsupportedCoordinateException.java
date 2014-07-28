package com.chris.tictactoe.rest.exceptions;

public class UnsupportedCoordinateException extends IllegalArgumentException {

	private static final long serialVersionUID = 1L;
	private static final String MESSAGE = "The coordinate is not valid, it must be something like A1, C1... Rows[A,B,C] and Cols[1,2,3] ";
	
	public UnsupportedCoordinateException() {
		super(MESSAGE);
	}

}
