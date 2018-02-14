package com.beeva.authentication.configuration;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.beeva.authentication.JwtAuthProvider;
import com.beeva.authentication.JwtAuthSuccessHandler;
import com.beeva.authentication.entrypoint.JwtEntryPoint;
import com.beeva.authentication.filter.JwtFilter;

@EnableGlobalMethodSecurity(prePostEnabled = true) //TODO REALMENTE NECESARIO? --HABILITA RESTRINGIR LOS METODOS USANDO AUTORIZACION?
@EnableWebSecurity
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private JwtEntryPoint theJwtEntryPoint;
	
	@Autowired
	private JwtAuthProvider theJwtAuthProvider;
	
	
	@Bean
	@Override
	public AuthenticationManager authenticationManager(){
		ProviderManager providerManager = new ProviderManager(Collections.singletonList(theJwtAuthProvider)); 
		//configura el authentication manager para utilizar JwtAuthProvider
		return providerManager;
	}

	@Bean
	public JwtFilter getJWTConfiguredFilter() {
		JwtFilter theJwtFilter = new JwtFilter();
		//asigna la clase JwtAuthProvider como provider de este filtro		
		theJwtFilter.setAuthenticationManager(authenticationManager());
		//TODO no tenemos logica en el successHandler, ¿vale la pena usarlo?
		//quiza es necesario dejarlo vacio para sobrescribir la funcionalidad predeterminada de spring
		theJwtFilter.setAuthenticationSuccessHandler(new JwtAuthSuccessHandler());
		return theJwtFilter;
	}
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	
		auth.inMemoryAuthentication().withUser("user").password("password").roles("ADMIN");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		//deshabilitamos la creación de sesiones
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			//Permitirmos el acceso a la url /login
//			.and().antMatcher("/login").authorizeRequests().anyRequest().permitAll()
			//Restringimos el acceso a todas las url y agregamos un filtro
			.and().authorizeRequests().antMatchers("***/hola/**").authenticated()
				
				//TODO conflictos con los metodos diferentes de GET,¿ es recomendable desactivarlo?
			.and().csrf().disable()
			//TODO en el video utilizan esto cual de los dos es correcto y en que situaciones son utilizados 
            .exceptionHandling().authenticationEntryPoint(theJwtEntryPoint);
//				.httpBasic().authenticationEntryPoint(theJwtEntryPoint);
				//TODO ¿ES NECESARIO?
		http.headers().cacheControl();
		
				http//Añadimos el filtro personalizado antes del filtro normal
				.addFilterBefore(getJWTConfiguredFilter(), UsernamePasswordAuthenticationFilter.class)
				;

		
	}
}
