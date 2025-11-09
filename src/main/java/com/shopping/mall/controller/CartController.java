package com.shopping.mall.controller;

import com.shopping.mall.entity.Cart;
import com.shopping.mall.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = "*")
public class CartController {
    
    @Autowired
    private CartService cartService;
    
    @GetMapping("/{customerId}")
    public ResponseEntity<Cart> getCart(@PathVariable Long customerId) {
        return ResponseEntity.ok(cartService.getCartByCustomerId(customerId));
    }
    
    @PostMapping("/{customerId}/items")
    public ResponseEntity<Cart> addItemToCart(@PathVariable Long customerId, 
                                              @RequestParam Long productId, 
                                              @RequestParam Integer quantity) {
        return ResponseEntity.ok(cartService.addItemToCart(customerId, productId, quantity));
    }
    
    @PutMapping("/{customerId}/items/{productId}")
    public ResponseEntity<Cart> updateCartItem(@PathVariable Long customerId, 
                                               @PathVariable Long productId, 
                                               @RequestParam Integer quantity) {
        return ResponseEntity.ok(cartService.updateCartItemQuantity(customerId, productId, quantity));
    }
    
    @DeleteMapping("/{customerId}/items/{productId}")
    public ResponseEntity<Cart> removeItemFromCart(@PathVariable Long customerId, 
                                                   @PathVariable Long productId) {
        return ResponseEntity.ok(cartService.removeItemFromCart(customerId, productId));
    }
    
    @DeleteMapping("/{customerId}")
    public ResponseEntity<Void> clearCart(@PathVariable Long customerId) {
        cartService.clearCart(customerId);
        return ResponseEntity.ok().build();
    }
}
