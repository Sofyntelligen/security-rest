package main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    com.beeva.authentication.configuration.SecurityConfigImplement securityConfigImplement;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests().antMatchers("/login").permitAll()
                .anyRequest().authenticated()
                .and()
        .addFilterBefore(securityConfigImplement.getJWTAuthenticationLogin("/login", authenticationManager()),
                UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(securityConfigImplement.getJWTAuthenticationToken(),
                        UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {

        authenticationManagerBuilder.inMemoryAuthentication()
                .withUser("josedaniel")
                .password("beeva")
                .roles("ADMIN");
    }

}