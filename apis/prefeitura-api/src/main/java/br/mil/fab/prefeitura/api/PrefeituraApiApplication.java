package br.mil.fab.prefeitura.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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
public class PrefeituraApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(PrefeituraApiApplication.class, args);
	}
	
	@Bean
	public Docket prefeituraApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors
				.basePackage("br.mil.fab.prefeitura.api.controller"))
				.paths(PathSelectors.any())
				.build()
				.tags(new Tag("PrefeituraApi", "Api de controle de prefeitura"))
				.apiInfo(info());

	}
	
	@Bean
	  ApiInfo info() {
		return new  ApiInfo("PrefeituraApi",
			       "Api de controle de prefeituras",
			       "v1",
			       "",
			       new Contact("Força Aérea Brasileira",
			    	      "http://www.ccarj.fab.mil",
			    	       ""),
			       "",
			       "");
	  }
	
	
	
}
