package com.spx.session;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.subject.Subject;

import com.spx.general.ApplicationConfiguration;

import lombok.extern.slf4j.Slf4j;

@Provider
@Slf4j
public class SessionSecurityInterceptor implements ContainerRequestFilter
{

	public static final String SESSION_TOKEN = "Session-Token";
	
	
	@Context
    private  ResourceInfo resourceInfo;


    private final ApplicationConfiguration config;
	
	
	@Inject
	public SessionSecurityInterceptor( ApplicationConfiguration config){
	    this.config=config;
	}

	
	@Override
	public void filter(ContainerRequestContext ctx) throws IOException
	{
	    if (this.isPathUnsecured(ctx.getUriInfo().getPath())){
	        return;
	    }

		Method method = this.resourceInfo.getResourceMethod();

		if (method.isAnnotationPresent(PermitAll.class) == false && StringUtils.isBlank(ctx.getHeaderString(SESSION_TOKEN)))
		{
			abort(ctx, method, "insecure request attempted on {}");
		}

		if (StringUtils.isBlank(ctx.getHeaderString(SESSION_TOKEN)) == false)
		{
			Subject subject = new Subject.Builder().sessionId(ctx.getHeaderString(SESSION_TOKEN)).buildSubject();

			log.debug("Admin Rolew is  {}", subject.hasRole("admin"));
			log.debug(" and now session id is {}", subject.getSession().getId().toString());

			boolean roleFound = subjectRolePermitted(method, subject);
			if (roleFound == false)	{
				abort(ctx, method, "Role not permitted for {}");
			}
		}

	}

	private void abort(ContainerRequestContext ctx, Method method, String message)
	{
		ResponseBuilder responseBuilder = Response.serverError();
		Response response = responseBuilder.status(Status.FORBIDDEN).build();
		log.warn(message, method.getName());
		ctx.abortWith(response);
	}

	private boolean subjectRolePermitted(Method method, Subject subject)
	{
		boolean roleFound = true;
		if (method.isAnnotationPresent(RolesAllowed.class)){
			roleFound = false;
			RolesAllowed rolesAllowed = method.getAnnotation(RolesAllowed.class);
			for (String allowedRole : rolesAllowed.value()){
				if (subject.hasRole(allowedRole)){
					roleFound = true;
					break;
				}
			}
			

		}
		
		
		
		return roleFound;
	}
	
	
	private boolean isPathUnsecured(final String path){
		return true;
//        return this.config.getUnsecurePaths()
//	    .stream()
//	    .anyMatch( p -> path.matches(p));
	}
	

}
