package com.spx.containment.business.exceptions;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;



@Provider
public class NotFoundErrorMapper implements ExceptionMapper<NotFoundException> {
    @Override
    public Response toResponse(NotFoundException ex) {
        
        return Response.status(Status.NOT_FOUND).entity(ex.getMessage()).type(MediaType.TEXT_PLAIN)
                .build();
    }
}
