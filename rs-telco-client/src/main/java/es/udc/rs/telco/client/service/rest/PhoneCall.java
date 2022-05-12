package es.udc.rs.telco.client.service.rest;

import java.net.URI;

public class PhoneCall {
    private String startDate;
    private Long duration;
    private String destinationNumber;

    private URI selfUri;

    public PhoneCall(String startDate, Long duration, String destinationNumber, URI selfUri) {
        this.startDate = startDate;
        this.duration = duration;
        this.destinationNumber = destinationNumber;
        this.selfUri = selfUri;
    }

    public PhoneCall() {
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

    public URI getSelfUri() {
        return selfUri;
    }

    public void setSelfUri(URI selfUri) {
        this.selfUri = selfUri;
    }

    @Override
    public String toString() {
        return "PhoneCall{" +
                "startDate='" + startDate + '\'' +
                ", duration=" + duration +
                ", destinationNumber='" + destinationNumber + '\'' +
                ", selfUri=" + selfUri +
                '}';
    }
}
