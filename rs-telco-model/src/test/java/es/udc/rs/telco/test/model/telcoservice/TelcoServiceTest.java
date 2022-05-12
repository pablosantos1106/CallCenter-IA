package es.udc.rs.telco.test.model.telcoservice;

import es.udc.rs.telco.model.customer.Customer;
import es.udc.rs.telco.model.phonecall.PhoneCall;
import es.udc.rs.telco.model.phonecall.PhoneCallStatus;
import es.udc.rs.telco.model.phonecall.PhoneCallType;
import es.udc.rs.telco.model.telcoservice.TelcoService;
import es.udc.rs.telco.model.telcoservice.TelcoServiceFactory;
import es.udc.rs.telco.model.telcoservice.deleteCalls;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import es.udc.rs.telco.model.telcoservice.exceptions.*;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;



import static org.junit.jupiter.api.Assertions.*;

public class TelcoServiceTest {

   private static Customer getValidCustomer(){
       return new Customer("Daniel Perez", "78065423Q", "Rua Pai Crespo 32", "609876543");
   }

    private static Customer getAnotherValidCustomer(){
        return new Customer("Ruben Dias", "79065674S", "Rua Pai 32", "609876777");
    }

    private static PhoneCall getValidPhoneCall(Customer c, LocalDateTime date){
       return new PhoneCall(c.getCustomerId(),date,Long.valueOf(20),"600335566", PhoneCallType.NATIONAL);
   }

    private static PhoneCall getAnotherValidPhoneCall(Customer c, LocalDateTime date){
        return new PhoneCall(c.getCustomerId(),date,Long.valueOf(20),"654321987", PhoneCallType.INTERNATIONAL);
    }

    private void removeCalls (){
       deleteCalls.deleteCalls();
    }

    private static TelcoService telcoService = null;

    @BeforeAll
    public static void init() {
        telcoService = TelcoServiceFactory.getService();
    }

    @Test
    public void testRemoveCustomer() throws InputValidationException, InstanceNotFoundException,
            CustomerWithPhoneCallsException, DNIAlreadyRegisteredException, NotAddedCustomerException {
                Customer c0 = getValidCustomer();
                telcoService.addCustomer(c0);
                Map<Long, Customer> clients = telcoService.deleteCustomer(c0.getCustomerId());
                assertEquals(null,clients.get(c0.getCustomerId()));
    }

