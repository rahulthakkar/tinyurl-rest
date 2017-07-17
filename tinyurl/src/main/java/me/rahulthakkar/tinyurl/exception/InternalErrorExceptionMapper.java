package me.rahulthakkar.tinyurl.exception;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import me.rahulthakkar.tinyurl.model.ErrorMessageModel;

@Provider
public class InternalErrorExceptionMapper implements ExceptionMapper<InternalError>{

	@Override
	public Response toResponse(InternalError exception) {
		
		return Response.status(Status.INTERNAL_SERVER_ERROR)
				.type(MediaType.APPLICATION_JSON)
				.entity(new ErrorMessageModel(exception.getMessage(), Status.INTERNAL_SERVER_ERROR.getStatusCode()))
				.build();
	}

}
