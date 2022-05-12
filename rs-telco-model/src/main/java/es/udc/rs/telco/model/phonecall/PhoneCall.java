package es.udc.rs.telco.model.phonecall;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Objects;

public class PhoneCall {

    private Long phoneCallId;
    private Long customerId;
    private LocalDateTime startDate;
    private Long duration;
    private String destinationNumber;
    private PhoneCallType phoneCallType;
    private PhoneCallStatus phoneCallStatus;
	private URI linkUri;
    
	public PhoneCall(Long clientId, LocalDateTime startDate, Long duration, String destinationNumber,
			PhoneCallType phoneCallType) {
		super();
		this.customerId = clientId;
		this.startDate = startDate;
		this.duration = duration;
		this.destinationNumber = destinationNumber;
		this.phoneCallType = phoneCallType;
	}

	public PhoneCall(Long phoneCallId, Long customerId, LocalDateTime startDate, Long duration, String destinationNumber, PhoneCallType phoneCallType, PhoneCallStatus phoneCallStatus) {
		this.phoneCallId = phoneCallId;
		this.customerId = customerId;
		this.startDate = startDate;
		this.duration = duration;
		this.destinationNumber = destinationNumber;
		this.phoneCallType = phoneCallType;
		this.phoneCallStatus = phoneCallStatus;
	}

	public PhoneCall(PhoneCall p) {
		this(p.getPhoneCallId(), p.getCustomerId(), p.getStartDate(), p.getDuration(), p.getDestinationNumber(), p.getPhoneCallType(), p.getPhoneCallStatus());
	}

    public PhoneCall(LocalDateTime startDate, Long duration, String destinationNumber, URI linkUri) {
		this.startDate = startDate;
		this.duration = duration;
		this.destinationNumber = destinationNumber;
		this.linkUri = linkUri;
    }


    public Long getPhoneCallId() {
		return phoneCallId;
	}

	public void setPhoneCallId(Long phoneCallId) {
		this.phoneCallId = phoneCallId;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public LocalDateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}

	public Long getDuration() {
		return duration;
	}

	public void setDuration(Long duration) {
		this.duration = duration;
	}

	public String getDestinationNumber() {
		return destinationNumber;
	}

	public void setDestinationNumber(String destinationNumber) {
		this.destinationNumber = destinationNumber;
	}

	public PhoneCallType getPhoneCallType() {
		return phoneCallType;
	}

	public void setPhoneCallType(PhoneCallType phoneCallType) {
		this.phoneCallType = phoneCallType;
	}

	public PhoneCallStatus getPhoneCallStatus() {
		return phoneCallStatus;
	}

	public void setPhoneCallStatus(PhoneCallStatus phoneCallStatus) {
		this.phoneCallStatus = phoneCallStatus;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		PhoneCall phoneCall = (PhoneCall) o;
		return phoneCallId.equals(phoneCall.phoneCallId) && Objects.equals(customerId, phoneCall.customerId) && Objects.equals(startDate, phoneCall.startDate) && Objects.equals(duration, phoneCall.duration) && Objects.equals(destinationNumber, phoneCall.destinationNumber) && phoneCallType == phoneCall.phoneCallType && phoneCallStatus == phoneCall.phoneCallStatus;
	}

	@Override
	public int hashCode() {
		return Objects.hash(phoneCallId, customerId, startDate, duration, destinationNumber, phoneCallType, phoneCallStatus);
	}

	@Override
	public String toString() {
		return "PhoneCall{" +
				"phoneCallId=" + phoneCallId +
				", customerId=" + customerId +
				", startDate=" + startDate +
				", duration=" + duration +
				", destinationNumber='" + destinationNumber + '\'' +
				", phoneCallType=" + phoneCallType +
				", phoneCallStatus=" + phoneCallStatus +
				'}';
	}
}