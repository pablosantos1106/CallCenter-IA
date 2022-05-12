package es.udc.rs.telco.client.service.rest;
import es.udc.rs.telco.client.service.rest.dto.*;
import jakarta.xml.bind.JAXBElement;

import java.util.ArrayList;
import java.util.List;


public class PhoneCallDtoJaxbToPhoneCallDtoJaxbConversor {

    public static JAXBElement<ClientPhoneCallDtoJaxb> toJaxbPhoneCall (PhoneCallDto p){
        ClientPhoneCallDtoJaxb phone = new ClientPhoneCallDtoJaxb();
        phone.setCustomerId(p.getCustomerId());
        phone.setStartDate(p.getStartDate());
        phone.setDuration(p.getDuration());
        phone.setDestinationNumber(p.getDestinationNumber());
        phone.setPhoneCallType(PhoneCallType.valueOf(String.valueOf(p.getPhoneCallType())));

        JAXBElement<ClientPhoneCallDtoJaxb> jaxbElement = new ObjectFactory().createPhoneCall(phone);
        return jaxbElement;
    }

    public static PhoneCall toPhoneCall(PhoneCallDetailsDtoJaxb phonecall) {
        return new PhoneCall(phonecall.getStartDate(), phonecall.getDuration(),
                phonecall.getDestinationNumber(), LinkUtil.getLinkUri(phonecall.getSelf()));
    }

    public static List<PhoneCall> toPhoneCalls(PhoneCallDtoJaxbList phoneCallListDto) {
        List<PhoneCall> phonecalls = new ArrayList<>(phoneCallListDto.getPhonecalls().size());
        for (PhoneCallDetailsDtoJaxb phoneCallDto:phoneCallListDto.getPhonecalls()) {
            phonecalls.add(toPhoneCall(phoneCallDto));
        }
        return phonecalls;
    }
}
