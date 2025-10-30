// package com.realestate.real_estate_management.config;

// import com.github.javafaker.Faker;
// import com.realestate.real_estate_management.entity.*;
// import com.realestate.real_estate_management.repository.UserRepository;
// import com.realestate.real_estate_management.service.*;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.CommandLineRunner;
// import org.springframework.stereotype.Component;

// import java.math.BigDecimal;
// import java.time.LocalDate;
// import java.util.*;
// import java.util.stream.Collectors;
// import java.security.Principal;

// @Component
// public class DataSeeder implements CommandLineRunner {

//     // --- Repositories/Services for Data Integrity ---
//     @Autowired private UserRepository userRepository;
//     @Autowired private SellerService sellerService;
//     @Autowired private BuyerService buyerService;
//     @Autowired private TenantService tenantService;
//     @Autowired private AdminService adminService;
//     @Autowired private FlatService flatService;
//     @Autowired private LandService landService;
//     @Autowired private CommercialService commercialService;
//     @Autowired private ColivingService colivingService;
//     @Autowired private RentalService rentalService;
//     @Autowired private ImageService imageService;
//     @Autowired private ReviewService reviewService;
//     @Autowired private LeaseService leaseService;
//     @Autowired private BoughtService boughtService;
//     // --- End Dependencies ---


//     private final Faker faker = new Faker(Locale.forLanguageTag("en-IN"));
//     private final Random rand = new Random();

//     @Override
//     public void run(String... args) throws Exception {
//         if (userRepository.count() > 0) {
//             System.out.println("...Database not empty. Skipping Seeding.");
//             return; 
//         }
//         System.out.println("Starting comprehensive database seed (India Data)...");

//         // --- Collections to Hold Saved Entities for Foreign Keys (FKs) ---
//         List<Seller> savedSellers = new ArrayList<>();
//         List<Buyer> savedBuyers = new ArrayList<>();
//         List<Tenant> savedTenants = new ArrayList<>();
//         List<Property> savedProperties = new ArrayList<>();
//         List<User> allUsers = new ArrayList<>();


//         // --- 1. USER INHERITANCE ---
//         // ... (Seller, Buyer, Tenant, Admin creation logic remains the same) ...
//         // [To save space, this part is assumed correct from your paste]

//         // 1.1 Create 10 SELLERS
//         for (int i = 0; i < 10; i++) {
//             Seller seller = new Seller();
//             seller.setFirstName(faker.name().firstName());
//             seller.setLastName(faker.name().lastName());
//             seller.setEmail(faker.name().username() + i + "@seller.in"); // Ensure unique emails
//             seller.setMobile(faker.number().digits(10));
//             seller.setPassword("password123"); 
//             seller.setCreatedOn(LocalDate.now().minusDays(rand.nextInt(365)));
//             seller.setSellerType("Individual");
//             savedSellers.add(sellerService.saveSeller(seller));
//         }

//         // 1.2 Create 10 BUYERS
//         for (int i = 0; i < 10; i++) {
//             Buyer buyer = new Buyer();
//             buyer.setFirstName(faker.name().firstName());
//             buyer.setLastName(faker.name().lastName());
//             buyer.setEmail(faker.name().username() + i + "@buyer.in");
//             buyer.setMobile(faker.number().digits(10));
//             buyer.setPassword("password123"); 
//             buyer.setCreatedOn(LocalDate.now().minusDays(rand.nextInt(365)));
//             buyer.setBudget(new BigDecimal(faker.number().numberBetween(1000000, 30000000)));
//             buyer.setPreferredLocation(faker.address().city());
//             savedBuyers.add(buyerService.saveBuyer(buyer));
//         }

//         // 1.3 Create 10 TENANTS
//         for (int i = 0; i < 10; i++) {
//             Tenant tenant = new Tenant();
//             tenant.setFirstName(faker.name().firstName());
//             tenant.setLastName(faker.name().lastName());
//             tenant.setEmail(faker.name().username() + i + "@tenant.in");
//             tenant.setMobile(faker.number().digits(10));
//             tenant.setPassword("password123"); 
//             tenant.setCreatedOn(LocalDate.now().minusDays(rand.nextInt(365)));
//             tenant.setBudget(new BigDecimal(faker.number().numberBetween(5000, 50000)));
//             tenant.setLeaseTerm(11);
//             tenant.setEmploymentStatus(rand.nextBoolean());
//             savedTenants.add(tenantService.saveTenant(tenant));
//         }

