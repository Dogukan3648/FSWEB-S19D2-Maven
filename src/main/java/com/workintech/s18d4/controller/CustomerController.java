
package com.workintech.s18d4.controller;

import com.workintech.s18d4.dto.CustomerResponse;
import com.workintech.s18d4.entity.Customer;
import com.workintech.s18d4.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping({"/customers", "/customer"})
public class CustomerController {

    private CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public List<CustomerResponse> getAllCustomers() {
        List<CustomerResponse> responses = new ArrayList<>();

        customerService.findAll().forEach(customer -> {
            responses.add(new CustomerResponse(
                    customer.getId(),
                    customer.getEmail(),
                    customer.getSalary()
            ));
        });

        return responses;
    }

    @GetMapping("/{id}")
    public CustomerResponse getCustomerById(@PathVariable long id) {
        Customer customer = customerService.find(id);

        if (customer == null) {
            return null;
        }

        return new CustomerResponse(
                customer.getId(),
                customer.getEmail(),
                customer.getSalary()
        );
    }

    @PostMapping
    public CustomerResponse addCustomer(@RequestBody Customer customer) {
        Customer savedCustomer = customerService.save(customer);

        return new CustomerResponse(
                savedCustomer.getId(),
                savedCustomer.getEmail(),
                savedCustomer.getSalary()
        );
    }

    @PutMapping("/{id}")
    public CustomerResponse updateCustomer(
            @PathVariable long id,
            @RequestBody Customer customer) {

        Customer uCustomer = customerService.find(id);

        if (uCustomer == null) {
            return null;
        }

        uCustomer.setFirstName(customer.getFirstName());
        uCustomer.setLastName(customer.getLastName());
        uCustomer.setAccounts(customer.getAccounts());
        uCustomer.setAddress(customer.getAddress());
        uCustomer.setEmail(customer.getEmail());
        uCustomer.setSalary(customer.getSalary());

        Customer updatedCustomer = customerService.save(uCustomer);

        return new CustomerResponse(
                updatedCustomer.getId(),
                updatedCustomer.getEmail(),
                updatedCustomer.getSalary()
        );
    }

    @DeleteMapping("/{id}")
    public CustomerResponse deleteCustomer(@PathVariable long id) {
        Customer deletedCustomer = customerService.delete(id);

        if (deletedCustomer == null) {
            return null;
        }

        return new CustomerResponse(
                deletedCustomer.getId(),
                deletedCustomer.getEmail(),
                deletedCustomer.getSalary()
        );
    }
}
