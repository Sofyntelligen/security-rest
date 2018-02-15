package com.beeva.authentication.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

//	@Autowired
//	DataSource thedataSource;

    @Autowired
	SecurityConfigImplement securityConfigImplement;
	
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable();

        http.authorizeRequests().antMatchers("/login").permitAll();

        http.authorizeRequests().antMatchers("/**").authenticated();

        http.addFilterBefore(securityConfigImplement.getJWTAuthenticationLogin("/login", authenticationManager()),
                        UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(securityConfigImplement.getJWTAuthenticationToken(),
                        UsernamePasswordAuthenticationFilter.class);

       http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {

        authenticationManagerBuilder.inMemoryAuthentication()
                .withUser("josedaniel")
                .password("beeva")
                .roles("ADMIN");
        
        // https://docs.spring.io/spring-security/site/docs/current/reference/htmlsingle/#user-schema
//        authenticationManagerBuilder.jdbcAuthentication()
//        	.dataSource(thedataSource).withDefaultSchema()
//        		.withUser("josedaniel2").password("beeva").roles("ADMIN");
    }

}
