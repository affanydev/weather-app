package com.labluetech.weather.config;

import java.util.List;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.annotations.OpenAPI30;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class OpenAPIConfig {


    private String devUrl="http://localhost:8528";

    @Bean
    public OpenAPI myOpenAPI() {
        Server devServer = new Server();
        devServer.setUrl(devUrl);
        devServer.setDescription("Server URL in Development environment");


        Contact contact = new Contact();
        contact.setEmail("affany.abdel.ing@gmail.com");
        contact.setName("Abdelmounaim AFFANY");
        contact.setUrl("https://www.aaffany.com");

        License mitLicense = new License().name("MIT License").url("https://choosealicense.com/licenses/mit/");

        Info info = new Info()
                .title("Config service API")
                .version("1.0")
                .contact(contact)
                .description("This API exposes endpoints to manage weather data.").termsOfService("https://google.fr")
                .license(mitLicense);

        return new OpenAPI().info(info).servers(List.of(devServer));
    }
}