    @Test
    public void testAddCustomerAndFindCustomer() throws InputValidationException, InstanceNotFoundException,
            CustomerWithPhoneCallsException , DNIAlreadyRegisteredException, NotAddedCustomerException {
        Customer c1 = new Customer("Daniel Perez", "78065424Q", "Rua Pai Crespo 32", "609876543");
        Customer c2 = new Customer("Anxo Fervenza", "32903473A", "Rua San Sebastian 12", "612095644");
        Customer c3 = new Customer("Anxo García", "12904429R", "Avenida Finisterre 21", "677665544");
        Customer c4 = new Customer("Anxo Pérez", "76458901A", "Avenida Colon 45", "654321890");
        Customer c5 = new Customer("Anxo Suárez", "78688945T", "Calle San Sebastian 12", "654444765");
        Customer c6 = new Customer("Anxo Santos", "34532312C", "Subida Nelle 89", "600111222");


            try {
                assertTrue(telcoService != null);
                telcoService.addCustomer(c1);
                telcoService.addCustomer(c2);
                telcoService.addCustomer(c3);
                telcoService.addCustomer(c4);
                telcoService.addCustomer(c5);
                telcoService.addCustomer(c6);

                //FindByName
                //Obtener 3 customers como máximo(offset=0)
                List<Customer> l = telcoService.findCustomerByName("Anxo",0,3);

                assertEquals(3, l.size());
                assertEquals(c2, l.get(0));
                assertEquals(c3, l.get(1));
                assertEquals(c4, l.get(2));

                //Obtener 2 customers como máximo(offset=2)
                List<Customer> l2 = telcoService.findCustomerByName("Anxo", 2, 2);

                assertEquals(2, l2.size());
                assertEquals(c4, l2.get(0));
                assertEquals(c5, l2.get(1));

                //Obtener 5 customers como máximo(offset=0)
                List<Customer> l3 = telcoService.findCustomerByName("Anxo", 0, 5);

                assertEquals(5, l3.size());
                assertEquals(c2, l3.get(0));
                assertEquals(c3, l3.get(1));
                assertEquals(c4, l3.get(2));
                assertEquals(c5, l3.get(3));
                assertEquals(c6, l3.get(4));

                //FindByDNI
                Customer findCustomerDNI2 = telcoService.findCustomerByDNI("32903473A");
                assertEquals(c2, findCustomerDNI2);
                Customer findCustomerDNI3 = telcoService.findCustomerByDNI("12904429R");
                assertEquals(c3, findCustomerDNI3);

                //FindByID
                Customer findCustomerID1 = telcoService.findCustomerByID(findCustomerDNI2.getCustomerId());
                assertEquals(c2, findCustomerID1);
                Customer findCustomerID3 = telcoService.findCustomerByID(findCustomerDNI3.getCustomerId());
                assertEquals(c3, findCustomerID3);

            }finally {
                telcoService.deleteCustomer(c1.getCustomerId());
                telcoService.deleteCustomer(c2.getCustomerId());
                telcoService.deleteCustomer(c3.getCustomerId());
                telcoService.deleteCustomer(c4.getCustomerId());
                telcoService.deleteCustomer(c5.getCustomerId());
                telcoService.deleteCustomer(c6.getCustomerId());
            }

    }

    @Test
    public void testFindByIDNull (){
        assertThrows(NotAddedCustomerException.class, () ->{
            telcoService.findCustomerByID(null);
        });
    }

    @Test
    public void testFindByIDNotAdded (){
        assertThrows(InstanceNotFoundException.class, () ->{
            telcoService.findCustomerByID(100L);
        });
    }

    @Test
    public void testFindByIDInvalid (){
        assertThrows(InputValidationException.class, () ->{
            telcoService.findCustomerByID(-10L);
        });
    }

    @Test
    public void testFindByIDDeleted() throws InputValidationException, DNIAlreadyRegisteredException, NotAddedCustomerException {
        Customer c1 = new Customer("Daniel Perez", "78065424Q", "Rua Pai Crespo 32", "609876543");
        telcoService.addCustomer(c1);
            assertThrows(InstanceNotFoundException.class, () ->{
                telcoService.deleteCustomer(c1.getCustomerId());
                telcoService.findCustomerByID(c1.getCustomerId());
            });
     }



    @Test
    public void testModifyCustomer() throws InputValidationException, InstanceNotFoundException,
            CustomerWithPhoneCallsException, DNIAlreadyRegisteredException, NotAddedCustomerException {
        Customer c = new Customer("Daniel Perez", "77014477Q", "Rua Pai Crespo 32", "609876543");
        String name = "Pablo Santos";
        String address = "Calle San Sebastian";
        String dni = "39462918R";

        try {
            telcoService.addCustomer(c);
            telcoService.modifyCustomer(c.getCustomerId(),dni,null,null);
            Customer cModified = telcoService.findCustomerByDNI("39462918R");
            //Comprobamos que solo se ha modificado el campo DNI
            assertEquals(dni, cModified.getDni()); assertNotEquals(name, cModified.getName());assertNotEquals(address, cModified.getAddress());

            //Modificamos únicamente el nombre y volvemos a buscarlo
            telcoService.modifyCustomer(c.getCustomerId(),null,name,null);
            Customer cModified2 = telcoService.findCustomerByDNI("39462918R");
            //Comprobamos que el dni y el nombre coinciden pero la dirección no (todavía no ha sido modificada)
            assertEquals(dni, cModified2.getDni()); assertEquals(name, cModified2.getName());assertNotEquals(address, cModified2.getAddress());

            //Modificamos la dirección volvemos a buscarlo
            telcoService.modifyCustomer(c.getCustomerId(),null,null,address);
            Customer cModified3 = telcoService.findCustomerByDNI("39462918R");
            //Comprobamos que el dni y el nombre coinciden pero la dirección no (todavía no ha sido modificada)
            assertEquals(dni, cModified3.getDni()); assertEquals(name, cModified3.getName());assertEquals(address, cModified3.getAddress());
        }
        finally {
            telcoService.deleteCustomer(c.getCustomerId());
        }
    }

