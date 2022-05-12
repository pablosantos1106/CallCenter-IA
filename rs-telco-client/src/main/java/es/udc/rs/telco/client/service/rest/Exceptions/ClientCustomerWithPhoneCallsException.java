package es.udc.rs.telco.client.service.rest.Exceptions;

public class ClientCustomerWithPhoneCallsException extends Exception{
    private Long id;
    public ClientCustomerWithPhoneCallsException(Long id) {
        super("Customer with ID:" + id + " has phonecalls");
        this.id = id;
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }
}
