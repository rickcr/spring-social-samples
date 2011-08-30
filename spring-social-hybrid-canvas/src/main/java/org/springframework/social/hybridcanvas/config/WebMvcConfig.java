package org.springframework.social.hybridcanvas.config;

import org.springframework.social.hybridcanvas.ConnectedToHandlerInterceptor;
import org.springframework.social.hybridcanvas.UserInterceptor;
import com.restfb.types.Checkin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import org.springframework.web.servlet.view.tiles2.TilesConfigurer;
import org.springframework.web.servlet.view.tiles2.TilesView;

import javax.inject.Inject;

@Configuration
@EnableWebMvc
public class WebMvcConfig extends WebMvcConfigurerAdapter {

	@Inject
	private ConnectionRepository connectionRepository;

	@Inject
	private UsersConnectionRepository usersConnectionRepository;

	public void configureInterceptors(InterceptorConfigurer configurer) {
		configurer.addInterceptor(new UserInterceptor(usersConnectionRepository));
		configurer.addInterceptor(new ConnectedToHandlerInterceptor(connectionRepository));
	}

	public void configureViewControllers(ViewControllerConfigurer configurer) {
		/* In Place of <mvc:view-controller path="/" view-name="welcome"/> */
		//not using right now configurer.mapViewName("/","/index.jsp");
	}

	@Bean
	public ViewResolver viewResolver() {
		UrlBasedViewResolver viewResolver = new UrlBasedViewResolver();
		viewResolver.setViewClass(TilesView.class);
		return viewResolver;
	}

	@Bean
	public TilesConfigurer tilesConfigurer() {
		TilesConfigurer configurer = new TilesConfigurer();
		configurer.setDefinitions(new String[] {
			"/WEB-INF/layouts/tiles.xml",
			"/WEB-INF/views/**/tiles.xml"
		});
		configurer.setCheckRefresh(true);
		return configurer;
	}

	public void configureResourceHandling(ResourceConfigurer resourceConfigurer) {
		resourceConfigurer.addPathMapping("/resources/**");
		resourceConfigurer.addResourceLocation("/resources/");
	}
}
