package com.realestate.real_estate_management.service;

import com.realestate.real_estate_management.entity.Land;
import com.realestate.real_estate_management.repository.LandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class LandService {

    @Autowired
    private LandRepository landRepository;

    public Land saveLand(Land land) {
        return landRepository.save(land);
    }

    public Optional<Land> updateLand(Long id, Land landDetails) {
        return landRepository.findById(id)
            .map(existingLand -> {
                existingLand.setPrice(landDetails.getPrice());
                existingLand.setPropertyStatus(landDetails.getPropertyStatus());
                existingLand.setDescription(landDetails.getDescription());
                existingLand.setAddress(landDetails.getAddress());
                existingLand.setLandArea(landDetails.getLandArea());
                existingLand.setHasFence(landDetails.getHasFence());
                existingLand.setHasRoad(landDetails.getHasRoad());
                existingLand.setZoningType(landDetails.getZoningType());

                return landRepository.save(existingLand);
            });
    }
}