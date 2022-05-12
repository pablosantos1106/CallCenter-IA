package es.udc.rs.telco.client.service.rest;

import es.udc.rs.telco.model.phonecall.PhoneCallStatus;

public class PhoneCallDto {

    private Long phoneCallId;
    private Long customerId;
    private String startDate;
    private Long duration;
    private String destinationNumber;
    private String phoneCallType;
    private PhoneCallStatus phoneCallStatus;

    public PhoneCallDto() {
    }

    public PhoneCallDto(Long phoneCallId, Long customerId, String startDate, Long duration, String destinationNumber, String phoneCallType, PhoneCallStatus phoneCallStatus) {
        this.phoneCallId = phoneCallId;
        this.customerId = customerId;
        this.startDate = startDate;
        this.duration = duration;
        this.destinationNumber = destinationNumber;
        this.phoneCallType = phoneCallType;
        this.phoneCallStatus = phoneCallStatus;
    }

    public PhoneCallDto(String startDate, Long duration, String destinationNumber) {
        this.startDate = startDate;
        this.duration = duration;
        this.destinationNumber = destinationNumber;
    }

    public PhoneCallDto(Long customerId, String startDate, Long duration, String destinationNumber, String type) {
        this.customerId = customerId;
        this.startDate = startDate;
        this.duration = duration;
        this.destinationNumber = destinationNumber;
        this.phoneCallType = type;
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

    public String getPhoneCallType() {
        return phoneCallType;
    }

    public void setPhoneCallType(String phoneCallType) {
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
