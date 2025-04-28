package com.example.demo.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Item;
import com.example.demo.entity.Stock;
import com.example.demo.repository.ItemRepository;
import com.example.demo.repository.StockRepository;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private StockRepository stockRepository;

    // 商品一覧取得（検索とページング対応）
    public Page<Item> getItems(String keyword, Pageable pageable, String[] sort) {
        String sortOrder = sort[1];

        	// quantityに基づく昇順・降順の切り替え
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                    Sort.by(Sort.Direction.fromString(sortOrder), "stock.quantity"));
            if (keyword == null || keyword.isEmpty()) {
                return itemRepository.findAll(pageable);
            } else {
                return itemRepository.findByNameContaining(keyword, pageable);
            }
    }

    // 単体取得（ID指定）
    public Item getItemById(Long id) {
        return itemRepository.findById(id).orElse(null);
    }

    // 新規登録
    public Item createItem(Item item) {
        return itemRepository.save(item);
    }

    // 更新
    public Item updateItem(Long id, Item itemDetails) {
        Item item = itemRepository.findById(id).orElseThrow(() -> new RuntimeException("商品が見つかりません"));
        item.setName(itemDetails.getName());
        item.setPrice(itemDetails.getPrice());
        item.setStock(itemDetails.getStock());
        return itemRepository.save(item);
    }

    // 削除
    public void deleteItem(Long id) {
        itemRepository.deleteById(id);
    }

    // 在庫更新
    @Transactional
    public void updateStock(Long itemId, Integer quantity) {
        Stock stock = stockRepository.findByItem_Id(itemId)
                .orElseThrow(() -> new RuntimeException("在庫情報が見つかりません"));
        stock.setQuantity(quantity);
    }

    // 価格更新
    @Transactional
    public void updatePrice(Long itemId, BigDecimal price) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("商品が見つかりません"));
        item.setPrice(price);
    }
}
