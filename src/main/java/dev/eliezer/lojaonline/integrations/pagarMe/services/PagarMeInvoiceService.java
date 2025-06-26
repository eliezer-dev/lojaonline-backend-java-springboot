package dev.eliezer.lojaonline.integrations.pagarMe.services;

import dev.eliezer.lojaonline.integrations.pagarMe.infra.PagarMeClient;
import dev.eliezer.lojaonline.integrations.pagarMe.infra.PagarMeClient.ApiResponse;
import dev.eliezer.lojaonline.integrations.pagarMe.payloads.FaturaPagarMeRequestPayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PagarMeInvoiceService {

    @Autowired
    private PagarMeClient pagarMeClient;

    public ApiResponse createInvoice (FaturaPagarMeRequestPayload faturaPagarMeRequestPayload) {

        final String URI = "/orders";

        ApiResponse response = pagarMeClient.postResource(URI, faturaPagarMeRequestPayload);

        return response;


    }


}
