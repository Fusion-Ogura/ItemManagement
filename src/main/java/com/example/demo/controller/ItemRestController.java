package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
import com.example.demo.repository.ItemRepository;
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/items")
public class ItemRestController {

    @Autowired
    private ItemRepository itemRepository;
    
 // 商品一覧取得（ページング/ソート/検索）
    @GetMapping
    public Page<Item> getItems(
    		@RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id,asc") String[] sort) {

        Pageable pageable = PageRequest.of(page, size);
        String sortField = sort[0];
        String sortOrder = sort[1];
        if (keyword != null && !keyword.isEmpty()) {
            if ("quantity".equals(sortField)) {
                return "asc".equals(sortOrder)
                        ? itemRepository.findByNameContainingOrderByQuantityAsc(keyword, pageable)
                        : itemRepository.findByNameContainingOrderByQuantityDesc(keyword, pageable);
            }
            return itemRepository.findByNameContaining(keyword, pageable);
        }
        if ("quantity".equals(sortField)) {
            return "asc".equals(sortOrder)
                    ? itemRepository.findAllOrderByQuantityAsc(pageable)
                    : itemRepository.findAllOrderByQuantityDesc(pageable);
        }

        // quantity以外は従来通り
        Sort sortObj = Sort.by(Sort.Direction.fromString(sortOrder), sortField);
        Pageable sortedPageable = PageRequest.of(page, size, sortObj);

        return itemRepository.findAll(sortedPageable);
    }

    // 一覧取得
    //@GetMapping
    //public List<Item> getAllItems() {
    //    return itemRepository.findAll();
    //}

    // 単体取得（ID指定）
    @GetMapping("/{id}")
    public Item getItemById(@PathVariable Long id) {
        return itemRepository.findById(id).orElse(null);
    }
    // 新規登録
    @PostMapping
    public Item createItem(@RequestBody Item item) {
        return itemRepository.save(item);
    }

    // 更新
    @PutMapping("/{id}")
    public Item updateItem(@PathVariable Long id, @RequestBody Item itemDetails) {
        Item item = itemRepository.findById(id).orElse(null);
        if (item == null) return null;

        item.setName(itemDetails.getName());
        item.setPrice(itemDetails.getPrice());
        item.setStock(itemDetails.getStock());

        return itemRepository.save(item);
    }

    // 削除
    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable Long id) {
        itemRepository.deleteById(id);
    }
}
