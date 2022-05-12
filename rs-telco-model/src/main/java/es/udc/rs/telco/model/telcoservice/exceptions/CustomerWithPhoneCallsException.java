package es.udc.rs.telco.model.telcoservice.exceptions;

public class CustomerWithPhoneCallsException extends Exception {

    private  Long id;
    public CustomerWithPhoneCallsException(Long id){

        super( "Customer with ID :" + id + " has phonecalls ");
        this.id = id;
    }

    public long getId() { return id; }

    public void setId(long id) { this.id = id; }
}
