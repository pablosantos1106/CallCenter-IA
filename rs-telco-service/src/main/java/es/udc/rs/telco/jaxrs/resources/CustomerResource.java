package es.udc.rs.telco.jaxrs.resources;


import es.udc.rs.telco.jaxrs.dto.CustomerDetailsDtoJaxb;
import es.udc.rs.telco.jaxrs.dto.CustomerDtoJaxbList;
import es.udc.rs.telco.jaxrs.util.CustomerToCustomerDtoJaxbConversor;
import es.udc.rs.telco.jaxrs.util.ServiceUtil;
import es.udc.rs.telco.model.telcoservice.exceptions.CustomerWithPhoneCallsException;
import es.udc.rs.telco.model.telcoservice.exceptions.DNIAlreadyRegisteredException;
import es.udc.rs.telco.model.telcoservice.exceptions.NotAddedCustomerException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import es.udc.rs.telco.jaxrs.dto.CustomerDtoJaxb;
import es.udc.rs.telco.model.telcoservice.TelcoServiceFactory;
import es.udc.rs.telco.model.customer.Customer;

import es.udc.ws.util.exceptions.*;

import java.net.URI;
import java.util.List;


@Path("customers")
public class CustomerResource{

    @POST
    @Operation(
            summary = "Add customer",
            description = "Create a customer using parameters name, DNI, address and phone number",
            tags = "customer",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Customer created",
                            content = {
                                    @Content(
                                            mediaType = "application/xml",
                                            schema = @Schema(
                                                    implementation = CustomerDtoJaxb.class
                                            )
                                    ),
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(
                                                    implementation = CustomerDtoJaxb.class
                                            )
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid parameters"
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "DNI is already registered"
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Customer not added"
                    )


            }

    )
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response addCustomer(
        @RequestBody(
                description = "To create a customer need a CustomerDtoJaxb",
                content = {
                        @Content(
                                mediaType = "application/xml",
                                schema = @Schema(
                                        implementation = CustomerDtoJaxb.class
                                ),
                                examples = @ExampleObject(
                                        value = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                                                "<customer xmlns=\"http://ws.udc.es/phonecalls/xml\">\n" +
                                                "    <name>borja</name>\n" +
                                                "    <dni>77012766S</dni>\n" +
                                                "    <address>eidos</address>\n" +
                                                "    <phoneNumber>666777999</phoneNumber>\n" +
                                                "</customer>"
                                )
                        ),
                        @Content(
                                mediaType = "application/json",
                                schema = @Schema(
                                        implementation = CustomerDtoJaxb.class
                                ),
                                examples = @ExampleObject(
                                        value = "{\"name\":\"borja\",\"dni\":\"12345678H\",\"address\":\"eidos\",\"phoneNumber\":\"666777888\"}"
                                )
                )}
        )  final CustomerDtoJaxb customerDto , @Context UriInfo ui) throws InputValidationException, DNIAlreadyRegisteredException, NotAddedCustomerException{

        Customer c = CustomerToCustomerDtoJaxbConversor.toCustomer(customerDto);
        c = TelcoServiceFactory.getService().addCustomer(c);

        final CustomerDtoJaxb resultCustomerDto = CustomerToCustomerDtoJaxbConversor.toCustomerDtoJaxb(c);

        final String requestUri = ui.getRequestUri().toString();
        return Response.created(URI.create(requestUri + (requestUri.endsWith("/") ? "" : "/") + c.getCustomerId())).entity(resultCustomerDto).build();


    }

    @PUT
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Operation(
            summary = "Modify Customer",
            description = "Modify customer's attributes (name, dni, address) ",
            tags = "customer",
            responses = {
                    @ApiResponse(description = "Successful Response ", responseCode = "204"),
                    @ApiResponse(description = "CustomerId not found", responseCode = "404"),
                    @ApiResponse(description = "Invalid input parameters", responseCode = "400"),
                    @ApiResponse(description = "Customer not added yet", responseCode = "409"),
                    @ApiResponse(description = "Dni parameter already registered by another customer", responseCode = "403"),
    })
    public void modifyCustomer
            (@Parameter(description = "Customer Id", example = "1", required = true) @QueryParam("customerId") final String customerId,
             @Parameter(description = "Customer Id", example = "Pablo" ) @QueryParam("name") final String name,
             @Parameter(description = "Customer Id", example = "39462918E") @QueryParam("dni") final String dni,
             @Parameter(description = "Customer Id", example = "C/ Plaza Pontevedra nº10") @QueryParam("address") final String address)
            throws NotAddedCustomerException, InputValidationException, InstanceNotFoundException, DNIAlreadyRegisteredException, NumberFormatException {
        Long c;
        try {
            c = Long.valueOf(customerId);
        } catch (final NumberFormatException ex) {
            throw new InputValidationException("Invalid Request: " + "unable to parse customer id '" + customerId + "'");
        }
        TelcoServiceFactory.getService().modifyCustomer(c,dni,name,address);
    }

    @GET
    @Path("/{customerId}")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Operation(
            summary = "Search a customer by Id",
            description = "Search a customer using Ids in Path parameter",
            tags = "customer",
            responses = {
                    @ApiResponse(description = "Successful Response ", responseCode = "204", content = {
                            @Content(mediaType = "application/xml",
                            schema = @Schema(implementation = CustomerDtoJaxb.class)),
                            @Content(mediaType = "application/json",
                    schema = @Schema(implementation = CustomerDtoJaxb.class))}),
                    @ApiResponse(description = "CustomerId not found", responseCode = "404"),
                    @ApiResponse(description = "Invalid input Id", responseCode = "400"),
            })
    public CustomerDtoJaxb findCustomerById
            (@Parameter(description = "Customer Id", example = "1", required = true) @PathParam("customerId") final String customerId)
            throws NotAddedCustomerException, InputValidationException, InstanceNotFoundException, NumberFormatException {
        Long c;
        try {
            c = Long.valueOf(customerId);
        } catch (final NumberFormatException ex) {
            throw new InputValidationException("Invalid Request: " + "unable to parse customer id '" + customerId + "'");
        }
        return CustomerToCustomerDtoJaxbConversor.toCustomerDtoJaxb(TelcoServiceFactory.getService().findCustomerByID(c));
    }


    @DELETE
    @Operation(
            summary = "Delete a customer",
            description = "Delete the customer with the ID",
            tags = "customer",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Customer removed"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid input"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Instance not found"
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Customer not added"
                    ),
                    @ApiResponse(
                            responseCode = "412",
                            description = "Customer with phoneCalls"
                    )

            }
    )
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Path("/{customerId}")
    public void deleteCustomer(
            @Parameter( description = "CustomerId to delete",
                        example = "1",
                        required = true)
            @PathParam("customerId") final String customerId)
            throws InputValidationException, InstanceNotFoundException, NotAddedCustomerException, CustomerWithPhoneCallsException {
        Long c;
        try {
            c = Long.valueOf(customerId);
        } catch (final NumberFormatException ex) {
            throw new InputValidationException("Invalid Request: " + "unable to parse customer id '" + customerId + "'");
        }

        TelcoServiceFactory.getService().deleteCustomer(c);

    }

    @GET
    @Path("/findCustomerByDNI")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Operation(
            summary = "Search a customer by DNI",
            description = "Search a customer using DNI in Path parameter",
            tags = "customer",
            responses = {
                    @ApiResponse(description = "Successful Response ", responseCode = "204", content = {
                            @Content(mediaType =  "application/json",
                            schema = @Schema(implementation = CustomerDtoJaxb.class)),
                            @Content(mediaType = "application/xml",
                            schema = @Schema(implementation = CustomerDtoJaxb.class))}),
                    @ApiResponse(description = "CustomerId not found", responseCode = "404"),
                    @ApiResponse(description = "Invalid DNI parameter", responseCode = "400"),
            })
    public CustomerDtoJaxb findCustomerByDni (
            @Parameter(description = "Customer DNI", example = "39462918R", required = true) @QueryParam("dni") final String dni)
            throws InputValidationException, InstanceNotFoundException {
        return CustomerToCustomerDtoJaxbConversor.toCustomerDtoJaxb(TelcoServiceFactory.getService().findCustomerByDNI(dni));
    }

    @GET
    @Operation(
            summary = "Search customers by name",
            description = "Returns a customers list with the given name ",
            tags = "customer",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Customer(s) found",
                            content = {
                                    @Content(
                                        mediaType = "application/json",
                                        array = @ArraySchema(
                                                schema = @Schema(
                                                        implementation = CustomerDtoJaxb.class
                                                )
                                        )
                                    ),
                                    @Content(
                                        mediaType = "application/xml",
                                        array = @ArraySchema(
                                                schema = @Schema(
                                                        implementation = CustomerDtoJaxb.class
                                                )
                                        )
                            )}
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid parameters"
                    ),
            }

    )
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Path("/findCustomerByName")
    public Response findCustomerByName (
            @Parameter(
                    description = "Customer name",
                    example = "Pablo",
                    required = true
            )
            @QueryParam("keywords") final String keywords,
            @Parameter(
                    description = "Start index of paged list",
                    example = "0"
            )@DefaultValue("0") @QueryParam("startIndex") String startIndex,
            @Parameter(
                    description = "Number of elements of paged list",
                    example = "1"
            )@DefaultValue("1")@QueryParam("count") String count,
            @Context UriInfo uriInfo, @Context HttpHeaders headers) throws InputValidationException {

        Integer startIndexParse;
        Integer countParse;
        try {
            startIndexParse = Integer.valueOf(startIndex);
        } catch (final NumberFormatException ex) {
            throw new InputValidationException("Invalid Request: " + "unable to parse startIndex '" + startIndex + "'");
        }
        try {
            countParse = Integer.valueOf(count);
        } catch (final NumberFormatException ex) {
            throw new InputValidationException("Invalid Request: " + "unable to parse count '" + count + "'");
        }
        final List<Customer> foundCustomers = TelcoServiceFactory.getService().findCustomerByName(keywords, startIndexParse, countParse);

        String type = ServiceUtil.getTypeAsStringFromHeaders(headers);

        List<CustomerDetailsDtoJaxb> foundCustomersDto = CustomerToCustomerDtoJaxbConversor.toCustomerDetailsListDtoJaxb(foundCustomers, uriInfo.getBaseUri(), type);

        Link selfLink = getSelfLink(uriInfo, keywords, startIndexParse, countParse, type);
        Link nextLink = getNextLink(uriInfo, keywords, startIndexParse, countParse,
                foundCustomers.size(), type);
        Link previousLink = getPreviousLink(uriInfo, keywords, startIndexParse,
                countParse, type);

        Response.ResponseBuilder response = Response.ok(
                new CustomerDtoJaxbList(foundCustomersDto)).links(selfLink);
        if (nextLink != null) {
            response.links(nextLink);
        }
        if (previousLink != null) {
            response.links(previousLink);
        }

        return response.build();

    }

    private static Link getNextLink(UriInfo uriInfo, String keywords,
                                    Integer startIndex, Integer count, int numberOfCustomers, String type) {
        if (numberOfCustomers < count) {
            return null;
        }
        return ServiceUtil.getCustomerIntervalLink(uriInfo, keywords, startIndex
                + count, count, "next", "Next interval of customers", type);
    }

    private Link getPreviousLink(UriInfo uriInfo, String keywords,
                                 int startIndex, int count, String type) {
        if (startIndex <= 0) {
            return null;
        }
        startIndex = startIndex - count;
        if (startIndex < 0) {
            startIndex = 0;
        }
        return ServiceUtil.getCustomerIntervalLink(uriInfo, keywords,
                startIndex, count, "previous", "Previous interval of customers", type);
    }
    private Link getSelfLink(UriInfo uriInfo, String keywords, int startIndex,
                             int count, String type) {
        return ServiceUtil
                .getCustomerIntervalLink(uriInfo, keywords, startIndex, count,
                        "self", "Current interval of customers", type);
    }


}
