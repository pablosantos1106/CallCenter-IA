package es.udc.rs.telco.model.telcoservice.exceptions;

import es.udc.rs.telco.model.phonecall.PhoneCallStatus;

public class NotAddedCustomerException extends Exception {
    private Long customerId;
    public NotAddedCustomerException(Long customerID){
        super("CustomerID is null, please add the customer first");
        this.customerId = customerID;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
}
