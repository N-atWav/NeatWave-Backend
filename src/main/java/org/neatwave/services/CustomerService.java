package org.neatwave.services;

import lombok.SneakyThrows;
import org.neatwave.dto.CustomerDTO;
import org.neatwave.models.Customer;
import org.neatwave.models.Role;
import org.neatwave.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<CustomerDTO> getCustomerById(Long customerId) {
        return customerRepository.findById(customerId)
                .map(this::convertToDTO);
    }

    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        Customer customer = convertToEntity(customerDTO);
        Customer savedCustomer = customerRepository.save(customer);
        return convertToDTO(savedCustomer);
    }

    public CustomerDTO updateCustomer(Long customerId, CustomerDTO customerDetails) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        customer.setFirstName(customerDetails.getFirstName());
        customer.setLastName(customerDetails.getLastName());
        customer.setPhone(customerDetails.getPhone());
        customer.setEmail(customerDetails.getEmail());
        customer.setAddress(customerDetails.getAddress());
        customer.setRegistrationDate(customerDetails.getRegistrationDate());
        Customer updatedCustomer = customerRepository.save(customer);
        return convertToDTO(updatedCustomer);
    }

    public void deleteCustomer(Long customerId) {
        customerRepository.deleteById(customerId);
    }

    private CustomerDTO convertToDTO(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setCustomerId(customer.getCustomerId());
        customerDTO.setFirstName(customer.getFirstName());
        customerDTO.setLastName(customer.getLastName());
        customerDTO.setPhone(customer.getPhone());
        customerDTO.setEmail(customer.getEmail());
        customerDTO.setAddress(customer.getAddress());
        customerDTO.setRegistrationDate(customer.getRegistrationDate());
        return customerDTO;
    }

    private Customer convertToEntity(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        customer.setCustomerId(customerDTO.getCustomerId());
        customer.setFirstName(customerDTO.getFirstName());
        customer.setLastName(customerDTO.getLastName());
        customer.setPhone(customerDTO.getPhone());
        customer.setEmail(customerDTO.getEmail());
        customer.setAddress(customerDTO.getAddress());
        customer.setRegistrationDate(customerDTO.getRegistrationDate());
        return customer;
    }

    public Customer getByUsername(String username) throws RuntimeException {
        return customerRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

    }

    public Customer create(Customer Customer) {
        if (customerRepository.existsByUsername(Customer.getUsername())) {
            // Заменить на свои исключения
            throw new RuntimeException("Пользователь с таким именем уже существует");
        }

        if (customerRepository.existsByEmail(Customer.getEmail())) {
            throw new RuntimeException("Пользователь с таким email уже существует");
        }

        return customerRepository.save(Customer);
    }

    @SneakyThrows
    public UserDetailsService userDetailsService() {
        return this::getByUsername;
    }

    public Customer getCurrentUser() throws Exception {
        // Получение имени пользователя из контекста Spring Security
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        return getByUsername(username);
    }

    @Deprecated
    public void getAdmin() throws Exception {
        var user = getCurrentUser();
        user.setRole(Role.ROLE_ADMIN);
        customerRepository.save(user);
    }
}
