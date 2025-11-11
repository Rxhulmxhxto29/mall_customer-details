package com.shopping.mall.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "shops")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Shop {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long shopId;
    
    @Column(nullable = false)
    private String shopName;
    
    @Column(length = 1000)
    private String description;
    
    private String address;
    
    private String phoneNumber;
    
    private String email;
    
    @Enumerated(EnumType.STRING)
    private ShopStatus status = ShopStatus.ACTIVE;
    
    @OneToMany(mappedBy = "shop", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<Customer> customers = new HashSet<>();
    
    private LocalDateTime createdDate;
    
    private LocalDateTime lastModifiedDate;
    
    @PrePersist
    protected void onCreate() {
        createdDate = LocalDateTime.now();
        lastModifiedDate = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        lastModifiedDate = LocalDateTime.now();
    }
    
    public enum ShopStatus {
        ACTIVE, INACTIVE, CLOSED
    }
}
