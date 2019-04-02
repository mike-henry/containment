package com.spx.containment;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.spx.containment.services.ContainerServicesTest;

@RunWith(Suite.class)
@SuiteClasses(value = { 
        com.spx.containment.actions.ActionTest.class,
        com.spx.containment.actions.ContainmentTest.class,
        com.spx.containment.actions.ContainterResourceTest.class,
        com.spx.containment.actions.CreateContainerActionTest.class,
        com.spx.product.api.ProductResourceTest.class,
        com.spx.product.actions.GetProductActionTest.class,
        com.spx.product.actions.CreateProductActionTest.class,
        com.spx.product.services.ProductManagerTest.class,
        com.spx.product.actions.RemoveProductActionTest.class,
        com.spx.containment.api.ContainerModelToViewMapperTest.class,
        com.spx.containment.services.ContainerAdaptorTests.class,
        ContainerServicesTest.class
        
})
public class UnitTests {
}
