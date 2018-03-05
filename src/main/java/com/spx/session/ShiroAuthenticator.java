package com.spx.session;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.spx.session.shiro.ShiroBootstrap;

import lombok.extern.slf4j.Slf4j;

// avoiding web  stuff as much possible 
@Slf4j
@RequestScoped
public class ShiroAuthenticator
{

   
	@Inject
	private ShiroBootstrap startup;

	public ShiroAuthenticator(){
	}

	@Inject
	public ShiroAuthenticator(ShiroBootstrap startup)
	{
		this.startup = startup;
	}

	@PostConstruct
	public void init()
	{
		startup.getName();

		log.debug("Shiro Auntenticator started");
	}

	public SessionToken startSession(Credentials credentials)
	{
		SessionToken result = new SessionToken();
		Subject user = SecurityUtils.getSubject();
		HashedUserPasswordToken token = new HashedUserPasswordToken(credentials.getUser(), credentials.getPassword(), credentials.getToken());
		user.login(token);
		result.setValue(user.getSession().getId().toString());
		log.debug(" start with Admin Role is  {}", user.hasRole("admin"));
		log.debug(" start session id is {}", user.getSession().getId().toString());
		return result;
	}

	public void end()
	{
		SecurityUtils.getSubject().logout();
	}

	public StartToken getChallenge(String userName)
	{
		StartToken result = new StartToken();
		log.debug("user {} initialized session with start token {}", userName, result.getValue());
		return result;
	}

}
