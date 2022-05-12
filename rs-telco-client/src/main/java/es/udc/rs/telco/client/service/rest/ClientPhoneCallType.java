package es.udc.rs.telco.client.service.rest;

public enum ClientPhoneCallType {
    LOCAL, NATIONAL, INTERNATIONAL;

    ClientPhoneCallType() {
    }

    public String value() {return name();}

    public static ClientPhoneCallType fromValue(String v) {return valueOf(v);}
}
