package com.spx.containment.persistance;

import com.fasterxml.jackson.annotation.JsonProperty;

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
    
//    @JsonProperty
//    private String datastoreProvider = org.hibernate.ogm.datastore.mongodb.impl.MongoDBDatastoreProvider.class.getName();

    @JsonProperty
    private DataSourceFactory containmentDataSource = new DataSourceFactory();
    
//    @JsonProperty
//    private String databasePassword = "password";
//    @JsonProperty
//    private String databaseUser = "root";
    
    @JsonProperty
    private String persistanceUnit = "containmentPU";

    
}
