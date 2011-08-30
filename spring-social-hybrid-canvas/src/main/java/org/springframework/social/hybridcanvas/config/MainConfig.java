package org.springframework.social.hybridcanvas.config;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseFactory;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Main configuration class for the application.
 * Turns on @Component scanning, loads externalized application.properties, and sets up the database.
 * @author Craig Walls
 */
@Configuration
@ComponentScan(basePackages = "org.springframework.social.hybridcanvas", excludeFilters = { @Filter(Configuration.class) })
@PropertySource("classpath:org/springframework/social/hybridcanvas/config/application.properties")
@EnableTransactionManagement
public class MainConfig {
	private static Logger logger = LoggerFactory.getLogger(MainConfig.class);

	@Bean(destroyMethod = "shutdown")
	public DataSource dataSource() {
		logger.debug("datsource() call in MainConfig. Create JdbcUsersConnectionRepository");
		EmbeddedDatabaseFactory factory = new EmbeddedDatabaseFactory();
		factory.setDatabaseName("social-hybrid");
		factory.setDatabaseType(EmbeddedDatabaseType.H2);
		factory.setDatabasePopulator(databasePopulator());
		return factory.getDatabase();
	}

	@Bean
	public PlatformTransactionManager transactionManager() {
		logger.debug("transactionManager() call in MainConfig");
		return new DataSourceTransactionManager(dataSource());
	}

	@Bean
	public JdbcTemplate jdbcTemplate() {
		logger.debug("jdbcTemplate() call in MainConfig");
		return new JdbcTemplate(dataSource());
	}

 	//internal helpers
	private DatabasePopulator databasePopulator() {
		logger.debug("databasePopulator() call in MainConfig");
		ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
		//sql script in framework core
		populator.addScript(new ClassPathResource("JdbcUsersConnectionRepository.sql", JdbcUsersConnectionRepository.class)); 
		return populator;
	}
}

