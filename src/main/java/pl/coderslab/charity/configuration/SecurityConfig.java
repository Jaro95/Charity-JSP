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
                                .requestMatchers("/**").permitAll()
                                //           .requestMatchers("/charity","/charity/login","/charity/registration").permitAll()
//                        .requestMatchers("charity/admin/create-start").permitAll()
//                        .requestMatchers("/donation/**").hasAnyRole("USER", "ADMIN", "SUPER_ADMIN")
//                       .requestMatchers("/charity/donation/**").hasRole("USER")
//                       .requestMatchers("/charity/admin/**").hasAnyRole("ADMIN","SUPER_ADMIN")
                                .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/charity/login")
                        .failureHandler(customAuthenticationFailureHandler())
                        .defaultSuccessUrl("/charity/donation", true)
                )
                .logout(logout -> logout
                        .logoutUrl("/charity/donation/logout")
                        .logoutSuccessUrl("/charity")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                        .logoutRequestMatcher(new AntPathRequestMatcher("/charity/donation/logout", "GET"))
                );
//                .exceptionHandling(exceptionHandling -> exceptionHandling
//                        .accessDeniedPage("/login")
//                );;
        //.exceptionHandling(exception -> exception.accessDeniedPage("/403"));
        return http.build();
    }

    @Bean
    public AuthenticationFailureHandler customAuthenticationFailureHandler() {
        return (request, response, exception) -> {
            response.sendRedirect("/charity/login?error=true");
        };
    }
}
