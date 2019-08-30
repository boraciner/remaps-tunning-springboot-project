package mg.remaps.service.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("index");
		registry.addViewController("/login");
		registry.addViewController("/contact-1");
		registry.addViewController("/contact-2");
		registry.addViewController("/page-login");
		registry.addViewController("/contact-1");
		registry.addViewController("/contact-2");
		registry.addViewController("/page-pricing-standard");
		registry.addViewController("/page-about-us");
	}
	
	 
}