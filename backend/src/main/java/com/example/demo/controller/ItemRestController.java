package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Item;
import com.example.demo.service.ItemService;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/items")
public class ItemRestController {

    @Autowired
    private ItemService itemService;
    
    // 商品一覧取得（ページング/ソート/検索）
    @GetMapping
    public Page<Item> getItems(
            @RequestParam(value = "keyword", required = false) String keyword,
            Pageable pageable,
            @RequestParam(defaultValue = "id,asc") String[] sort) {

        return itemService.getItems(keyword, pageable, sort);
    }

    // 単体取得（ID指定）
    @GetMapping("/{id}")
    public Item getItemById(@PathVariable Long id) {
        return itemService.getItemById(id);
    }

    // 新規登録
    @PostMapping
    public Item createItem(@RequestBody Item item) {
        return itemService.createItem(item);
    }

    // 更新
    @PutMapping("/{id}")
    public Item updateItem(@PathVariable Long id, @RequestBody Item itemDetails) {
        return itemService.updateItem(id, itemDetails);
    }

    // 削除
    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable Long id) {
        itemService.deleteItem(id);
    }
}
