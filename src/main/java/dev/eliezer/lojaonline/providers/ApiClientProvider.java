package dev.eliezer.lojaonline.providers;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class ApiClientProvider {

    private final WebClient.Builder webClientBuilder;

    public ApiClientProvider(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    public WebClient createClient(String baseUrl) {
        return webClientBuilder.baseUrl(baseUrl).build();
    }


}
