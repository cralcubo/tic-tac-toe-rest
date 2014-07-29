package com.chris.tictactoe.rest.jersey;

import java.util.LinkedHashMap;

public class ErrorMapper extends LinkedHashMap<String, String> {

	private static final long serialVersionUID = 1L;
	
	private static final String STATUS_PROP_NAME = "status";
    private static final String CODE_PROP_NAME = "code";
    private static final String MESSAGE_PROP_NAME = "message";
    private static final String MORE_INFO_PROP_NAME = "moreInfo";

    private static final String DEFAULT_MORE_INFO_URL = "mailto:christian@anEmail.com";
	
	public ErrorMapper(ErrorEnum errorEnum, Exception exception) {
		put(STATUS_PROP_NAME, errorEnum.getStatus());
		put(CODE_PROP_NAME, String.valueOf(errorEnum.getCode()));
		put(MESSAGE_PROP_NAME, exception.getMessage());
		put(MORE_INFO_PROP_NAME, DEFAULT_MORE_INFO_URL);
	}

}
