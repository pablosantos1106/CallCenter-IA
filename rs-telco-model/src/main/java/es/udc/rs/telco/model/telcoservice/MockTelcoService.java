package es.udc.rs.telco.model.telcoservice;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import es.udc.rs.telco.model.customer.Customer;
import es.udc.rs.telco.model.phonecall.PhoneCall;
import es.udc.rs.telco.model.phonecall.PhoneCallStatus;
import es.udc.rs.telco.model.phonecall.PhoneCallType;
import es.udc.rs.telco.model.telcoservice.exceptions.*;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.util.validation.PropertyValidator;


public class MockTelcoService implements TelcoService {

	private static Map<Long, Customer> clientsMap = new LinkedHashMap<Long, Customer>();
	private static Map<Long, PhoneCall> phoneCallsMap = new LinkedHashMap<Long, PhoneCall>();
	private static Map<Long, List<PhoneCall>> phoneCallsByUserMap = new LinkedHashMap<Long, List<PhoneCall>>();

	private static long lastClientId = 0;
	private static long lastPhoneCallId = 0;

	public static Map<Long, List<PhoneCall>> getPhoneCallsByUserMap() {
		return phoneCallsByUserMap;
	}


	public static void setPhoneCallsByUserMap(Map<Long, List<PhoneCall>> phoneCallsByUserMap) {
		MockTelcoService.phoneCallsByUserMap = phoneCallsByUserMap;
	}

	private static synchronized long getNextClientId() {
		return ++lastClientId;
	}

	private static synchronized long getNextPhoneCallId() {
		return ++lastPhoneCallId;
	}

	public static long getLastPhoneCallId() {
		return lastPhoneCallId;
	}

	public static void setLastPhoneCallId(Long lastPhoneCallId) {
		MockTelcoService.lastPhoneCallId = lastPhoneCallId;
	}

