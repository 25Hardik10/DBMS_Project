package com.realestate.real_estate_management.controller;

import com.realestate.real_estate_management.entity.Flat;
import com.realestate.real_estate_management.service.FlatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/flats")
public class FlatController {

    @Autowired
    private FlatService flatService;

    @PostMapping
    public Flat createFlat(@RequestBody Flat flat) {
        return flatService.saveFlat(flat);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Flat> updateFlat(@PathVariable Long id, @RequestBody Flat flatDetails) {
        return flatService.updateFlat(id, flatDetails)
                .map(updatedFlat -> ResponseEntity.ok(updatedFlat)) // 200 OK
                .orElse(ResponseEntity.notFound().build()); // 404 Not Found
    }
}