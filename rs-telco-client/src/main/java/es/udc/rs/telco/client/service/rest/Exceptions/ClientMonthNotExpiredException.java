package es.udc.rs.telco.client.service.rest.Exceptions;

public class ClientMonthNotExpiredException extends Exception{
    private  int month;

    public ClientMonthNotExpiredException(int month){
        super( "Month:" + month + " has not expired yet");
        this.month = month;
    }

    public int getMonth() { return month; }

    public void setMonth(int month) { this.month = month; }
}

