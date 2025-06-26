package dev.eliezer.lojaonline.integrations.pagarMe.mappers;

import dev.eliezer.lojaonline.integrations.pagarMe.Entity.PagarMeInvoicesEntity;
import dev.eliezer.lojaonline.integrations.pagarMe.dtos.PagarMeResponseDTO;
import dev.eliezer.lojaonline.integrations.pagarMe.payloads.FaturaPagarMeRequestPayload;
import dev.eliezer.lojaonline.integrations.pagarMe.payloads.FaturaPagarMeRequestPayload.Item;
import dev.eliezer.lojaonline.integrations.pagarMe.payloads.FaturaPagarMeResponsePayload;
import dev.eliezer.lojaonline.modules.client.entities.ClientEntity;
import dev.eliezer.lojaonline.modules.order.dtos.CreateOrderDTO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class FaturaPagarMeRequestMapper {

    public static FaturaPagarMeRequestPayload toFaturaPagarMeRequestPayload(CreateOrderDTO createOrderDTO, ClientEntity client) {
        FaturaPagarMeRequestPayload faturaPagarMeRequestPayload = new FaturaPagarMeRequestPayload();

        List<Item> items = new ArrayList<>();

        Integer pagarMeAmmount = createOrderDTO.getTotalValue().multiply(BigDecimal.valueOf(100)).intValue();
        createOrderDTO.getOrderItems().forEach(orderItem -> {
            Item item = new Item();
            item.setAmount(orderItem.getPrice().multiply(BigDecimal.valueOf(100)).intValue());
            item.setQuantity(orderItem.getQuantity().intValue());
            item.setCode(orderItem.getProductId().toString());
            item.setDescription(orderItem.getName());
            items.add(item);
        });
        faturaPagarMeRequestPayload.setItems(items);

        faturaPagarMeRequestPayload.getCustomer().setName(client.getName());
        faturaPagarMeRequestPayload.getCustomer().setEmail(client.getEmail());
        faturaPagarMeRequestPayload.getCustomer().setDocument(client.getDocument());
        faturaPagarMeRequestPayload.getCustomer().getPhones().getMobilePhone().setCountryCode(client.getPhones()
                .getFirst().getCountryCode().replace("+", ""));
        faturaPagarMeRequestPayload.getCustomer().getPhones().getMobilePhone().setAreaCode(client.getPhones().getFirst().getAreaCode());
        faturaPagarMeRequestPayload.getCustomer().getPhones().getMobilePhone().setNumber(client.getPhones().getFirst().getNumber());

        faturaPagarMeRequestPayload.getShipping().setRecipientName(client.getName());
        faturaPagarMeRequestPayload.getShipping()
                .setRecipientPhone(client.getPhones().getFirst().getCountryCode().replace("+", "") +
                client.getPhones().getFirst().getAreaCode() +
                client.getPhones().getFirst().getNumber());
        faturaPagarMeRequestPayload.getShipping().getAddress().setCountry(client.getAddress().getCountry());
        faturaPagarMeRequestPayload.getShipping().getAddress().setCity(client.getAddress().getCity());
        faturaPagarMeRequestPayload.getShipping().getAddress().setLine1(client.getAddress().getStreet());
        faturaPagarMeRequestPayload.getShipping().getAddress().setZipCode(client.getAddress().getZipCode());
        faturaPagarMeRequestPayload.getShipping().getAddress().setState(client.getAddress().getState());

        return faturaPagarMeRequestPayload;
    }


    public static PagarMeInvoicesEntity toPagarMeInvoicesEntity(FaturaPagarMeResponsePayload faturaPagarMeResponsePayload) {
       PagarMeInvoicesEntity pagarMeInvoice = new PagarMeInvoicesEntity();

        pagarMeInvoice.setIdFatura(faturaPagarMeResponsePayload.getIdFatura());
        pagarMeInvoice.setCreatedAt(faturaPagarMeResponsePayload.getCreatedAt());
        pagarMeInvoice.setPagarMeUrl(faturaPagarMeResponsePayload.getCheckouts().getFirst().getPaymentUrl());

        return pagarMeInvoice;
    }

    public static PagarMeResponseDTO toPagarMeResponseDTO (PagarMeInvoicesEntity pagarMeInvoice) {
        PagarMeResponseDTO pagarMeResponseDTO = new PagarMeResponseDTO();
        pagarMeResponseDTO.setIdFatura(pagarMeInvoice.getIdFatura());
        pagarMeResponseDTO.setUrl(pagarMeInvoice.getPagarMeUrl());

        return pagarMeResponseDTO;
    }

}
