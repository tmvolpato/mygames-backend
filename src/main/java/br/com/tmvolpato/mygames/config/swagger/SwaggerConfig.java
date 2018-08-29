package br.com.tmvolpato.mygames.config.swagger;

import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.LocalDate;
import java.util.Collections;

/**
 * Configuration swagger2 of application.
 *
 * @author Thiago Michel Volpato
 * @since 2018
 * @version 1.0.0
 */

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private static final String PATH_PACKAGE = "br.com.tmvolpato.mygames.resource";

    @Bean
    public Docket documentationAPI() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select().apis(RequestHandlerSelectors.basePackage(PATH_PACKAGE))
                .paths(PathSelectors.any())
                .build()
                .globalResponseMessage(RequestMethod.POST,
                        Lists.newArrayList(
                                new ResponseMessageBuilder().code(201).message("Saved with sucessfully").build(),
                                new ResponseMessageBuilder().code(400).message("Information sending by parameter is incorrect").build(),
                                new ResponseMessageBuilder().code(401).message("You are not authorized to the resource").build(),
                                new ResponseMessageBuilder().code(403).message("The resource you where trying to reach is forbidden").build(),
                                new ResponseMessageBuilder().code(404).message("The resource you were trying to reach is not found").build()))

                .globalResponseMessage(RequestMethod.GET,
                        Lists.newArrayList(new ResponseMessageBuilder().code(200).message("Retrieved with successfully ").build(),
                                new ResponseMessageBuilder().code(400).message("Information sending by parameter is incorrect").build(),
                                new ResponseMessageBuilder().code(401).message("You are not authorized to the resource").build(),
                                new ResponseMessageBuilder().code(403).message("The resource you where trying to reach is forbidden").build(),
                                new ResponseMessageBuilder().code(404).message("The resource you were trying to reach is not found").build()))

                .globalResponseMessage(RequestMethod.PUT,
                        Lists.newArrayList(new ResponseMessageBuilder().code(200).message("Updated with sucessfully").build(),
                                new ResponseMessageBuilder().code(400).message("Information sending by parameter is incorrect").build(),
                                new ResponseMessageBuilder().code(401).message("You are not authorized to the resource").build(),
                                new ResponseMessageBuilder().code(403).message("The resource you where trying to reach is forbidden").build(),
                                new ResponseMessageBuilder().code(404).message("The resource you were trying to reach is not found").build()))

                .globalResponseMessage(RequestMethod.DELETE,
                        Lists.newArrayList(new ResponseMessageBuilder().code(204).message("No Content").build(),
                                new ResponseMessageBuilder().code(400).message("Information sending by parameter is incorrect").build(),
                                new ResponseMessageBuilder().code(401).message("You are not authorized to the resource").build(),
                                new ResponseMessageBuilder().code(403).message("The resource you where trying to reach is forbidden").build(),
                                new ResponseMessageBuilder().code(404).message("The resource you were trying to reach is not found").build()))

                .directModelSubstitute(LocalDate.class, String.class)
                .genericModelSubstitutes(ResponseEntity.class)
                .apiInfo(this.metaData()).useDefaultResponseMessages(false);
    }

    /**
     * Complement the information.
     *
     * @return
     */
    private ApiInfo metaData() {
        return new ApiInfo(
                "API MyGames",
                "Managment your games",
                "1.0.0",
                "Terms of Service",
                new Contact(
                        "Thiago Michel Volpato",
                        "https://github.com/tmvolpato/mygames-backend",
                        "tmvolpato@gmail.com"),
                "apache license version 2.0", "", Collections.emptyList());
    }
}
