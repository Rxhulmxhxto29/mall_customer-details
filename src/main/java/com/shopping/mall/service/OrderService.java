package com.shopping.mall.service;

import com.shopping.mall.entity.*;
import com.shopping.mall.repository.AddressRepository;
import com.shopping.mall.repository.OrderRepository;
import com.shopping.mall.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class OrderService {
    
    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private CartService cartService;
    
    @Autowired
    private AddressRepository addressRepository;
    
    @Autowired
    private PaymentRepository paymentRepository;
    
    public List<Order> getOrdersByCustomerId(Long customerId) {
        return orderRepository.findByCustomerId(customerId);
    }
    
    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));
    }
    
    @Transactional
    public Order createOrder(Long customerId, Long shippingAddressId, Long billingAddressId, 
                           Payment.PaymentMethod paymentMethod) {
        Cart cart = cartService.getCartByCustomerId(customerId);
        
        if (cart.getItems().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }
        
        Address shippingAddress = addressRepository.findById(shippingAddressId)
                .orElseThrow(() -> new RuntimeException("Shipping address not found"));
        
        Address billingAddress = addressRepository.findById(billingAddressId)
                .orElseThrow(() -> new RuntimeException("Billing address not found"));
        
        Order order = new Order();
        order.setCustomer(cart.getCustomer());
        order.setShippingAddress(shippingAddress);
        order.setBillingAddress(billingAddress);
        order.setStatus(Order.OrderStatus.PENDING);
        
        Set<OrderItem> orderItems = new HashSet<>();
        BigDecimal totalAmount = BigDecimal.ZERO;
        
        for (CartItem cartItem : cart.getItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getPrice());
            orderItems.add(orderItem);
            
            totalAmount = totalAmount.add(orderItem.getSubtotal());
        }
        
        order.setItems(orderItems);
        order.setTotalAmount(totalAmount);
        
        Order savedOrder = orderRepository.save(order);
        
        // Create payment
        Payment payment = new Payment();
        payment.setOrder(savedOrder);
        payment.setAmount(totalAmount);
        payment.setPaymentMethod(paymentMethod);
        payment.setStatus(Payment.PaymentStatus.PENDING);
        payment.setTransactionId("TXN-" + System.currentTimeMillis());
        paymentRepository.save(payment);
        
        // Clear cart after order
        cartService.clearCart(customerId);
        
        return savedOrder;
    }
    
    @Transactional
    public Order updateOrderStatus(Long orderId, Order.OrderStatus status) {
        Order order = getOrderById(orderId);
        order.setStatus(status);
        return orderRepository.save(order);
    }
    
    @Transactional
    public void cancelOrder(Long orderId) {
        Order order = getOrderById(orderId);
        order.setStatus(Order.OrderStatus.CANCELLED);
        orderRepository.save(order);
    }
}
