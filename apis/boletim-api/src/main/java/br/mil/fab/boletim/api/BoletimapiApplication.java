package br.mil.fab.boletim.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@SpringBootApplication
@EnableSwagger2
public class BoletimapiApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(BoletimapiApplication.class, args);
	}

	@Bean
	public Docket boletimApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors
				.basePackage("br.mil.fab.boletim.api.controller"))
				.paths(PathSelectors.any())
				.build()
				.tags(new Tag("BoletimApi", "Api de controle de boletins"))
				.apiInfo(info());

	}
	
	@Bean
	  ApiInfo info() {
		return new  ApiInfo("BoletimApi",
			       "Api de controle de itens de boletim",
			       "v1",
			       "",
			       new Contact("Força Aérea Brasileira",
			    	      "http://www.ccarj.fab.mil",
			    	       ""),
			       "",
			       "");
	  }
	
	
	
	
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(BoletimapiApplication.class);
	}

	
	
	
}
