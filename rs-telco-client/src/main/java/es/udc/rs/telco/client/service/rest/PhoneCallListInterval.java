package es.udc.rs.telco.client.service.rest;


import java.net.URI;
import java.util.List;

public class PhoneCallListInterval {

	private List<PhoneCall> phonecalls;
	private URI nextIntervalUri;
	private URI previousIntervalUri;

	public PhoneCallListInterval(List<PhoneCall> phonecalls, URI nextIntervalUri,
                               URI previousIntervalUri) {
		this.phonecalls = phonecalls;
		this.nextIntervalUri = nextIntervalUri;
		this.previousIntervalUri = previousIntervalUri;
	}

	public List<PhoneCall> getPhonecalls() {
		return phonecalls;
	}

	public void setPhonecalls(List<PhoneCall> phonecalls) {
		this.phonecalls = phonecalls;
	}

	public URI getNextIntervalUri() {
		return nextIntervalUri;
	}

	public void setNextIntervalUri(URI nextIntervalUri) {
		this.nextIntervalUri = nextIntervalUri;
	}

	public URI getPreviousIntervalUri() {
		return previousIntervalUri;
	}

	public void setPreviousIntervalUri(URI previousIntervalUri) {
		this.previousIntervalUri = previousIntervalUri;
	}

	@Override
	public String toString() {
		return "PhoneCallListInterval{" +
				"phonecalls=" + phonecalls +
				", nextIntervalUri=" + nextIntervalUri +
				", previousIntervalUri=" + previousIntervalUri +
				'}';
	}
}
