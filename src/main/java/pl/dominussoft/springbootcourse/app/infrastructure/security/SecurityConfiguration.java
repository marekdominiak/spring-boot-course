package pl.dominussoft.springbootcourse.app.infrastructure.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.dominussoft.springbootcourse.app.domain.Authority;

@Profile("!no-security")
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/h2-console/**").permitAll()  //    allow access to h2-conosle
                .antMatchers(HttpMethod.POST, "/courses/*").hasAuthority(Authority.MANAGE_COURSE.name())
                .antMatchers(HttpMethod.PUT, "/courses/*").hasAuthority(Authority.MANAGE_COURSE.name())
                .antMatchers(HttpMethod.POST, "/instructors").hasAuthority(Authority.MANAGE_INSTRUCTOR.name())
                .antMatchers(HttpMethod.PUT, "/instructors").hasAuthority(Authority.MANAGE_INSTRUCTOR.name())
                .antMatchers(HttpMethod.POST, "/purchase/*").hasAuthority(Authority.BUY_COURSE.name())
                .antMatchers(HttpMethod.PUT, "/purchase/*").hasAuthority(Authority.BUY_COURSE.name())
                .antMatchers(HttpMethod.POST, "/cart/*").authenticated()
                .antMatchers(HttpMethod.PUT, "/cart/*").authenticated()
                .anyRequest().authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf().disable() // for h2-console and our convenience
                .headers().frameOptions().disable() // for h2-console and our convenience
                .and()
                .httpBasic();
    }
//
//    @Bean
//    public AuthenticationEntryPoint unauthorizedEntryPoint() {
//        return (request, response, authException) -> {
//            if (authException instanceof LockedException) {
//                response.sendError(SC_UNAUTHORIZED, "LOCKED");
//            } else if (authException instanceof DisabledException) {
//                response.sendError(SC_UNAUTHORIZED, "DISABLED");
//            } else if (authException instanceof BadCredentialsException) {
//                response.sendError(SC_UNAUTHORIZED, "BAD_CREDENTIALS");
//            } else {
//                response.sendError(SC_UNAUTHORIZED, authException.getMessage());
//            }
//        };
//    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
