package com.realestate.real_estate_management.controller;

import com.realestate.real_estate_management.entity.Land;
import com.realestate.real_estate_management.service.LandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/lands")
public class LandController {

    @Autowired
    private LandService landService;

    @PostMapping
    public Land createLand(@RequestBody Land land) {
        return landService.saveLand(land);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Land> updateLand(@PathVariable Long id, @RequestBody Land landDetails) {
        return landService.updateLand(id, landDetails)
                .map(updatedLand -> ResponseEntity.ok(updatedLand)) // 200 OK
                .orElse(ResponseEntity.notFound().build()); // 404 Not Found
    }
}