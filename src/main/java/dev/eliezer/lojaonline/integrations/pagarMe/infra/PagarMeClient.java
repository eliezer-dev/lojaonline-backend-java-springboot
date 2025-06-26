package dev.eliezer.lojaonline.integrations.pagarMe.infra;

import dev.eliezer.lojaonline.providers.ApiClientProvider;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class PagarMeClient {
    private static final String PAGAR_ME_BASE_URL = "https://api.pagar.me/core/v5";

    private static final String PAGAR_ME_TOKEN = "c2tfdGVzdF83ZTQ2ZjgzNTU3NGY0MzNmOGE4NjExN2JmZDZkN2E1MTo=";

    private final ApiClientProvider apiClientProvider;

    public PagarMeClient(ApiClientProvider apiClientProvider) {
        this.apiClientProvider = apiClientProvider;
    }


//    public String getResource(String baseUrl, String uri, String token) {
//        WebClient webClient = apiClientProvider.createClient(PagarMeBaseUrl);
//
//        return webClient.post()
//                .uri(uri)
//                .header("Authorization", "Basic " + PagarMeToken)
//                .retrieve()
//                .bodyToMono(String.class)
//                .block();
//    }

    public ApiResponse postResource(String uri, Object requestBody) {

        WebClient webClient = apiClientProvider.createClient(PAGAR_ME_BASE_URL);

        try {
            return webClient.post()
                    .uri(uri)
                    .header("Authorization", "Basic " + PAGAR_ME_TOKEN)
                    .bodyValue(requestBody)
                    .retrieve()
                    .onStatus(
                            status -> status.isError(),
                            response -> response.bodyToMono(String.class)
                                    .map(body -> new RuntimeException("Erro na requisição: " + body))
                    )
                    .bodyToMono(String.class)
                    .map(body -> new ApiResponse(true, body))
                    .block();
        } catch (Exception e) {
            return new ApiResponse(false, "Erro ao realizar a requisição: " + e.getMessage());
        }
    }

    public class ApiResponse {
        private boolean success;
        private String message;

        public ApiResponse(boolean success, String message) {
            this.success = success;
            this.message = message;
        }

        public boolean isSuccess() {
            return success;
        }

        public String getMessage() {
            return message;
        }
    }



}
