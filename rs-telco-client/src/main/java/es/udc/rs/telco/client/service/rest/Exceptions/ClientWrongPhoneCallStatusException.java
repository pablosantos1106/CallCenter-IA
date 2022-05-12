package es.udc.rs.telco.client.service.rest.Exceptions;

import es.udc.rs.telco.client.service.rest.ClientPhonecallStatus;

public class ClientWrongPhoneCallStatusException extends Exception{

    private ClientPhonecallStatus status;
    public ClientWrongPhoneCallStatusException(ClientPhonecallStatus status){
        super("Invalid PhoneCall Status:" + status + ". Valid values are [BILLED,PAID]");
        this.status = status;
    }

    public ClientPhonecallStatus getCustomerId() {
        return status;
    }

    public void setCustomerId(ClientPhonecallStatus status) {
        this.status = status;
    }
}
