package sample.sdr.auth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;

/**
 * To change any default config, we need to subclass
 * RepositoryRestMvcConfiguration
 * 
 */
@Configuration
public class CustomRepositoryRestMvcConfiguration extends
		RepositoryRestMvcConfiguration {

	@Override
	protected void configureRepositoryRestConfiguration(
			RepositoryRestConfiguration config) {
		config.setDefaultPageSize(1000);
	}
}
