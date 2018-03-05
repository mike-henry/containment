package com.spx.containment.api;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

import javax.annotation.security.PermitAll;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.spx.containment.actions.ActionExecutor;
import com.spx.containment.actions.ContainerActionFactory;
import com.spx.containment.actions.CreateContainerTreeAction;
import com.spx.containment.actions.GetContainerTreeAction;
import com.spx.containment.actions.RemoveContainerAction;

import io.swagger.annotations.Api;
@Produces({ APPLICATION_JSON, TEXT_PLAIN })
@Consumes({ APPLICATION_JSON, TEXT_PLAIN })
@Path(ContainmentResource.PATH)
@Api(ContainmentResource.PATH)
@Dependent
@Transactional(value=TxType.REQUIRED)
public class ContainmentResource {

    public final static String RESOURCE = "containers";
    public final static String NAMED_PARAM = "name";
    public final static String RESOURCE_NAME = RESOURCE + "/{"+ NAMED_PARAM + "}";

    public static final String PATH = "containmentApi";

    private final ContainerActionFactory actionFactory;
    private final ActionExecutor executor;

    @Inject
    public ContainmentResource(ContainerActionFactory actionFactory,ActionExecutor executor) {
        this.actionFactory = actionFactory;
        this.executor=executor;
    }

    @Path(RESOURCE)
    @POST
    @Consumes(APPLICATION_JSON)
    @PermitAll /// temp
    public void createTree(ContainerView[] containerViews) {
        final CreateContainerTreeAction action = actionFactory.buildCreateContainerTreeAction(containerViews);
        executor.call(action);
    }

    @Path(RESOURCE_NAME)
    @GET
    @PermitAll /// temp
    public ContainerView[] getTree(@PathParam(NAMED_PARAM) String rootName) {
        GetContainerTreeAction action = actionFactory.buildGetContainerTreeAction(rootName);
        return  executor.call(action) ;
    }

    @Path(RESOURCE_NAME)
    @DELETE
    @PermitAll
    public void removeContainer(@PathParam(NAMED_PARAM) String name) {
        RemoveContainerAction action = actionFactory.buildRemoveContainerTreeAction(name);
        executor.call(action) ;
    }

}
