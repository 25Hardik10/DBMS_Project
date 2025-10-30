package com.realestate.real_estate_management.service;

import com.realestate.real_estate_management.entity.Rental;
import com.realestate.real_estate_management.repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class RentalService {

    @Autowired
    private RentalRepository RentalRepository;

    public Rental saveRental(Rental Rental) {
        return RentalRepository.save(Rental);
    }
    
    public Optional<Rental> updateRental(Long id, Rental RentalDetails) {
        return RentalRepository.findById(id)
            .map(existingRental -> {
                existingRental.setPrice(RentalDetails.getPrice());
                existingRental.setPropertyStatus(RentalDetails.getPropertyStatus());
                existingRental.setDescription(RentalDetails.getDescription());
                existingRental.setAddress(RentalDetails.getAddress());

                existingRental.setRent(RentalDetails.getRent());
                existingRental.setLeaseTerm(RentalDetails.getLeaseTerm());
                existingRental.setFurnishingStatus(RentalDetails.getFurnishingStatus());
                existingRental.setPetAllowed(RentalDetails.getPetAllowed());
                existingRental.setDeposit(RentalDetails.getDeposit());
                existingRental.setAvailableFrom(RentalDetails.getAvailableFrom());
                existingRental.setTenantType(RentalDetails.getTenantType());

                return RentalRepository.save(existingRental);
            });
    }
}
