package com.spx.inventory.api;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

import java.util.List;

import javax.annotation.security.PermitAll;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.spx.containment.actions.ActionExecutor;
import com.spx.inventory.actions.InventoryActionFactory;

import io.dropwizard.hibernate.UnitOfWork;
import io.swagger.annotations.Api;
@Produces({ APPLICATION_JSON, TEXT_PLAIN })
@Consumes({ APPLICATION_JSON, TEXT_PLAIN })
@Path(InventoryResource.PATH)
@Api(InventoryResource.PATH)
@Dependent
@Transactional(value=TxType.REQUIRED)
public class InventoryResource {

    public final static String RESOURCE = "inventory";
    public final static String NAMED_PARAM = "reference";
    public final static String RESOURCE_NAME = RESOURCE + "/{" + NAMED_PARAM + "}";

    public static final String PATH = "inventoryApi";

    private final InventoryActionFactory actionFactory;
    private final ActionExecutor executor;

    @Inject
    public InventoryResource(InventoryActionFactory actionFactory,ActionExecutor executor) {
        this.actionFactory=actionFactory;
        this.executor=executor;
    }

    @Path(RESOURCE)
    @POST
    @Consumes(APPLICATION_JSON)
    @PermitAll /// temp
    @Transactional(value=TxType.REQUIRED)
    @UnitOfWork
    public void create(InventoryView view) {
        this.executor.call(actionFactory.buildCreateInventoryAction(view));
    }
    
    @Path(RESOURCE+"/{container-reference}")
    @GET
    @Consumes(APPLICATION_JSON)
    @PermitAll /// temp
    @Transactional(value=TxType.REQUIRED)
    @UnitOfWork
    public List<InventoryView> getInventoryInContainer(@PathParam("container-reference")String containerReference) {
        return  this.executor.call(actionFactory.buildGetInventoryInContainerAction(containerReference) );
    }

}
