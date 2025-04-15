-- データベース作成（明示的）  
CREATE DATABASE IF NOT EXISTS sql_combine_test  
  CHARACTER SET utf8mb4  
  COLLATE utf8mb4_unicode_ci;  

USE sql_combine_test;  

-- ユーザー管理テーブル作成  
CREATE TABLE IF NOT EXISTS users (  
  user_id INT AUTO_INCREMENT PRIMARY KEY,  
  user_name VARCHAR(50) NOT NULL,  
  email VARCHAR(100) UNIQUE,  
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP  
) ENGINE=InnoDB;  

-- 初期データ挿入（ダブルクォート→シングルクォート）  
INSERT INTO users (user_name, email) VALUES  
  ('テストユーザー1', 'test1@example.com'),  
  ('テストユーザー2', 'test2@example.com');  

-- ユーザー権限付与（sql_combineユーザー用）  
GRANT ALL PRIVILEGES ON *.* TO 'root'@'%'; 
FLUSH PRIVILEGES;  