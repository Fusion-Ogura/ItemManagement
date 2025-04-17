package com.example.demo.controller;  
import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
    public String listItems(Model model, @PageableDefault(size = 10) Pageable pageable) {
        Page<Item> page = itemService.findAll(pageable);
        model.addAttribute("page", page);
        return "item/list";
    }
}  