package com.example.demo.entity;  
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

import lombok.Data;  

@Data  
@Entity  
public class Stock {  
    @Id  
    @GeneratedValue(strategy = GenerationType.IDENTITY)  
    private Long id;  

    @Column(nullable = false)  
    private Integer quantity;  

    @OneToOne(optional = false)  
    @JoinColumn(name = "item_id", nullable = false, unique = true)  
    private Item item;  

    @Override  
    public String toString() {  
        return "Stock{id=" + id + ", quantity=" + quantity + "}";  
    }  
}  
