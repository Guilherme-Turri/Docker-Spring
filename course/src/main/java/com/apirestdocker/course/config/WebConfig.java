package com.apirestdocker.course.config;

import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration // tells sb to read this class on load the project.
public class WebConfig implements WebMvcConfigurer {

@Value("${cors.originPatterns:default}")
	private String corsoriginPatterns ="";
	
@Override
public void addCorsMappings(CorsRegistry registry) {

	var allowedOrigins =corsoriginPatterns.split(",");
	registry.addMapping("/**")
//	.allowedMethods("GET", "POST","PUT"...)
	.allowedMethods("*")
	.allowedOrigins(allowedOrigins)
	.allowCredentials(true);
}


	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {

		configurer.favorParameter(true).parameterName("mediaType").ignoreAcceptHeader(true)
				.useRegisteredExtensionsOnly(false).defaultContentType(MediaType.APPLICATION_JSON)
				.mediaType("json", MediaType.APPLICATION_JSON).mediaType("xml", MediaType.APPLICATION_XML);
	}
}
