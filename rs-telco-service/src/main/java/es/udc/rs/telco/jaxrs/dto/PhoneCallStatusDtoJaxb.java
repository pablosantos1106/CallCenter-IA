package es.udc.rs.telco.jaxrs.dto;

import jakarta.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlRootElement(name = "PhonecallStatus")
@XmlType(name = "PhonecallStatusType")
@XmlEnum

public enum PhoneCallStatusDtoJaxb {

    PENDING,
    BILLED,
    PAID;


    PhoneCallStatusDtoJaxb(){}

    public String value() {return name();}

    public static PhoneCallStatusDtoJaxb fromValue(String v) {return valueOf(v);}

}