    @Test
    public void testFindByDNInvalid (){

        assertThrows(InputValidationException.class, () ->{
            telcoService.findCustomerByDNI("7776666907,.-");
        });

        assertThrows(InputValidationException.class, () ->{
            telcoService.findCustomerByDNI("AABBEECC2,.-");
        });

        assertThrows(InstanceNotFoundException.class, () ->{
            telcoService.findCustomerByDNI("36676456J");
        });

        assertThrows(InputValidationException.class, () ->{
            telcoService.findCustomerByDNI(null);
        });
    }

    @Test
    public void testAddInvalidCustomer(){


        // Customer null
        assertThrows(InputValidationException.class, () ->{
            telcoService.addCustomer(null);
        });

        //DNI erróneo
        assertThrows(InputValidationException.class, () ->{
            Customer c = new Customer("Daniel Perez", "12345678@", "Rua Pai Crespo 32", "609876543");
            telcoService.addCustomer(c);
        });
        //Dni con más caracteres
        assertThrows(InputValidationException.class, () ->{
            Customer c = new Customer("Daniel Perez", "123456784R", "Rua Pai Crespo 32", "609876543");
            telcoService.addCustomer(c);
        });
        //Tlf con mas caracteres
        assertThrows(InputValidationException.class, () ->{
            Customer c = new Customer("Daniel Perez", "12345678R", "Rua Pai Crespo 32", "6098765543");
            telcoService.addCustomer(c);
        });

        //insertar segundo customer con el mismo DNI que el primer customer
        assertThrows(InputValidationException.class, () ->{
            Customer c = new Customer("Daniel Perez", "12345678R", "Rua Pai Crespo 32", "6098765543");
            Customer c2 = new Customer("Pedro Perez", "12345678R", "Subida Ronda Nelle 45", "654231897");
            telcoService.addCustomer(c);
            telcoService.addCustomer(c2);
            telcoService.deleteCustomer(c.getCustomerId());
        });
    }

    @Test
    public void testRemoveInvalidCustomer() throws InputValidationException, InstanceNotFoundException,
            CustomerWithPhoneCallsException, DNIAlreadyRegisteredException, NotAddedCustomerException {
        Customer c = telcoService.addCustomer(getValidCustomer());

        try{
            //customerId negativo
            assertThrows(InputValidationException.class, () ->{
                telcoService.deleteCustomer(-1L);
            });

            //customerId que no existe
            assertThrows(InstanceNotFoundException.class, () ->{
                telcoService.deleteCustomer(85L);
            });

            //Customer null
            assertThrows(NotAddedCustomerException.class, () ->{
                telcoService.deleteCustomer(null);
            });

            assertThrows(CustomerWithPhoneCallsException.class, () ->{
                    PhoneCall p = getValidPhoneCall(c,LocalDateTime.of(2020,03,14,15,00));
                    telcoService.addCall(p);
                    telcoService.deleteCustomer(c.getCustomerId());
                });
        }finally {
            removeCalls();
            telcoService.deleteCustomer(c.getCustomerId());
        }
    }

