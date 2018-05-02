package com.spx.containment.services;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import com.spx.containment.business.exceptions.NotFoundException;
import com.spx.containment.model.Container;
import com.spx.containment.model.Global;
import com.spx.containment.persistance.ContainerRepository;

import lombok.extern.slf4j.Slf4j;

@RequestScoped
@Slf4j
public class ContainerServices
{
    private ContainerRepository repository;

    @Inject
    public ContainerServices(ContainerRepository repository) {
        this.repository = repository;
    }

    public ContainerServices() {
    }
    
    

    public Global getGlobal() {
        return (Global) fetchContainerByName(Global.NAME).orElseGet(() -> buildGlobal());
    }

    private Global buildGlobal() {
        Global result = new Global();
        result.setName(Global.NAME);
        result.setParent(result);
        result=(Global) repository.save(result);
        
        return result;
    }

    public Optional<Container> fetchContainerByName(String name) {
        Optional<Container> result = repository.findByName(name); 
        if (!result.isPresent() && Global.NAME.equals(name) ){
            return Optional.ofNullable(buildGlobal());
        }
        return result;
    }
	
	public Container findByReference(String reference)
	{
	    return repository.findByReference(reference)
	     .orElseThrow(() -> new NotFoundException(String.format("reference not found :%s", reference)));
	}

    @SuppressWarnings("unchecked")
    public <C extends Container> Optional<C> fetchContainerByName(String name, Class<C> type) {
        Optional<Container> result = fetchContainerByName(name);
        if (result.isPresent() && type.isInstance(result.get())) {
            return (Optional<C>) result; 
        }
        return  Optional.ofNullable(null);
    }
	
	public Container save(Container container){
	    Container result=repository.save(container);
	    repository.save(container.getParent().get());
	    return result;
	}

    public Container saveTree(Container container) {
        container.setParent(container.getParent().orElseGet(() -> getGlobal()));
        return doSaveTree(container);
    }

    private Container doSaveTree(Container container) {
        log.info("save ..." + container);        
        container
        .getChildren()
        .stream()
        .filter(c -> !Global.NAME.equalsIgnoreCase(c.getName()))
        .forEach(this::doSaveTree);
        Container result = save(container);
        log.info("saved ..." + container.getName());
        return result;
    }

    public void remove(Container c) {
       repository.remove(c);
    }

    
    public Set<Container> getDecendants(Container container){
       Set<Container> result = new HashSet<Container>();
       result.add(container);
       container.getChildren()
       .forEach(child -> result.addAll(getDecendants(child)));       
       return result;
    }
}
