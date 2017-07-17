package me.rahulthakkar.tinyurl.exception;

import javax.ws.rs.NotAllowedException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import me.rahulthakkar.tinyurl.model.ErrorMessageModel;

@Provider
public class NotAllowedExceptionMapper implements ExceptionMapper<NotAllowedException>{

	@Override
	public Response toResponse(NotAllowedException exception) {
		
		return Response.status(Status.METHOD_NOT_ALLOWED)
				.type(MediaType.APPLICATION_JSON)
				.entity(new ErrorMessageModel(exception.getMessage(), Status.METHOD_NOT_ALLOWED.getStatusCode()))
				.build();
	}

}
