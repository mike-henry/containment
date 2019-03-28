package com.spx.general.config;

import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.spx.containment.persistance.ContainmentConfiguration;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = false)
@Named
@ApplicationScoped
public class ApplicationConfiguration extends Configuration {


  private List<String> applicationPackages = new ArrayList<String>();

  @JsonProperty
  private ContainmentConfiguration containment = new ContainmentConfiguration();

  @JsonProperty
  private DataSourceFactory database = new DataSourceFactory();

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

  public List<String> getUnsecurePaths() {
    return unsecurePaths;
  }

  public void setUnsecurePaths(List<String> unsecurePaths) {
    this.unsecurePaths = unsecurePaths;
  }
}
