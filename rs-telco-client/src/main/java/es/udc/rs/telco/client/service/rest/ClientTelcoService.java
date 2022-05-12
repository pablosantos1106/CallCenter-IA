package es.udc.rs.telco.client.service.rest;

import es.udc.rs.telco.client.service.rest.dto.ClientCustomerDtoJaxb;
import es.udc.rs.telco.client.service.rest.dto.ClientPhoneCallDtoJaxb;
import es.udc.rs.telco.client.service.rest.dto.PhoneCallType;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.rs.telco.client.service.rest.Exceptions.*;

import java.time.LocalDateTime;
import java.util.List;


public interface ClientTelcoService {

    ClientCustomerDtoJaxb addCustomer(CustomerDto c) throws InputValidationException, ClientNotAddedCustomerException, ClientDNIAlreadyRegisteredException;

    PhoneCallListInterval findCallByIntervalWithoutIndexAndCountAndType(Long customerId, LocalDateTime firstDay, LocalDateTime lastDay)
            throws InputValidationException, ClientNotAddedCustomerException, InstanceNotFoundException;

    PhoneCallListInterval findCallByIntervalWithoutIndexAndCount
            (Long customerId, LocalDateTime firstDay, LocalDateTime lastDay, String tipo)
            throws InputValidationException, ClientNotAddedCustomerException, InstanceNotFoundException;

    PhoneCallListInterval findCallByIntervalWithoutType
            (Long customerId, LocalDateTime firstDay, LocalDateTime lastDay, Integer startIndex, Integer count)
            throws InputValidationException, ClientNotAddedCustomerException, InstanceNotFoundException;

    PhoneCallListInterval findCallByInterval(Long customerId, LocalDateTime firstDay, LocalDateTime lastDay, String tipo, Integer startIndex, Integer count) throws InputValidationException, ClientNotAddedCustomerException, InstanceNotFoundException;

    ClientPhoneCallDtoJaxb addCall (PhoneCallDto p) throws InputValidationException,InstanceNotFoundException;

    void deleteCustomer (Long customerId) throws ClientCustomerWithPhoneCallsException, InstanceNotFoundException, InputValidationException, ClientNotAddedCustomerException;

    void changeState (Long customerID, Integer month, Integer year, String status) throws ClientMonthNotExpiredException,
            InputValidationException, InstanceNotFoundException, ClientNotAddedCustomerException, ClientWrongPhoneCallStatusException;
}
