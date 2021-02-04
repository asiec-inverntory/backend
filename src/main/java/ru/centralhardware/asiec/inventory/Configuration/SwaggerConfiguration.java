package ru.centralhardware.asiec.inventory.Configuration;

import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String DEFAULT_INCLUDE_PATTERN = "*";

    private final Config config;

    public SwaggerConfiguration(Config config) {
        this.config = config;
    }

    /**
     * init swagger documentation
     * @return data about rest api
     */
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).
                select().
                apis(RequestHandlerSelectors.basePackage("ru.centralhardware.asiec.inventory.web")).
                paths(PathSelectors.any()).
                build().
                apiInfo(apiEndPointsInfo()).
                useDefaultResponseMessages(false).
                securityContexts(Lists.newArrayList(securityContext())).
                securitySchemes(Lists.newArrayList(apiKey()));
    }

    /**
     * get data about api endpoints
     * @return api info
     */
    private ApiInfo apiEndPointsInfo() {
        Contact contact = new Contact(
                "Федечкин А.Б и Павлов ",
                "-",
                "-");
        return new ApiInfoBuilder().title("КГБПОУ АПЭК система учета перемещения материальных средств")
                .description("")
                .version(String.valueOf(config.apiVersion)).contact(contact)
                .build();
    }

    /**
     * get api key
     * @return api key
     */
    private ApiKey apiKey() {
        return new ApiKey("JWT", AUTHORIZATION_HEADER, "header");
    }

    /**
     * get security context
     * @return security context
     */
    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.regex(DEFAULT_INCLUDE_PATTERN))
                .build();
    }

    /**
     * get default authentication method
     * @return  authentication method
     */
    List<SecurityReference> defaultAuth() {
        var authorizationScope
                = new AuthorizationScope("global", "accessEverything");
        var authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Lists.newArrayList(
                new SecurityReference("JWT", authorizationScopes));
    }

}
