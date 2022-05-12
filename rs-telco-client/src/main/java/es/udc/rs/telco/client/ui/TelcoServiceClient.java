package es.udc.rs.telco.client.ui;

import es.udc.rs.telco.client.service.rest.*;
import es.udc.rs.telco.client.service.rest.dto.ClientCustomerDtoJaxb;
import es.udc.rs.telco.client.service.rest.dto.ClientPhoneCallDtoJaxb;
import es.udc.rs.telco.client.service.rest.CustomerDto;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.rs.telco.client.service.rest.Exceptions.*;

import java.time.LocalDateTime;
import java.util.List;

public class TelcoServiceClient {

	public static void main(String[] args) {

		if (args.length == 0) {
			printUsageAndExit();
		}
		ClientTelcoService clientTelcoService = ClientTelcoServiceFactory.getService();
		if ("-addClient".equalsIgnoreCase(args[0])) {
			validateArgs(args, 5, new int[]{});

			// [-addCustomer] TelcoServiceClient -addClient <name> <dni> <address> <phoneNumber>

			try {
				CustomerDto c = new CustomerDto(String.valueOf(args[1]), String.valueOf(args[2]), String.valueOf(args[3]), String.valueOf(args[4]));
				ClientCustomerDtoJaxb customerResult = clientTelcoService.addCustomer(c);
				System.out.println("Customer " + customerResult.getCustomerId() + " " + "created sucessfully");

			} catch (InputValidationException | ClientDNIAlreadyRegisteredException | ClientNotAddedCustomerException ex) {
				ex.printStackTrace(System.err);

			} catch (Exception ex) {
				ex.printStackTrace(System.err);
			}


		} else if ("-addPhoneCall".equalsIgnoreCase(args[0])) {
			validateArgs(args, 6, new int[]{1, 3});

			//[-addPhoneCall] ClientTelcoServiceClient -addPhoneCalls <clientId> <startDate> <duration> <destination> <PhoneCallType>

			try {
				PhoneCallDto p = new PhoneCallDto(Long.valueOf(args[1]), (args[2]),Long.valueOf(args[3]), String.valueOf(args[4]), (args[5]));
				ClientPhoneCallDtoJaxb phoneResult = clientTelcoService.addCall(p);
				System.out.println("\n");
				System.out.println("PhoneCall with ID " + phoneResult.getPhoneCallId() + " created successfully");
			} catch (NumberFormatException | InstanceNotFoundException | InputValidationException ex) {
				ex.printStackTrace(System.err);

			} catch (Exception ex) {
				ex.printStackTrace(System.err);
			}

		} else if ("-removeClient".equalsIgnoreCase(args[0])) {
			validateArgs(args, 2, new int[]{1});

			//[-removeClient] ClienteTelcoServiceClient -removeClient <clientId>

			try {
				clientTelcoService.deleteCustomer(Long.valueOf(args[1]));
				System.out.println("Customer with ID " + args[1] + " removed successfully");
			} catch (ClientCustomerWithPhoneCallsException | InstanceNotFoundException |
					InputValidationException | ClientNotAddedCustomerException ex) {
				ex.printStackTrace(System.err);
			} catch (Exception ex) {
				ex.printStackTrace(System.err);
			}

		} else if ("-findPhoneCalls".equalsIgnoreCase(args[0])) {

			// [-findPhoneCalls] ClientTelcoServiceClient -findPhoneCalls <customerId> <startDate> <finalDate> <tipo> <startIndex> <count>

			if (args.length == 4){
				validateArgs(args, 4, new int[]{1});
				try {
					Long customerId = Long.valueOf(args[1]);
					LocalDateTime firstDay = LocalDateTime.parse(args[2]);
					LocalDateTime lastDay = LocalDateTime.parse(args[3]);
					PhoneCallListInterval phoneCalls = clientTelcoService.findCallByIntervalWithoutIndexAndCountAndType(customerId,firstDay, lastDay);
					System.out.println(phoneCalls);
				} catch (NumberFormatException | InputValidationException | ClientNotAddedCustomerException ex) {
					ex.printStackTrace(System.err);
				} catch (Exception ex) {
					ex.printStackTrace(System.err);
				}

			} else if (args.length == 5) {
				validateArgs(args, 5, new int[]{1});
				try {
					Long customerId = Long.valueOf(args[1]);
					LocalDateTime firstDay = LocalDateTime.parse(args[2]);
					LocalDateTime lastDay = LocalDateTime.parse(args[3]);
					String tipo = String.valueOf(args[4]);
					PhoneCallListInterval phoneCalls = clientTelcoService.findCallByIntervalWithoutIndexAndCount(customerId,
							firstDay, lastDay, tipo);
					System.out.println(phoneCalls);

				} catch (NumberFormatException | InputValidationException | ClientNotAddedCustomerException ex) {
					ex.printStackTrace(System.err);
				} catch (Exception ex) {
					ex.printStackTrace(System.err);
				}
			} else if (args.length == 6){
				validateArgs(args, 6, new int[]{1,4,5});
				try {
					Long customerId = Long.valueOf(args[1]);
					LocalDateTime firstDay = LocalDateTime.parse(args[2]);
					LocalDateTime lastDay = LocalDateTime.parse(args[3]);
					Integer startIndex = Integer.valueOf(args[4]);
					Integer count = Integer.valueOf(args[5]);
					PhoneCallListInterval phoneCalls = clientTelcoService.findCallByIntervalWithoutType(customerId,
							firstDay, lastDay, startIndex, count);
					System.out.println(phoneCalls);
				} catch (NumberFormatException | InputValidationException | ClientNotAddedCustomerException ex) {
					ex.printStackTrace(System.err);
				} catch (Exception ex) {
					ex.printStackTrace(System.err);
				}
			}else {
				validateArgs(args,7,new int[]{1,5,6});
				try {
					Long customerId = Long.valueOf(args[1]);
					LocalDateTime firstDay = LocalDateTime.parse(args[2]);
					LocalDateTime lastDay = LocalDateTime.parse(args[3]);
					String tipo = String.valueOf(args[4]);
					Integer startIndex = Integer.valueOf(args[5]);
					Integer count = Integer.valueOf(args[6]);
					PhoneCallListInterval phoneCalls = clientTelcoService.findCallByInterval(customerId,
							firstDay, lastDay, tipo, startIndex, count);
					System.out.println(phoneCalls);

				} catch (NumberFormatException | InputValidationException | ClientNotAddedCustomerException ex) {
					ex.printStackTrace(System.err);
				} catch (Exception ex) {
					ex.printStackTrace(System.err);
				}

			}

		} else if ("-changeState".equalsIgnoreCase(args[0])) {
			validateArgs(args, 5, new int[]{1, 2, 3});

			// [-changeState] ClientTelcoServiceClient -changeState <clientId> <month> <year> <status>
			try {
				Long customerId = Long.parseLong(args[1]);
				Integer month = Integer.valueOf(args[2]);
				Integer year = Integer.valueOf(args[3]);
				clientTelcoService.changeState(customerId, month, year, args[4]);
			} catch (NumberFormatException | InstanceNotFoundException | InputValidationException |
					ClientMonthNotExpiredException | ClientNotAddedCustomerException | ClientWrongPhoneCallStatusException ex) {
				ex.printStackTrace(System.err);
			} catch (Exception ex) {
				ex.printStackTrace(System.err);
			}
		}
	}


	public static void validateArgs(String[] args, int expectedArgs, int[] numericArguments) {
		if (expectedArgs != args.length) {
			printUsageAndExit();
		}
		for (int i = 0; i < numericArguments.length; i++) {
			int position = numericArguments[i];
			try {
				Double.parseDouble(args[position]);
			} catch (NumberFormatException n) {
				printUsageAndExit();
			}
		}
	}

	public static void printUsageAndExit() {
		printUsage();
		System.exit(-1);
	}

	public static void printUsage() {
		System.err.println(
				"Usage:\n" + "    [-addClient]    TelcoServiceClient -addClient <name> <dni> <address> <phoneNumber>\n" +
							 "	  [-findPhoneCalls]   TelcoServiceClient -findPhoneCalls <customerId> <startDate> <finalDate> <tipo> <startIndex> <count>\n" +
							 "    [-addPhoneCall]   TelcoServiceClient -addPhoneCall <clientId> <startDate> <duration> <destination> <PhoneCallType>\n" +
						     "    [-removeClient] TelcoServiceClient -removeClient <clientId>\n" +
							 "	  [-changeState] TelcoServiceClient -changeState <clientId>  <month> <year> <status>\n" +
						     "    ...\n");
	}
}
