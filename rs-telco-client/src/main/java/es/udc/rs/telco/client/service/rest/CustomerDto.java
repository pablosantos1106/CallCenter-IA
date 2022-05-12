package es.udc.rs.telco.client.service.rest;

public class CustomerDto {
        private String name;
        private String dni;
        private String address;
        private String phoneNumber;
        private Long customerId;

        public CustomerDto() {
        }

    public CustomerDto(String name, String dni, String address, String phoneNumber, Long customerId) {
        this.name = name;
        this.dni = dni;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.customerId = customerId;
    }

    public CustomerDto(String name, String dni, String address, String phoneNumber) {
        this.name = name;
        this.dni = dni;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public String getDni() {
        return dni;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    @Override
    public String toString() {
        return "CustomerDtoJaxb{" +
                "name='" + name + '\'' +
                ", dni='" + dni + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", customerId=" + customerId +
                '}';
    }
}

