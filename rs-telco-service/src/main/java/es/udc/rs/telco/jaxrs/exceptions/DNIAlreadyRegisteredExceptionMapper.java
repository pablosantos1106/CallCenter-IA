package es.udc.rs.telco.jaxrs.exceptions;

import es.udc.rs.telco.jaxrs.dto.DNIAlreadyRegisteredExceptionDtoJaxb;
import es.udc.rs.telco.model.telcoservice.exceptions.DNIAlreadyRegisteredException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class DNIAlreadyRegisteredExceptionMapper  implements
        ExceptionMapper<DNIAlreadyRegisteredException> {

    @Override
    public Response toResponse(DNIAlreadyRegisteredException ex) {
        return Response.status(Response.Status.FORBIDDEN)
                .entity(new DNIAlreadyRegisteredExceptionDtoJaxb(ex.getDNI()))
                .build();
    }
}

