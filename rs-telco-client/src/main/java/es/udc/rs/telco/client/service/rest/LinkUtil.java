package es.udc.rs.telco.client.service.rest;

import es.udc.rs.telco.client.service.rest.dto.AtomLinkType;
import jakarta.ws.rs.core.Link;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public class LinkUtil {

	public static URI getLinkUriFromList(List<AtomLinkType> links, String rel) {
		String url = null;
		for (int i = 0; i < links.size() && url == null; i++) {
			AtomLinkType l = links.get(i);
			if (l.getRel() != null && l.getRel().equals(rel)) {
				url = l.getHref();
			}
		}
		URI uri = null;
		if (url != null) {
			try {
				uri = new URI(url);
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
		}
		return uri;
	}

	public static URI getLinkUri(AtomLinkType link) {
		String url = null;
		if (link != null) {
			url = link.getHref();
		}
		URI uri = null;
		if (url != null) {
			try {
				uri = new URI(url);
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
		}
		return uri;
	}

	public static URI getHeaderLinkUri(Response r, String linkRel) {
		URI linkUri = null;
		Link link = r.getLink(linkRel);
		if (link != null) {
			linkUri = link.getUri();
		}
		return linkUri;
	}

}
