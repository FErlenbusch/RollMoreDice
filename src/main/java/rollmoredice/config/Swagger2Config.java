package rollmoredice.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configuration
@EnableSwagger2
public class Swagger2Config {
    
	@Bean
    public Docket api() { 
		return new Docket(DocumentationType.SWAGGER_2)
				.select()                                  
				.apis(RequestHandlerSelectors.basePackage("rollmoredice.controller"))  
				.paths(PathSelectors.any())
				.build()
				.apiInfo(apiInfo())
				.tags(  new Tag("Authorization Api", "Api to log in sign up users and login users"),
						new Tag("Character Sheet Api", "Api for Character Sheet Functions"),
						new Tag("Game Api", "Api for Game Functions"),
						new Tag("Item Api", "Api for Item Functions"),
						new Tag("Spell Api", "Api for Spell Functions"),
						new Tag("User Api", "Api for User Functions"));
    }
	
	private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Roll More Dice API").version("1.0").build();
    }
}