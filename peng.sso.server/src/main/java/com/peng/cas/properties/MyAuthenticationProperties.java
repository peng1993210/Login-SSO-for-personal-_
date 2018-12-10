package com.peng.cas.properties;

import java.io.Serializable;
import org.apereo.cas.configuration.model.support.jpa.AbstractJpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "com.peng.authn.properties")
//@PropertySource("classpath:aplication.properties")

public class MyAuthenticationProperties  extends AbstractJpaProperties implements Serializable{
//public class MyAuthenticationProperties  implements Serializable{
	private static final long serialVersionUID = 23542534566655641L;
	private String sql;
	private String fieldPassword;
	private String fieldExpired;
	private String fieldDisabled;
    /**
     * Name of the authentication handler.
     */
    private String name;
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	public String getFieldPassword() {
		return fieldPassword;
	}
	public void setFieldPassword(String fieldPassword) {
		this.fieldPassword = fieldPassword;
	}
	public String getFieldExpired() {
		return fieldExpired;
	}
	public void setFieldExpired(String fieldExpired) {
		this.fieldExpired = fieldExpired;
	}
	public String getFieldDisabled() {
		return fieldDisabled;
	}
	public void setFieldDisabled(String fieldDisabled) {
		this.fieldDisabled = fieldDisabled;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
    
}
