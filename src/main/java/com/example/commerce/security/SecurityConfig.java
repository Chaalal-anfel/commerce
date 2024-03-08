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
        UserDetails usr1 = User.builder()
                .username("khelassi_Sara")
                .password(passwordEncoder.encode("SaraDelege*1"))
                .roles("USER")
                .build();
        UserDetails usr2 = User.builder()
                .username("Benali_Imene")
                .password(passwordEncoder.encode("ImeneDelege**2"))
                .roles("USER")
                .build();
        UserDetails usr3 = User.builder()
                .username("Zeeoiali_Meriem")
                .password(passwordEncoder.encode("MeriemDelege***3"))
                .roles("USER")
                .build();
        UserDetails usr4 = User.builder()
                .username("Bouguerra_Randa")
                .password(passwordEncoder.encode("RandaDelege****4"))
                .roles("USER")
                .build();
        UserDetails usr5 = User.builder()
                .username("Ladjal_Chaima")
                .password(passwordEncoder.encode("ChaimaDelege*****5"))
                .roles("USER")
                .build();
        UserDetails usr6 = User.builder()
                .username("Nadjet_Ecommerce")
                .password(passwordEncoder.encode("NadjetDelege******6"))
                .roles("USER")
                .build();
        UserDetails usr7 = User.builder()
                .username("Chahrazed_Ecommerce")
                .password(passwordEncoder.encode("ChahrazedDelege*******7"))
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(admin,admin1,usr1,usr2,usr3,usr4,usr5,usr6,usr7);
    }
}
