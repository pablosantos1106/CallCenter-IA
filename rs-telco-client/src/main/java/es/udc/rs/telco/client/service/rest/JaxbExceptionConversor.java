package es.udc.rs.telco.client.service.rest;

import es.udc.rs.telco.client.service.rest.Exceptions.*;
import es.udc.rs.telco.client.service.rest.dto.*;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.rs.telco.client.service.rest.dto.ClientDNIAlreadyRegisteredExceptionDtoJaxb;
import es.udc.rs.telco.client.service.rest.dto.ClientInputValidationExceptionDtoJaxb;
import es.udc.rs.telco.client.service.rest.dto.ClientInstanceNotFoundExceptionDtoJaxb;
import es.udc.rs.telco.client.service.rest.dto.ClientNotAddedCustomerExceptionDtoJaxb;

public class JaxbExceptionConversor {

    public static InputValidationException toInputValidationException(
            ClientInputValidationExceptionDtoJaxb exDto) {
        return new InputValidationException(exDto.getMessage());
    }

    public static InstanceNotFoundException toInstanceNotFoundException(
            ClientInstanceNotFoundExceptionDtoJaxb exDto) {
        return new InstanceNotFoundException(exDto.getInstanceId(),
                exDto.getInstanceType());
    }

    public static ClientCustomerWithPhoneCallsException toClientCustomerWithPhoneCallsException(
            ClientCustomerWithPhoneCallsExceptionDtoJaxb exDto) {
        return new ClientCustomerWithPhoneCallsException(exDto.getCustomerId());
    }

    public static ClientNotAddedCustomerException toClientNotAddedCustomerException(
            ClientNotAddedCustomerExceptionDtoJaxb exDto) {
        return new ClientNotAddedCustomerException(exDto.getCustomerId());
    }

    public static ClientDNIAlreadyRegisteredException toClientDNIAlreadyRegisteredException(
            ClientDNIAlreadyRegisteredExceptionDtoJaxb exDto){
        return new ClientDNIAlreadyRegisteredException(exDto.getDni());
    }

    public static ClientWrongPhoneCallStatusException toClientWrongPhoneCallStatusException(ClientWrongPhoneCallStatusExceptionDtoJaxb exDto){
        return new ClientWrongPhoneCallStatusException(ClientPhonecallStatusConversor.PhonecallStatusToClientPhonecallStatus(exDto.getStatus()));
    }

}