//         // 1.4 Create 2 ADMINS
//         for (int i = 0; i < 2; i++) {
//             Admin admin = new Admin();
//             admin.setFirstName("Super");
//             admin.setLastName("Admin" + i);
//             admin.setEmail("admin" + i + "@hub.in");
//             admin.setMobile(faker.number().digits(10));
//             admin.setPassword("admin123"); 
//             admin.setCreatedOn(LocalDate.now());
//             admin.setAdminPrivileges("FULL");
//             adminService.saveAdmin(admin);
//         }

//         allUsers.addAll(savedSellers);
//         allUsers.addAll(savedBuyers);
//         allUsers.addAll(savedTenants);


//         // --- 2. PROPERTY INHERITANCE (100 Properties) ---

//         int totalProperties = 100;
//         List<Class<? extends Property>> types = Arrays.asList(Flat.class, Land.class, Commercial.class, Coliving.class, Rental.class);
        
//         for (int i = 0; i < totalProperties; i++) {
//             Class<? extends Property> typeClass = types.get(rand.nextInt(types.size()));
//             Property prop = null;
//             String propTypeString = "";

//             if (typeClass == Flat.class) { prop = new Flat(); propTypeString = "Flat"; }
//             else if (typeClass == Land.class) { prop = new Land(); propTypeString = "Land"; }
//             else if (typeClass == Commercial.class) { prop = new Commercial(); propTypeString = "Commercial"; }
//             else if (typeClass == Coliving.class) { prop = new Coliving(); propTypeString = "Coliving"; }
//             else if (typeClass == Rental.class) { prop = new Rental(); propTypeString = "Rental"; }
            
//             if (prop == null) continue;

//             // --- Property Base Fields ---
//             prop.setListedDate(LocalDate.now().minusDays(rand.nextInt(30)));
//             prop.setPropertyStatus(rand.nextBoolean() ? "Available" : "Sold");
//             prop.setAddress(faker.address().streetAddress());
//             prop.setCity(faker.address().city());
//             prop.setState(faker.address().state());
//             prop.setPin(Integer.parseInt(faker.number().digits(6)));
//             prop.setDescription(faker.lorem().sentence(15));
//             prop.setSeller(savedSellers.get(rand.nextInt(savedSellers.size()))); 
//             prop.setPropertyType(propTypeString); 
            
//             Property savedProp = null; // Variable to hold the result of the save operation

//             // --- Specific Type Data & Service Call (with FIX) ---
//             if (prop instanceof Flat flat) {
//                 flat.setPrice(new BigDecimal(faker.number().numberBetween(2000000, 15000000)));
//                 flat.setBhk(rand.nextInt(4) + 1);
//                 flat.setBuiltArea(faker.number().numberBetween(800, 3000));
//                 flat.setCarpetArea(flat.getBuiltArea() - rand.nextInt(200));
//                 // FIX: Overwrite the 'savedProp' reference with the saved entity
//                 savedProp = flatService.saveFlat(flat,principal); 

//             } else if (prop instanceof Land land) {
//                 land.setPrice(new BigDecimal(faker.number().numberBetween(500000, 10000000)));
//                 land.setLandArea(faker.number().numberBetween(1000, 10000));
//                 savedProp = landService.saveLand(land);

//             } else if (prop instanceof Commercial commercial) {
//                 commercial.setPrice(new BigDecimal(faker.number().numberBetween(5000000, 50000000)));
//                 commercial.setCommercialType("Office");
//                 commercial.setBuiltArea(faker.number().numberBetween(1000, 5000));
//                 commercial.setCarpetArea(commercial.getBuiltArea() - rand.nextInt(500));
//                 savedProp = commercialService.saveCommercial(commercial);

