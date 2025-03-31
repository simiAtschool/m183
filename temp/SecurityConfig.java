package com.helvetia.m295.libraryserver.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.HeaderWriterLogoutHandler;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter.Directive;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Spring Security Configuration responsible for authentication and
 * authorization. Provides the authenticationProvider and securityFilterChain
 * beans.
 *
 * @author vonarma1
 * @see https://www.unlogged.io/post/securing-spring-boot-3-applications-with-spring-security-6-1-and-beyond
 */
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * This method sets up the rules for checking who is trying to access our
     * application: The userDetailsService and the PasswordEncoder. In essence, this
     * method prepares our application to securely check if users are who they claim
     * to be and to handle their passwords securely.
     *
     * @return the Spring Security AuthenticationProvider
     */
    @SuppressWarnings("deprecation")
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(new PasswordEncoderDummy());
        return provider;
    }

    /**
     * This method sets the rules for what is allowed in our application and how
     * security is managed: - Turn off CSRF protection - Control Access: Declaration
     * of who (role) can use which endpoint. Every request in the application needs
     * the user to be logged in accordingly to the requirements. - Session
     * Management: No record are kept of user sessions. This means, each request to
     * the server must include credentials, making it more secure for stateless
     * applications like REST APIs. - Basic Authentication is used
     * <p>
     * Changes to the previous versions includes the replacement of AntMatchers with
     * RequestMatchers and the use of DSL (Domain Specific Language) lambdas.
     *
     * @param httpSecurity the reference to the HttpSecurity to be configured
     * @return the configured and finally built SecurityFilterChain
     * @throws Exception the Exception thrown
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        // TODO Mapping von Requests auf Rollen fertigstellen
        httpSecurity.csrf(AbstractHttpConfigurer::disable).cors(Customizer.withDefaults())
                .authorizeHttpRequests(auth -> auth.requestMatchers("/ausleihe/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.GET, "/medium/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.POST, "/medium").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/medium/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/medium/**").hasRole("ADMIN")
                        .requestMatchers("/kunde/**").hasRole("ADMIN")
                        .requestMatchers("/adresse/**").hasRole("ADMIN")
                        .requestMatchers("/logout.html").hasAnyRole("ADMIN", "USER")
                        .requestMatchers("/Goodbye.html").anonymous()
                        .anyRequest()
                        .authenticated())

                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .logout((logout) -> logout
                        .invalidateHttpSession(true)
                        //.logoutSuccessUrl("/Goodbye.html")
                        .addLogoutHandler(new HeaderWriterLogoutHandler(new ClearSiteDataHeaderWriter(Directive.ALL)))
                        .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler(HttpStatus.UNAUTHORIZED))
                )
                .httpBasic(Customizer.withDefaults());
        return httpSecurity.build();
    }
}
