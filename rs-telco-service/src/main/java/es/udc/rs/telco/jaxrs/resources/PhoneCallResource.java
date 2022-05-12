package es.udc.rs.telco.jaxrs.resources;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import es.udc.rs.telco.jaxrs.dto.*;
import es.udc.rs.telco.jaxrs.util.PhoneCallStatusToPhoneCallStatusDtoJaxbConversor;
import es.udc.rs.telco.jaxrs.util.PhoneCallToPhoneCallDtoJaxbConversor;
import es.udc.rs.telco.jaxrs.util.PhoneCallTypeToPhoneCallTypeDtoJaxbConversor;
import es.udc.rs.telco.jaxrs.util.ServiceUtil;
import es.udc.rs.telco.model.phonecall.PhoneCallStatus;
import es.udc.rs.telco.model.phonecall.PhoneCallType;
import es.udc.rs.telco.model.telcoservice.exceptions.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import es.udc.rs.telco.model.telcoservice.TelcoServiceFactory;
import es.udc.rs.telco.model.phonecall.PhoneCall;
import es.udc.ws.util.exceptions.*;

@Path("phonecalls")
public class PhoneCallResource {

    @POST
    @Operation(
            summary = "Add a phoneCall",
            description = "Create a new phoneCall with the following parameters: customerId, " +
                    "startDate, duration, destinationNumber and phoneCallType.",
            tags = "phoneCall",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "The phoneCall",
                            content = {
                                    @Content(
                                            mediaType = "application/xml",
                                            schema = @Schema(implementation = PhoneCallDtoJaxb.class)
                                    ),
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = PhoneCallDtoJaxb.class)
                            )}
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid input"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Instance not found"
                    )
            }
    )
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response addCall(
            @RequestBody(
                    description = "To create a phoneCall need a PhoneCallDtoJaxb",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PhoneCallDtoJaxb.class),
                                    examples = @ExampleObject(
                                            value = "{\"customerId\":1,\"startDate\":\"2020-12-10T17:58:04.056857800\",\"duration\":150,\"destinationNumber\":\"666000111\",\"phoneCallType\":\"LOCAL\"}"
                                    )
                            ),
                            @Content(
                                    mediaType = "application/xml",
                                    schema = @Schema(implementation = PhoneCallDtoJaxb.class),
                                    examples = @ExampleObject(
                                            value = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                                                    "<phoneCall xmlns=\"http://ws.udc.es/phonecalls/xml\">\n" +
                                                    "    <customerId>1</customerId>\n" +
                                                    "    <startDate>2020-12-10T17:58:04.056857800</startDate>\n" +
                                                    "    <duration>150</duration>\n" +
                                                    "    <destinationNumber>666000111</destinationNumber>\n" +
                                                    "    <phoneCallType>'LOCAL'</phoneCallType>\n" +
                                                    "</phoneCall>"
                            )
                    )}
            )
            final PhoneCallDtoJaxb callDto, @Context UriInfo ui) throws InputValidationException, InstanceNotFoundException {

        PhoneCall p = PhoneCallToPhoneCallDtoJaxbConversor.toPhoneCall(callDto);

        p = TelcoServiceFactory.getService().addCall(p);
        final PhoneCallDtoJaxb resultPhoneCallDto = PhoneCallToPhoneCallDtoJaxbConversor.toPhoneCallDtoJaxb(p);

        final String requestUri = ui.getRequestUri().toString();
        return Response.created(URI.create(requestUri + (requestUri.endsWith("/") ? "" : "/") + p.getPhoneCallId()))
                .entity(resultPhoneCallDto).build();
    }


    @GET
    @Operation(
            summary = "Search phonecalls by interval",
            description = "Returns a customer's phonecall list with the given interval ",
            tags = "phoneCall",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "phonecall(s) found",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(
                                                    schema = @Schema(
                                                            implementation = PhoneCallDtoJaxb.class
                                                    )
                                            )
                                    ),
                                    @Content(
                                            mediaType = "application/xml",
                                            array = @ArraySchema(
                                                    schema = @Schema(
                                                            implementation = PhoneCallDtoJaxb.class
                                            )
                                    )
                            )}
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid parameters"
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Customer not added"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Customer not found"
                    ),
            }

    )
    @Path("/findCallByInterval")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response findCallByInterval(
            @Parameter(
                    description = "Customer ID to search phonecalls",
                    example = "1",
                    required = true

            ) @QueryParam("customerId") final String customerId,
            @Parameter(
                    description = "Start date of the interval to search for calls",
                    example = "2021-10-01T00:00:00"
            )@QueryParam("firstDay") final String firstDay,
            @Parameter(
                    description = "Final date of the interval to search for calls",
                    example = "2021-12-01T23:59:59"
            )@QueryParam("lastDay") final String lastDay,
            @Parameter(
                    description = "Phonecall type to filter the search",
                    example = "LOCAL"
            ) @QueryParam("tipo") final String tipo,@Parameter(
                    description = "Start index of paged list",
                    example = "0"
            ) @DefaultValue("0") @QueryParam("startIndex") final String startIndex,
            @Parameter(
                    description = "Number of elements of paged list",
                    example = "1"
            ) @DefaultValue("1")@QueryParam("count") final String count,
            @Context UriInfo uriInfo, @Context HttpHeaders headers) throws InputValidationException, NotAddedCustomerException, InstanceNotFoundException{
        Integer startIndexParse, countParse;
        Long id;
        LocalDateTime firstDayParse, lastDayParse;

        try {
            id = Long.valueOf(customerId);
        } catch (final NumberFormatException ex) {
            throw new InputValidationException("Invalid Request: " + "unable to parse customer id '" + customerId + "'");
        }
        try{
            startIndexParse = Integer.valueOf(startIndex);
        } catch (final NumberFormatException ex) {
            throw new InputValidationException("Invalid Request: " + "unable to parse startIndex '" + startIndex + "'");
        }
        try {
            countParse = Integer.valueOf(count);
        } catch (final NumberFormatException ex) {
            throw new InputValidationException("Invalid Request: " + "unable to parse count '" + count + "'");
        }
        if (firstDay != null) {
            try {
                firstDayParse = LocalDateTime.parse(firstDay);
            } catch (final DateTimeParseException ex) {
                throw new InputValidationException("Invalid Request: " + "unable to parse firstDay '" + firstDay + "'");
            }
        }else{
            firstDayParse = LocalDateTime.parse("2000-01-01T00:00:00");
        }
        if (lastDay != null) {
            try {
                lastDayParse = LocalDateTime.parse(lastDay);
            } catch (final DateTimeParseException ex) {
                throw new InputValidationException("Invalid Request: " + "unable to parse lastDay '" + lastDay + "'");
            }
        }else{
            lastDayParse = LocalDateTime.now();
        }

        PhoneCallType type;
        if (tipo != null) {
            try {
                type = PhoneCallTypeToPhoneCallTypeDtoJaxbConversor.toPhoneCallType(PhoneCallTypeDtoJaxb.fromValue(tipo));
            } catch (Exception ex) {
                throw new InputValidationException(ex.getMessage());
            }
        }else{
            type = null;
        }

        final List<PhoneCall> p = TelcoServiceFactory.getService().findCallByInterval(id,firstDayParse,lastDayParse,type,startIndexParse,countParse);

        String typeMedia = ServiceUtil.getTypeAsStringFromHeaders(headers);

        List<PhoneCallDetailsDtoJaxb> phoneCallsDto = PhoneCallToPhoneCallDtoJaxbConversor.toPhoneCallDetailsListDtoJaxb(p, uriInfo, typeMedia);

        Link selfLink = getSelfLink(uriInfo, String.valueOf(firstDayParse), String.valueOf(lastDay), String.valueOf(type), startIndexParse, countParse, typeMedia);
        Link nextLink = getNextLink(uriInfo, String.valueOf(firstDay), String.valueOf(lastDay), String.valueOf(type), startIndexParse, countParse, p.size(), typeMedia);
        Link previousLink = getPreviousLink(uriInfo, String.valueOf(firstDay), String.valueOf(lastDay), String.valueOf(type), startIndexParse,
                countParse, typeMedia);

        Response.ResponseBuilder response = Response.ok(
                new PhoneCallDtoJaxbList(phoneCallsDto)).links(selfLink);

        if (nextLink != null){
            response.links(nextLink);
        }

         if (previousLink != null) {
            response.links(previousLink);
        }
        return response.build();
    }

    private static Link getNextLink(UriInfo uriInfo, String firstDay, String lastDay, String tipo,
                                    int startIndex, int count, int numberOfPhoneCalls, String type) {
        if (numberOfPhoneCalls < count) {
            return null;
        }
        return ServiceUtil.getPhoneCallsIntervalLink(uriInfo, firstDay, lastDay, tipo, startIndex
                + count, count, "next", "Next interval of phonecalls", type);
    }

    private Link getPreviousLink(UriInfo uriInfo, String firstDay, String lastDay, String tipo,
                                 int startIndex, int count, String type) {
        if (startIndex <= 0) {
            return null;
        }
        startIndex = startIndex - count;
        if (startIndex < 0) {
            startIndex = 0;
        }
        return ServiceUtil.getPhoneCallsIntervalLink(uriInfo, firstDay, lastDay, tipo,
                startIndex, count, "previous", "Previous interval of phonecalls",
                type);
    }

        private Link getSelfLink(UriInfo uriInfo, String firstDay, String lastDay, String tipo, int startIndex,
                             int count, String type) {
        return ServiceUtil
                .getPhoneCallsIntervalLink(uriInfo, firstDay, lastDay, tipo, startIndex, count,
                        "self", "Current interval of phonecalls", type);
    }

    @GET
    @Operation(
            summary = "Search phoneCalls to bill",
            description = "Get a list of phoneCalls that have phoneCallStatus PENDING",
            tags = "phoneCall",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "List of phoneCalls",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = PhoneCallDtoJaxb.class)
                                    ),
                                    @Content(
                                            mediaType = "application/xml",
                                            schema = @Schema(implementation = PhoneCallDtoJaxb.class)
                            )}
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
                            responseCode = "406",
                            description = "PhoneCalls with invalid Status"
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Customer not added"
                    )
            }
    )
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Path("/findCallsByMonth")
    public List<PhoneCallDtoJaxb> findCallsMonth(
            @Parameter( description = "CustomerId for search phoneCalls",
                        example = "1",
                        required = true
            ) @QueryParam("customerId") final String customerId,
            @Parameter( description = "Month of the phoneCalls",
                        example = "10",
                        required = true
            ) @QueryParam("month") final String month,
            @Parameter( description = "Year of the phoneCalls",
                        example = "2021",
                        required = true
            )@QueryParam("year") final String year)
            throws MonthNotExpiredException, InvalidStatusException, InputValidationException, NotAddedCustomerException, InstanceNotFoundException {
        Long c;
        int m,y;

        try{
            c = Long.valueOf(customerId);
        } catch (final NumberFormatException ex){
            throw new InputValidationException("Invalid Request : " + "unable to parse customer id '" + customerId + "'");
        }
        try{
            m = Integer.valueOf(month);
        } catch (final NumberFormatException ex){
            throw new InputValidationException("Invalid Request : " + "unable to parse month  '" + month + "'");
        }
        try{
            y = Integer.valueOf(year);
        } catch (final NumberFormatException ex){
            throw new InputValidationException("Invalid Request : " + "unable to parse year  '" + year + "'");
        }

        final List<PhoneCall> p = TelcoServiceFactory.getService().findCallsMonth(c, m, y);
        return  PhoneCallToPhoneCallDtoJaxbConversor.toPhoneCallDtoJaxb(p);
    }

    @PUT
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Operation(
            summary = "Changes the status of a customer's phonecalls ",
            description = "Update all customers's phonecalls status made in a month and year indicated by parameter",
            tags = "customer",
            responses = {
                    @ApiResponse(description = "Successful Response", responseCode = "204"),
                    @ApiResponse(description = "CustomerId not found", responseCode = "404"),
                    @ApiResponse(description = "Invalid input parameter", responseCode = "400"),
                    @ApiResponse(description = "Customer not added yet", responseCode = "409"),
                    @ApiResponse(description = "Month has no expired yet", responseCode = "409"),
            })
    public void updatePhoneCallStatus(
            @Parameter(description = "Customer Id", example = "1", required = true) @QueryParam("customerId") final String customerId,
            @Parameter(description = "Month call ", example = "5", required = true) @QueryParam("month") final String month,
            @Parameter(description = "Month year", example = "2021", required = true) @QueryParam ("year") final String year,
            @Parameter(description = "Status to which the call will be updated ", example = "BILLED", required = true) @QueryParam("status") final String status)
            throws WrongPhoneCallStatusException, MonthNotExpiredException, InputValidationException, InstanceNotFoundException, NotAddedCustomerException {
        Long c = null;
        try {c = Long.valueOf(customerId);} catch (final NumberFormatException ex) {
            throw new InputValidationException("Invalid Request: " + "unable to parse customer id '" + customerId + "'");}
        PhoneCallStatus phStatus = null;
        try {
            phStatus = PhoneCallStatusToPhoneCallStatusDtoJaxbConversor.toPhonecallStatus(PhoneCallStatusDtoJaxb.fromValue(status));
        } catch (Exception ex) {
            throw new InputValidationException(ex.getMessage());}
        Integer monthParsed = null;
        try {
            monthParsed=Integer.valueOf(month);} catch (final NumberFormatException ex) {
            throw new InputValidationException("Invalid Request: " + "unable to parse month value '" + month + ",'");}
        Integer yearParsed = null;
        try {yearParsed=Integer.valueOf(year);} catch (final NumberFormatException ex) {
            throw new InputValidationException("Invalid Request: " + "unable to parse year value '" + year + ",'");}
        TelcoServiceFactory.getService().updatePhoneCallStatus(c,phStatus,monthParsed,yearParsed);
    }
}
