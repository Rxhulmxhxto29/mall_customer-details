package com.shopping.mall.controller;

import com.shopping.mall.entity.Shop;
import com.shopping.mall.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/shops")
@CrossOrigin(origins = "*")
public class ShopController {
    
    @Autowired
    private ShopService shopService;
    
    @GetMapping
    public ResponseEntity<List<Shop>> getAllShops() {
        return ResponseEntity.ok(shopService.getActiveShops());
    }
    
    @GetMapping("/{shopId}")
    public ResponseEntity<Shop> getShopById(@PathVariable Long shopId) {
        return ResponseEntity.ok(shopService.getShopById(shopId));
    }
    
    @GetMapping("/name/{shopName}")
    public ResponseEntity<Shop> getShopByName(@PathVariable String shopName) {
        return ResponseEntity.ok(shopService.getShopByName(shopName));
    }
    
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Shop> createShop(@RequestBody Shop shop) {
        return ResponseEntity.ok(shopService.createShop(shop));
    }
    
    @PutMapping("/{shopId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Shop> updateShop(@PathVariable Long shopId, @RequestBody Shop shop) {
        return ResponseEntity.ok(shopService.updateShop(shopId, shop));
    }
    
    @DeleteMapping("/{shopId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteShop(@PathVariable Long shopId) {
        shopService.deleteShop(shopId);
        return ResponseEntity.ok().build();
    }
}
