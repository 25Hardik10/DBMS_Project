package com.realestate.real_estate_management.controller;

import com.realestate.real_estate_management.entity.Property;
import com.realestate.real_estate_management.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam; // <-- Add this import
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/properties")
public class PropertyController {

    @Autowired
    private PropertyService propertyService;

    // --- This is our new search endpoint ---
    /**
     * API Endpoint for advanced search.
     * Example: GET http://localhost:8080/api/properties/search?city=Mumbai&type=Flat
     * @param city Optional search parameter for city.
     * @param propertyType Optional search parameter for property type.
     * @return A list of matching properties.
     */
    @GetMapping("/search")
    public List<Property> searchProperties(
            @RequestParam(name = "city", required = false) String city,
            @RequestParam(name = "type", required = false) String propertyType) {
        
        return propertyService.searchProperties(city, propertyType);
    }
    // --------------------------------------

    @GetMapping("/all")
    public List<Property> getAllProperties() {
        return propertyService.getAllProperties();
    }

    @PostMapping
    public Property createProperty(@RequestBody Property property) {
        return propertyService.saveProperty(property);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Property> getPropertyById(@PathVariable Long id) {
        return propertyService.getPropertyById(id)
                .map(property -> ResponseEntity.ok(property))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePropertyById(@PathVariable Long id) {
        // Note: You must be authenticated to use this endpoint.
        propertyService.deletePropertyById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Property> updateProperty(@PathVariable Long id, @RequestBody Property propertyDetails) {
        // Note: You must be authenticated to use this endpoint.
        return propertyService.updateProperty(id, propertyDetails)
                .map(updatedProperty -> ResponseEntity.ok(updatedProperty))
                .orElse(ResponseEntity.notFound().build());
    }
}