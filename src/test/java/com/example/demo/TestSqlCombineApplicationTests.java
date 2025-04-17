package com.example.demo;

import static org.junit.jupiter.api.Assertions.*;

import javax.sql.DataSource;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles; 

@SpringBootTest(  
	    classes = TestSqlCombineApplication.class,  
	    webEnvironment = SpringBootTest.WebEnvironment.NONE // 必須  
	)
@Disabled("テストが未整備なため一時的に無効化")
@EntityScan(basePackages = "com.example.demo.entity")
@ActiveProfiles("test")  
class TestSqlCombineApplicationTests {  
    @Autowired  
    private DataSource dataSource; // 接続確認用  

    @Test  
    void contextLoads() {  
        assertNotNull(dataSource);  
    }  
}  