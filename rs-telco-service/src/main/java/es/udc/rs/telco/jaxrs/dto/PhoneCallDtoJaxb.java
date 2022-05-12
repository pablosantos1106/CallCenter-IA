package es.udc.rs.telco.jaxrs.dto;

import com.fasterxml.jackson.annotation.JsonRootName;
import es.udc.rs.telco.model.phonecall.PhoneCallStatus;
import es.udc.rs.telco.model.phonecall.PhoneCallType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

import java.time.LocalDateTime;

@XmlRootElement(name = "phoneCall")
@XmlType(name = "phoneCallType", propOrder = {"phoneCallId", "customerId", "startDate", "duration", "destinationNumber","phoneCallType","phoneCallStatus" })
public class PhoneCallDtoJaxb {

    @XmlAttribute(name = "phoneCallId",required = true)
    private Long phoneCallId;
    @XmlElement(required = true)
    private Long customerId;
    @XmlElement(required = true)
    private String startDate;
    @XmlElement(required = true)
    private Long duration;
    @XmlElement(required = true)
    private String destinationNumber;
    @XmlElement(required = true)
    private PhoneCallType phoneCallType;
    @XmlElement(required = true)
    private PhoneCallStatus phoneCallStatus;

    public PhoneCallDtoJaxb() {
    }

    public PhoneCallDtoJaxb(Long phoneCallId, Long customerId, String startDate, Long duration, String destinationNumber, PhoneCallType phoneCallType, PhoneCallStatus phoneCallStatus) {
        this.phoneCallId = phoneCallId;
        this.customerId = customerId;
        this.startDate = startDate;
        this.duration = duration;
        this.destinationNumber = destinationNumber;
        this.phoneCallType = phoneCallType;
        this.phoneCallStatus = phoneCallStatus;
    }


    public Long getPhoneCallId() {
        return phoneCallId;
    }

    public void setPhoneCallId(Long phoneCallId) {
        this.phoneCallId = phoneCallId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public String getDestinationNumber() {
        return destinationNumber;
    }

    public void setDestinationNumber(String destinationNumber) {
        this.destinationNumber = destinationNumber;
    }

    public PhoneCallType getPhoneCallType() {
        return phoneCallType;
    }

    public void setPhoneCallType(PhoneCallType phoneCallType) {
        this.phoneCallType = phoneCallType;
    }

    public PhoneCallStatus getPhoneCallStatus() {
        return phoneCallStatus;
    }

    public void setPhoneCallStatus(PhoneCallStatus phoneCallStatus) {
        this.phoneCallStatus = phoneCallStatus;
    }

    @Override
    public String toString() {
        return "PhoneCallDtoJaxb{" +
                "phoneCallId=" + phoneCallId +
                ", customerId=" + customerId +
                ", startDate=" + startDate +
                ", duration=" + duration +
                ", destinationNumber='" + destinationNumber + '\'' +
                ", phoneCallType=" + phoneCallType +
                ", phoneCallStatus=" + phoneCallStatus +
                '}';
    }
}
