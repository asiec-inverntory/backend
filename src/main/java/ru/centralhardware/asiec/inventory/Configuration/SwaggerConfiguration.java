package ru.centralhardware.asiec.inventory.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

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
                useDefaultResponseMessages(false);
    }

    /**
     * get data about api endpoints
     * @return api info
     */
    private ApiInfo apiEndPointsInfo() {
        Contact contact = new Contact(
                "Федечкин А.Б и Павлов ",
                "-",
                "alex@centralhardware.ru");
        return new ApiInfoBuilder().title("КГБПОУ АПЭК система учета перемещения материальных средств")
                .description("")
                .version(String.valueOf(config.apiVersion)).contact(contact)
                .build();
    }

}
