package es.udc.rs.telco.client.service.rest;

import es.udc.rs.telco.client.service.rest.ClientTelcoService;
import es.udc.rs.telco.client.service.rest.Exceptions.*;
import es.udc.rs.telco.client.service.rest.dto.*;
import es.udc.rs.telco.model.customer.Customer;
import es.udc.rs.telco.model.telcoservice.exceptions.NotAddedCustomerException;
import es.udc.rs.telco.model.telcoservice.exceptions.NotAddedCustomerException;
import es.udc.rs.telco.client.service.rest.json.JaxbJsonContextResolver;
import es.udc.ws.util.configuration.ConfigurationParametersManager;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericEntity;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.jackson.JacksonFeature;

import java.time.LocalDateTime;
import java.util.List;

public abstract class RestClientTelcoService implements ClientTelcoService {

    private static jakarta.ws.rs.client.Client client = null;

    private final static String ENDPOINT_ADDRESS_PARAMETER = "RestClientTelcoService.endpointAddress";
    private WebTarget endPointWebTarget = null;

    /*
     * Client instances are expensive resources. It is recommended a configured
     * instance is reused for the creation of Web resources. The creation of Web
     * resources, the building of requests and receiving of responses are guaranteed
     * to be thread safe. Thus a Client instance and WebTarget instances may be
     * shared between multiple threads.
     */
    private static Client getClient() {
        if (client == null) {
            client = ClientBuilder.newClient();
            client.register(JacksonFeature.class);
            client.register(JaxbJsonContextResolver.class);
        }
        return client;
    }

    private WebTarget getEndpointWebTarget() {
        if (endPointWebTarget == null) {
            endPointWebTarget = getClient()
                    .target(ConfigurationParametersManager.getParameter(ENDPOINT_ADDRESS_PARAMETER));
        }
        return endPointWebTarget;
    }

    protected abstract MediaType getMediaType();


    @Override
    public ClientCustomerDtoJaxb addCustomer(CustomerDto c) throws InputValidationException, ClientNotAddedCustomerException,
            ClientDNIAlreadyRegisteredException {
        WebTarget wt = getEndpointWebTarget().path("customers");
        Response response = wt.request().accept(this.getMediaType()).post(Entity.entity(CustomerToCustomerDtoJaxbConversor.toJaxbCustomer(c), this.getMediaType()));

        try {
            validateResponse(Response.Status.CREATED.getStatusCode(), response);
            response.bufferEntity();
            ClientCustomerDtoJaxb customerResult = response.readEntity(ClientCustomerDtoJaxb.class);
            String s = response.readEntity(String.class);
            System.out.println(s);
            return customerResult;
        } catch (InputValidationException | ClientNotAddedCustomerException | ClientDNIAlreadyRegisteredException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            if (response != null) {
                response.close();
            }
        }
    }

    @Override
    public ClientPhoneCallDtoJaxb addCall(PhoneCallDto p) throws InputValidationException, InstanceNotFoundException {
        WebTarget wt = getEndpointWebTarget().path("phonecalls");
        Response response = wt.request().accept(this.getMediaType())
                .post(Entity.entity(PhoneCallDtoJaxbToPhoneCallDtoJaxbConversor.toJaxbPhoneCall(p), this.getMediaType()));
        try {
            validateResponse(Response.Status.CREATED.getStatusCode(), response);
            response.bufferEntity();
            ClientPhoneCallDtoJaxb phoneResult = response.readEntity(ClientPhoneCallDtoJaxb.class);
            String y = response.readEntity(String.class);
            System.out.println(y);
            return phoneResult;
        } catch (InputValidationException | InstanceNotFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            if (response != null) {
                response.close();
            }
        }
    }

    @Override
    public PhoneCallListInterval findCallByIntervalWithoutIndexAndCountAndType(Long customerId, LocalDateTime firstDay, LocalDateTime lastDay)
            throws InputValidationException, ClientNotAddedCustomerException, InstanceNotFoundException{
        return findCallByInterval(customerId,firstDay,lastDay,null,0,1);
    }

