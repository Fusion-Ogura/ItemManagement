package com.example.demo.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entity.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {  
    @Query("SELECT i FROM Item i JOIN FETCH i.stock")  
    List<Item> findAllWithStock();  
}  