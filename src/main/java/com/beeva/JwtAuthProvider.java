package com.beeva;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthProvider extends AbstractUserDetailsAuthenticationProvider {

//	@Autowired
//	private JwtUtil jwtUtil;

	
	
	@Override
	public boolean supports(Class<?> authentication) {
		return (JwtAuthenticationToken.class.isAssignableFrom(authentication));
	}

	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
	}

	@Override
	protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {
		
		System.out.println("entra a rretrieveUser JWTAUTHPROVIDER");
		
		
		JwtAuthenticationToken jwtAuthenticationToken =  (JwtAuthenticationToken) authentication;
		String token = jwtAuthenticationToken.getToken();

//		User parsedUser = jwtUtil.parseToken(token);

//		User parsedUser = new User("user","password", Arrays.asList(new SimpleGrantedAuthority("ADMIN")));
		
		User parsedUser = new User("user","password",true,true,true,true,Arrays.asList(new SimpleGrantedAuthority("ADMIN")));
		
		if (parsedUser == null) {
//			throw new JwtTokenMalformedException("JWT token is not valid");
			try {
				throw new Exception("JWT token is not valid");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

//		List<GrantedAuthority> authorityList  = (List<GrantedAuthority>) parsedUser.getAuthorities();
//		List<GrantedAuthority> authorityList  = AuthorityUtils.commaSeparatedStringToAuthorityList(parsedUser.getRole());

		
		
//		return new AuthenticatedUser(parsedUser.getId(), parsedUser.getUsername(), token, authorityList);
		System.out.println("Sale de retrieve User: " + parsedUser.toString());
		return parsedUser;
	}

}
