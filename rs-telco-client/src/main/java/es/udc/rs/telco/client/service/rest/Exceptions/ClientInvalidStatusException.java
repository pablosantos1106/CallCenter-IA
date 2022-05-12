package es.udc.rs.telco.client.service.rest.Exceptions;

public class ClientInvalidStatusException extends Exception{

    private long e;
    private int m;
    private int y;

    public ClientInvalidStatusException(Long e, int m, int y){
        super("The PhoneCall with id="+ e +" in year=" + y +" and month=" + m +" has not the status PENDING");
        this.e = e;
        this.m = m;
        this.y = y;
    }

    public Long getE() { return e; }

    public void setE(Long e) { this.e = e; }

    public int getM() { return m; }

    public void setM(int m) { this.m = m; }

    public int getY() { return y; }

    public void setY(int y) { this.y = y; }
}
