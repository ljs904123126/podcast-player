package org.liaimei.podcast.player.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {


    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http)
            throws Exception {
        http.csrf().disable();
        http
                .authorizeHttpRequests((authorize) -> authorize
                        .antMatchers("/login/**").permitAll()
                        .antMatchers("/st/**").permitAll()
                        .antMatchers("/op/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin()
                .loginPage("/login/index")
                .and()
                .headers().and();

//        http.authenticationProvider()

        return http.build();
    }


    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails userDetails = User.withDefaultPasswordEncoder()
                .username("user")
                .password("password")
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(userDetails);
    }

}