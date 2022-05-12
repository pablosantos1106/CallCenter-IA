package es.udc.rs.telco.jaxrs.dto;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

import java.util.List;


@XmlRootElement(name="customerDetails")
@XmlType(name="customerDetailsType", propOrder = {"customerId", "name", "dni", "self", "links"})

public class CustomerDetailsDtoJaxb {

    @XmlAttribute(required = true)
    private Long customerId;
    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String dni;
    @XmlElement(name = "self", namespace = "http://www.w3.org/2005/Atom")
    private AtomLinkDtoJaxb self;
    @XmlElement(name = "link", namespace = "http://www.w3.org/2005/Atom")
    private List<AtomLinkDtoJaxb> links;

    public CustomerDetailsDtoJaxb() {
    }

    public CustomerDetailsDtoJaxb(Long customerId, String name, String dni, AtomLinkDtoJaxb self, List<AtomLinkDtoJaxb> links) {
        this.customerId = customerId;
        this.name = name;
        this.dni = dni;
        this.self = self;
        this.links = links;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public String getDni() {
        return dni;
    }

    public String getName() {
        return name;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public void setName(String name) {
        this.name = name;
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
        return "CustomerDetailsDtoJaxb{" +
                "customerId=" + customerId +
                ", name='" + name + '\'' +
                ", dni='" + dni + '\'' +
                ", self=" + self +
                ", links=" + links +
                '}';
    }
}