//             } else if (prop instanceof Coliving coliving) {
//                 coliving.setPrice(new BigDecimal(faker.number().numberBetween(5000, 20000)));
//                 coliving.setRentPerPerson(faker.number().numberBetween(4000, 15000));
//                 coliving.setTotalRooms(rand.nextInt(50) + 10);
//                 savedProp = colivingService.saveColiving(coliving);

//             } else if (prop instanceof Rental rental) {
//                 rental.setPrice(new BigDecimal(faker.number().numberBetween(10000, 70000)));
//                 rental.setRent(rental.getPrice());
//                 rental.setLeaseTerm(11);
//                 rental.setDeposit(rental.getPrice().multiply(new BigDecimal(rand.nextInt(5) + 1)));
//                 savedProp = rentalService.saveRental(rental);
//             }
            
//             // CRITICAL FIX: Add the SAVED entity (with ID) to the list
//             if (savedProp != null) {
//                 savedProperties.add(savedProp);
//             }
//         }
        
//         // --- 3. JUNCTION TABLES / RELATIONAL DATA (Reviews, Images, Transactions) ---
//         // ... (This logic remains the same, relying on savedProperties list) ...
        
//         List<Property> availableProperties = savedProperties.stream()
//             .filter(p -> p.getPropertyStatus() != null && p.getPropertyStatus().equals("Available"))
//             .collect(Collectors.toList());
        
//         // 3.1 Create 20 IMAGES
//         for (int i = 0; i < 20; i++) {
//             Image image = new Image();
//             image.setUrl("https://picsum.photos/id/" + faker.number().numberBetween(1, 100) + "/800/600");
//             image.setCaption(faker.lorem().sentence(5));
//             image.setProperty(savedProperties.get(rand.nextInt(savedProperties.size())));
//             imageService.saveImage(image.getProperty().getPropertyId(), image); 
//         }
        
//         // 3.2 Create 10 REVIEWS
//         for (int i = 0; i < 10; i++) {
//             Review review = new Review();
//             review.setRating(new BigDecimal(rand.nextInt(5) + 1));
//             review.setComments(faker.lorem().sentence(8));
            
//             Property propToReview = availableProperties.get(rand.nextInt(availableProperties.size()));
//             User userPosting = allUsers.get(rand.nextInt(allUsers.size()));

//             try {
//                 reviewService.createReview(propToReview.getPropertyId(), userPosting.getEmail(), review);
//             } catch (Exception e) {
//                  // Ignore exceptions
//             }
//         }
        
//         // 3.3 Create 5 LEASE Transactions
//         List<Property> rentalProperties = savedProperties.stream()
//                 .filter(p -> p.getPropertyType() != null && p.getPropertyType().equals("Rental"))
//                 .collect(Collectors.toList());

//         for (int i = 0; i < Math.min(5, rentalProperties.size()); i++) {
//             Lease lease = new Lease();
//             lease.setAmount(rentalProperties.get(i).getPrice());
//             lease.setEndDate(LocalDate.now().plusMonths(11));
//             lease.setModeOfPayment("Online");
            
//             leaseService.createLease(
//                 rentalProperties.get(i).getPropertyId(),
//                 savedTenants.get(i % savedTenants.size()).getEmail(),
//                 lease
//             );
//         }

//         // 3.4 Create 5 BOUGHT Transactions
//         List<Property> soldProperties = savedProperties.stream()
//                 .filter(p -> p.getPropertyStatus().equals("Sold"))
//                 .collect(Collectors.toList());

//         for (int i = 0; i < Math.min(5, soldProperties.size()); i++) {
//             Bought bought = new Bought();
//             bought.setAmount(soldProperties.get(i).getPrice());
//             bought.setModeOfPayment("Bank Transfer");

//             boughtService.createBought(
//                 soldProperties.get(i).getPropertyId(),
//                 savedBuyers.get(i % savedBuyers.size()).getEmail(),
//                 bought
//             );
//         }


//         System.out.println("...Database seed complete. Total users: " + userRepository.count() + ". Total properties: " + savedProperties.size());
//     }
// }