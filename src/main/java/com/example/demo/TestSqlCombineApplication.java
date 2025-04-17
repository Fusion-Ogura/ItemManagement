package com.example.demo;

import java.math.BigDecimal;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.demo.entity.Item;
import com.example.demo.entity.Stock;
import com.example.demo.repository.ItemRepository;

@SpringBootApplication
public class TestSqlCombineApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestSqlCombineApplication.class, args);
	}
	 @Bean
	    public CommandLineRunner dataLoader(ItemRepository itemRepository) {
	        return args -> {
	            if (itemRepository.count() > 0) return;

	            for (int i = 1; i <= 100; i++) {
	                Item item = new Item();
	                item.setName("商品" + i);
	                item.setPrice(BigDecimal.valueOf(100 + i));

	                Stock stock = new Stock();
	                stock.setQuantity(i * 10);

	                item.setStock(stock); // 双方向リンク

	                itemRepository.save(item); // cascade で stock も保存
	            }

	            System.out.println("初期データ100件作成完了！");
	        };

        }
}
