package dev.eliezer.lojaonline.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private SecurityUserFilter securityUserFilter;

    private static final String[] SWAGGER_LIST = {
            "/swagger-ui/*",
            "/v3/api-docs/**",
            "/swagger-resources/**",
            "/documentation"
    };

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        http.csrf(AbstractHttpConfigurer::disable)
                .cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(auth -> {
                    auth
                            .requestMatchers("/users").permitAll()
                            .requestMatchers("/users/auth").permitAll()
                            .requestMatchers("/clients/auth").permitAll()
                            .requestMatchers("/clients").permitAll()
                            .requestMatchers("/clients/*").permitAll()
                            .requestMatchers("/products").permitAll()
                            .requestMatchers("/products/*").permitAll()
                            .requestMatchers("/products/images").permitAll()
                            .requestMatchers("/products/images/*").permitAll()
                            .requestMatchers("/categories").permitAll()
                            .requestMatchers("/categories/*").permitAll()
                            .requestMatchers("/config/header/menu/categories").permitAll()
                            .requestMatchers("/config/header/menu/categories/*").permitAll()
                            .requestMatchers("/orders").permitAll()
                            .requestMatchers("/orders/").permitAll()
                            .requestMatchers("/products/*").permitAll()
                            .requestMatchers("/images/*").permitAll()
                            .requestMatchers(SWAGGER_LIST).permitAll()
                            .requestMatchers("/clients/create").permitAll()
                            .requestMatchers("/system/status").permitAll();
                    auth.anyRequest().authenticated();
                })
                .addFilterBefore(securityUserFilter, BasicAuthenticationFilter.class);
        http.httpBasic(withDefaults());
        return http.build();

    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
//        configuration.addAllowedOrigin("localhost:5173");
        configuration.addAllowedOriginPattern("*");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
