package es.udc.rs.telco.jaxrs.dto;

import es.udc.rs.telco.model.phonecall.PhoneCallStatus;
import es.udc.rs.telco.model.phonecall.PhoneCallType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

import java.util.List;

@XmlRootElement(name = "phoneCallDetails")
@XmlType(name = "phoneCallDetailsType", propOrder = { "startDate", "duration", "destinationNumber", "self", "links"})
public class PhoneCallDetailsDtoJaxb {

    @XmlElement(required = true)
    private String startDate;
    @XmlElement(required = true)
    private Long duration;
    @XmlElement(required = true)
    private String destinationNumber;
    @XmlElement(name = "self", namespace = "http://www.w3.org/2005/Atom")
    private AtomLinkDtoJaxb self;
    @XmlElement(name = "link", namespace = "http://www.w3.org/2005/Atom")
    private List<AtomLinkDtoJaxb> links;


    public PhoneCallDetailsDtoJaxb() {
    }

    public PhoneCallDetailsDtoJaxb(String startDate, Long duration, String destinationNumber, AtomLinkDtoJaxb self, List<AtomLinkDtoJaxb> links) {
        this.startDate = startDate;
        this.duration = duration;
        this.destinationNumber = destinationNumber;
        this.self = self;
        this.links = links;
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

    public AtomLinkDtoJaxb getSelf() {
        return self;
    }

    public void setSelf(AtomLinkDtoJaxb self) {
        this.self = self;
    }

    public List<AtomLinkDtoJaxb> getLinks() {
        return links;
    }

    public void setLinks(List<AtomLinkDtoJaxb> links) {
        this.links = links;
    }

    @Override
    public String toString() {
        return "PhoneCallDetailsDtoJaxb{" +
                "startDate='" + startDate + '\'' +
                ", duration=" + duration +
                ", destinationNumber='" + destinationNumber + '\'' +
                ", self=" + self +
                ", links=" + links +
                '}';
    }
}
