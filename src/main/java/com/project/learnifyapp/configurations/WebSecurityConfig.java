package com.project.learnifyapp.configurations;

import com.project.learnifyapp.components.JwtTokenUtil;
import com.project.learnifyapp.filters.JwtTokenFilter;
import com.project.learnifyapp.models.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

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
                            //Payment
                            .requestMatchers(HttpMethod.POST, String.format("%s/payment/**", apiPrefix)).hasAnyRole(Role.USER)
                            .requestMatchers(HttpMethod.PUT, String.format("%s/payment/**", apiPrefix)).hasRole(Role.ADMIN)
                            .requestMatchers(HttpMethod.DELETE, String.format("%s/payment/**", apiPrefix)).hasRole(Role.ADMIN)
                            .requestMatchers(HttpMethod.GET, String.format("%s/payment**", apiPrefix)).hasAnyRole(Role.ADMIN, Role.USER)
                            //Payment-History
                            .requestMatchers(HttpMethod.POST, String.format("%s/payment_history/**", apiPrefix)).hasAnyRole(Role.USER)
                            .requestMatchers(HttpMethod.PUT, String.format("%s/payment_history/**", apiPrefix)).hasRole(Role.ADMIN)
                            .requestMatchers(HttpMethod.DELETE, String.format("%s/payment_history/**", apiPrefix)).hasRole(Role.ADMIN)
                            .requestMatchers(HttpMethod.GET, String.format("%s/payment_history**", apiPrefix)).hasAnyRole(Role.ADMIN, Role.USER)
                            //Categories
                            .requestMatchers(HttpMethod.POST, String.format("%s/categories/**", apiPrefix)).hasRole(Role.ADMIN)
                            .requestMatchers(HttpMethod.PUT, String.format("%s/categories/**", apiPrefix)).hasRole(Role.ADMIN)
                            .requestMatchers(HttpMethod.DELETE, String.format("%s/categories/**", apiPrefix)).hasRole(Role.ADMIN)
                            .requestMatchers(HttpMethod.GET, String.format("%s/categories**", apiPrefix)).hasAnyRole(Role.ADMIN, Role.USER, Role.TEACHER)
                            //Course
                            .requestMatchers(HttpMethod.POST, String.format("%s/courses/**", apiPrefix)).hasAnyRole(Role.ADMIN, Role.TEACHER)
                            .requestMatchers(HttpMethod.PUT, String.format("%s/courses/**", apiPrefix)).hasAnyRole(Role.ADMIN, Role.TEACHER)
                            .requestMatchers(HttpMethod.DELETE, String.format("%s/courses/**", apiPrefix)).hasAnyRole(Role.ADMIN, Role.TEACHER)
                            .requestMatchers(HttpMethod.GET, String.format("%s/courses**", apiPrefix)).hasAnyRole(Role.ADMIN, Role.USER, Role.TEACHER)
                            //Discount
                            .requestMatchers(HttpMethod.POST, String.format("%s/discounts/**", apiPrefix)).hasAnyRole(Role.ADMIN, Role.TEACHER)
                            .requestMatchers(HttpMethod.PUT, String.format("%s/discounts/**", apiPrefix)).hasAnyRole(Role.ADMIN, Role.TEACHER)
                            .requestMatchers(HttpMethod.DELETE, String.format("%s/discounts/**", apiPrefix)).hasAnyRole(Role.ADMIN, Role.TEACHER)
                            .requestMatchers(HttpMethod.GET, String.format("%s/discounts**", apiPrefix)).hasAnyRole(Role.ADMIN, Role.USER, Role.TEACHER)
                            //Lesson
                            .requestMatchers(HttpMethod.POST, String.format("%s/lessons/**", apiPrefix)).hasAnyRole(Role.ADMIN, Role.TEACHER)
                            .requestMatchers(HttpMethod.PUT, String.format("%s/lessons/**", apiPrefix)).hasAnyRole(Role.ADMIN, Role.TEACHER)
                            .requestMatchers(HttpMethod.DELETE, String.format("%s/lessons/**", apiPrefix)).hasAnyRole(Role.ADMIN, Role.TEACHER)
                            .requestMatchers(HttpMethod.GET, String.format("%s/lessons**", apiPrefix)).hasAnyRole(Role.ADMIN, Role.USER, Role.TEACHER)
                            //Section
                            .requestMatchers(HttpMethod.POST, String.format("%s/section/**", apiPrefix)).hasAnyRole(Role.ADMIN, Role.TEACHER)
                            .requestMatchers(HttpMethod.PUT, String.format("%s/section/**", apiPrefix)).hasAnyRole(Role.ADMIN, Role.TEACHER)
                            .requestMatchers(HttpMethod.DELETE, String.format("%s/section/**", apiPrefix)).hasAnyRole(Role.ADMIN, Role.TEACHER)
                            .requestMatchers(HttpMethod.GET, String.format("%s/section**", apiPrefix)).hasAnyRole(Role.ADMIN, Role.USER, Role.TEACHER)
                            //Comments
                            .requestMatchers(HttpMethod.POST, String.format("%s/comment/**", apiPrefix)).hasAnyRole(Role.ADMIN, Role.TEACHER, Role.USER)
                            .requestMatchers(HttpMethod.PUT, String.format("%s/comment/**", apiPrefix)).hasAnyRole(Role.ADMIN, Role.TEACHER, Role.USER)
                            .requestMatchers(HttpMethod.DELETE, String.format("%s/comment/**", apiPrefix)).hasAnyRole(Role.ADMIN, Role.TEACHER, Role.USER)
                            .requestMatchers(HttpMethod.GET, String.format("%s/comment**", apiPrefix)).hasAnyRole(Role.ADMIN, Role.USER, Role.TEACHER)
                            //Favourite
                            .requestMatchers(HttpMethod.POST, String.format("%s/favourite/**", apiPrefix)).hasAnyRole(Role.ADMIN, Role.TEACHER, Role.USER)
                            .requestMatchers(HttpMethod.PUT, String.format("%s/favourite/**", apiPrefix)).hasAnyRole(Role.ADMIN, Role.TEACHER, Role.USER)
                            .requestMatchers(HttpMethod.DELETE, String.format("%s/favourite/**", apiPrefix)).hasAnyRole(Role.ADMIN, Role.TEACHER, Role.USER)
                            .requestMatchers(HttpMethod.GET, String.format("%s/favourite**", apiPrefix)).hasAnyRole(Role.ADMIN, Role.USER, Role.TEACHER)
                            //Rating
                            .requestMatchers(HttpMethod.POST, String.format("%s/ratings/**", apiPrefix)).hasAnyRole(Role.USER)
                            .requestMatchers(HttpMethod.PUT, String.format("%s/ratings/**", apiPrefix)).hasAnyRole(Role.ADMIN)
                            .requestMatchers(HttpMethod.DELETE, String.format("%s/ratings/**", apiPrefix)).hasAnyRole(Role.ADMIN)
                            .requestMatchers(HttpMethod.GET, String.format("%s/ratings**", apiPrefix)).hasAnyRole(Role.ADMIN, Role.USER, Role.TEACHER)
                            //Role
                            .requestMatchers(HttpMethod.POST, String.format("%s/role/**", apiPrefix)).hasAnyRole(Role.ADMIN)
                            .requestMatchers(HttpMethod.PUT, String.format("%s/role/**", apiPrefix)).hasAnyRole(Role.ADMIN)
                            .requestMatchers(HttpMethod.DELETE, String.format("%s/role/**", apiPrefix)).hasAnyRole(Role.ADMIN)
                            .requestMatchers(HttpMethod.GET, String.format("%s/role**", apiPrefix)).hasAnyRole(Role.ADMIN, Role.USER, Role.TEACHER)
                            //User
                            .requestMatchers(HttpMethod.POST, String.format("%s/users/**", apiPrefix)).hasAnyRole(Role.ADMIN)
                            .requestMatchers(HttpMethod.PUT, String.format("%s/users/**", apiPrefix)).hasAnyRole(Role.ADMIN)
                            .requestMatchers(HttpMethod.DELETE, String.format("%s/users/**", apiPrefix)).hasAnyRole(Role.ADMIN)
                            .requestMatchers(HttpMethod.GET, String.format("%s/users**", apiPrefix)).hasAnyRole(Role.ADMIN, Role.USER, Role.TEACHER)
                            .anyRequest()
                            .authenticated();
                });
        return httpSecurity.build();
    }
}
