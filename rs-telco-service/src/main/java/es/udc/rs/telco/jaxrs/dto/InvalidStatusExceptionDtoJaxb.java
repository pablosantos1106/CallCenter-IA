package es.udc.rs.telco.jaxrs.dto;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlRootElement(name="InvalidStatusException")
@XmlType(name="InvalidStatusExceptionType")
public class InvalidStatusExceptionDtoJaxb {

    @XmlAttribute(required = true)
    private String errorType;
    @XmlElement(required = true)
    private Long phoneCallId;
    @XmlElement(required = true)
    private int month;
    @XmlElement(required = true)
    private int year;

    public InvalidStatusExceptionDtoJaxb(){}

    public InvalidStatusExceptionDtoJaxb(Long phoneCallId, int month, int year) {
        this.errorType = "InvalidStatus";
        this.phoneCallId = phoneCallId;
        this.month = month;
        this.year = year;
    }

    public String getErrorType() {
        return errorType;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    public Long getPhoneCallId() {
        return phoneCallId;
    }

    public void setPhoneCallId(Long phoneCallId) {
        this.phoneCallId = phoneCallId;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "InvalidStatusExceptionDtoJaxb{" +
                "errorType='" + errorType + '\'' +
                ", phoneCallId=" + phoneCallId +
                ", month=" + month +
                ", year=" + year +
                '}';
    }
}
