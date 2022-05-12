package es.udc.rs.telco.jaxrs.dto;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;



@XmlRootElement(name="customer")
@XmlType(name="customerType", propOrder = {"customerId", "name", "dni", "address","phoneNumber"})
public class CustomerDtoJaxb {

    @XmlAttribute(required = true)
    private Long customerId;
    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String dni;
    @XmlElement(required = true)
    private String address;
    @XmlElement(required = true)
    private String phoneNumber;

    public CustomerDtoJaxb() {
    }

    public CustomerDtoJaxb(Long customerId, String name, String dni, String address, String phoneNumber){
        this.customerId = customerId;
        this.name = name;
        this.dni = dni;
        this.address = address;
        this.phoneNumber = phoneNumber;
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

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
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

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }



    @Override
    public String toString() {
        return "CustomerDtoJaxb{" +
                "customerId=" + customerId +
                ", dni='" + dni + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }

}


