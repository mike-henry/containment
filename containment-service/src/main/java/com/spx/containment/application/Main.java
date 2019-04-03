package com.spx.containment.application;

import javax.enterprise.context.ApplicationScoped;
import com.spx.general.CoreApplication;
import com.spx.general.utils.ClassFinder;

@ApplicationScoped
public class Main extends CoreApplication{

  
  @Override
  public String getName() {
    return "Global-Containment";
  }
  
  
  public static void main(String[] args) throws Exception {

      ClassFinder classFinder = new ClassFinder();
      System.out.println("starting..");
      try {
        new CoreApplication(classFinder).run(args);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

   
    
  
}
