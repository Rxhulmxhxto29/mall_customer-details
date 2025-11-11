package com.shopping.mall.config;

import com.shopping.mall.entity.*;
import com.shopping.mall.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {
    
    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private CustomerRepository customerRepository;
    
    @Autowired
    private CategoryRepository categoryRepository;
    
    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private AddressRepository addressRepository;
    
    @Autowired
    private CartRepository cartRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private com.shopping.mall.repository.ShopRepository shopRepository;
    
    @Override
    public void run(String... args) throws Exception {
        initializeRoles();
        initializeShops();
        initializeCategories();
        initializeProducts();
        initializeTestUser();
    }
    
    private void initializeRoles() {
        if (roleRepository.count() == 0) {
            Role customerRole = new Role();
            customerRole.setName(Role.RoleName.ROLE_CUSTOMER);
            roleRepository.save(customerRole);
            
            Role adminRole = new Role();
            adminRole.setName(Role.RoleName.ROLE_ADMIN);
            roleRepository.save(adminRole);
            
            System.out.println("Roles initialized");
        }
    }
    
    private void initializeShops() {
        if (shopRepository.count() == 0) {
            com.shopping.mall.entity.Shop shop1 = new com.shopping.mall.entity.Shop();
            shop1.setShopName("Main Street Mall");
            shop1.setDescription("Premier shopping destination in downtown");
            shop1.setAddress("123 Main Street, New York, NY 10001");
            shop1.setPhoneNumber("555-0100");
            shop1.setEmail("info@mainstreetmall.com");
            shop1.setStatus(com.shopping.mall.entity.Shop.ShopStatus.ACTIVE);
            shopRepository.save(shop1);
            
            com.shopping.mall.entity.Shop shop2 = new com.shopping.mall.entity.Shop();
            shop2.setShopName("City Center Plaza");
            shop2.setDescription("Modern shopping complex with 100+ stores");
            shop2.setAddress("456 Center Ave, New York, NY 10002");
            shop2.setPhoneNumber("555-0200");
            shop2.setEmail("contact@citycenterplaza.com");
            shop2.setStatus(com.shopping.mall.entity.Shop.ShopStatus.ACTIVE);
            shopRepository.save(shop2);
            
            com.shopping.mall.entity.Shop shop3 = new com.shopping.mall.entity.Shop();
            shop3.setShopName("Riverside Shopping Center");
            shop3.setDescription("Family-friendly mall with entertainment");
            shop3.setAddress("789 River Road, New York, NY 10003");
            shop3.setPhoneNumber("555-0300");
            shop3.setEmail("hello@riversidesc.com");
            shop3.setStatus(com.shopping.mall.entity.Shop.ShopStatus.ACTIVE);
            shopRepository.save(shop3);
            
            System.out.println("Shops initialized");
        }
    }
    
    private void initializeCategories() {
        if (categoryRepository.count() == 0) {
            String[] categories = {"Electronics", "Clothing", "Books", "Home & Kitchen", "Sports", "Toys"};
            
            for (String catName : categories) {
                Category category = new Category();
                category.setName(catName);
                category.setDescription(catName + " products");
                categoryRepository.save(category);
            }
            
            System.out.println("Categories initialized");
        }
    }
    
    private void initializeProducts() {
        if (productRepository.count() == 0) {
            Category electronics = categoryRepository.findByName("Electronics").orElse(null);
            Category clothing = categoryRepository.findByName("Clothing").orElse(null);
            
            if (electronics != null) {
                createProduct("Smartphone", "Latest smartphone with advanced features", new BigDecimal("699.99"), 50, electronics);
                createProduct("Laptop", "High-performance laptop", new BigDecimal("1299.99"), 30, electronics);
                createProduct("Headphones", "Wireless noise-cancelling headphones", new BigDecimal("199.99"), 100, electronics);
            }
            
            if (clothing != null) {
                createProduct("T-Shirt", "Cotton comfortable t-shirt", new BigDecimal("29.99"), 200, clothing);
                createProduct("Jeans", "Classic blue jeans", new BigDecimal("59.99"), 150, clothing);
                createProduct("Jacket", "Winter jacket", new BigDecimal("89.99"), 80, clothing);
            }
            
            System.out.println("Products initialized");
        }
    }
    
    private void createProduct(String name, String description, BigDecimal price, int stock, Category category) {
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setStockQuantity(stock);
        product.setCategory(category);
        product.setStatus(Product.ProductStatus.AVAILABLE);
        product.setBrand("Generic Brand");
        productRepository.save(product);
    }
    
    private void initializeTestUser() {
        if (!customerRepository.existsByEmail("test@example.com")) {
            Customer customer = new Customer();
            customer.setEmail("test@example.com");
            customer.setPassword(passwordEncoder.encode("password"));
            customer.setFirstName("Test");
            customer.setLastName("User");
            customer.setPhoneNumber("1234567890");
            customer.setStatus(Customer.CustomerStatus.ACTIVE);
            
            // Associate customer with a shop
            com.shopping.mall.entity.Shop defaultShop = shopRepository.findByShopName("Main Street Mall").orElse(null);
            if (defaultShop != null) {
                customer.setShop(defaultShop);
            }
            
            Role customerRole = roleRepository.findByName(Role.RoleName.ROLE_CUSTOMER).orElse(null);
            if (customerRole != null) {
                Set<Role> roles = new HashSet<>();
                roles.add(customerRole);
                customer.setRoles(roles);
            }
            
            Customer savedCustomer = customerRepository.save(customer);
            
            // Create address
            Address address = new Address();
            address.setStreet("123 Main St");
            address.setCity("New York");
            address.setState("NY");
            address.setZipCode("10001");
            address.setCountry("USA");
            address.setType(Address.AddressType.BOTH);
            address.setDefault(true);
            address.setCustomer(savedCustomer);
            addressRepository.save(address);
            
            // Create cart
            Cart cart = new Cart();
            cart.setCustomer(savedCustomer);
            cartRepository.save(cart);
            
            System.out.println("Test user created - Email: test@example.com, Password: password");
        }
    }
}
