package com.shopping.mall.service;

import com.shopping.mall.dto.AuthResponse;
import com.shopping.mall.dto.LoginRequest;
import com.shopping.mall.dto.RegisterRequest;
import com.shopping.mall.entity.Cart;
import com.shopping.mall.entity.Customer;
import com.shopping.mall.entity.Role;
import com.shopping.mall.repository.CartRepository;
import com.shopping.mall.repository.CustomerRepository;
import com.shopping.mall.repository.RoleRepository;
import com.shopping.mall.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashSet;
import java.util.Set;

@Service
public class AuthService {
    
    @Autowired
    private CustomerRepository customerRepository;
    
    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private CartRepository cartRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private JwtTokenProvider tokenProvider;
    
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (customerRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists!");
        }
        
        Customer customer = new Customer();
        customer.setEmail(request.getEmail());
        customer.setPassword(passwordEncoder.encode(request.getPassword()));
        customer.setFirstName(request.getFirstName());
        customer.setLastName(request.getLastName());
        customer.setPhoneNumber(request.getPhoneNumber());
        
        Role customerRole = roleRepository.findByName(Role.RoleName.ROLE_CUSTOMER)
                .orElseThrow(() -> new RuntimeException("Customer Role not found"));
        
        Set<Role> roles = new HashSet<>();
        roles.add(customerRole);
        customer.setRoles(roles);
        
        Customer savedCustomer = customerRepository.save(customer);
        
        // Create cart for the new customer
        Cart cart = new Cart();
        cart.setCustomer(savedCustomer);
        cartRepository.save(cart);
        
        // Authenticate and generate token
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = tokenProvider.generateToken(authentication);
        
        return new AuthResponse(token, savedCustomer.getId(), savedCustomer.getEmail(), 
                               savedCustomer.getFirstName(), savedCustomer.getLastName());
    }
    
    public AuthResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = tokenProvider.generateToken(authentication);
        
        Customer customer = customerRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        return new AuthResponse(token, customer.getId(), customer.getEmail(), 
                               customer.getFirstName(), customer.getLastName());
    }
}
