package org.springframework.social.hybridcanvas.config;

import org.springframework.social.hybridcanvas.SecurityContext;
import org.springframework.social.hybridcanvas.controller.HomeController;
import org.springframework.social.hybridcanvas.signin.SimpleSignInAdapter;
import org.springframework.social.hybridcanvas.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.support.ConnectionFactoryRegistry;
import org.springframework.social.connect.web.ConnectController;
import org.springframework.social.connect.web.ProviderSignInController;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;

import javax.inject.Inject;
import javax.sql.DataSource;

@Configuration
public class SocialConfig {
	private static Logger logger = LoggerFactory.getLogger(SocialConfig.class);

	@Inject
	private Environment environment;

	@Inject
	private DataSource dataSource;

	/**
	 * When a new provider is added to the app, register its {@link org.springframework.social.connect.ConnectionFactory} here.
	 * @see FacebookConnectionFactory
	 */
	@Bean
	public ConnectionFactoryLocator connectionFactoryLocator() {
		logger.debug("connectionFactoryLocator()");
		ConnectionFactoryRegistry registry = new ConnectionFactoryRegistry();
		registry.addConnectionFactory(new FacebookConnectionFactory(environment.getProperty("facebook.clientId"),
		environment.getProperty("facebook.clientSecret")));
		return registry;
	}


	/**
	 * Singleton data access object providing access to connections across all users.
	 */
	@Bean
	@Scope(value="singleton", proxyMode=ScopedProxyMode.INTERFACES)
	public UsersConnectionRepository usersConnectionRepository() {
		logger.debug("usersConnectionRepository()"); 
		JdbcUsersConnectionRepository repository = new JdbcUsersConnectionRepository(dataSource, connectionFactoryLocator(), Encryptors.noOpText());
		return repository;
	}

	/**
	 * Request-scoped data access object providing access to the current user's connections.
	 */
	@Bean
	@Scope(value="request", proxyMode= ScopedProxyMode.INTERFACES)
	public ConnectionRepository connectionRepository() {
		logger.debug("connectionRepository()"); 
	  User user = SecurityContext.getCurrentUser();
		if (user == null) {
			throw new IllegalStateException("Unable to get a ConnectionRepository: no user signed in");
		}
	  return usersConnectionRepository().createConnectionRepository(user.getUserName());
	}

	@Bean
	@Scope(value="request", proxyMode=ScopedProxyMode.INTERFACES)
	public Facebook facebook() {
		logger.debug("facebook()"); 
		Connection<Facebook> facebook = connectionRepository().findPrimaryConnection(Facebook.class);
		return facebook != null ? facebook.getApi() : new FacebookTemplate();
	}

	@Bean
	public ConnectController connectController() {
		ConnectController connectController = new ConnectController(connectionFactoryLocator(), connectionRepository()); 
		return connectController;
	} 

	@Bean
	public ProviderSignInController providerSignInController() {
		ProviderSignInController signInController = new ProviderSignInController(connectionFactoryLocator(), usersConnectionRepository(), new SimpleSignInAdapter());
		signInController.setPostSignInUrl("http://apps.facebook.com/springsocialcanvas/");
		signInController.setSignUpUrl("http://apps.facebook.com/springsocialcanvas/signup");
		return signInController;
	}

	@Bean
	public HomeController homeController() {
		 return new HomeController(facebook(), connectionRepository());
	}

}
