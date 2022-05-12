package es.udc.rs.telco.jaxrs.util;

import es.udc.rs.telco.jaxrs.dto.PhoneCallTypeDtoJaxb;
import es.udc.rs.telco.model.phonecall.PhoneCallType;

public class PhoneCallTypeToPhoneCallTypeDtoJaxbConversor {

    public static PhoneCallTypeDtoJaxb toPhoneCallTypeDtoJaxb (PhoneCallType type){
        PhoneCallTypeDtoJaxb typeDto = PhoneCallTypeDtoJaxb.fromValue(type.toString());
        return typeDto;
    }

    public static PhoneCallType toPhoneCallType (PhoneCallTypeDtoJaxb typeDto){
        PhoneCallType type = PhoneCallType.valueOf(typeDto.name());
        return type;
    }
}
