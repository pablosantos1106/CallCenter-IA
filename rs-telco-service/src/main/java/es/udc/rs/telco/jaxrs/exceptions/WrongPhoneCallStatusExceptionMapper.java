package es.udc.rs.telco.jaxrs.exceptions;

import es.udc.rs.telco.jaxrs.dto.WrongPhoneCallStatusExceptionDtoJaxb;
import es.udc.rs.telco.model.telcoservice.exceptions.WrongPhoneCallStatusException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

    @Provider
    public class WrongPhoneCallStatusExceptionMapper implements
            ExceptionMapper<WrongPhoneCallStatusException> {

        @Override
        public Response toResponse(WrongPhoneCallStatusException ex) {
            return Response.status(Response.Status.EXPECTATION_FAILED)
                    .entity(new WrongPhoneCallStatusExceptionDtoJaxb(ex.getStatus()))
                    .build();
        }
    }

