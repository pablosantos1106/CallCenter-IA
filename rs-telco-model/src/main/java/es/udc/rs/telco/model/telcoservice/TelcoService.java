package es.udc.rs.telco.model.telcoservice;

import es.udc.rs.telco.model.customer.Customer;
import es.udc.rs.telco.model.phonecall.PhoneCall;
import es.udc.rs.telco.model.phonecall.PhoneCallStatus;
import es.udc.rs.telco.model.phonecall.PhoneCallType;
import es.udc.rs.telco.model.telcoservice.exceptions.*;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;



public interface TelcoService {

    Customer addCustomer(Customer c) throws InputValidationException, DNIAlreadyRegisteredException, NotAddedCustomerException;

    void modifyCustomer(Long CustomerId, String DNI, String name, String address) throws InstanceNotFoundException, InputValidationException, DNIAlreadyRegisteredException, NotAddedCustomerException;

    Map<Long, Customer> deleteCustomer(Long customerId) throws CustomerWithPhoneCallsException, InstanceNotFoundException, InputValidationException, NotAddedCustomerException;

    Customer findCustomerByDNI(String dni) throws InstanceNotFoundException, InputValidationException;

    Customer findCustomerByID (Long id) throws InstanceNotFoundException, InputValidationException, NotAddedCustomerException;

    List<Customer> findCustomerByName(String keywords, Integer startIndex, Integer count);

    PhoneCall addCall(PhoneCall p) throws InputValidationException,InstanceNotFoundException;

    void updatePhoneCallStatus(Long customerID, PhoneCallStatus status, int month, int year) throws WrongPhoneCallStatusException, MonthNotExpiredException, InputValidationException, InstanceNotFoundException, NotAddedCustomerException;

    List<PhoneCall> findCallsMonth(Long customerId, int month, int year) throws InvalidStatusException,InputValidationException, MonthNotExpiredException, NotAddedCustomerException, InstanceNotFoundException;

    List<PhoneCall> findCallByInterval (Long customerId, LocalDateTime firstDay, LocalDateTime lastDay, PhoneCallType tipo, Integer startIndex, Integer count) throws InputValidationException, NotAddedCustomerException, InstanceNotFoundException;
    }
