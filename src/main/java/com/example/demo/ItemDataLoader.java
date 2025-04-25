package com.example.demo;

import java.math.BigDecimal;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.demo.entity.Item;
import com.example.demo.entity.Stock;
import com.example.demo.repository.ItemRepository;
import com.example.demo.repository.StockRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ItemDataLoader implements CommandLineRunner {

    private final ItemRepository itemRepository;
    private final StockRepository stockRepository;

    @Override
    public void run(String... args) {
        for (int i = 1; i <= 100; i++) {
            Item item = new Item();
            item.setName("商品" + i);
            item.setPrice(BigDecimal.valueOf(1000.0 * i));

            Stock stock = new Stock();
            stock.setQuantity(10 * i);

            // 双方向で紐付け
            item.setStock(stock); // この中で stock.setItem(item) が呼ばれる

            itemRepository.save(item); // cascadeでstockも保存される
        }
    }
}
