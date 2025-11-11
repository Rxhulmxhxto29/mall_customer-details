package com.shopping.mall.service;

import com.shopping.mall.entity.Shop;
import com.shopping.mall.repository.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class ShopService {
    
    @Autowired
    private ShopRepository shopRepository;
    
    public List<Shop> getAllShops() {
        return shopRepository.findAll();
    }
    
    public List<Shop> getActiveShops() {
        return shopRepository.findActiveShops();
    }
    
    public Shop getShopById(Long shopId) {
        return shopRepository.findById(shopId)
                .orElseThrow(() -> new RuntimeException("Shop not found with id: " + shopId));
    }
    
    public Shop getShopByName(String shopName) {
        return shopRepository.findByShopName(shopName)
                .orElseThrow(() -> new RuntimeException("Shop not found with name: " + shopName));
    }
    
    @Transactional
    public Shop createShop(Shop shop) {
        return shopRepository.save(shop);
    }
    
    @Transactional
    public Shop updateShop(Long shopId, Shop shopDetails) {
        Shop shop = getShopById(shopId);
        
        shop.setShopName(shopDetails.getShopName());
        shop.setDescription(shopDetails.getDescription());
        shop.setAddress(shopDetails.getAddress());
        shop.setPhoneNumber(shopDetails.getPhoneNumber());
        shop.setEmail(shopDetails.getEmail());
        shop.setStatus(shopDetails.getStatus());
        
        return shopRepository.save(shop);
    }
    
    @Transactional
    public void deleteShop(Long shopId) {
        Shop shop = getShopById(shopId);
        shopRepository.delete(shop);
    }
}
