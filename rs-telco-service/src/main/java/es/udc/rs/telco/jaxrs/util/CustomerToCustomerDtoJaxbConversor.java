package es.udc.rs.telco.jaxrs.util;

import es.udc.rs.telco.jaxrs.dto.AtomLinkDtoJaxb;
import es.udc.rs.telco.jaxrs.dto.CustomerDetailsDtoJaxb;
import es.udc.rs.telco.jaxrs.dto.CustomerDtoJaxb;
import es.udc.rs.telco.jaxrs.dto.PhoneCallDtoJaxb;
import es.udc.rs.telco.jaxrs.resources.CustomerResource;
import es.udc.rs.telco.jaxrs.resources.PhoneCallResource;
import es.udc.rs.telco.model.customer.Customer;
import es.udc.rs.telco.model.phonecall.PhoneCall;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class CustomerToCustomerDtoJaxbConversor {

    public static List<CustomerDetailsDtoJaxb> toCustomerDetailsListDtoJaxb(List<Customer> customers, URI baseUri, String type) {
        List<CustomerDetailsDtoJaxb> customerDtos = new ArrayList<>(customers.size());
        for (Customer c : customers) {
            customerDtos.add(toCustomerDetailsDtoJaxb(c, baseUri, type));
        }
        return customerDtos;
    }

    public static CustomerDetailsDtoJaxb toCustomerDetailsDtoJaxb(Customer c, URI baseUri, String type){
        AtomLinkDtoJaxb selfLink = ServiceUtil.getLinkFromUri(baseUri, CustomerResource.class,
                c.getCustomerId(), "self", "Self link", type);

        AtomLinkDtoJaxb phonecallsLink = ServiceUtil.getLinkFromUri(baseUri, PhoneCallResource.class,
                c.getCustomerId(), "phonecalls",
                "Customer phonecalls", type);
        List<AtomLinkDtoJaxb> links = new ArrayList<AtomLinkDtoJaxb>();
        links.add(phonecallsLink);
        links.add(selfLink);
        return new CustomerDetailsDtoJaxb(c.getCustomerId(), c.getName(), c.getDni(), selfLink, links);
    }
    public static CustomerDtoJaxb toCustomerDtoJaxb(Customer c) {
        return new CustomerDtoJaxb(c.getCustomerId(), c.getName(), c.getDni(), c.getAddress(), c.getPhoneNumber());
    }


    public static Customer toCustomer(CustomerDtoJaxb c) {
        return new Customer(c.getName(), c.getDni(), c.getAddress(), c.getPhoneNumber());
    }
}
