package es.udc.rs.telco.client.service.rest;
import es.udc.rs.telco.client.service.rest.dto.PhonecallStatusDtoJaxb;

public class ClientPhonecallStatusToPhonecallStatusDtoJaxbConversor {

    public static PhonecallStatusDtoJaxb toPhonecallStatusDtoJaxb (ClientPhonecallStatus status){
        return  PhonecallStatusDtoJaxb.fromValue(status.toString());
    }

    public static ClientPhonecallStatus toPhonecallStatus (PhonecallStatusDtoJaxb statusDto){
        return ClientPhonecallStatus.valueOf(statusDto.name());
    }

}
