package com.spx.containment.actions;

import java.util.concurrent.Callable;
import java.util.function.Supplier;
import javax.inject.Named;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Named
@Transactional
public class ActionExecutor {

  public <R> R call(Callable<R> param) {
    String className = param.getClass().getName();
    R result;
    try {
      log.info("executing... " + className);
      result = param.call();
      log.info("execution complete..." + className);
      return result;
    } catch (Throwable error) {
      log.error("Error Occured executing:" + className, error);
      if (error instanceof RuntimeException) {
        throw (RuntimeException) error;
      }

      throw new RuntimeException(error);
    }


  }

  public <R> R get(Supplier<R> param) {
    return call(new Callable<R>() {
      public R call() {
        return param.get();
      }
    });

  }


}