    @Test
    public void testModifyInvalidCustomer() throws InputValidationException, InstanceNotFoundException,
            CustomerWithPhoneCallsException , DNIAlreadyRegisteredException, NotAddedCustomerException {
            Customer c0 = new Customer("Daniel Perez", "78065423Q", "Rua Pai Crespo 32", "609876543");
            Customer c1 = new Customer("Anxo Fervenza", "32903453A", "Rua San Sebastian 12", "612095644");
            telcoService.addCustomer(c0);
            telcoService.addCustomer(c1);
            String invalidDNI = "0000000000000";

            try {
                //AlreadyUsed DNI (modificamos el DNI de c0 a un DNI ya registrado (c1))
                assertThrows(DNIAlreadyRegisteredException.class, () -> {
                    telcoService.modifyCustomer(c0.getCustomerId(),"32903453A",null,null);
                });

                //Formato de DNI Inválido
                assertThrows(InputValidationException.class, () -> {
                    telcoService.modifyCustomer(c1.getCustomerId(),invalidDNI,null, null);
                    System.out.println(c1.getDni());
                });

                //Costumer without CustomerId
                assertThrows(InstanceNotFoundException.class, () -> {
                    Customer c3 = new Customer("Daniel Perez", "12332214Q", "Rua Pai Crespo 32", "609876543");
                    telcoService.addCustomer(c3);
                    telcoService.deleteCustomer(c3.getCustomerId());
                    telcoService.modifyCustomer(c3.getCustomerId(), c3.getDni(),c3.getName(),c3.getAddress());
                });

                //Customer NULL
                assertThrows(NotAddedCustomerException.class, () -> {
                    telcoService.modifyCustomer(null,null,null,null);
                });
            }
            finally {
                telcoService.deleteCustomer(c0.getCustomerId());
                telcoService.deleteCustomer(c1.getCustomerId());
            }
    }


    @Test
    public void testAddCallAndFindCall() throws InputValidationException, InstanceNotFoundException, InvalidStatusException,
            CustomerWithPhoneCallsException, MonthNotExpiredException, DNIAlreadyRegisteredException, NotAddedCustomerException {
        Customer c0 = getValidCustomer();
        Customer c1 = getAnotherValidCustomer();


        try{
            assertTrue(telcoService != null);
            telcoService.addCustomer(c0);
            telcoService.addCustomer(c1);

            PhoneCall p0 = getValidPhoneCall(c0, LocalDateTime.of(2021, 9,30 , 16, 40).withSecond(0));

            PhoneCall p1 = getValidPhoneCall(c1, LocalDateTime.of(2019, 04, 28, 23, 16).withSecond(0));
            PhoneCall p2 = getValidPhoneCall(c1, LocalDateTime.of(2019, 04, 17, 19, 06).withSecond(0));
            PhoneCall p3 = getAnotherValidPhoneCall(c1, LocalDateTime.of(2020, 07, 04, 19, 20).withSecond(0));
            PhoneCall p4 = getAnotherValidPhoneCall(c1, LocalDateTime.of(2021, 01, 07, 12, 23).withSecond(0));
            PhoneCall p5 = getValidPhoneCall(c1, LocalDateTime.of(2019, 06, 19, 18, 45).withSecond(0));


            telcoService.addCall(p0);
            telcoService.addCall(p1);
            telcoService.addCall(p2);
            telcoService.addCall(p3);
            telcoService.addCall(p4);
            telcoService.addCall(p5);


            //FindByMonth
            List<PhoneCall> l1 = telcoService.findCallsMonth(c0.getCustomerId(), 9, 2021);
            List<PhoneCall> l2 = telcoService.findCallsMonth(c1.getCustomerId(), 04, 2019);

            assertEquals(2, l2.size());
            assertEquals(l2.get(0), p1);
            assertEquals(l2.get(1), p2);
            assertEquals(1,l1.size());
            assertEquals(l1.get(0), p0);

            //FindByInterval
            //Obtener 2 llamadas como máximo (offset=0) en el intervalo dado sin tipo
            List<PhoneCall> list1 = telcoService.findCallByInterval(c1.getCustomerId(), LocalDateTime.of(2019, 01, 01, 00, 00).withSecond(0),
                    LocalDateTime.of(2020, 01, 01, 01, 00, 00).withSecond(0), null, 0, 2);

            assertEquals(2, list1.size());
            assertEquals(p1, list1.get(0));
            assertEquals(p2, list1.get(1));

            //Obtener 2 llamadas como máximo (offset=0) en el intervalo dado que sean NACIONALES
            List<PhoneCall> list2 = telcoService.findCallByInterval(c1.getCustomerId(), LocalDateTime.of(2019, 05, 01, 00, 00).withSecond(0),
                    LocalDateTime.of(2020, 12, 01, 01, 00, 00).withSecond(0), PhoneCallType.NATIONAL, 0, 2);

            assertEquals(1, list2.size());
            assertEquals(p5, list2.get(0));

            //Obtener 2 llamadas como máximo (offset=1) en el intervalo dado que sean INTERNACIONALES
            List<PhoneCall> list3 = telcoService.findCallByInterval(c1.getCustomerId(), LocalDateTime.of(2020, 01, 01, 00, 00).withSecond(0),
                    LocalDateTime.of(2021, 10, 01, 01, 00, 00).withSecond(0), PhoneCallType.INTERNATIONAL, 1, 2);

            assertEquals(1, list3.size());
            assertEquals(p4, list3.get(0));


        }finally{
            removeCalls();
            telcoService.deleteCustomer(c0.getCustomerId());
            telcoService.deleteCustomer(c1.getCustomerId());
        }
    }

