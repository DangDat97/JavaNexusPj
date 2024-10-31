package nasuxjava.webnexus.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import nasuxjava.webnexus.security.CustomAuthenticationFailureHandler;
import nasuxjava.webnexus.security.CustomAuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SpringSecurity {
        @Autowired
        private UserDetailsService userDetailsService;

        @Autowired
        private CustomAuthenticationFailureHandler failureHandler;

        @Autowired
        private CustomAuthenticationSuccessHandler successHandler;

        @Bean
        public static PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
                        throws Exception {
                return authenticationConfiguration.getAuthenticationManager();
        }

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
                http.authorizeHttpRequests((authorize) -> authorize
                                // .requestMatchers("/admin/**",
                                // "/client/**", "/webjars/**", "/resources/**")
                                // .permitAll()
                                // .requestMatchers("/index", "/register", "/login").permitAll()
                                // .requestMatchers("/users").hasRole("ADMIN")
                                // .requestMatchers("/Admin/**").authenticated()
                                .anyRequest().permitAll())
                                .formLogin(
                                                form -> form
                                                                .loginPage("/login")
                                                                .loginProcessingUrl("/login")
                                                                .usernameParameter("email")
                                                                .passwordParameter("password")
                                                                .successHandler((request, response, authentication) -> {
                                                                        // Kiểm tra vai trò của người dùng
                                                                        if (authentication.getAuthorities().stream()
                                                                                        .anyMatch(grantedAuthority -> grantedAuthority
                                                                                                        .getAuthority()
                                                                                                        .equals("ROLE_ADMIN"))) {
                                                                                response.sendRedirect(
                                                                                                "/Admin/Dashboard");
                                                                        } else {
                                                                                response.sendRedirect(
                                                                                                "/Account/Dashboard");
                                                                        }
                                                                })
                                                                .failureHandler(failureHandler)
                                                                .permitAll())
                                .logout(
                                                logout -> logout
                                                                .logoutRequestMatcher(
                                                                                new AntPathRequestMatcher("/logout"))
                                                                .permitAll())
                                .sessionManagement(session -> session
                                                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                                                .maximumSessions(1) // Giới hạn số phiên đăng nhập đồng thời
                                                .expiredUrl("/login?expired") // URL để chuyển hướng khi phiên hết hạn
                                );
                return http.build();
        }

        @Autowired
        public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
                auth
                                .userDetailsService(userDetailsService)
                                .passwordEncoder(passwordEncoder());
        }

}