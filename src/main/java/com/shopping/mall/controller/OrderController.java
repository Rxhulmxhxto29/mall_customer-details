package com.shopping.mall.controller;

import com.shopping.mall.entity.Order;
import com.shopping.mall.entity.Payment;
import com.shopping.mall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*")
public class OrderController {
    
    @Autowired
    private OrderService orderService;
    
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Order>> getCustomerOrders(@PathVariable Long customerId) {
        return ResponseEntity.ok(orderService.getOrdersByCustomerId(customerId));
    }
    
    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.getOrderById(orderId));
    }
    
    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestParam Long customerId,
                                            @RequestParam Long shippingAddressId,
                                            @RequestParam Long billingAddressId,
                                            @RequestParam Payment.PaymentMethod paymentMethod) {
        return ResponseEntity.ok(orderService.createOrder(customerId, shippingAddressId, 
                                                         billingAddressId, paymentMethod));
    }
    
    @PutMapping("/{orderId}/status")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable Long orderId, 
                                                   @RequestParam Order.OrderStatus status) {
        return ResponseEntity.ok(orderService.updateOrderStatus(orderId, status));
    }
    
    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> cancelOrder(@PathVariable Long orderId) {
        orderService.cancelOrder(orderId);
        return ResponseEntity.ok().build();
    }
}
