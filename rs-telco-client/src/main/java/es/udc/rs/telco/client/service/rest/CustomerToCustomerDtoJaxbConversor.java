package es.udc.rs.telco.client.service.rest;

import es.udc.rs.telco.client.service.rest.dto.ClientCustomerDtoJaxb;
import es.udc.rs.telco.client.service.rest.dto.ClientPhoneCallDtoJaxb;
import es.udc.rs.telco.client.service.rest.dto.ObjectFactory;
import jakarta.xml.bind.JAXBElement;

public class CustomerToCustomerDtoJaxbConversor {

    public static JAXBElement<ClientCustomerDtoJaxb> toJaxbCustomer(CustomerDto c){
        ClientCustomerDtoJaxb customer = new ClientCustomerDtoJaxb();
        customer.setName(c.getName());
        customer.setDni(c.getDni());
        customer.setAddress(c.getAddress());
        customer.setPhoneNumber(c.getPhoneNumber());

        JAXBElement<ClientCustomerDtoJaxb> jaxbElement = new ObjectFactory().createCustomer(customer);
        return jaxbElement;

    }
}
