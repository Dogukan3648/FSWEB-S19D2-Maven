package com.workintech.s18d4.controller;

import com.workintech.s18d4.entity.Address;
import com.workintech.s18d4.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/address")
public class AddressController {
    private AddressService addressService;

    @Autowired
    public AddressController(AddressService addressService){
        this.addressService = addressService;
    }

    @GetMapping
    public List<Address> getAllAddresses(){
        return addressService.findAll();
    }
    @GetMapping("/{id}")
    public Address getAddressById(@PathVariable long id){
        return addressService.find(id);
    }
    @PostMapping
    public Address addAddress(@RequestBody Address address){
        return addressService.save(address);
    }
    @PutMapping("/{id}")
    public Address updateAddress(@PathVariable long id, @RequestBody Address address){
        Address existingAddress = addressService.find(id);

        if (existingAddress == null){
            return null;
        }
        existingAddress.setStreet(address.getStreet());
        existingAddress.setNo(address.getNo());
        existingAddress.setCity(address.getCity());
        existingAddress.setCountry(address.getCountry());
        existingAddress.setDescription(address.getDescription());

        return addressService.save(existingAddress);

    }
    @DeleteMapping("/{id}")
    public Address deleteAddress(@PathVariable long id){
        return addressService.delete(id);
    }

}
