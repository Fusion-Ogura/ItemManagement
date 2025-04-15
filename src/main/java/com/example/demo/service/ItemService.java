package com.example.demo.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
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
}  