package es.udc.rs.telco.jaxrs.exceptions;

import es.udc.rs.telco.jaxrs.dto.InvalidStatusExceptionDtoJaxb;
import es.udc.rs.telco.model.telcoservice.exceptions.InvalidStatusException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class InvalidStatusExceptionMapper implements ExceptionMapper<InvalidStatusException> {
    @Override
    public Response toResponse(InvalidStatusException ex) {
        return Response.status(Response.Status.NOT_ACCEPTABLE)
                .entity(new InvalidStatusExceptionDtoJaxb(ex.getPhoneCallId(),ex.getYear(),ex.getMonth()))
                .build();
    }
}
