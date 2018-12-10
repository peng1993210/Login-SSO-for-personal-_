package com.peng.cas.customer;

import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apereo.cas.authentication.AuthenticationHandlerExecutionResult;
import org.apereo.cas.authentication.PreventedException;
import org.apereo.cas.authentication.UsernamePasswordCredential;
import org.apereo.cas.authentication.handler.support.AbstractUsernamePasswordAuthenticationHandler;
import org.apereo.cas.authentication.principal.PrincipalFactory;
import org.apereo.cas.services.ServicesManager;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public class MyAuthenticationHandler extends AbstractUsernamePasswordAuthenticationHandler {

	private final JdbcTemplate jdbcTemplate;
	private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private final DataSource dataSource;
	private String fieldPassword;
	private String sql;
	private String fieldExpired;
	private String fieldDisabled;

	public MyAuthenticationHandler(final String name, final ServicesManager servicesManager,
			final PrincipalFactory principalFactory, final Integer order, final DataSource dataSource, final String sql,
			final String fieldPassword, final String fieldExpired, final String fieldDisabled) {
		super(name, servicesManager, principalFactory, order);
		this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(this.jdbcTemplate);
		this.sql = sql;
		this.fieldPassword = fieldPassword;
		this.fieldExpired = fieldExpired;
		this.fieldDisabled = fieldDisabled;
	}

	@Override
	protected AuthenticationHandlerExecutionResult authenticateUsernamePasswordInternal(
			UsernamePasswordCredential credential, String originalPassword)
			throws GeneralSecurityException, PreventedException {
        final String username = credential.getUsername();
        final String password = credential.getPassword();
		try {
			final Map<String, Object> queryResult=this.query(credential); //查询结果
			if(queryResult==null) {
				throw new GeneralSecurityException(username+"用户名错误");
			}
			if(!queryResult.containsKey(this.fieldPassword)) {
				throw new GeneralSecurityException(username+"不存的密码字段");
			}
			if(queryResult.containsKey(this.fieldDisabled)) {
				final int status= (int) queryResult.get(this.fieldDisabled);
				if(status==0) {
					throw new GeneralSecurityException(username+"账号无效错误");
				}
			}
			final int pwd_status;
			if(queryResult.containsKey("pwd_status")) {
				pwd_status=(int) queryResult.get("pwd_status");
			}else {
				pwd_status=0; //默认不加密
			}
			switch(pwd_status) {
			case 0://不加密
				if(!password.equals(queryResult.get(this.fieldPassword))) {
					throw new GeneralSecurityException(username+"密码错误");
				}
				break;
			case 1: //base64
				if(!base64Auth()) {
					throw new GeneralSecurityException(username+"密码错误");
				}
				break;
			case 2: //md5
				if(!MD5Auth()) {
					throw new GeneralSecurityException(username+"密码错误");
				}
				break;
			}
		}catch (final DataAccessException e ) {
            throw new PreventedException("SQL exception while executing query for " + username, e);
        }
		System.out.println("认证成功");
		return createHandlerResult(credential, this.principalFactory.createPrincipal(credential.getUsername()),
				new ArrayList<>(0));

	}

	/**
	 * MD5认证
	 * @return
	 */
	private boolean MD5Auth() {
		// TODO Auto-generated method stub
		return false; 
	}

	/***
	 * base64 认证
	 * @return
	 */
	private boolean base64Auth() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 *	 查询方法
	 * @param credential
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Map<String, Object> query(final UsernamePasswordCredential credential) {
		if (this.sql.contains("?")) {
			return getJdbcTemplate().queryForMap(this.sql, credential.getUsername());
		}
		@SuppressWarnings("rawtypes")
		final Map parameters = new LinkedHashMap();
		parameters.put("username", credential.getUsername());
		parameters.put("password", credential.getPassword());
		return getNamedJdbcTemplate().queryForMap(this.sql, parameters);
	}

	/**
	 * Method to return the jdbcTemplate.
	 *
	 * @return a fully created JdbcTemplate.
	 */
	protected JdbcTemplate getJdbcTemplate() {
		return this.jdbcTemplate;
	}

	protected NamedParameterJdbcTemplate getNamedJdbcTemplate() {
		return this.namedParameterJdbcTemplate;
	}

	protected DataSource getDataSource() {
		return this.dataSource;
	}
}
