package com.spx.containment.persistance;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.spx.general.PeristanceConfiguration;
import io.dropwizard.db.DataSourceFactory;
import lombok.Data;

@Data
public class ContainmentConfiguration {
    
    @JsonProperty
    private String  transactionCoordinationStrategy = "jta";
    
    @JsonProperty
    private String  transactionPlatform = "JBossTS";
    
    @JsonProperty
    private String  databaseName = "containment";

    @JsonProperty
    private DataSourceFactory containmentDataSource = new DataSourceFactory();
    
    @JsonProperty
    private PeristanceConfiguration database = new PeristanceConfiguration(); 
    
    @JsonProperty
    private String persistanceUnit = "containmentPU";
    
    

    
}
