package es.udc.rs.telco.jaxrs.dto;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "PhonecallType")
@XmlType(name = "PhonecallTypeType")
@XmlEnum
public enum PhoneCallTypeDtoJaxb {

    LOCAL,
    NATIONAL,
    INTERNATIONAL;


    PhoneCallTypeDtoJaxb() {
    }

    public String value(){return name();}

    public static PhoneCallTypeDtoJaxb fromValue(String v){return valueOf(v);}


}
