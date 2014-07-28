package com.chris.tictactoe.rest.jersey;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
public class DefaultExceptionMapper implements ExceptionMapper<Exception> {
	private final static Logger LOG = LoggerFactory.getLogger(DefaultExceptionMapper.class);

	@Override
	public Response toResponse(Exception exception) {
		LOG.error("Error: ", exception);
		ErrorEnum error = ErrorEnum.getErrorEnum(exception);
		
		Response response;
		if(error != null){
			response = Response.status(error.getCode()).entity(new ErrorMapper(error, exception)).type(MediaType.APPLICATION_JSON).build();
		} else {
//			exception.printStackTrace();
			response = Response.status(500).entity(exception.getMessage()).type(MediaType.TEXT_PLAIN).build();
		}

		return response;
	}

}
