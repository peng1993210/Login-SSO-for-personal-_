package com.peng.cas.config;

import org.apereo.cas.authentication.AuthenticationEventExecutionPlan;
import org.apereo.cas.authentication.AuthenticationEventExecutionPlanConfigurer;
import org.apereo.cas.authentication.AuthenticationHandler;
import org.apereo.cas.authentication.principal.DefaultPrincipalFactory;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.configuration.support.JpaBeans;
import org.apereo.cas.services.ServicesManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;

import com.peng.cas.customer.MyAuthenticationHandler;
import com.peng.cas.customer.MyAuthenticationHandler1;
import com.peng.cas.properties.MyAuthenticationProperties;

/**
 * @author Administrator
 *
 */
@Configuration("myAuthenticationConfiguration")
@ComponentScan(value="com.peng.cas.properties")
public class MyAuthenticationConfiguration implements AuthenticationEventExecutionPlanConfigurer {


	
    @Autowired
    private CasConfigurationProperties casProperties;

    @Autowired
    @Qualifier("servicesManager")
    private ServicesManager servicesManager;
    
    @Autowired
    private MyAuthenticationProperties myAuthenticationProperties;
	
    
    //JpaBeans.newDataSource(myAuthenticationProperties)
	/***
	 * 自定义认证处理器
	 * @return
	 */
	
	@Bean
    public AuthenticationHandler myAuthenticationHandler() {
        MyAuthenticationHandler handler = new MyAuthenticationHandler(MyAuthenticationHandler.class.getSimpleName(), servicesManager, 
        		new DefaultPrincipalFactory(), 1,JpaBeans.newDataSource(myAuthenticationProperties), myAuthenticationProperties.getSql(), 
        		myAuthenticationProperties.getFieldPassword(), myAuthenticationProperties.getFieldExpired(),
        		myAuthenticationProperties.getFieldDisabled());
        ;
        return handler;
    }
	
	
/*	@Bean
    public AuthenticationHandler myAuthenticationHandler1() {
		MyAuthenticationHandler1 handler = new MyAuthenticationHandler1(MyAuthenticationHandler1.class.getSimpleName(), servicesManager, 
        		new DefaultPrincipalFactory(), 1);
        //JpaBeans.newDataSource(myAuthenticationProperties);
		
        return handler;
    }
	*/

	/***
	 * 注册颜验证器
	 */
	@Override
	public void configureAuthenticationExecutionPlan(AuthenticationEventExecutionPlan plan) {
		// TODO Auto-generated method stub
		plan.registerAuthenticationHandler(myAuthenticationHandler());
	}

}
