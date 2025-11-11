package com.shopping.mall.repository;

import com.shopping.mall.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {
    Optional<Shop> findByShopName(String shopName);
    List<Shop> findByStatus(Shop.ShopStatus status);
    
    @Query("SELECT s FROM Shop s WHERE s.status = 'ACTIVE'")
    List<Shop> findActiveShops();
}
