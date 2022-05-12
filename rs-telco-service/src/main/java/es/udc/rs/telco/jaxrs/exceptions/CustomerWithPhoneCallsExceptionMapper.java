package es.udc.rs.telco.jaxrs.exceptions;

import es.udc.rs.telco.jaxrs.dto.CustomerWithPhoneCallsExceptionDtoJaxb;
import es.udc.rs.telco.model.telcoservice.exceptions.CustomerWithPhoneCallsException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class CustomerWithPhoneCallsExceptionMapper implements ExceptionMapper<CustomerWithPhoneCallsException> {

    @Override
    public Response toResponse(CustomerWithPhoneCallsException ex) {
        return Response.status(Response.Status.PRECONDITION_FAILED)
                .entity(new CustomerWithPhoneCallsExceptionDtoJaxb(ex.getId()))
                .build();
    }
}
