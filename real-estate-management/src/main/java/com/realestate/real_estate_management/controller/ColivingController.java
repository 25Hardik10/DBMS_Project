package com.realestate.real_estate_management.controller;

import com.realestate.real_estate_management.entity.Coliving;
import com.realestate.real_estate_management.service.ColivingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/colivings")
public class ColivingController {

    @Autowired
    private ColivingService ColivingService;

    @PostMapping
    public Coliving createColiving(@RequestBody Coliving coliving) {
        coliving.setPropertyType("Coliving");
        return ColivingService.saveColiving(coliving);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Coliving> updateColiving(@PathVariable Long id, @RequestBody Coliving ColivingDetails) {
        return ColivingService.updateColiving(id, ColivingDetails)
                .map(updatedColiving -> ResponseEntity.ok(updatedColiving)) // 200 OK
                .orElse(ResponseEntity.notFound().build()); // 404 Not Found
    }
}