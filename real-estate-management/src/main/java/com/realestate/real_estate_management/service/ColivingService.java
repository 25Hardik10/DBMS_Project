package com.realestate.real_estate_management.service;

import com.realestate.real_estate_management.entity.Coliving;
import com.realestate.real_estate_management.repository.ColivingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class ColivingService {

    @Autowired
    private ColivingRepository ColivingRepository;
    
    public Coliving saveColiving(Coliving Coliving) {
        return ColivingRepository.save(Coliving);
    }
    
    public Optional<Coliving> updateColiving(Long id, Coliving ColivingDetails) {
        return ColivingRepository.findById(id)
            .map(existingColiving -> {
                existingColiving.setPrice(ColivingDetails.getPrice());
                existingColiving.setPropertyStatus(ColivingDetails.getPropertyStatus());
                existingColiving.setDescription(ColivingDetails.getDescription());
                existingColiving.setAddress(ColivingDetails.getAddress());

                existingColiving.setRoomType(ColivingDetails.getRoomType());
                existingColiving.setGenderPreference(ColivingDetails.getGenderPreference());
                existingColiving.setRentPerPerson(ColivingDetails.getRentPerPerson());
                existingColiving.setTotalRooms(ColivingDetails.getTotalRooms());
                existingColiving.setAvailableRooms(ColivingDetails.getAvailableRooms());
                existingColiving.setCleaning(ColivingDetails.getCleaning());

                return ColivingRepository.save(existingColiving);
            });
    }
}
