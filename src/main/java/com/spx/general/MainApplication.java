package com.spx.general;



import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.Path;
import javax.ws.rs.ext.Provider;

import org.jboss.weld.environment.se.Weld;

import com.spx.dropwizard.extensions.BeanFactory;
import com.spx.general.utils.ClassFinder;
import com.spx.session.SessionSecurityInterceptor;

import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import lombok.extern.slf4j.Slf4j;

@ApplicationScoped
@Slf4j
public class MainApplication  extends Application<ApplicationConfiguration>{
	
	private final ClassFinder classFinder;
	 
	public MainApplication(ClassFinder classFinder) {
        this.classFinder=classFinder;
    }

    public static void main(String[] args) throws Exception {
	      ClassFinder classFinder = new ClassFinder();
	        new MainApplication(classFinder).run(args);
	    }

	    @Override
	    public String getName() {
	        return "Global-Containment";
	    }

	    @Override
	    public void initialize(Bootstrap<ApplicationConfiguration> bootstrap) {
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
	    	registerResources( configuration,  environment);
	    	Weld weld = new Weld();
	    	BeanFactory beanFactory= new BeanFactory();
	    	weld.addExtension(new com.spx.dropwizard.extensions.ConfigWeldExtension(configuration,beanFactory));
	    	weld.addExtension(new com.spx.dropwizard.extensions.ShiroWeldExtension(configuration,beanFactory));
	    	environment.jersey().register(SessionSecurityInterceptor.class);
	    	environment.jersey().getResourceConfig().register(org.jboss.resteasy.plugins.providers.jackson.ResteasyJackson2Provider.class);
	    	weld.initialize();
	    }

		private void registerResources(ApplicationConfiguration configuration, Environment environment) {
			classFinder.findClassesWithAnnotation(configuration.getApplicationPackages(),Path.class)
			.forEach(type ->environment.jersey().register(type));
			classFinder.findClassesWithAnnotation(configuration.getApplicationPackages(),Provider.class)
			.filter(c -> c != com.spx.session.SessionSecurityInterceptor.class )
			.peek(c->log.info("Registering class as jersery resource{}",c.getName()))
			.forEach(type ->environment.jersey().getResourceConfig().register(type));
		}
}
