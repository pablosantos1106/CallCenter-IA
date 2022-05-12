package es.udc.rs.telco.model.telcoservice.exceptions;

public class DNIAlreadyRegisteredException extends Exception {

    private String DNI;

    public DNIAlreadyRegisteredException(String DNI) {
        super("Invalid DNI(" + DNI + ") already registered by another customer");
        this.DNI = DNI;
    }

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }
}