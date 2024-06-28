package com.ra.config;

import com.ra.models.entity.RoleEnum;
import com.ra.security.RoleBasedAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import com.ra.security.UserDetailService;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebConfig{
    @Autowired
    private UserDetailService userDetailService;
   @Bean
   SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
       return httpSecurity.csrf(AbstractHttpConfigurer::disable)
               .authenticationProvider(authenticationProvider())
               .authorizeHttpRequests((auth) ->auth
                               .requestMatchers("/*").permitAll()
                       .requestMatchers("/auth/**").permitAll()
                       .requestMatchers("/pay/**").permitAll()
                       .requestMatchers("/user/**").hasAuthority(String.valueOf(RoleEnum.USER))
                       .requestMatchers("/admin/**").hasAuthority(String.valueOf(RoleEnum.ADMIN))
                       .anyRequest().authenticated()
               )     .formLogin(login -> login
                       .loginPage("/login")
                       .loginProcessingUrl("/login")
                       .usernameParameter("username")
                       .passwordParameter("password")
                       .successHandler(roleBasedAuthenticationSuccessHandler())
               )
               .logout(logout -> logout.logoutUrl("/logout").logoutSuccessUrl("/login"))
               .build();
   }

    @Bean
    WebSecurityCustomizer webSecurityCustomizer() {
        return (web -> web.ignoring().requestMatchers("/assets/**", "/fe/**", "/upload/**"));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public AuthenticationSuccessHandler roleBasedAuthenticationSuccessHandler() {
        return new RoleBasedAuthenticationSuccessHandler();
    }
   }
