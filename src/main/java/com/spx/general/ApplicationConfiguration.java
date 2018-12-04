package com.spx.general;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.inject.Vetoed;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.spx.containment.persistance.ContainmentConfiguration;

import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;


@Vetoed 
public class ApplicationConfiguration extends Configuration {

	private List<String> applicationPackages = new ArrayList<String>();

	@JsonProperty
	private ContainmentConfiguration containment = new ContainmentConfiguration();
	
	@JsonProperty
    private DataSourceFactory database = new DataSourceFactory();

    @NotEmpty
	private String defaultWord = "Unkonwn geezer";

    @NotEmpty
	private String firstname;

	@Valid
    @NotNull
    private final SwaggerBundleConfiguration swagger = new SwaggerBundleConfiguration();
	
	private List<String> unsecurePaths = new ArrayList<String>();

	@JsonProperty
	public List<String> getApplicationPackages() {
		return applicationPackages;
	}

	public ContainmentConfiguration getContainment() {
        return containment;
    }

	
    public DataSourceFactory getDatabase() {
       return database;
    }

    @JsonProperty
	public String getDefaultWord() {
		return defaultWord;
	}

    @JsonProperty
	public String getFirstname() {
		return firstname;
	}

    @JsonProperty("swagger")
    public SwaggerBundleConfiguration getSwagger() {
        return swagger;
    }


  
    
    
    
    @JsonProperty
	public void setApplicationPackages(List<String> applicationPackages) {
		this.applicationPackages = applicationPackages;
	}

    
    
 
    public void setContainment(ContainmentConfiguration containment) {
        this.containment = containment;
    }
    
    
    
    
    @JsonProperty
	public void setDefaultWord(String defaultWord) {
		this.defaultWord = defaultWord;
	}

    
    
 
    @JsonProperty
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

    public List<String> getUnsecurePaths() {
        return unsecurePaths;
    }

    public void setUnsecurePaths(List<String> unsecurePaths) {
        this.unsecurePaths = unsecurePaths;
    }
    
    

}
