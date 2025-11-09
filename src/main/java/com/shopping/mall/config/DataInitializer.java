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
    
    @Override
    public void run(String... args) throws Exception {
        initializeRoles();
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
