package com.realestate.real_estate_management.service;

import com.realestate.real_estate_management.entity.Commercial;
import com.realestate.real_estate_management.repository.CommercialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class CommercialService {

    @Autowired
    private CommercialRepository commercialRepository;

    public Commercial saveCommercial(Commercial commercial) {
        return commercialRepository.save(commercial);
    }

    public Optional<Commercial> updateCommercial(Long id, Commercial commercialDetails) {
        return commercialRepository.findById(id)
            .map(existingCommercial -> {
                existingCommercial.setPrice(commercialDetails.getPrice());
                existingCommercial.setPropertyStatus(commercialDetails.getPropertyStatus());
                existingCommercial.setDescription(commercialDetails.getDescription());
                existingCommercial.setAddress(commercialDetails.getAddress());
                
                existingCommercial.setBuiltArea(commercialDetails.getBuiltArea());
                existingCommercial.setCarpetArea(commercialDetails.getCarpetArea());
                existingCommercial.setFloorNumber(commercialDetails.getFloorNumber());
                existingCommercial.setParkingSpace(commercialDetails.getParkingSpace());
                existingCommercial.setWashrooms(commercialDetails.getWashrooms());

                return commercialRepository.save(existingCommercial);
            });
    }
}