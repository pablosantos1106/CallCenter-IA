package es.udc.rs.telco.jaxrs.util;

import es.udc.rs.telco.jaxrs.dto.AtomLinkDtoJaxb;
import es.udc.rs.telco.jaxrs.dto.PhoneCallDetailsDtoJaxb;
import es.udc.rs.telco.jaxrs.dto.PhoneCallDtoJaxb;
import es.udc.rs.telco.jaxrs.resources.CustomerResource;
import es.udc.rs.telco.jaxrs.resources.PhoneCallResource;
import es.udc.rs.telco.model.phonecall.PhoneCall;
import es.udc.rs.telco.model.phonecall.PhoneCallType;
import jakarta.ws.rs.core.UriInfo;


import java.net.URI;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PhoneCallToPhoneCallDtoJaxbConversor {

    public static List<PhoneCallDtoJaxb> toPhoneCallDtoJaxb(List<PhoneCall> calls){
        List<PhoneCallDtoJaxb> callDtos = new ArrayList<>(calls.size());
        for (PhoneCall call : calls) {
            callDtos.add(toPhoneCallDtoJaxb(call));
        }
        return callDtos;
    }

    public static List<PhoneCallDetailsDtoJaxb> toPhoneCallDetailsListDtoJaxb(List<PhoneCall> calls, UriInfo baseUri, String type){
        List<PhoneCallDetailsDtoJaxb> callDtos = new ArrayList<>(calls.size());
        for (PhoneCall call : calls) {
            callDtos.add(toPhoneCallDetailsDtoJaxb(call, baseUri, type));
        }
        return callDtos;
    }

    public static PhoneCallDetailsDtoJaxb toPhoneCallDetailsDtoJaxb(PhoneCall call, UriInfo uriInfo, String type){

        AtomLinkDtoJaxb phoneCallLink = ServiceUtil.getLinkFromUri2(uriInfo, call.getCustomerId(), "customer",
                "Phonecall customer", type);
        AtomLinkDtoJaxb selfLink = ServiceUtil.getLinkFromUri2(uriInfo,call.getCustomerId(), "self", "Self link", type);
        List<AtomLinkDtoJaxb> links = new ArrayList<AtomLinkDtoJaxb>();
        links.add(phoneCallLink);
        links.add(selfLink);
        return new PhoneCallDetailsDtoJaxb(String.valueOf(call.getStartDate()),call.getDuration(),call.getDestinationNumber(), selfLink, links);

    }

    public static PhoneCallDtoJaxb toPhoneCallDtoJaxb(PhoneCall p){
        return new PhoneCallDtoJaxb(p.getPhoneCallId(), p.getCustomerId(),String.valueOf(p.getStartDate()), p.getDuration(),
                p.getDestinationNumber(), p.getPhoneCallType() , p.getPhoneCallStatus());
    }


    public static PhoneCall toPhoneCall(PhoneCallDtoJaxb p){
        return new PhoneCall(p.getCustomerId(),LocalDateTime.parse(p.getStartDate()),p.getDuration(), p.getDestinationNumber(),
                p.getPhoneCallType());
    }
}
