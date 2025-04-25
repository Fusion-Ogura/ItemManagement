package com.example.demo.repository;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entity.Item;  

public interface ItemRepository extends JpaRepository<Item, Long> {  
	@EntityGraph(attributePaths = "stock")
	Page<Item> findAll(Pageable pageable);
@Query("SELECT i FROM Item i LEFT JOIN i.stock s ORDER BY s.quantity ASC")
	Page<Item> findAllOrderByQuantityAsc(Pageable pageable);

@Query("SELECT i FROM Item i LEFT JOIN i.stock s ORDER BY s.quantity DESC")
	Page<Item> findAllOrderByQuantityDesc(Pageable pageable);
List<Item> findByNameContaining(String keyword);
Page<Item> findByNameContaining(String keyword, Pageable pageable);
}  
