package com.example.commerce.security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;

@AllArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private PasswordEncoder passwordEncoder;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((authz) -> authz
                .requestMatchers("/commandes").authenticated()
                .requestMatchers("/confirmedOrders").authenticated()
                .requestMatchers("/statistics").authenticated()
                .requestMatchers("/choixParametre").authenticated()
                .anyRequest().permitAll()
        ).httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public UserDetailsService users(){
        UserDetails admin = User.builder()
                .username("lilia.Ecommerce")
                .password(passwordEncoder.encode("lilia"))
                .roles("ADMIN")
                .build();
        UserDetails admin1 = User.builder()
                .username("lidia.Ecommerce")
                .password(passwordEncoder.encode("lidia"))
                .roles("ADMIN")
                .build();
        UserDetails usr = User.builder()
                .username("delege.01")
                .password(passwordEncoder.encode("delege"))
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(admin,admin1,usr);
    }
}
