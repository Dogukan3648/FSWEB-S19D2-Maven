package com.workintech.s18d4.service;

import com.workintech.s18d4.entity.Address;
import com.workintech.s18d4.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AddressServiceImpl implements AddressService{

   private AddressRepository addressRepository;

   @Autowired
   public AddressServiceImpl(AddressRepository addressRepository){
       this.addressRepository = addressRepository;
   }

    @Override
    public List<Address> findAll() {
        return addressRepository.findAll();
    }

    @Override
    public Address find(long id) {
        Optional<Address> addressOptional =  addressRepository.findById(id);
        if (addressOptional.isPresent()){
            return addressOptional.get();
        }
        return null;
    }

    @Override
    public Address save(Address address) {
        return addressRepository.save(address);
    }

    @Override
    public Address delete(long id) {
        Optional<Address> address = addressRepository.findById(id);

        if (address.isPresent()){
            addressRepository.delete(address.get());
            return address.get();
        }
        return null;

    }
}
