package es.udc.rs.telco.jaxrs.util;

import es.udc.rs.telco.jaxrs.dto.AtomLinkDtoJaxb;
import jakarta.ws.rs.core.*;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import org.apache.http.client.utils.URIBuilder;

public class ServiceUtil {

    private static List<MediaType> responseMediaTypes = Arrays
            .asList(new MediaType[] { MediaType.APPLICATION_XML_TYPE,
                    MediaType.APPLICATION_JSON_TYPE });

    public static String getTypeAsStringFromHeaders(HttpHeaders headers) {
        List<MediaType> mediaTypes = headers.getAcceptableMediaTypes();
        for (MediaType m : mediaTypes) {
            MediaType compatibleType = getCompatibleAcceptableMediaType(m);
            if (compatibleType != null) {
                return compatibleType.toString();
            }
        }
        return null;
    }

    private static MediaType getCompatibleAcceptableMediaType(MediaType type) {
        for (MediaType m : responseMediaTypes) {
            if (m.isCompatible(type)) {
                return m;
            }
        }
        return null;
    }


    public static AtomLinkDtoJaxb getLinkFromUri(URI baseUri, Class<?> resourceClass,
                                                 Object instanceId, String rel, String title, String type) {
        Link.Builder linkBuilder = Link
                .fromPath(
                        baseUri.toString()
                                + UriBuilder.fromResource(resourceClass)
                                .build().toString() + "/"
                                + instanceId).rel(rel).title(title);
        if (type != null) {
            linkBuilder.type(type);
        }

        Link link = linkBuilder.build();
        return new AtomLinkDtoJaxb(link.getUri(), link.getRel(), link.getType(), link.getTitle());
    }
    public static AtomLinkDtoJaxb getLinkFromUri2(UriInfo uriInfo, Object instanceId, String rel, String title, String type) {

        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder()
                .queryParam("customerId", instanceId.toString());

        Link.Builder linkBuilder = Link.fromUriBuilder(uriBuilder)
                .rel(rel)
                .title(title);

        if (type != null) {
            linkBuilder.type(type);
        }

        Link link = linkBuilder.build();
        return new AtomLinkDtoJaxb(link.getUri(), link.getRel(), link.getType(), link.getTitle());
    }



    public static Link getPhoneCallsIntervalLink(UriInfo uriInfo, String firstDay, String lastDay, String tipo,
                                               int startIndex, int count, String rel, String title, String type) {
        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder()
                .queryParam("firstDay", firstDay)
                .queryParam("lastDay", lastDay)
                .queryParam("tipo", tipo)
                .queryParam("startIndex", startIndex)
                .queryParam("count", count);
        Link.Builder linkBuilder = Link.fromUriBuilder(uriBuilder)
                .rel(rel)
                .title(title);
        if (type!=null) {
            linkBuilder.type(type);
        }
        return linkBuilder.build();
    }
    public static Link getCustomerIntervalLink(UriInfo uriInfo, String keywords,
                                                 int startIndex, int count, String rel, String title, String type) {
        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder()
                .queryParam("keywords", keywords)
                .queryParam("startIndex", startIndex)
                .queryParam("count", count);
        Link.Builder linkBuilder = Link.fromUriBuilder(uriBuilder)
                .rel(rel)
                .title(title);
        if (type!=null) {
            linkBuilder.type(type);
        }
        return linkBuilder.build();
    }
}
