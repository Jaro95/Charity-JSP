package pl.coderslab.charity.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                       // .requestMatchers("/**").permitAll()
                        .requestMatchers("/admin/create-start").permitAll()
                        //.requestMatchers("/donation/**").hasAnyRole("USER", "ADMIN", "SUPER_ADMIN")
                       .requestMatchers("/donation/**").hasRole("USER")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .failureHandler(customAuthenticationFailureHandler())
                        .defaultSuccessUrl("/donation", true)
                )
                .logout(logout -> logout
                        .logoutUrl("/donation/logout")
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                        .logoutRequestMatcher(new AntPathRequestMatcher("/donation/logout", "GET"))
                )
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .accessDeniedPage("/login")
                );;
        //.exceptionHandling(exception -> exception.accessDeniedPage("/403"));
        return http.build();
    }

    @Bean
    public AuthenticationFailureHandler customAuthenticationFailureHandler() {
        return (request, response, exception) -> {
            response.sendRedirect("/login?error=true");
        };
    }
}