    @Test
    public void testAddCallWithInvalidData() throws InputValidationException, InstanceNotFoundException,
            CustomerWithPhoneCallsException , DNIAlreadyRegisteredException, NotAddedCustomerException {
        Customer c = getValidCustomer();
        telcoService.addCustomer(c);

        try {

            assertThrows(InputValidationException.class, () -> {
                PhoneCall p = getValidPhoneCall(c, LocalDateTime.of(2020, 9, 9, 15, 00));
                p.setCustomerId(-1L);
                telcoService.addCall(p);
            });

            assertThrows(InputValidationException.class, () -> {
                PhoneCall p = getValidPhoneCall(c, LocalDateTime.of(2020, 9, 9, 15, 00));
                p.setCustomerId(null);
                telcoService.addCall(p);
            });

            assertThrows(InputValidationException.class, () -> {
                PhoneCall p = getValidPhoneCall(c, LocalDateTime.of(2020, 9, 9, 15, 00));
                p.setCustomerId(Integer.MAX_VALUE + 1L);
                telcoService.addCall(p);
            });

            assertThrows(InputValidationException.class, () -> {
                PhoneCall p = getValidPhoneCall(c, LocalDateTime.of(2020, 9, 9, 15, 00));
                p.setDuration(-1L);
                telcoService.addCall(p);
            });

            assertThrows(InputValidationException.class, () -> {
                PhoneCall p = getValidPhoneCall(c, LocalDateTime.of(2020, 9, 9, 15, 00));
                p.setDuration(Integer.MAX_VALUE + 1L);
                telcoService.addCall(p);
            });

            assertThrows(InputValidationException.class, () -> {
                PhoneCall p = getValidPhoneCall(c, LocalDateTime.of(2020, 9, 9, 15, 00));
                p.setDestinationNumber("699");
                telcoService.addCall(p);
            });

            assertThrows(InputValidationException.class, () -> {
                PhoneCall p = getValidPhoneCall(c, LocalDateTime.of(2022, 9, 9, 15, 00));
                telcoService.addCall(p);
            });

            assertThrows(InstanceNotFoundException.class, () -> {
                PhoneCall p = getValidPhoneCall(c, LocalDateTime.of(2020, 9, 9, 15, 00));
                p.setCustomerId(85L);
                telcoService.addCall(p);
            });
        } finally {
            telcoService.deleteCustomer(c.getCustomerId());
        }
    }