    @Override
    public PhoneCallListInterval findCallByIntervalWithoutIndexAndCount(Long customerId, LocalDateTime firstDay, LocalDateTime lastDay,
                                                                       String tipo)
            throws InputValidationException, ClientNotAddedCustomerException, InstanceNotFoundException{
        return findCallByInterval(customerId,firstDay,lastDay,tipo,0,1);
    }

    @Override
    public PhoneCallListInterval findCallByIntervalWithoutType(Long customerId, LocalDateTime firstDay, LocalDateTime lastDay,
                                                                       Integer startIndex, Integer count)
            throws InputValidationException, ClientNotAddedCustomerException, InstanceNotFoundException{
        return findCallByInterval(customerId,firstDay,lastDay,null,startIndex,count);
    }

    @Override
    public PhoneCallListInterval findCallByInterval(Long customerId, LocalDateTime firstDay, LocalDateTime lastDay,
                                                           String tipo, Integer startIndex, Integer count)
            throws InputValidationException, ClientNotAddedCustomerException, InstanceNotFoundException {

        WebTarget wt;
        if (tipo != null) {
            ClientPhoneCallType enumPhoneCallType = null;
            for (ClientPhoneCallType type : ClientPhoneCallType.values()) {
                if (type.name().equalsIgnoreCase(tipo)) {
                    enumPhoneCallType = type;
                }
            }

            if (enumPhoneCallType == null) {
                throw new InputValidationException("Invalid phonecall Type " + tipo);
            }
            wt = getEndpointWebTarget().path("phonecalls/findCallByInterval").queryParam("customerId", customerId)
                    .queryParam("firstDay", firstDay).queryParam("lastDay", lastDay).queryParam("tipo",
                            ClientPhoneCallTypeToPhoneCallTypeDtoJaxbConversor.toPhoneCallTypeDtoJaxb(enumPhoneCallType).value())
                    .queryParam("startIndex", startIndex).queryParam("count", count);
        }else{
            wt = getEndpointWebTarget().path("phonecalls/findCallByInterval").queryParam("customerId", customerId)
                    .queryParam("firstDay", firstDay).queryParam("lastDay", lastDay).queryParam("tipo", tipo)
                    .queryParam("startIndex", startIndex).queryParam("count", count);
        }

        Response response = wt.request().accept(this.getMediaType()).get();
        try {
            validateResponse(Response.Status.OK.getStatusCode(), response);
            response.bufferEntity();
            PhoneCallDtoJaxbList phoneCalls = response.readEntity(PhoneCallDtoJaxbList.class);
            String s = response.readEntity(String.class);
            System.out.println(s);
            return new PhoneCallListInterval(
                    PhoneCallDtoJaxbToPhoneCallDtoJaxbConversor.toPhoneCalls(phoneCalls),
                    LinkUtil.getHeaderLinkUri(response, "next"),
                    LinkUtil.getHeaderLinkUri(response, "previous"));

        } catch (InputValidationException | ClientNotAddedCustomerException | InstanceNotFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            if (response != null) {
                response.close();
            }
        }
    }

    @Override
    public void deleteCustomer(Long customerId) throws ClientCustomerWithPhoneCallsException, InstanceNotFoundException, InputValidationException, ClientNotAddedCustomerException {
        WebTarget wt = getEndpointWebTarget().path("customers/{customerId}").resolveTemplate("customerId", customerId);
        Response response = wt.request().accept(this.getMediaType()).delete();
        try {
            validateResponse(Response.Status.NO_CONTENT.getStatusCode(), response);
        } catch (ClientCustomerWithPhoneCallsException | InstanceNotFoundException | InputValidationException | ClientNotAddedCustomerException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            if (response != null) {
                response.close();
            }
        }
    }

