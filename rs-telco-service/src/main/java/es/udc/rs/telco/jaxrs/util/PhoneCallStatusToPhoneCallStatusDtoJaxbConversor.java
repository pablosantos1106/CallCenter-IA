package es.udc.rs.telco.jaxrs.util;

import es.udc.rs.telco.jaxrs.dto.PhoneCallStatusDtoJaxb;
import es.udc.rs.telco.model.phonecall.PhoneCallStatus;

public class PhoneCallStatusToPhoneCallStatusDtoJaxbConversor {

    public static PhoneCallStatusDtoJaxb toPhoneCallStatusDtoJaxb(PhoneCallStatus status) {
        PhoneCallStatusDtoJaxb statusDto = PhoneCallStatusDtoJaxb.fromValue(status.toString());
        return statusDto;
    }

    public static PhoneCallStatus toPhonecallStatus(PhoneCallStatusDtoJaxb statusDto) {
        PhoneCallStatus status = PhoneCallStatus.valueOf(statusDto.name());
        return status;
    }
}
