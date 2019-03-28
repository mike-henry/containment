package com.spx.general.persistance;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.transaction.Transactional;
import com.spx.containment.persistance.ContainmentAccess;

@Transactional
@Interceptor
public class TransactionInterceptor {

    @Inject
    private EntityManager  em;
    
    @Inject
    @ContainmentAccess
    private EntityManager  containerEntityMananger;
  
    @AroundInvoke
    public Object executeInTransaction(InvocationContext ic) throws Exception {
    
        EntityTransaction containerTransaction = containerEntityMananger.getTransaction();
       
        EntityTransaction emTransaction = em.getTransaction();
        boolean containerActivated = containerTransaction.isActive() == false;
        boolean emActive = emTransaction.isActive() == false;
        
        try {
            if (containerActivated) {
                containerTransaction.begin();
            }
            if (emActive) {
                emTransaction.begin();
            }
           
            Object result = ic.proceed();
            if (containerActivated) {
                containerTransaction.commit();
            }
            if (emActive) {
                emTransaction.commit();
            }
            return result;
        } catch (Exception e) {
            if (containerActivated && containerTransaction.isActive()) {
                containerTransaction.rollback();
            }
            if (emActive && containerTransaction.isActive()) {
                emTransaction.rollback();
            }
            throw e;
        }
    }
    
    
}
