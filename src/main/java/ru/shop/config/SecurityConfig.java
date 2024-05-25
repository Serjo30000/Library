package ru.shop.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.shop.filters.JwtFilter;
import ru.shop.services.UserLibraryDetailsService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@AllArgsConstructor
public class SecurityConfig {
    private final UserLibraryDetailsService aService;
    private final JwtFilter jwtFilter;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(aService);
        authProvider.setPasswordEncoder(passwordEncoder);

        return authProvider;
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationProvider provider) throws Exception {
        http.cors().and().csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/api/userLibraries/user/{login}", "/api/userLibraries/createDefaultAdmin","/api/authors","/api/authors/{id}","/api/email/sendBuyBook","/api/email/sendDeleteBook","/api/auth/login","/api/auth/check","api/books/genre/{genre}","/api/userLibraries/role/{role}","/api/grades/userLibrary/{userLibrary}","/api/grades/book/{book}","/api/books/publisher/{publisher}","/api/books/author/{author}","/api/rentals/userLibrary/{userLibrary}","/api/rentals/book/{book}","/api/genres","/api/genres/{id}","/api/publishers","/api/publishers/{id}","/api/grades","/api/grades/{id}","/api/rentals","/api/rentals/{id}","/api/userLibraries","/api/books","/api/books/{id}","/api/userLibraries/save","/api/userLibraries/{id}/updateRole","/api/activismUsers/save","/api/fonds/save","/swagger-ui/**","/swagger-resources/*",
                        "/v3/api-docs/**")
                .permitAll()
                .requestMatchers("/**")
                .authenticated()
                .and()
                .authenticationProvider(provider)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfiguration) throws Exception {
        return authConfiguration.getAuthenticationManager();
    }
}
