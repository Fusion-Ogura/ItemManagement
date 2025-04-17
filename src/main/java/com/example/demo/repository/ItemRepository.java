package com.example.demo.repository;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entity.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {  
    @Query("SELECT i FROM Item i JOIN FETCH i.stock")  
    List<Item> findAllWithStock(); 
 // 商品名を部分一致で検索、ページング付き
    Page<Item> findByNameContaining(String name, Pageable pageable);
}  