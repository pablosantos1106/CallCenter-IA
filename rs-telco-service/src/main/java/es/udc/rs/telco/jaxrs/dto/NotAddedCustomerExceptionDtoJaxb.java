package es.udc.rs.telco.jaxrs.dto;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlRootElement(name="NotAddedCustomerException")
@XmlType(name="NotAddedCustomerExceptionType")
public class NotAddedCustomerExceptionDtoJaxb {

    @XmlAttribute(required = true)
    private String errorType;
    @XmlElement(required = true)
    private Long customerId;

    public NotAddedCustomerExceptionDtoJaxb(){}

    public NotAddedCustomerExceptionDtoJaxb(Long customerId) {
        this.errorType = "NotAddedCustomer";
        this.customerId = customerId;
    }

    public String getErrorType() {
        return errorType;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    @Override
    public String toString() {
        return "NotAddedCustomerExceptionDtoJaxb{" +
                "errorType='" + errorType + '\'' +
                ", customerId=" + customerId +
                '}';
    }
}
