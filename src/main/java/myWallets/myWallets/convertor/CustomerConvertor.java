package myWallets.myWallets.convertor;

import myWallets.myWallets.DTO.CustomerAccountRecieveDTO;
import myWallets.myWallets.DTO.CustomerDTO;
import myWallets.myWallets.entity.Customer;
import myWallets.myWallets.repository.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerConvertor {

    @Autowired
    CustomerRepo customerRepo;

    public static Customer customerDtoToCustomer(CustomerDTO customerDTO){
        return Customer.builder()
                .customerName(customerDTO.getCustomerName())
                .email(customerDTO.getEmail())
                .countryCode(customerDTO.getCountryCode())
                .address(customerDTO.getAddress())
                .dateOfBirth(customerDTO.getDateOfBirth())
                .gender(customerDTO.getGender())
                .mobileNumber(customerDTO.getMobileNumber())
                .createdDate(ZonedDateTime.now())
                .password(customerDTO.getPassword())
//                .otp(String.valueOf(Validator.otp()))
                .isActive(true)
                .build();
    }

    public static CustomerAccountRecieveDTO customerToCustomerDTO(Customer customer){
        return CustomerAccountRecieveDTO.builder()
                .id(customer.getId())
                .customerName(customer.getCustomerName())
                .dateOfBirth(customer.getDateOfBirth())
                .email(customer.getEmail())
                .mobileNumber(customer.getMobileNumber())
                .build();
    }

    public static Customer updateCustomerAccount( CustomerAccountRecieveDTO customerAccountRecieveDTO){
        return Customer.builder()
                .customerName(customerAccountRecieveDTO.getCustomerName())
                .mobileNumber(customerAccountRecieveDTO.getMobileNumber())
                .email(customerAccountRecieveDTO.getEmail())
                .dateOfBirth(customerAccountRecieveDTO.getDateOfBirth())
                .build();
    }

    public static List<CustomerAccountRecieveDTO> mappedToCustomerAccountRecieve(Object[] customerAllDetails) {
        List<CustomerAccountRecieveDTO> customerAccountRecieveDTOS = new ArrayList<>();
        for (Object customerDetails : customerAllDetails) {
            CustomerAccountRecieveDTO customerAccountRecieveDTO = new CustomerAccountRecieveDTO();
//            customerAccountRecieveDTO.setCustomerName((Long)customerDetails[0]);
        }
        return customerAccountRecieveDTOS;
    }
}
