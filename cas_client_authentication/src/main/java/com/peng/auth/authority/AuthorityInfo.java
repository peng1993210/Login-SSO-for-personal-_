package com.peng.auth.authority;

import org.springframework.security.core.GrantedAuthority;

public class AuthorityInfo implements GrantedAuthority {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 权限CODE
	 */
	private String authority;

	public AuthorityInfo(String authority) {
		this.authority = authority;
	}

	@Override
	public String getAuthority() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

}
