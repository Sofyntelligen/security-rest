package com.beeva.authentication.model;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class JwtUserDetails implements UserDetails{

	private String username;
    private String token;
    private Collection<? extends GrantedAuthority> authorities;
	
    public JwtUserDetails(String username, String token, List<GrantedAuthority> grantedAuthorities) {

        this.username = username;
        this.token= token;
        this.authorities = grantedAuthorities;
    }
    
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public String getUserName() {
		return username;
	}

	public String getToken() {
		return token;
	}

}
