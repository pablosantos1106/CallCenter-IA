package es.udc.rs.telco.jaxrs.dto;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlRootElement(name="MonthNotExpiredException")
@XmlType(name="MonthNotExpiredExceptionType")
public class MonthNotExpiredExceptionDtoJaxb {

    @XmlAttribute(required = true)
    private String errorType;
    @XmlElement(required = true)
    private int month;

    public MonthNotExpiredExceptionDtoJaxb(){}

    public MonthNotExpiredExceptionDtoJaxb(int month){
        this.errorType = "MonthNotExpired";
        this.month = month;
    }

    public String getErrorType() { return errorType; }

    public void setErrorType(String errorType) { this.errorType = errorType; }

    public int getMonth() { return month; }

    public void setMonth(int month) { this.month = month; }

    @Override
    public String toString() {
        return "MonthNotExpiredExceptionDtoJaxb{" +
                "errorType='" + errorType + '\'' +
                ", month=" + month +
                '}';
    }
}
