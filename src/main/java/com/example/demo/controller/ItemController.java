package com.example.demo.controller;  
import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Item;
import com.example.demo.service.ItemService;

@Controller  
public class ItemController {  
    @Autowired  
    private ItemService itemService;  

    @PostMapping("/items/{id}/price")  
    public String updatePrice(  
        @PathVariable Long id,  
        @RequestParam BigDecimal price,  
        RedirectAttributes redirectAttributes) {  

        itemService.updatePrice(id, price);  
        redirectAttributes.addFlashAttribute("message", "価格を更新しました");  
        return "redirect:/items";  
    }  

    @PostMapping("/items/{id}/stock")  
    public String updateStock(  
        @PathVariable Long id,  
        @RequestParam Integer quantity,  
        RedirectAttributes redirectAttributes) {  

        itemService.updateStock(id, quantity);  
        redirectAttributes.addFlashAttribute("message", "在庫数を更新しました");  
        return "redirect:/items";  
    }  
    @GetMapping("/items")  
    public String listItems( @RequestParam(name = "keyword", required = false) String keyword,
            @PageableDefault(size = 10) Pageable pageable,
            Model model) {
    	Page<Item> page = itemService.searchItems(keyword, pageable); // ←Serviceに渡す
        model.addAttribute("page", page);
        model.addAttribute("keyword", keyword); // 入力値を再表示用に渡す
        return "item/list";
    }
    @GetMapping("/items/{id}")
    public String showDetail(@PathVariable Long id, Model model) {
        Item item = itemService.findById(id);
        model.addAttribute("item", item);
        return "item/detail";
    }
 // 編集画面の表示
    @GetMapping("/items/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Item item = itemService.findById(id); // DBから商品取得
        model.addAttribute("item", item);     // 画面に渡す
        return "item/edit";                   // 編集画面へ
    }
    
 // 商品の更新（RESTっぽく PUT 使用）
    @PutMapping("/items/{id}")
    public String updateItem(
            @PathVariable Long id,
            @ModelAttribute("item") Item formItem) {

        itemService.updateItem(id, formItem.getPrice(), formItem.getStock().getQuantity());
        return "redirect:/items/" + id; // 詳細画面へリダイレクト
    }
}  