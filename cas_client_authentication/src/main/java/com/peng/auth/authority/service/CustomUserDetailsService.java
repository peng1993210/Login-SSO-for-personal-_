package com.peng.auth.authority.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.cas.authentication.CasAssertionAuthenticationToken;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.peng.auth.authority.AuthorityInfo;
import com.peng.auth.properties.UserInfo;

public class CustomUserDetailsService implements AuthenticationUserDetailsService<CasAssertionAuthenticationToken> {


	@Override
	public UserDetails loadUserDetails(CasAssertionAuthenticationToken token) throws UsernameNotFoundException {
		/* 这里我为了方便，就直接返回一个用户信息，实际当中这里修改为查询数据库或者调用服务什么的来获取用户信息 */
		UserInfo userInfo = new UserInfo();
		userInfo.setUsername("admin");
		userInfo.setName("admin");
		Set<AuthorityInfo> authorities = new HashSet<AuthorityInfo>();
		AuthorityInfo authorityInfo = new AuthorityInfo("TEST");
		authorities.add(authorityInfo);
		userInfo.setAuthorities(authorities);
		return userInfo;
	}

}
