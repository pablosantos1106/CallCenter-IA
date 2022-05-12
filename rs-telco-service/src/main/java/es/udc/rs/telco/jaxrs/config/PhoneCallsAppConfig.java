package es.udc.rs.telco.jaxrs.config;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

public class PhoneCallsAppConfig extends ResourceConfig {
    public PhoneCallsAppConfig() {
        register(JacksonFeature.class);
    }

}
