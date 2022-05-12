package es.udc.rs.telco.jaxrs.dto;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "phonecalls")
@XmlType(name = "phoneCallListType")
public class PhoneCallDtoJaxbList {

    @XmlElement(name = "phoneCallDetails")
    private List<PhoneCallDetailsDtoJaxb> phonecalls = null;

    public PhoneCallDtoJaxbList() { this.phonecalls = new ArrayList<PhoneCallDetailsDtoJaxb>();
    }

    public PhoneCallDtoJaxbList(List<PhoneCallDetailsDtoJaxb> phonecalls) {
        this.phonecalls = phonecalls;
    }

    public List<PhoneCallDetailsDtoJaxb> getPhonecalls() {
        return phonecalls;
    }

    public void setPhonecalls(List<PhoneCallDetailsDtoJaxb> phonecalls) {
        this.phonecalls = phonecalls;
    }

    @Override
    public String toString() {
        return "PhoneCallDtoJaxbList{" +
                "phonecalls=" + phonecalls +
                '}';
    }
}
