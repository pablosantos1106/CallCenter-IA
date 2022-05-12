package es.udc.rs.telco.model.customer;

import java.time.LocalDateTime;
import java.util.Objects;

public class Customer {

    private Long customerId;
    private String name;
    private String dni;
    private String address;
    private LocalDateTime creationDate;
    private String phoneNumber;
    

	public Customer(String name, String dni, String address, String phoneNumber) {
		super();
		this.name = name;
		this.dni = dni;
		this.address = address;
		this.phoneNumber = phoneNumber;
	}

	public Customer(Long customerId, String name, String dni, String address, LocalDateTime creationDate, String phoneNumber) {
		this.customerId = customerId;
		this.name = name;
		this.dni = dni;
		this.address = address;
		this.creationDate = creationDate;
		this.phoneNumber = phoneNumber;
	}

	public Customer(Customer c) {
		this(c.getCustomerId(), c.getName(), c.getDni(), c.getAddress(), c.getCreationDate(), c.getPhoneNumber());
	}


	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getAddress() {return address;}

	public void setAddress(String address) {
		this.address = address;
	}

	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Customer customer = (Customer) o;
		return customerId.equals(customer.customerId) && name.equals(customer.name) && dni.equals(customer.dni) && Objects.equals(address, customer.address) && creationDate.equals(customer.creationDate) && Objects.equals(phoneNumber, customer.phoneNumber);
	}

	@Override
	public int hashCode() {
		return Objects.hash(customerId, name, dni, address, creationDate, phoneNumber);
	}

	@Override
	public String toString() {
		return "Customer{" +
				"customerId=" + customerId +
				", name='" + name + '\'' +
				", dni='" + dni + '\'' +
				", address='" + address + '\'' +
				", creationDate=" + creationDate +
				", phoneNumber='" + phoneNumber + '\'' +
				'}';
	}

}