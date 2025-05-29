package config;

import config.CustomLoginSuccessHandler;
import services.CustomUserDetailsService;

//import model.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomLoginSuccessHandler loginSuccessHandler;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/register", "/login", "/css/**", "/images/**").permitAll()
                
                // EMPLOYEE routes
                .requestMatchers("/leaves/apply").hasAuthority("EMPLOYEE")
                .requestMatchers(HttpMethod.POST, "/leaves").hasAuthority("EMPLOYEE")
                .requestMatchers("/leaves/{id}/view", "/leaves/{id}/download").hasAuthority("EMPLOYEE")
                .requestMatchers("/employee/**").hasAuthority("EMPLOYEE")

                // HR routes
                .requestMatchers("/leaves/**").hasAuthority("HR")

                // ADMIN routes
                .requestMatchers("/", "/index").hasAuthority("ADMIN")
                .requestMatchers("/admin/**").hasAuthority("ADMIN")

                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .successHandler(loginSuccessHandler)
                .permitAll()
            )
            .logout(logout -> logout.logoutSuccessUrl("/login?logout").permitAll());

        return http.build();
    }

    // ✅ Register PasswordEncoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // ✅ Register AuthenticationManager with CustomUserDetailsService
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
