package es.udc.rs.telco.client.service.rest;

import es.udc.rs.telco.client.service.rest.dto.PhoneCallStatus;

public class ClientPhonecallStatusConversor {

    public static ClientPhonecallStatus PhonecallStatusToClientPhonecallStatus(PhoneCallStatus status){
        ClientPhonecallStatus Status = null;
        if (status.name().equalsIgnoreCase("PENDING")) {Status = ClientPhonecallStatus.PENDING;}
        else  if (status.name().equalsIgnoreCase("BILLED")) {Status = ClientPhonecallStatus.BILLED;}
        else  if (status.name().equalsIgnoreCase("PAID")) {Status = ClientPhonecallStatus.PAID;}
                return Status;
    }


}
