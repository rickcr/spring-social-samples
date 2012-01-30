package org.springframework.social.hybridcanvas.config;

import org.springframework.social.hybridcanvas.ConnectedToHandlerInterceptor;
import org.springframework.social.hybridcanvas.UserInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import org.springframework.web.servlet.view.tiles2.TilesConfigurer;
import org.springframework.web.servlet.view.tiles2.TilesView;
import org.springframework.web.servlet.view.tiles2.TilesViewResolver;

import javax.inject.Inject;

@Configuration
@EnableWebMvc
public class WebMvcConfig extends WebMvcConfigurerAdapter {

	@Inject
	private ConnectionRepository connectionRepository;

	@Inject
	private UsersConnectionRepository usersConnectionRepository;

	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new UserInterceptor(usersConnectionRepository));
		registry.addInterceptor(new ConnectedToHandlerInterceptor(connectionRepository));
	}

	@Bean
	public TilesViewResolver configureTilesViewResolver() {
		return new TilesViewResolver();
	}

	@Bean
	public TilesConfigurer configureTilesConfigurer() {
		TilesConfigurer configurer = new TilesConfigurer();
		configurer.setDefinitions(new String[] {"/WEB-INF/layouts/tiles.xml", "/WEB-INF/views/**/tiles.xml"});
		return configurer;
	}

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}


	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/").addResourceLocations("/resources/**");
	 }
}
