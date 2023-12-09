package com.project.learnifyapp.configurations;

import com.project.learnifyapp.filters.JwtTokenFilter;
import com.project.learnifyapp.models.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtTokenFilter jwtTokenFilter;

    @Value("${api.prefix}")
    private String apiPrefix;

    //Co nhiem vu nhu ong bao ve. khi co request gui den thi se chan xem
    // la da~ du? giay to chua va co giay to gi moi duoc vao he thong cua toi
    @Bean
    //Pair.of(String.format("%s/courses", apiPrefix), "GET"),
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(requests -> {
                    requests
                            .requestMatchers(
                                String.format("%s/users/register", apiPrefix),
                                String.format("%s/users/login", apiPrefix)
                            )
                            .permitAll()
//                            Payment
                            .requestMatchers(HttpMethod.POST, String.format("%s/payment/**", apiPrefix)).hasRole(Role.USER)
                            .requestMatchers(HttpMethod.PUT, String.format("%s/payment/**", apiPrefix)).hasRole(Role.ADMIN)
                            .requestMatchers(HttpMethod.DELETE, String.format("%s/payment/**", apiPrefix)).hasRole(Role.ADMIN)
                            .requestMatchers(HttpMethod.GET, String.format("%s/payment**", apiPrefix)).permitAll()
                            //Payment-History
                            .requestMatchers(HttpMethod.POST, String.format("%s/payment_history/**", apiPrefix)).hasAnyRole(Role.USER)
                            .requestMatchers(HttpMethod.PUT, String.format("%s/payment_history/**", apiPrefix)).hasRole(Role.ADMIN)
                            .requestMatchers(HttpMethod.DELETE, String.format("%s/payment_history/**", apiPrefix)).hasRole(Role.ADMIN)
                            .requestMatchers(HttpMethod.GET, String.format("%s/payment_history?**", apiPrefix)).permitAll()
                            //Categories
                            .requestMatchers(HttpMethod.POST, String.format("%s/categories/**", apiPrefix)).hasRole(Role.ADMIN)
                            .requestMatchers(HttpMethod.PUT, String.format("%s/categories/**", apiPrefix)).hasRole(Role.ADMIN)
                            .requestMatchers(HttpMethod.DELETE, String.format("%s/categories/**", apiPrefix)).hasRole(Role.ADMIN)
                            .requestMatchers(HttpMethod.GET, String.format("%s/categories**", apiPrefix)).permitAll()
                            //Course
                            .requestMatchers(HttpMethod.POST, String.format("%s/courses/**", apiPrefix)).hasAnyRole(Role.ADMIN, Role.TEACHER)
                            .requestMatchers(HttpMethod.PUT, String.format("%s/courses/**", apiPrefix)).hasAnyRole(Role.ADMIN, Role.TEACHER)
                            .requestMatchers(HttpMethod.DELETE, String.format("%s/courses/**", apiPrefix)).hasAnyRole(Role.ADMIN, Role.TEACHER)
                            .requestMatchers(HttpMethod.GET, String.format("%s/courses**", apiPrefix)).permitAll()
                            //Discount
                            .requestMatchers(HttpMethod.POST, String.format("%s/discounts/**", apiPrefix)).hasAnyRole(Role.ADMIN, Role.TEACHER)
                            .requestMatchers(HttpMethod.PUT, String.format("%s/discounts/**", apiPrefix)).hasAnyRole(Role.ADMIN, Role.TEACHER)
                            .requestMatchers(HttpMethod.DELETE, String.format("%s/discounts/**", apiPrefix)).hasAnyRole(Role.ADMIN, Role.TEACHER)
                            .requestMatchers(HttpMethod.GET, String.format("%s/discounts**", apiPrefix)).permitAll()
                            //Lesson
                            .requestMatchers(HttpMethod.POST, String.format("%s/lessons/**", apiPrefix)).hasAnyRole(Role.ADMIN, Role.TEACHER)
                            .requestMatchers(HttpMethod.PUT, String.format("%s/lessons/**", apiPrefix)).hasAnyRole(Role.ADMIN, Role.TEACHER)
                            .requestMatchers(HttpMethod.DELETE, String.format("%s/lessons/**", apiPrefix)).hasAnyRole(Role.ADMIN, Role.TEACHER)
                            .requestMatchers(HttpMethod.GET, String.format("%s/lessons**", apiPrefix)).permitAll()
                            //Section
                            .requestMatchers(HttpMethod.POST, String.format("%s/section/**", apiPrefix)).hasAnyRole(Role.ADMIN, Role.TEACHER)
                            .requestMatchers(HttpMethod.PUT, String.format("%s/section/**", apiPrefix)).hasAnyRole(Role.ADMIN, Role.TEACHER)
                            .requestMatchers(HttpMethod.DELETE, String.format("%s/section/**", apiPrefix)).hasAnyRole(Role.ADMIN, Role.TEACHER)
                            .requestMatchers(HttpMethod.GET, String.format("%s/section**", apiPrefix)).permitAll()
                            //Comments
                            .requestMatchers(HttpMethod.POST, String.format("%s/comment**", apiPrefix)).permitAll()
                            .requestMatchers(HttpMethod.PUT, String.format("%s/comment**", apiPrefix)).permitAll()
                            .requestMatchers(HttpMethod.DELETE, String.format("%s/comment**", apiPrefix)).permitAll()
                            .requestMatchers(HttpMethod.GET, String.format("%s/comment**", apiPrefix)).permitAll()
                            //Favourite
                            .requestMatchers(HttpMethod.POST, String.format("%s/favourite/**", apiPrefix)).hasAnyRole(Role.ADMIN, Role.TEACHER, Role.USER)
                            .requestMatchers(HttpMethod.PUT, String.format("%s/favourite/**", apiPrefix)).hasAnyRole(Role.ADMIN, Role.TEACHER, Role.USER)
                            .requestMatchers(HttpMethod.DELETE, String.format("%s/favourite/**", apiPrefix)).hasAnyRole(Role.ADMIN, Role.TEACHER, Role.USER)
                            .requestMatchers(HttpMethod.GET, String.format("%s/favourite**", apiPrefix)).permitAll()
                            //Rating
                            .requestMatchers(HttpMethod.POST, String.format("%s/ratings/**", apiPrefix)).hasAnyRole(Role.USER)
                            .requestMatchers(HttpMethod.PUT, String.format("%s/ratings/**", apiPrefix)).hasAnyRole(Role.ADMIN)
                            .requestMatchers(HttpMethod.DELETE, String.format("%s/ratings/**", apiPrefix)).hasAnyRole(Role.ADMIN)
                            .requestMatchers(HttpMethod.GET, String.format("%s/ratings**", apiPrefix)).permitAll()
                            //Role
//                            .requestMatchers(HttpMethod.POST, String.format("%s/role/**", apiPrefix)).permitAll()
//                            .requestMatchers(HttpMethod.PUT, String.format("%s/role/**", apiPrefix)).permitAll()
//                            .requestMatchers(HttpMethod.DELETE, String.format("%s/role/**", apiPrefix)).permitAll()
                            .requestMatchers(HttpMethod.GET, String.format("%s/roles**", apiPrefix)).permitAll()
                            //User
                            .requestMatchers(HttpMethod.POST, String.format("%s/users/**", apiPrefix)).hasAnyRole(Role.ADMIN)
                            .requestMatchers(HttpMethod.POST, String.format("%s/users/uploads/**", apiPrefix)).permitAll()
                            .requestMatchers(HttpMethod.PUT, String.format("%s/users/**", apiPrefix)).hasAnyRole(Role.ADMIN)
                            .requestMatchers(HttpMethod.DELETE, String.format("%s/users/**", apiPrefix)).hasAnyRole(Role.ADMIN)
                            .requestMatchers(HttpMethod.GET, String.format("%s/users**", apiPrefix)).permitAll()
                            .anyRequest()
                            .authenticated();
                });
        httpSecurity.cors(new Customizer<CorsConfigurer<HttpSecurity>>() { //khi deloy sever thi nen bo di
            @Override
            public void customize(CorsConfigurer<HttpSecurity> httpSecurityCorsConfigurer) {
                CorsConfiguration configuration = new CorsConfiguration();
                configuration.setAllowedOrigins(List.of("*")); // cho phep tat ca ai gui cung dc
                configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")); // cho phep tat ca cac method
                configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token"));
                configuration.setExposedHeaders(List.of("x-auth-token"));
                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**", configuration);
                httpSecurityCorsConfigurer.configurationSource(source);
            }
        });
        return httpSecurity.build();
    }
}
