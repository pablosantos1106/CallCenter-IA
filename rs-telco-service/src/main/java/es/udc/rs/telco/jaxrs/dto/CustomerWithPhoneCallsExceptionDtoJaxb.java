package es.udc.rs.telco.jaxrs.dto;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlRootElement(name="CustomerWithPhoneCallsException")
@XmlType(name="CustomerWithPhoneCallsExceptionType")
public class CustomerWithPhoneCallsExceptionDtoJaxb {

    @XmlAttribute(required = true)
    private String errorType;
    @XmlElement(required = true)
    private Long customerId;

    public CustomerWithPhoneCallsExceptionDtoJaxb(){}

    public CustomerWithPhoneCallsExceptionDtoJaxb(Long customerId){
        this.errorType = "CustomerWithPhoneCalls";
        this.customerId = customerId;
    }


    public String getErrorType() { return errorType; }

    public void setErrorType(String errorType) { this.errorType = errorType; }

    public Long getCustomerId() { return customerId; }

    public void setCustomerId(Long customerId) { this.customerId = customerId; }

    @Override
    public String toString() {
        return "CustomerWithPhoneCallsExceptionDtoJaxb{" +
                "errorType='" + errorType + '\'' +
                ", customerId=" + customerId +
                '}';
    }
}
