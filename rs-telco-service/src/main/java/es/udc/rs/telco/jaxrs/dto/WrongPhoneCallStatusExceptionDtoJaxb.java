package es.udc.rs.telco.jaxrs.dto;

import es.udc.rs.telco.model.phonecall.PhoneCallStatus;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlRootElement(name="WrongPhoneCallStatusException")
@XmlType(name="WrongPhoneCallStatusExceptionType")
public class WrongPhoneCallStatusExceptionDtoJaxb {

    @XmlAttribute(required = true)
    private String errorType;
    @XmlElement(required = true)
    private PhoneCallStatus status;

    public WrongPhoneCallStatusExceptionDtoJaxb() {
    }

    public WrongPhoneCallStatusExceptionDtoJaxb(PhoneCallStatus status) {
        this.errorType = "WrongPhoneCallStatus";
        this.status = status;
    }

    public String getErrorType() {
        return errorType;
    }

    public PhoneCallStatus getStatus() {
        return status;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    public void setStatus(PhoneCallStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "WrongPhoneCallStatusExceptionDtoJaxb{" +
                "errorType='" + errorType + '\'' +
                ",status=" + status +
                '}';
    }

}