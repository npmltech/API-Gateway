package br.com.npml.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }

    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
            .route(p -> p
                .path("/api/aluno/**")
                .filters(f -> f.stripPrefix(2)
                .retry(2)
                .addRequestHeader("Message", "SERVICO_ALUNO")
                .addResponseHeader("Allow-Origins", "*"))
                .uri("lb://ALUNO-SERVICE/")
                // .uri("http://localhost:8058")
            )
            .route(p -> p
                .path("/api/cliente/**")
                .filters(f -> f.stripPrefix(2)
                .retry(2)
                .addRequestHeader("Message", "SERVICO_CLIENTE")
                .addResponseHeader("Allow-Origins", "*"))
                .uri("lb://CLIENTE-SERVICE/")
            )
            .build();
        //
    }
}
