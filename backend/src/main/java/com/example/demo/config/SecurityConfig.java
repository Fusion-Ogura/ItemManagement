package com.example.demo.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

	 // InMemoryUserDetailsManagerを設定
    @Bean
    public UserDetailsService userDetailsService() {
        return new InMemoryUserDetailsManager(
            User.withUsername("user")
                .password("{noop}password")  // {noop}は平文パスワードを意味します
                .roles("USER")
                .build()
        );
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorizeRequests ->
                authorizeRequests
                    .requestMatchers("/login", "/css/**", "/js/**").permitAll()  // ログインページと静的リソースは許可
                    .anyRequest().authenticated()  // 他のリクエストは認証が必要
            )
            .formLogin(formLogin ->
                formLogin
                    .loginPage("/login")  // ログインページのURL（任意）
                    .defaultSuccessUrl("/home", true)  // ★ここを追加：ログイン成功後の遷移先を固定
                    .permitAll()  // ログインページへのアクセスを許可
            )
            .logout(logout ->
                logout
                    .permitAll()  // ログアウトページへのアクセスを許可
            );

        return http.build();
    }
}