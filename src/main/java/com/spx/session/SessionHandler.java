package com.spx.session;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import io.swagger.annotations.Api;

@Produces({
    APPLICATION_JSON
})
@Consumes({ 
    APPLICATION_JSON
})
@Path(SessionHandler.PATH)
@Api(SessionHandler.PATH)
public class SessionHandler
{

	

	public static final String PATH = "/session";

	private ShiroAuthenticator shiroAuthenticaor;

	public SessionHandler(){

	}

	@Inject
	public SessionHandler(CredentialAuthenticator authenticator, ShiroAuthenticator shiroA){
		shiroAuthenticaor = shiroA;
	}

	@Path("init")
	@GET
	@PermitAll
	public StartToken initialize(@QueryParam("user") String userName)
	{
		return shiroAuthenticaor.getChallenge(userName);
	}

	@Path("create")
	@POST
	@Consumes(APPLICATION_JSON)
	@PermitAll
	public SessionToken create(Credentials credentials){
		try{
			return shiroAuthenticaor.startSession(credentials);
		} catch (Exception e){
			throw new WebApplicationException(Response.Status.FORBIDDEN);
		}
	}

	@Path("logout")
	@POST
	@Consumes(APPLICATION_JSON)
	public void close(SessionToken session){
		shiroAuthenticaor.end();
	}

}