	private static boolean isNumeric(String str) {
		try {
			double d = Double.parseDouble(str);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

	private static void validateDNI(String dni) throws InputValidationException {
		if (dni == null) {
			throw new InputValidationException("DNI cannot be null");
		}
		if  ((dni.length() == 9) && (Character.isLetter(dni.charAt(8)))) {
			for (int i = 0; i < 8; i++) {
				if (!Character.isDigit(dni.charAt(i))) {
					throw new InputValidationException(dni + " format is not valid ");
				}
			}
		} else {throw new InputValidationException(dni + " format is not valid ");}
	}

	private static void validatePhoneNumber(String phoneNumber) throws InputValidationException {
		if (phoneNumber.length() != 9 || (!isNumeric(phoneNumber))) {
			throw new InputValidationException(phoneNumber + " is invalid, must be a 9 length digit");
		}
	}

	private void validateCustomerData(Customer c) throws InputValidationException {
		if (c!=null) {
			PropertyValidator.validateMandatoryString("name", c.getName());
			PropertyValidator.validateMandatoryString("dni", c.getDni());
			validateDNI(c.getDni());
			PropertyValidator.validateMandatoryString("address", c.getAddress());
			PropertyValidator.validateMandatoryString("tlf", c.getPhoneNumber());
			validatePhoneNumber(c.getPhoneNumber());
		} else {
			throw new InputValidationException("Invalid input data");
		}

	}

	private static void validatePhoneCallData(PhoneCall p) throws InputValidationException {
		if (p.getCustomerId() == null ) {throw new InputValidationException(" Customer has no ID");}
		else{
			PropertyValidator.validateLong("customerId", p.getCustomerId(), 0, Integer.MAX_VALUE);
		}

		PropertyValidator.validateLong("duration", p.getDuration(), 0, Integer.MAX_VALUE);
		validatePhoneNumber(p.getDestinationNumber());
		if ((p.getStartDate().isEqual(LocalDateTime.now())) || p.getStartDate().isAfter(LocalDateTime.now())) {
			throw new InputValidationException("The start date must be before to the current date");
		}
	}

	private static void validateCustomerId(Long c) throws InputValidationException, NotAddedCustomerException {
		if (c==null) {throw new NotAddedCustomerException(c);}
		else if (c < 0) {throw new InputValidationException("Invalid CustomerId, cannot be negative");}
			else if (c>Integer.MAX_VALUE) {throw new InputValidationException("Invalid CustomerId, overflows Integer MaxValue ");}
	}

	private static void checkDNIAlreadyUsed(String dni) throws DNIAlreadyRegisteredException {
		for (Customer cus : clientsMap.values()) {
			if (cus.getDni().equals(dni) ){
				throw new DNIAlreadyRegisteredException(dni);
			}
		}
	}

	public Customer addCustomer(Customer c) throws InputValidationException, DNIAlreadyRegisteredException, NotAddedCustomerException {
		validateCustomerData(c);
		checkDNIAlreadyUsed(c.getDni());
		c.setCustomerId(getNextClientId());
		validateCustomerId(c.getCustomerId());
		c.setCreationDate(LocalDateTime.now());
		clientsMap.put(c.getCustomerId(), new Customer(c));
		return c;
	}

	public void modifyCustomer(Long CustomerId, String DNI, String name, String address) throws InstanceNotFoundException, InputValidationException , DNIAlreadyRegisteredException, NotAddedCustomerException {
		validateCustomerId(CustomerId); // Validamos el ID del cliente
		Customer custom = clientsMap.get(CustomerId); //Buscamos el cliente por su ID

		if (custom == null){
			throw new InstanceNotFoundException(CustomerId,"Customer");}
		else{
			if (DNI != null) {
				validateDNI(DNI);
				checkDNIAlreadyUsed(DNI);
				String oldDNI = custom.getDni();
				custom.setDni(DNI);
				System.out.println("Customer ID (" + CustomerId + ")'s DNI has changed ("+ oldDNI+ " -> " + custom.getDni() +")");
			}
			if (name != null){
				PropertyValidator.validateMandatoryString("name", name);
				String oldName = custom.getName();
				custom.setName(name);
				System.out.println("Customer ID (" + CustomerId + ")'s name has changed ("+ oldName+ " -> " + custom.getName() +")");
			}
			if (address != null){
				PropertyValidator.validateMandatoryString("address", address);
				String oldAddress = custom.getAddress();
				custom.setAddress(address);
				System.out.println("Customer ID (" + CustomerId + ")'s address has changed ("+ oldAddress+ " -> " + custom.getAddress() +")");
			}
		}
	}

	public Map<Long, Customer> deleteCustomer(Long customerId) throws CustomerWithPhoneCallsException, InputValidationException, NotAddedCustomerException, InstanceNotFoundException {
		validateCustomerId(customerId);
		if (clientsMap.get(customerId) != null ) {
			if (phoneCallsByUserMap.get(customerId) == null) {
				clientsMap.remove(customerId);
				if (clientsMap.size()==0){
					lastClientId=0;
				}
			} else {
				throw new CustomerWithPhoneCallsException(customerId);
			}
		} else{
			throw new InstanceNotFoundException(customerId, "customerID");
		}
		return clientsMap;
	}



	//Buscar Clientes por DNI
	public Customer findCustomerByDNI(String dni) throws InstanceNotFoundException, InputValidationException {
		validateDNI(dni);
		//Recorre la lista
		for (Customer c : clientsMap.values()) {
			//Busca si hay algun elemento con el mismo DNI.
			if (c.getDni().contains(dni)) {
				return new Customer(c);
			}
		}
		//Si no encuentra ninguno laza excepcion de que no existe.
		throw new InstanceNotFoundException(dni, "DNI");
	}


	//Buscar CLiente Por ID
	public Customer findCustomerByID(Long id) throws InstanceNotFoundException, InputValidationException, NotAddedCustomerException {
		validateCustomerId(id);
		Customer c = clientsMap.get(id);
		if (c == null) {
			throw new InstanceNotFoundException(id, "customerId");
		} else return new Customer(c);
	}

	public List<Customer> findCustomerByName(String keywords, Integer startIndex, Integer count) {

		List<Customer> foundCustomers = new ArrayList<>();
		String searchKeyword = keywords != null ? keywords.toLowerCase() : "";
		for (Customer c : clientsMap.values()) {
			if (c.getName().toLowerCase().contains(searchKeyword)) {
				foundCustomers.add(new Customer(c));
			}
		}
		int fromIndex = (foundCustomers.size() > startIndex) ? startIndex
				: foundCustomers.size();
		int toIndex = (foundCustomers.size() >= startIndex + count) ? startIndex
				+ count : foundCustomers.size();

		return foundCustomers.subList(fromIndex, toIndex);
	}

	public List<PhoneCall> findCallByInterval(Long customerId, LocalDateTime firstDay, LocalDateTime lastDay, PhoneCallType tipo, Integer startIndex, Integer count) throws InputValidationException, NotAddedCustomerException, InstanceNotFoundException {
		List<PhoneCall> foundPhoneCalls = new ArrayList<>();
		validateCustomerId(customerId);
		List<PhoneCall> l = phoneCallsByUserMap.get(customerId);
		if (l != null){
			if (firstDay != null && lastDay != null) {
				for (PhoneCall p : l) {
					if (p.getCustomerId().equals(customerId)) {
						if (p.getStartDate().isAfter(firstDay) && p.getStartDate().isBefore(lastDay)) {

							if (tipo != null) {

								if (p.getPhoneCallType() == tipo) {

									foundPhoneCalls.add(new PhoneCall(p));
								}
							} else {
								foundPhoneCalls.add(new PhoneCall(p));
							}
						}
					}
				}
			}else{
				throw new InputValidationException("firstDay or lastDay cannot be null");
			}
		}else{
			throw new InstanceNotFoundException(customerId,"Customer not found");
		}

		int fromIndex = (foundPhoneCalls.size() > startIndex) ? startIndex
				: foundPhoneCalls.size();
		int toIndex = (foundPhoneCalls.size() >= startIndex + count) ? startIndex
				+ count : foundPhoneCalls.size();

		return foundPhoneCalls.subList(fromIndex,toIndex);
	}

	public PhoneCall addCall(PhoneCall p) throws InputValidationException,InstanceNotFoundException {
		validatePhoneCallData(p);
		if (clientsMap.get(p.getCustomerId()) != null) {
			p.setPhoneCallId(getNextPhoneCallId());
			p.setPhoneCallStatus(PhoneCallStatus.PENDING);
			PhoneCall newcall = new PhoneCall(p);
			phoneCallsMap.put(p.getPhoneCallId(), newcall);
			List<PhoneCall> calls;

			if (phoneCallsByUserMap.get(p.getCustomerId()) == null) {
				calls = new ArrayList<>();

			} else {
				calls = phoneCallsByUserMap.get(p.getCustomerId());
			}

			calls.add(newcall);
			phoneCallsByUserMap.put(p.getCustomerId(), calls);
			return p;
		} else {
			throw new InstanceNotFoundException(p.getCustomerId(), "customerId");
		}
	}

	public List<PhoneCall> findCallsMonth(Long customerId, int month, int year) throws MonthNotExpiredException, InvalidStatusException, InputValidationException, NotAddedCustomerException, InstanceNotFoundException {
		validateCustomerId(customerId);
		List<PhoneCall> foundCalls = new ArrayList<>();
		List<PhoneCall> phoneCalls = phoneCallsByUserMap.get(customerId);
		if (phoneCalls != null) {
			if (year <= LocalDateTime.now().getYear()){
				if ((month > 0) && (month < 13)){
					if(LocalDateTime.now().minusMonths(1L).isAfter(LocalDateTime.of(year,month,1,00,00))){
						for (PhoneCall p : phoneCalls) {
							LocalDateTime date = p.getStartDate();
							if ((date.getMonth().getValue() == month) && (date.getYear() == year)) {
								if (p.getPhoneCallStatus() == PhoneCallStatus.PENDING) {
									foundCalls.add(new PhoneCall(p));
								} else {
									throw new InvalidStatusException(p.getPhoneCallId(),month,year);
								}
							}
						}
					}else{
						throw new MonthNotExpiredException(month);
					}
				}else{
					throw new InputValidationException("The input month is not valid");
				}
			}else {
				throw new InputValidationException("The input year is not valid");
			}
			return foundCalls;
		}else{
			throw new InstanceNotFoundException(customerId, "customerId");
		}
	}

	public void updatePhoneCallStatus(Long customerID, PhoneCallStatus status, int month, int year) throws WrongPhoneCallStatusException,
			MonthNotExpiredException, InputValidationException, InstanceNotFoundException , NotAddedCustomerException {

		if (month < 0 || month > 12){ throw new InputValidationException("Invalid Month:" + month);}
		if (year > LocalDateTime.now().getYear() || month >= LocalDateTime.now().getMonth().getValue()){ throw new MonthNotExpiredException(month);}
		if ((status != PhoneCallStatus.BILLED) && (status != PhoneCallStatus.PAID)) {throw new WrongPhoneCallStatusException(status);}

		validateCustomerId(customerID);
		findCustomerByID(customerID);
		List<PhoneCall> calls = phoneCallsByUserMap.get(customerID);

		if ((calls != null) && (!calls.isEmpty())) {
			for (PhoneCall p : calls) {
				if (p.getStartDate().getMonth().getValue() == month && p.getStartDate().getYear() == year) {
					if (LocalDateTime.now().minusMonths(1L).isAfter(LocalDateTime.of(year, month, 1, 00, 00))) {
						if ((status == PhoneCallStatus.BILLED) && ((p.getPhoneCallStatus() == PhoneCallStatus.PENDING)))  {
							p.setPhoneCallStatus(status);
							System.out.println("Customer ID (" + customerID + ") PhoneCall with ID: " + p.getPhoneCallId() + " has changed his status " + PhoneCallStatus.PENDING.toString() + " to " + p.getPhoneCallStatus());
						} else if ((status == PhoneCallStatus.PAID) && (p.getPhoneCallStatus() == PhoneCallStatus.BILLED) ) {
							p.setPhoneCallStatus(status);
							System.out.println("Customer ID (" + customerID + ") PhoneCall with ID: " + p.getPhoneCallId() + " has changed his status " + PhoneCallStatus.BILLED.toString() + " to " + p.getPhoneCallStatus());
						}
					}
				}
			}
		} else {
			System.out.println("Customer ID (" + customerID + ") has no PhoneCalls yet");
		}
	}
}
