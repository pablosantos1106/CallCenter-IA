package es.udc.rs.telco.client.service.rest;

import es.udc.rs.telco.client.service.rest.dto.PhoneCallTypeDtoJaxb;

public class ClientPhoneCallTypeToPhoneCallTypeDtoJaxbConversor {

    public static PhoneCallTypeDtoJaxb toPhoneCallTypeDtoJaxb(ClientPhoneCallType type){
        PhoneCallTypeDtoJaxb typeDto = PhoneCallTypeDtoJaxb.fromValue(type.toString());
        return typeDto;
    }

    public static ClientPhoneCallType toClientPhoneCallType (PhoneCallTypeDtoJaxb typeDto){
        ClientPhoneCallType type = ClientPhoneCallType.valueOf(typeDto.name());
        return type;
    }
}