    @Override
    public void changeState(Long customerId, Integer month, Integer year, String status)
            throws InputValidationException, InstanceNotFoundException, ClientNotAddedCustomerException, ClientWrongPhoneCallStatusException{

        if (customerId == null | status == null){throw new InputValidationException( customerId + "cannot be null");}
        else if (month == null) {throw new InputValidationException( month + "cannot be null");}
        else if (year == null) {throw new InputValidationException( year + "cannot be null");}

        ClientPhonecallStatus Status = null;
        if (status.equalsIgnoreCase("PENDING")) {Status = ClientPhonecallStatus.PENDING;}
        else if (status.equalsIgnoreCase("BILLED")) {Status = ClientPhonecallStatus.BILLED;}
        else if (status.equalsIgnoreCase("PAID")) {Status = ClientPhonecallStatus.PAID;}
        else {throw new InputValidationException(status);}

        if (Status == ClientPhonecallStatus.PENDING) {throw new ClientWrongPhoneCallStatusException(Status);}

        WebTarget wt = getEndpointWebTarget().path("phonecalls").
                queryParam("customerId",customerId).queryParam("month",month)
                .queryParam("year",year).queryParam("status",status);
        Response response = wt.request().accept(this.getMediaType()).put(
                Entity.entity(customerId,this.getMediaType())
                .entity(month,this.getMediaType()).entity(year,this.getMediaType())
                        .entity(ClientPhonecallStatusToPhonecallStatusDtoJaxbConversor
                                .toPhonecallStatusDtoJaxb(Status).value(),this.getMediaType()));
        try {validateResponse(Response.Status.NO_CONTENT.getStatusCode(), response);
        } catch (InputValidationException | InstanceNotFoundException | ClientNotAddedCustomerException | ClientWrongPhoneCallStatusException  ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            if (response != null) {
                response.close();
            }
        }
    }

    private void validateResponse(int expectedStatusCode, Response response) throws InstanceNotFoundException,
            InputValidationException, ClientCustomerWithPhoneCallsException, ClientNotAddedCustomerException, ClientWrongPhoneCallStatusException,
            ClientDNIAlreadyRegisteredException{
        Response.Status statusCode = Response.Status.fromStatusCode(response.getStatus());
        String contentType = response.getMediaType() != null ? response.getMediaType().toString() : null;
        boolean expectedContentType = this.getMediaType().toString().equalsIgnoreCase(contentType);
        if (!expectedContentType && (statusCode.getStatusCode() != Response.Status.NO_CONTENT.getStatusCode())) {
            throw new RuntimeException("HTTP error; status code = " + statusCode);
        }
        switch (statusCode) {

            case NOT_FOUND: {
                ClientInstanceNotFoundExceptionDtoJaxb exDto = response.readEntity(ClientInstanceNotFoundExceptionDtoJaxb.class);
                    throw JaxbExceptionConversor.toInstanceNotFoundException(exDto);
            }

            case BAD_REQUEST: {
                ClientInputValidationExceptionDtoJaxb exDto = response.readEntity(ClientInputValidationExceptionDtoJaxb.class);
                throw JaxbExceptionConversor.toInputValidationException(exDto);
            }
            case EXPECTATION_FAILED: {
                ClientWrongPhoneCallStatusExceptionDtoJaxb exDto = response.readEntity(ClientWrongPhoneCallStatusExceptionDtoJaxb.class);
                throw JaxbExceptionConversor.toClientWrongPhoneCallStatusException(exDto);
            }

            case PRECONDITION_FAILED: {
                ClientCustomerWithPhoneCallsExceptionDtoJaxb exDto = response.readEntity(ClientCustomerWithPhoneCallsExceptionDtoJaxb.class);
                throw JaxbExceptionConversor.toClientCustomerWithPhoneCallsException(exDto);
            }

            case CONFLICT: {
                ClientNotAddedCustomerExceptionDtoJaxb exDto = response.readEntity(ClientNotAddedCustomerExceptionDtoJaxb.class);
                throw JaxbExceptionConversor.toClientNotAddedCustomerException(exDto);
            }

            case FORBIDDEN: {
                ClientDNIAlreadyRegisteredExceptionDtoJaxb exDto = response.readEntity(ClientDNIAlreadyRegisteredExceptionDtoJaxb.class);
                throw JaxbExceptionConversor.toClientDNIAlreadyRegisteredException(exDto);
            }

            default:
                    if (statusCode.getStatusCode() != expectedStatusCode) {
                        throw new RuntimeException("HTTP error; status code = " + statusCode);
                    }
                    break;
        }
    }
}

