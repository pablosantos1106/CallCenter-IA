package es.udc.rs.telco.jaxrs.dto;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlRootElement(name="DNIAlreadyRegisteredException")
@XmlType(name="DNIAlreadyRegisteredExceptionType")
public class DNIAlreadyRegisteredExceptionDtoJaxb {

    @XmlAttribute(required = true)
    private String errorType;
    @XmlElement(required = true)
    private String dni;

    public DNIAlreadyRegisteredExceptionDtoJaxb() {
    }

    public DNIAlreadyRegisteredExceptionDtoJaxb(String dni) {
        this.errorType = "DNIAlreadyRegistered";
        this.dni = dni;
    }

    public String getErrorType() {
        return errorType;
    }

    public String getDni() {
        return dni;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    @Override
    public String toString() {
        return "DNIAlreadyRegisteredExceptionDtoJaxb{" +
                "errorType='" + errorType + '\'' +
                ", dni='" + dni + '\'' +
                '}';
    }
}
