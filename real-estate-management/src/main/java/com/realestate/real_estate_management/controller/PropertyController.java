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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/properties")
public class PropertyController {

    @Autowired
    private PropertyService propertyService;

    // --- UPDATED search endpoint ---
    /**
     * API Endpoint for advanced search.
     * Example: GET /api/properties/search?city=Mumbai&amenity=Gym&amenity=Pool
     * @param city Optional search parameter for city.
     * @param propertyType Optional search parameter for property type.
     * @param minPrice Optional search parameter for minimum price.
     * @param maxPrice Optional search parameter for maximum price.
     * @param amenities Optional list of amenities (can be repeated).
     * @return A list of matching properties.
     */
    @GetMapping("/search")
    public List<Property> searchProperties(
            @RequestParam(name = "city", required = false) String city,
            @RequestParam(name = "type", required = false) String propertyType,
            @RequestParam(name = "minPrice", required = false) BigDecimal minPrice,
            @RequestParam(name = "maxPrice", required = false) BigDecimal maxPrice,
            @RequestParam(name = "amenity", required = false) List<String> amenities // <-- Add this line
        ) {
        
        // Pass all parameters to the service
        return propertyService.searchProperties(city, propertyType, minPrice, maxPrice, amenities);
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
        propertyService.deletePropertyById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Property> updateProperty(@PathVariable Long id, @RequestBody Property propertyDetails) {
        return propertyService.updateProperty(id, propertyDetails)
                .map(updatedProperty -> ResponseEntity.ok(updatedProperty))
                .orElse(ResponseEntity.notFound().build());
    }
}