package com.spx.product.api;

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
import com.spx.product.actions.CreateProductAction;
import com.spx.product.actions.GetProductAction;
import com.spx.product.actions.ProductActionFactory;
import com.spx.product.actions.RemoveProductAction;
import io.swagger.annotations.Api;

@Produces({APPLICATION_JSON, TEXT_PLAIN})
@Consumes({APPLICATION_JSON, TEXT_PLAIN})
@Path(ProductResource.PATH)
@Api(ProductResource.PATH)
@Dependent
@Transactional(value = TxType.REQUIRED)
public class ProductResource {

  public final static String RESOURCE = "products";
  public final static String NAMED_PARAM = "name";
  public final static String RESOURCE_NAME = RESOURCE + "/{" + NAMED_PARAM + "}";

  public static final String PATH = "productApi";

  private final ProductActionFactory actionFactory;
  private final ActionExecutor executor;

  @Inject
  public ProductResource(ProductActionFactory actionFactory, ActionExecutor executor) {
    this.actionFactory = actionFactory;
    this.executor = executor;
  }

  @Path(RESOURCE)
  @POST
  @Consumes(APPLICATION_JSON)
  @PermitAll /// temp
  public void createTree(ProductView[] containerViews) {
    CreateProductAction action = actionFactory.buildCreateProductAction(containerViews);
    executor.call(action);
  }

  @Path(RESOURCE_NAME)
  @GET
  @PermitAll /// temp
  public ProductView getPoduct(@PathParam(NAMED_PARAM) String reference) {
    GetProductAction action = actionFactory.buildGetProductAction(reference);
    return executor.call(action);
  }

  @Path(RESOURCE_NAME)
  @DELETE
  @PermitAll
  public void removeProduct(@PathParam(NAMED_PARAM) String reference) {
    RemoveProductAction action = actionFactory.buildRemoveProductAction(reference);
    executor.call(action);
  }

}