    @Test
    public void testFindCallsMonthWithInvalidData () throws InstanceNotFoundException, InputValidationException,
            CustomerWithPhoneCallsException, DNIAlreadyRegisteredException, NotAddedCustomerException {
        Customer c = getValidCustomer();
        telcoService.addCustomer(c);
        telcoService.addCall(getValidPhoneCall(c, LocalDateTime.of(2020, 10, 10, 15, 00)));
        telcoService.addCall(getValidPhoneCall(c, LocalDateTime.of(2021, 07, 10, 15, 00)));
        telcoService.addCall(getValidPhoneCall(c, LocalDateTime.of(2021, 8, 10, 15, 00)));

        try{
            //CustomerId null
            assertThrows(NotAddedCustomerException.class, () -> {
                telcoService.findCallsMonth(null, 10, 2021);

            });
            assertThrows(NotAddedCustomerException.class, () ->{
                telcoService.findCallByInterval(null, LocalDateTime.of(20212, 01, 01, 00, 00).withSecond(0),
                        LocalDateTime.of(2021, 10, 01, 01, 00, 00).withSecond(0), null, 0, 2);

            });

            //Mes que no expiro
            assertThrows(MonthNotExpiredException.class, () -> {
                telcoService.findCallsMonth(c.getCustomerId(), LocalDateTime.now().getMonth().getValue(), LocalDateTime.now().getYear());

            });

            //Año invalid
            assertThrows(InputValidationException.class, () -> {
                telcoService.findCallsMonth(c.getCustomerId(), LocalDateTime.now().minusMonths(1).getMonth().getValue(), LocalDateTime.now().plusYears(1).getYear());

            });

            //mes que no existe
            assertThrows(InputValidationException.class, () -> {
                telcoService.findCallsMonth(c.getCustomerId(), 14, 2020);

            });

            //llamadas que no estan en pendiente
            assertThrows(InvalidStatusException.class, () -> {
                telcoService.updatePhoneCallStatus(c.getCustomerId(),PhoneCallStatus.BILLED,07,2021);
                telcoService.findCallsMonth(c.getCustomerId(),07,2021);
            });
        } finally {
            removeCalls();
            telcoService.deleteCustomer(c.getCustomerId());
        }
    }

