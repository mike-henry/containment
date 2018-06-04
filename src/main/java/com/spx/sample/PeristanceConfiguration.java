package com.spx.sample;


import java.util.Properties;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;


@Getter

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
    private String password;

    // the JDBC URL
    private String url;

    //the user name
    private String user;

    //the SQL query to run when validating a connection's liveness
    String validationQuery;

    


}