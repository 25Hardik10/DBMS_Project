package com.realestate.real_estate_management.config;

import com.github.javafaker.Faker;
import com.realestate.real_estate_management.entity.Flat;
import com.realestate.real_estate_management.entity.Land;
import com.realestate.real_estate_management.entity.Seller;
import com.realestate.real_estate_management.repository.UserRepository;
import com.realestate.real_estate_management.service.FlatService;
import com.realestate.real_estate_management.service.LandService;
import com.realestate.real_estate_management.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SellerService sellerService;

    @Autowired
    private FlatService flatService;

    @Autowired
    private LandService landService;

    @Override
    public void run(String... args) throws Exception {
        
        if (userRepository.count() > 0) {
            return; 
        }

        System.out.println("Starting to seed database...");

        Faker faker = new Faker(Locale.forLanguageTag("en-IN")); 
        Random rand = new Random();
        List<Seller> savedSellers = new ArrayList<>();

        // === 1. CREATE 10 SELLERS ===
        for (int i = 0; i < 10; i++) {
            Seller seller = new Seller();
            seller.setFirstName(faker.name().firstName());
            seller.setLastName(faker.name().lastName());
            seller.setEmail(faker.internet().emailAddress());
            seller.setMobile(faker.phoneNumber().subscriberNumber(10));
            seller.setPassword("password123"); 
            seller.setCreatedOn(LocalDate.now().minusDays(rand.nextInt(365)));
            seller.setSellerType("Individual");

            savedSellers.add(sellerService.saveSeller(seller));
        }

        // === 2. CREATE 25 FLATS ===
        for (int i = 0; i < 25; i++) {
            Flat flat = new Flat();
            
            flat.setListedDate(LocalDate.now().minusDays(rand.nextInt(30)));
            flat.setPrice(new BigDecimal(faker.number().numberBetween(2000000, 15000000)));
            flat.setPropertyStatus("Available");
            flat.setAddress(faker.address().streetAddress());
            flat.setCity(faker.address().city());
            flat.setState(faker.address().state());
            
            // --- This is the fixed line ---
            // Use digits(6) to guarantee a 6-digit PIN code
            flat.setPin(Integer.parseInt(faker.number().digits(6)));
            // -----------------------------

            flat.setDescription(faker.lorem().sentence(10));
            flat.setPropertyType("Flat");
            flat.setSeller(savedSellers.get(rand.nextInt(savedSellers.size())));

            flat.setFloorNumber(rand.nextInt(15) + 1);
            flat.setBhk(rand.nextInt(4) + 1);
            flat.setBathrooms(rand.nextInt(3) + 1);
            flat.setParking(rand.nextBoolean());
            flat.setHasLift(rand.nextBoolean());
            flat.setBuiltArea(faker.number().numberBetween(800, 3000));
            flat.setCarpetArea(flat.getBuiltArea() - rand.nextInt(200));

            flatService.saveFlat(flat);
        }

        // === 3. CREATE 25 LANDS ===
        for (int i = 0; i < 25; i++) {
            Land land = new Land();
            
            land.setListedDate(LocalDate.now().minusDays(rand.nextInt(30)));
            land.setPrice(new BigDecimal(faker.number().numberBetween(500000, 10000000)));
            land.setPropertyStatus("Available");
            land.setAddress(faker.address().streetAddress());
            land.setCity(faker.address().city());
            land.setState(faker.address().state());

            // --- This is the fixed line ---
            land.setPin(Integer.parseInt(faker.number().digits(6)));
            // -----------------------------

            land.setDescription(faker.lorem().sentence(10));
            land.setPropertyType("Land");
            land.setSeller(savedSellers.get(rand.nextInt(savedSellers.size())));

            land.setLandArea(faker.number().numberBetween(1000, 10000));
            land.setHasFence(rand.nextBoolean());
            land.setHasRoad(rand.nextBoolean());
            land.setZoningType(rand.nextBoolean() ? "Residential" : "Commercial");

            landService.saveLand(land);
        }
        
        System.out.println("...Database seeding complete.");
    }
}