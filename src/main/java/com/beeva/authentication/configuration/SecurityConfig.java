package com.beeva.authentication.configuration;

import java.util.Collections;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.beeva.authentication.JWTAuthenticationLogin;
import com.beeva.authentication.JWTAuthenticationToken;
import com.beeva.authentication.JwtAuthSuccessHandler;
import com.beeva.authentication.JwtAuthenticationTokenFilter;
import com.beeva.authentication.JwtAuthenticationTokenProvider;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	// @Autowired
	// DataSource thedataSource;

	@Autowired
	JwtAuthenticationTokenProvider jwtAuthTokenProvider;

	public JwtAuthenticationTokenFilter tokenFilterConfigurated() {

		JwtAuthenticationTokenFilter filter = new JwtAuthenticationTokenFilter();
		filter.setAuthenticationManager(authenticationManager());
		filter.setAuthenticationSuccessHandler(new JwtAuthSuccessHandler());
		return filter;
	}

	@Override
	protected AuthenticationManager authenticationManager() {
		return new ProviderManager(Collections.singletonList(jwtAuthTokenProvider));
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.csrf().disable();

		http.authorizeRequests().antMatchers("/login").permitAll();

		http.authorizeRequests().antMatchers("/**").hasAnyRole("USER", "ADMIN");

		http.addFilterBefore(new JWTAuthenticationLogin("/login", authenticationManager()),
				UsernamePasswordAuthenticationFilter.class)
				.addFilterBefore(tokenFilterConfigurated(), UsernamePasswordAuthenticationFilter.class);

		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}

	@Override
	protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {

		authenticationManagerBuilder.inMemoryAuthentication().withUser("josedaniel").password("bea").roles("ADMIN");

		// https://docs.spring.io/spring-security/site/docs/current/reference/htmlsingle/#user-schema
		// authenticationManagerBuilder.jdbcAuthentication()
		// .dataSource(thedataSource).withDefaultSchema()
		// .withUser("josedaniel2").password("beeva").roles("ADMIN");
	}

}
