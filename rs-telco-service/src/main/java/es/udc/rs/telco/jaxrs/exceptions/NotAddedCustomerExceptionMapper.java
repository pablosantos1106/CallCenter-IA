package es.udc.rs.telco.jaxrs.exceptions;

import es.udc.rs.telco.jaxrs.dto.NotAddedCustomerExceptionDtoJaxb;
import es.udc.rs.telco.model.telcoservice.exceptions.NotAddedCustomerException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

    @Provider
    public class NotAddedCustomerExceptionMapper implements
            ExceptionMapper<NotAddedCustomerException> {

        @Override
        public Response toResponse(NotAddedCustomerException ex) {
            return Response.status(Response.Status.CONFLICT)
                    .entity(new NotAddedCustomerExceptionDtoJaxb(ex.getCustomerId()))
                    .build();
        }
    }

