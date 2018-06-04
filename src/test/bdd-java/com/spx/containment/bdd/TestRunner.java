package com.spx.containment.bdd;



import org.junit.runner.RunWith; 
import cucumber.junit.Cucumber; 


//TODO  update  version.
@RunWith(Cucumber.class) 
@Cucumber.Options(format = {"pretty", "html:target/cucumber"},features="specification/com/spx/containment/bdd")
//,features="src/test/resources"
public class TestRunner {

}
