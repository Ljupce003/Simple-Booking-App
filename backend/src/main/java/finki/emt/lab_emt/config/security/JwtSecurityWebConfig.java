package finki.emt.lab_emt.config.security;

import finki.emt.lab_emt.config.jwt.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Profile("postgres")
@Configuration
@EnableWebSecurity
public class JwtSecurityWebConfig {

    private final CustomAuthenticationProvider customAuthenticationProvider;
    private final JwtFilter jwtFilter;

    public JwtSecurityWebConfig(CustomAuthenticationProvider customAuthenticationProvider, JwtFilter jwtFilter) {
        this.customAuthenticationProvider = customAuthenticationProvider;
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(List.of("*"));
        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
        corsConfiguration.setAllowedHeaders(List.of("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(corsCustomizer ->
                        corsCustomizer.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(authorizeHttpReqCustomizer ->
                        authorizeHttpReqCustomizer
                                .anyRequest().permitAll()
//                                .requestMatchers("/swagger-ui/**",
//                                        "/v3/api-docs/**",
//                                        "/api/user/register",
//                                        "/api/user/login",
//                                        "/run_initializer",
//                                        "/update-views-manually"
//                                        ,"/"
//                                )
//                                .permitAll()
//                                .requestMatchers(
//                                        "/api/country"
//                                        ,"/api/hosts",
//                                        "/api/reservation",
//                                        "/api/smestuvanje")
//                                .authenticated()
////                                .permitAll()
//                                .anyRequest()
////                                .permitAll()
//                                //.hasRole("HOST")
//                                .hasAnyRole("HOST")
                )
                .sessionManagement(sessionManagementConfigurer ->
                        sessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(customAuthenticationProvider)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
