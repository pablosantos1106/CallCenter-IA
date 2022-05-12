package es.udc.rs.telco.client.service.rest;

public enum ClientPhonecallStatus {
    PENDING, BILLED, PAID;

    ClientPhonecallStatus() {}

    public String value() {return name();}

    public static ClientPhonecallStatus fromValue(String v) {return valueOf(v);}
}
