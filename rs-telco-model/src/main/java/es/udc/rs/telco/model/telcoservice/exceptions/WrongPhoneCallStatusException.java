package es.udc.rs.telco.model.telcoservice.exceptions;

import es.udc.rs.telco.model.phonecall.PhoneCallStatus;

public class WrongPhoneCallStatusException extends Exception{
    private PhoneCallStatus status;

    public WrongPhoneCallStatusException(PhoneCallStatus status){
        super("PhoneCalls cannot be update with this status, try with BILLED or PAID ");
        this.status = status;
    }

    public PhoneCallStatus getStatus() {
        return status;
    }

    public void setStatus(PhoneCallStatus status) {
        this.status = status;
    }
}
