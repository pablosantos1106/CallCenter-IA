package es.udc.rs.telco.model.telcoservice.exceptions;

public class InvalidStatusException extends Exception{

    private long phoneCallId;
    private int month;
    private int year;

    public InvalidStatusException(Long phoneCallId, int month, int year){
        super("The PhoneCall with id="+ phoneCallId +" in year=" + year +" and month=" + month +" has not the status PENDING");
        this.phoneCallId = phoneCallId;
        this.month = month;
        this.year = year;
    }

    public long getPhoneCallId() {
        return phoneCallId;
    }

    public void setPhoneCallId(long phoneCallId) {
        this.phoneCallId = phoneCallId;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
