package es.udc.rs.telco.model.telcoservice.exceptions;

public class MonthNotExpiredException extends Exception {

    private  int month;

    public MonthNotExpiredException(int month){
        super( "Month:" + month + " has not expired yet");
        this.month = month;
    }

    public int getMonth() { return month; }

    public void setMonth(int month) { this.month = month; }
}
