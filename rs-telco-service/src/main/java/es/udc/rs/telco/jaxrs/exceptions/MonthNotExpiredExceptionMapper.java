package es.udc.rs.telco.jaxrs.exceptions;

import es.udc.rs.telco.jaxrs.dto.MonthNotExpiredExceptionDtoJaxb;
import es.udc.rs.telco.jaxrs.dto.NotAddedCustomerExceptionDtoJaxb;
import es.udc.rs.telco.model.telcoservice.exceptions.MonthNotExpiredException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class MonthNotExpiredExceptionMapper implements ExceptionMapper<MonthNotExpiredException> {

    @Override
    public Response toResponse(MonthNotExpiredException ex) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(new MonthNotExpiredExceptionDtoJaxb(ex.getMonth()))
                .build();
    }
}
