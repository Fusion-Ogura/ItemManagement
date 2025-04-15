# ベースイメージ（Java 21対応）  
FROM eclipse-temurin:21-jdk-jammy  

# 作業ディレクトリ設定  
WORKDIR /app  

# ビルド済みJARファイルをコピー  
COPY target/TestSqlCombine-0.0.1-SNAPSHOT.jar app.jar  

# ポート公開  
EXPOSE 8080  

# アプリ起動コマンド  
ENTRYPOINT ["java", "-jar", "app.jar"]  