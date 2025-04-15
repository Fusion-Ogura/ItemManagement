package com.example.demo.entity;  
import java.math.BigDecimal;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

import lombok.Data;  

@Data  
@Entity  
public class Item {  
    @Id  
    @GeneratedValue(strategy = GenerationType.IDENTITY)  
    private Long id;  

    @Column(nullable = false)  
    private String name;  

    @Column(nullable = false)  
    private BigDecimal price;  

    @OneToOne(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)  
    private Stock stock;  

    // 循環参照防止メソッド  
    public void setStock(Stock stock) {  
        if (stock == null) {  
            if (this.stock != null) this.stock.setItem(null);  
        } else {  
            stock.setItem(this);  
        }  
        this.stock = stock;  
    }  
}  
