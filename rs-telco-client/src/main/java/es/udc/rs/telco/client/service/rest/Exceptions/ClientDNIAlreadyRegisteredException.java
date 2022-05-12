package es.udc.rs.telco.client.service.rest.Exceptions;

public class ClientDNIAlreadyRegisteredException extends Exception{

    private String dni;

    public ClientDNIAlreadyRegisteredException(String dni) {
        super("Invalid DNI(" + dni + ") already registered by another customer");
        this.dni = dni;
    }

    public String getDNI() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }
}
