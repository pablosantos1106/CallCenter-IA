package es.udc.rs.telco.jaxrs.dto;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "customers")
@XmlType(name = "customerListType")
public class CustomerDtoJaxbList {

	@XmlElement(name = "customerDetails")
	private List<CustomerDetailsDtoJaxb> customers = null;

	public CustomerDtoJaxbList() { this.customers = new ArrayList<CustomerDetailsDtoJaxb>();}


	public CustomerDtoJaxbList(List<CustomerDetailsDtoJaxb> customers) {
		this.customers = customers;
	}

	public List<CustomerDetailsDtoJaxb> getCustomers() {
		return customers;
	}

	public void setCustomers(List<CustomerDetailsDtoJaxb> customers) {
		this.customers = customers;
	}

	@Override
	public String toString() {
		return "CustomerDtoJaxbList{" +
				"customers=" + customers +
				'}';
	}

}
