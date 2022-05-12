package es.udc.rs.telco.model.telcoservice;

import es.udc.rs.telco.model.phonecall.PhoneCall;

import java.util.List;
import java.util.Map;

public class deleteCalls extends MockTelcoService{
    public static void deleteCalls() {
        Map<Long, List<PhoneCall>> l= getPhoneCallsByUserMap();
        l.clear();
        setPhoneCallsByUserMap(l);
        setLastPhoneCallId(0L);
    }

}
