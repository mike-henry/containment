package com.spx.general;


import javax.enterprise.context.ApplicationScoped;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.ws.rs.Path;
import javax.ws.rs.ext.Provider;
import org.apache.webbeans.config.WebBeansContext;
import org.apache.webbeans.spi.ContainerLifecycle;
import com.spx.dropwizard.extensions.BeanFactory;
import com.spx.general.config.ApplicationConfiguration;
import com.spx.general.utils.ClassFinder;
import io.dropwizard.Application;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import lombok.extern.slf4j.Slf4j;

@ApplicationScoped
@Slf4j
public class MainApplication extends Application<ApplicationConfiguration> {

  private final ClassFinder classFinder;


  private ApplicationConfiguration configuration;


  static ContainerLifecycle lifecycle;

  public MainApplication(ClassFinder classFinder) {
    this.classFinder = classFinder;
  }

  public static void main(String[] args) throws Exception {
    ClassFinder classFinder = new ClassFinder();
    System.out.println("starting..");
    try {
      new MainApplication(classFinder).run(args);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public String getName() {
    return "Global-Containment";
  }

  @Override
  public void initialize(Bootstrap<ApplicationConfiguration> bootstrap) {
    bootstrap.setConfigurationSourceProvider(new SubstitutingSourceProvider(
        bootstrap.getConfigurationSourceProvider(), new EnvironmentVariableSubstitutor(false)));

    addSwaggerBundle(bootstrap);
    addMigrationBundle(bootstrap);
  }

  private void addMigrationBundle(Bootstrap<ApplicationConfiguration> bootstrap) {
    bootstrap.addBundle(new MigrationsBundle<ApplicationConfiguration>() {
      public DataSourceFactory getDataSourceFactory(ApplicationConfiguration configuration) {
        return configuration.getDatabase();
      }
    });
  }

  private void addSwaggerBundle(Bootstrap<ApplicationConfiguration> bootstrap) {
    bootstrap.addBundle(new SwaggerBundle<ApplicationConfiguration>() {
      @Override
      protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(ApplicationConfiguration myConfiguration) {
        return myConfiguration.getSwagger();
      }
    });
  }

  @Override
  public void run(ApplicationConfiguration configuration,
      Environment environment) {
    registerResources(configuration, environment);

    BeanFactory beanFactory = new BeanFactory();
    this.configuration = configuration;

    WebBeansContext currentInstance = WebBeansContext.currentInstance();
    currentInstance.getScannerService();
    currentInstance.registerService(ApplicationConfiguration.class, configuration);
    // WebBeansContext.currentInstance().getExtensionLoader().addExtension(new
    // com.spx.dropwizard.extensions.ShiroWeldExtension(configuration,beanFactory));
    lifecycle = currentInstance.getService(ContainerLifecycle.class);


    currentInstance.getExtensionLoader()
        .addExtension(new com.spx.dropwizard.extensions.ConfigExtension(configuration, beanFactory));
    ServletContext servletContext = environment.getApplicationContext().getServletContext();
    ServletContextEvent event = new ServletContextEvent(servletContext);
    lifecycle.startApplication(event);


    // environment.jersey().register(SessionSecurityInterceptor.class);


  }

  private void registerResources(ApplicationConfiguration configuration, Environment environment) {


    classFinder.findClassesWithAnnotation(configuration.getApplicationPackages(), Path.class)
        .forEach(type -> environment.jersey().register(type));
    classFinder.findClassesWithAnnotation(configuration.getApplicationPackages(), Provider.class)
        .filter(c -> c.getPackage().getName().startsWith("com.spx"))
        .filter(c -> c != com.spx.session.SessionSecurityInterceptor.class)
        .peek(c -> log.info("Registering class as jersey resource {}", c.getName()))
        .forEach(type -> environment.jersey().getResourceConfig().register(type));
  }


}