    @Test
    public void testUpdatePhoneCallStatus() throws InputValidationException, InstanceNotFoundException,
            WrongPhoneCallStatusException, MonthNotExpiredException, CustomerWithPhoneCallsException ,
            DNIAlreadyRegisteredException, NotAddedCustomerException {

        Customer c = getValidCustomer();
        telcoService.addCustomer(c);
        Customer c2 =getAnotherValidCustomer();
        telcoService.addCustomer(c2);
        PhoneCall p = getValidPhoneCall(c,LocalDateTime.of(2021,03,14,15,00));
        PhoneCall p2 = getValidPhoneCall(c,LocalDateTime.of(2021,05,10,12,00));

        try{
            //Comprobar que actualiza Pendientes - Facturadas
            telcoService.addCall(p);
            assertEquals(PhoneCallStatus.PENDING,p.getPhoneCallStatus());
            telcoService.updatePhoneCallStatus(c.getCustomerId(),PhoneCallStatus.BILLED,3,2021);

            //Buscamos la llamada
            List<PhoneCall> updatedcall = telcoService.findCallByInterval(c.getCustomerId(),p.getStartDate().minusDays(1), p.getStartDate().plusDays(1),PhoneCallType.NATIONAL,0,1);
            assertEquals(PhoneCallStatus.BILLED,updatedcall.get(0).getPhoneCallStatus());


            //Comprobar que actualiza Facturadas - Pagadas del mes correpondiente
            telcoService.addCall(p2);
            assertEquals(PhoneCallStatus.PENDING,p2.getPhoneCallStatus());
            //Comprobamos estado BILLED
            assertEquals(PhoneCallStatus.BILLED, updatedcall.get(0).getPhoneCallStatus() );
            telcoService.updatePhoneCallStatus(c.getCustomerId(),PhoneCallStatus.PAID,3,2021);

            //Buscamos la llamada
            List<PhoneCall> updatedcall2 = telcoService.findCallByInterval(c.getCustomerId(),p.getStartDate().minusDays(1), p.getStartDate().plusDays(1),PhoneCallType.NATIONAL,0,1);
            assertEquals(PhoneCallStatus.PAID,updatedcall2.get(0).getPhoneCallStatus());

            //Comprobamos que el resto de llamadas conservan su estado
            assertEquals(PhoneCallStatus.PENDING,p2.getPhoneCallStatus());


            //Actualizar llamadas de un usuario que no tiene llamadas (no se obtiene resultado pero no produce error)
            telcoService.updatePhoneCallStatus(c2.getCustomerId(), PhoneCallStatus.BILLED,5,2021);

        } finally {
            removeCalls();
            telcoService.deleteCustomer(c.getCustomerId());
            telcoService.deleteCustomer(c2.getCustomerId());
        }
    }

@Test
    public void testInvalidUpdatePhoneCallStatus() throws InputValidationException, InstanceNotFoundException,
        CustomerWithPhoneCallsException, DNIAlreadyRegisteredException, NotAddedCustomerException {

        Customer c = getValidCustomer();
        Customer c2 = getAnotherValidCustomer();
        Customer c3 = getValidCustomer();

        telcoService.addCustomer(c);
        PhoneCall p = getValidPhoneCall(c,LocalDateTime.of(2021,05,10,12,00));
        telcoService.addCall(p);
        telcoService.addCustomer(c2);

    try{

            //Update de un Customer que no ha sido añadido (CustomerId==null)
            assertThrows(NotAddedCustomerException.class, () -> {
                telcoService.updatePhoneCallStatus(c3.getCustomerId(), PhoneCallStatus.BILLED,5,2021);
            });

            //Pasarle un estado que no es válido (únicamente pueden ser BILLED y PAID).
            assertThrows(WrongPhoneCallStatusException.class, () -> {
                telcoService.updatePhoneCallStatus(c.getCustomerId(), PhoneCallStatus.PENDING,5,2021);
            });

            // Mes no vencido
            assertThrows(MonthNotExpiredException.class, () -> {
                telcoService.updatePhoneCallStatus(c.getCustomerId(), PhoneCallStatus.BILLED, LocalDateTime.now().getMonth().getValue(),2022);
            });

            // Fecha posterior a la actual
            assertThrows(MonthNotExpiredException.class, () -> {
                telcoService.updatePhoneCallStatus(c.getCustomerId(), PhoneCallStatus.BILLED, LocalDateTime.now().plusMonths(1).getMonth().getValue(), LocalDateTime.now().plusYears(1).getYear());
            });

            // Check Input mes
            assertThrows(InputValidationException.class, () -> {
                telcoService.updatePhoneCallStatus(c.getCustomerId(), PhoneCallStatus.BILLED,17,2021);
                telcoService.updatePhoneCallStatus(c.getCustomerId(), PhoneCallStatus.BILLED,-1,2021);
            });

            //Customer que ha sido borrado
            assertThrows(InstanceNotFoundException.class, () -> {
                telcoService.deleteCustomer(c2.getCustomerId());
                telcoService.updatePhoneCallStatus(c2.getCustomerId(), PhoneCallStatus.BILLED,6,2021);
            });

            //Customer todavía no insertado
            assertThrows(NotAddedCustomerException.class, () -> {
                telcoService.updatePhoneCallStatus(null, PhoneCallStatus.BILLED,6,2021);
            });
        } finally {
            removeCalls();
            telcoService.deleteCustomer(c.getCustomerId());
        }
    }
}
