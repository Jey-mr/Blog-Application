package com.jey.blogapp.security;

import com.jey.blogapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;
import java.util.List;

@Configuration
public class SecurityConfig {

/*
    @Bean
    public InMemoryUserDetailsManager userDetailsManager() {

        UserDetails Jey = User.builder()
                .username("Jey")
                .password("{noop}test123")
                .roles("AUTHOR", "ADMIN")
                .build();

        UserDetails Ganesh = User.builder()
                .username("Ganesh")
                .password("{noop}test123")
                .roles("AUTHOR")
                .build();

        return new InMemoryUserDetailsManager(Jey, Ganesh);
    }
*/

    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource) {

        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);

        jdbcUserDetailsManager.setUsersByUsernameQuery(
                "select name, password, active from users where name=?");

        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery(
                "select name, role from users where name=?");

        return jdbcUserDetailsManager;
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(configurer ->
                        configurer
                                .requestMatchers("/**").permitAll()
                )
                .formLogin(form ->
                        form
                                .loginPage("/login")
                                .loginProcessingUrl("/authenticateTheUser")
                                .permitAll()
                )
                .logout(logout -> logout.permitAll()
                );

        return http.build();
    }
}
