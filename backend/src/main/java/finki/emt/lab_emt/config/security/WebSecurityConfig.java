//package finki.emt.lab_emt.config.security;
//
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Profile;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.CorsConfigurationSource;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//
//import java.util.List;
//
//@Profile("old_sec")
//@Configuration
//@EnableWebSecurity
//@EnableMethodSecurity
//public class WebSecurityConfig {
//
//    private final PasswordEncoder passwordEncoder;
//    private final CustomAuthenticationProvider authenticationProvider;
//
//    public WebSecurityConfig(PasswordEncoder passwordEncoder, CustomAuthenticationProvider authenticationProvider) {
//        this.passwordEncoder = passwordEncoder;
//        this.authenticationProvider = authenticationProvider;
//    }
//
//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration corsConfiguration = new CorsConfiguration();
//        corsConfiguration.setAllowedOrigins(List.of("http://localhost:3000"));
//        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
//        corsConfiguration.setAllowedHeaders(List.of("*"));
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", corsConfiguration);
//        return source;
//    }
//
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//
//        /*
//         .headers is used for the h2 db console. The menu is made of frames, and they are normally
//         blocked due to security reasons.Now I enabled it to only allow frames that have common attributes
//         to the current URL that I am located.
//
//         .csrf disables cross-site forgery attacks commands are transmitted from a user that is trusted.
//
//         .anyRequest and .authenticated mean that any request not covered by the rules above will require
//         authentication.
//
//         .httpBasic(Customizer.withDefaults()) enables the basic http authentication protocol
//
//         */
//
//        http
//                .csrf(AbstractHttpConfigurer::disable)
//                .cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.configurationSource(corsConfigurationSource()))
//
//                .headers(headers -> headers
//                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
//                .authorizeHttpRequests(req -> req
//                        .anyRequest().permitAll()
////                        .requestMatchers("/swagger-ui/*","/v3/**","/**").authenticated()
////                        .anyRequest().hasRole("ADMIN")
//
//                )
//                //.httpBasic(Customizer.withDefaults())
//                .formLogin( form -> form
//                        //.loginPage("/login")
//                        .permitAll()
//                        .failureUrl("/login?error=badCredentials")
//                        .defaultSuccessUrl("/swagger-ui/index.html",true)
//                )
//                .logout( logout -> logout
//                        .logoutUrl("/logout")
//                        .clearAuthentication(true)
//                        .invalidateHttpSession(true)
//                        .deleteCookies("JSESSIONID")
//                        .logoutSuccessUrl("/login")
//                )
//                .exceptionHandling((ex) -> ex
//                        .accessDeniedPage("/login")
//                );
//
//        return http.build();
//    }
//
//    //.failureHandler(new AuthenticationErrorHandler())
//
////    @Bean
////    public UserDetailsService userDetailsService(){
////        UserDetails user1= User.builder()
////                .username("emt")
////                .password(passwordEncoder.encode("emt"))
////                .roles("USER")
////                .build();
////        UserDetails admin= User.builder()
////                .username("admin")
////                .password(passwordEncoder.encode("admin"))
////                .roles("ADMIN")
////                .build();
////
////        return new InMemoryUserDetailsManager(user1,admin);
////    }
//
//    @Bean
//    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
//        AuthenticationManagerBuilder managerBuilder= http
//                .getSharedObject(AuthenticationManagerBuilder.class);
//        managerBuilder.authenticationProvider(authenticationProvider);
//        return managerBuilder.build();
//    }
//
//}
