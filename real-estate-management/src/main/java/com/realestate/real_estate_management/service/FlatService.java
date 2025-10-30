package com.realestate.real_estate_management.service;

import com.realestate.real_estate_management.entity.Flat;
import com.realestate.real_estate_management.repository.FlatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class FlatService {

    @Autowired
    private FlatRepository flatRepository;

    public Flat saveFlat(Flat flat) {
        return flatRepository.save(flat);
    }
    
    public Optional<Flat> updateFlat(Long id, Flat flatDetails) {
        return flatRepository.findById(id)
            .map(existingFlat -> {
                existingFlat.setPrice(flatDetails.getPrice());
                existingFlat.setPropertyStatus(flatDetails.getPropertyStatus());
                existingFlat.setDescription(flatDetails.getDescription());
                existingFlat.setAddress(flatDetails.getAddress());

                existingFlat.setFloorNumber(flatDetails.getFloorNumber());
                existingFlat.setBhk(flatDetails.getBhk());
                existingFlat.setBathrooms(flatDetails.getBathrooms());
                existingFlat.setParking(flatDetails.getParking());
                existingFlat.setHasLift(flatDetails.getHasLift());
                existingFlat.setBuiltArea(flatDetails.getBuiltArea());
                existingFlat.setCarpetArea(flatDetails.getCarpetArea());

                return flatRepository.save(existingFlat);
            });
    }
}