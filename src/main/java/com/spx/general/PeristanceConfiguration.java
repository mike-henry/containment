package com.spx.general;


import java.util.Properties;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;


@Data
public class PeristanceConfiguration {
    
    
    private Properties properties;
    
    @JsonProperty
    public Properties getProperties() {
        return properties;
    }
   
    //whether or not idle connections should be validated
    boolean checkConnectionWhileIdle= false;

    //the name of your JDBC driver
    private String driverClass;

    private int maxSize;

    //the maximum amount of time to wait on an empty pool before throwing an exception
    String maxWaitForConnection;

    //the minimum number of connections to keep open
    private int minSize;

    // the password
    private String password = "container-password";

    // the JDBC URL
    private String url;

    //the user name
    private String user = "container-user";

    //the SQL query to run when validating a connection's liveness
    private String validationQuery;

    @JsonProperty
    private String persistanceUnit = "containmentPU";

}
