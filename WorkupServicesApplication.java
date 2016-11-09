package com.workup.services;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.web.WebApplicationInitializer;

import com.workup.ThreadCancelarAtendimentosPendentes;
import com.workup.WorkupServicesApplication;

@Configuration
@ComponentScan
@EnableAutoConfiguration
@SpringBootApplication
@PropertySources({ @PropertySource("classpath:application.properties") })
public class WorkupServicesApplication extends SpringBootServletInitializer implements WebApplicationInitializer {

	public static void main(String[] args) {
		SpringApplication.run(WorkupServicesApplication.class, args);
		
		Thread t = new Thread(new ThreadCancelarAtendimentosPendentes());
		t.start();
	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
	return application.sources(WorkupServicesApplication.class);
	} 


}
