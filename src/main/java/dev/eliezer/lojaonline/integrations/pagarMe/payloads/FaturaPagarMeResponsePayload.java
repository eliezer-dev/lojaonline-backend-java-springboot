package dev.eliezer.lojaonline.integrations.pagarMe.payloads;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class FaturaPagarMeResponsePayload {
    @JsonProperty("id")
    String idFatura;


    @JsonProperty("created_at")
    LocalDateTime createdAt;


    @JsonProperty("checkouts")
    List<CheckoutsPayload> checkouts;


    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CheckoutsPayload {

        @JsonProperty("id")
        String idCheckout;

        @JsonProperty("payment_url")
        String paymentUrl;

    }

}
