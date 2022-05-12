package es.udc.rs.telco.client.service.rest.Exceptions;

public class ClientNotAddedCustomerException extends Exception{

    private Long customerId;
    public ClientNotAddedCustomerException(Long customerID){
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
