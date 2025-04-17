package com.example.demo.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Item;  // 追加 
import com.example.demo.entity.Stock;
import com.example.demo.repository.ItemRepository;
import com.example.demo.repository.StockRepository;

@Service
public class ItemService {  
    @Autowired  
    private ItemRepository itemRepository;  

    @Autowired  
    private StockRepository stockRepository;  

    @Transactional  
    public void updateStock(Long itemId, Integer quantity) {  
        Stock stock = stockRepository.findByItem_Id(itemId)  
            .orElseThrow(() -> new RuntimeException("在庫情報が見つかりません"));  
        stock.setQuantity(quantity);  
    }  

    @Transactional  
    public void updatePrice(Long itemId, BigDecimal price) {  
        Item item = itemRepository.findById(itemId)  
            .orElseThrow(() -> new RuntimeException("商品が見つかりません"));  
        item.setPrice(price);  
    }  
    @Transactional
    public void updateItem(Long itemId, BigDecimal price, Integer quantity) {
        Item item = itemRepository.findById(itemId)
            .orElseThrow(() -> new RuntimeException("商品が見つかりません"));
        item.setPrice(price);
        item.getStock().setQuantity(quantity);
    }
 // findAll メソッドを追加
    public Page<Item> findAll(Pageable pageable) {
        return itemRepository.findAll(pageable);
    }
    
    public Page<Item> searchItems(String keyword, Pageable pageable) {
        if (keyword == null || keyword.isEmpty()) {
            return itemRepository.findAll(pageable);
        } else {
            return itemRepository.findByNameContaining(keyword, pageable);
        }
    }
    public Item findById(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("指定された商品が見つかりません"));
    }
    
}  
