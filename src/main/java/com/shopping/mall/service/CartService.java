package com.shopping.mall.service;

import com.shopping.mall.entity.Cart;
import com.shopping.mall.entity.CartItem;
import com.shopping.mall.entity.Customer;
import com.shopping.mall.entity.Product;
import com.shopping.mall.repository.CartRepository;
import com.shopping.mall.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CartService {
    
    @Autowired
    private CartRepository cartRepository;
    
    @Autowired
    private CustomerRepository customerRepository;
    
    @Autowired
    private ProductService productService;
    
    public Cart getCartByCustomerId(Long customerId) {
        return cartRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new RuntimeException("Cart not found for customer: " + customerId));
    }
    
    @Transactional
    public Cart addItemToCart(Long customerId, Long productId, Integer quantity) {
        Cart cart = getCartByCustomerId(customerId);
        Product product = productService.getProductById(productId);
        
        // Check if product already exists in cart
        CartItem existingItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(null);
        
        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
        } else {
            CartItem newItem = new CartItem();
            newItem.setCart(cart);
            newItem.setProduct(product);
            newItem.setQuantity(quantity);
            newItem.setPrice(product.getPrice());
            cart.getItems().add(newItem);
        }
        
        return cartRepository.save(cart);
    }
    
    @Transactional
    public Cart updateCartItemQuantity(Long customerId, Long productId, Integer quantity) {
        Cart cart = getCartByCustomerId(customerId);
        
        CartItem cartItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Product not found in cart"));
        
        if (quantity <= 0) {
            cart.getItems().remove(cartItem);
        } else {
            cartItem.setQuantity(quantity);
        }
        
        return cartRepository.save(cart);
    }
    
    @Transactional
    public Cart removeItemFromCart(Long customerId, Long productId) {
        Cart cart = getCartByCustomerId(customerId);
        
        cart.getItems().removeIf(item -> item.getProduct().getId().equals(productId));
        
        return cartRepository.save(cart);
    }
    
    @Transactional
    public void clearCart(Long customerId) {
        Cart cart = getCartByCustomerId(customerId);
        cart.getItems().clear();
        cartRepository.save(cart);
    }
}
