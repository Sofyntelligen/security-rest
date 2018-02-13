package com.beeva;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	JwtEntryPoint theJwtEntryPoint;
	
	@Autowired
	JwtFilter theJwtFilter;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	
		auth.inMemoryAuthentication().withUser("user").password("password").roles("ADMIN");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and().antMatcher("/login").authorizeRequests().anyRequest().permitAll()
			.and().antMatcher("/**").addFilterBefore(theJwtFilter, UsernamePasswordAuthenticationFilter.class).authorizeRequests().anyRequest().hasRole("ADMIN")
			.and().csrf().disable()
				.httpBasic().authenticationEntryPoint(theJwtEntryPoint)
				;

		
	}
}
