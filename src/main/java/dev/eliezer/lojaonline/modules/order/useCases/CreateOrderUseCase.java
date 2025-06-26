package dev.eliezer.lojaonline.modules.order.useCases;

import dev.eliezer.lojaonline.exceptions.BusinessException;
import dev.eliezer.lojaonline.exceptions.NotFoundException;
import dev.eliezer.lojaonline.integrations.pagarMe.Entity.PagarMeInvoicesEntity;
import dev.eliezer.lojaonline.integrations.pagarMe.dtos.PagarMeResponseDTO;
import dev.eliezer.lojaonline.integrations.pagarMe.infra.PagarMeClient.ApiResponse;
import dev.eliezer.lojaonline.integrations.pagarMe.payloads.FaturaPagarMeResponsePayload;
import dev.eliezer.lojaonline.integrations.pagarMe.repositories.PagarMeInvoicesRepository;
import dev.eliezer.lojaonline.integrations.pagarMe.services.PagarMeInvoiceService;
import dev.eliezer.lojaonline.integrations.pagarMe.mappers.FaturaPagarMeRequestMapper;
import dev.eliezer.lojaonline.modules.client.entities.ClientEntity;
import dev.eliezer.lojaonline.modules.client.repositories.ClientRepository;
import dev.eliezer.lojaonline.modules.order.dtos.*;
import dev.eliezer.lojaonline.modules.order.entities.*;
import dev.eliezer.lojaonline.integrations.pagarMe.payloads.FaturaPagarMeRequestPayload;
import dev.eliezer.lojaonline.modules.order.mappers.OrderMapper;
import dev.eliezer.lojaonline.modules.order.repositories.*;
import dev.eliezer.lojaonline.modules.product.entities.ProductEntity;
import dev.eliezer.lojaonline.modules.product.repositories.ProductRepository;
import dev.eliezer.lojaonline.modules.user.entities.UserEntity;
import dev.eliezer.lojaonline.modules.user.repositories.UserRepository;
import dev.eliezer.lojaonline.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class CreateOrderUseCase {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private OrderInstallmentsRepository orderInstallmentsRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PagarMeInvoicesRepository pagarMeInvoicesRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PagarMeInvoiceService pagarMeInvoiceService;

    @Autowired
    private ObjectUtils objectUtils;

    @Autowired
    private OrderMapper orderMapper;

    private UserEntity user;

    private ClientEntity client;

    private CreateOrderDTO request;

    private OrderEntity order;

    private List<OrderItemResponseDTO> orderItemResponseDTOList;

    private List<OrderInstallmentsResponseDTO> orderInstallmentsResponseDTOList;

    private FaturaPagarMeRequestPayload faturaPagarMeRequest;

    private FaturaPagarMeResponsePayload faturaPagarMeResponse = new FaturaPagarMeResponsePayload();

    private PagarMeInvoicesEntity pagarMeInvoice;

    private PagarMeResponseDTO pagarMeResponseDTO;




    public OrderResponseDTO execute (CreateOrderDTO dataReq) {

        this.request = dataReq;
        this.user = userRepository.findById(dataReq.getUserId()).orElseThrow(() -> new NotFoundException(request.getUserId()));
        this.client = clientRepository.findById(dataReq.getClientId()).orElseThrow(() -> new NotFoundException(request.getClientId()));

        orderValidator ();

        savePagarMeInvoice ();

        saveOrder ();

        saveOrderItem();

        saveOrderInstallments ();

        return orderMapper.toOrderResponseDTO(order,
                orderItemResponseDTOList, orderInstallmentsResponseDTOList, pagarMeResponseDTO);

    }

    public void saveOrder () {

        OrderEntity orderToSave = new OrderEntity();
        orderToSave.setClient(client);
        orderToSave.setInvoiceNumber(request.getInvoiceNumber());
        orderToSave.setTotalValue(request.getTotalValue());
        orderToSave.setUser(user);
        orderToSave.setPagarMeInvoice(pagarMeInvoice);

        order = orderRepository.save(orderToSave);


    }

    public void savePagarMeInvoice () {

        faturaPagarMeRequest = FaturaPagarMeRequestMapper.toFaturaPagarMeRequestPayload(request, client);

        ApiResponse retorno = pagarMeInvoiceService.createInvoice(faturaPagarMeRequest);

        faturaPagarMeResponse = objectUtils.parseObject(FaturaPagarMeResponsePayload.class, retorno.getMessage());

        pagarMeInvoice = pagarMeInvoicesRepository
                .save(FaturaPagarMeRequestMapper.toPagarMeInvoicesEntity(faturaPagarMeResponse));

        pagarMeResponseDTO = FaturaPagarMeRequestMapper.toPagarMeResponseDTO(pagarMeInvoice);

    }

    public void saveOrderItem() {
        orderItemResponseDTOList = new ArrayList<>();

        request.getOrderItems().forEach(item -> {

            ProductEntity productEntity = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new NotFoundException(item.getProductId()));

            OrderItemEntity orderItemSaved = orderItemRepository
                    .save(new OrderItemEntity(order, item, productEntity));

            orderItemResponseDTOList.add(new OrderItemResponseDTO(orderItemSaved));
        });
    }



    public void saveOrderInstallments () {
        orderInstallmentsResponseDTOList = new ArrayList<>();

        request.getOrderInstallments().forEach(createOrderInstallmentsDTO -> {

            OrderInstallmentsEntity orderInstallmentsSaved = orderInstallmentsRepository
                    .save(new OrderInstallmentsEntity(order, createOrderInstallmentsDTO));

            orderInstallmentsResponseDTOList.add(new OrderInstallmentsResponseDTO(orderInstallmentsSaved));
        });
    }


    public void orderValidator () {
        BigDecimal sumTotalValue = BigDecimal.valueOf(0.00);

        for (CreateOrderItemDTO orderItem : request.getOrderItems()) {
            BigDecimal totalValueItem = orderItem.getPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity()));
            sumTotalValue = sumTotalValue.add(totalValueItem);

        }

        if (sumTotalValue.compareTo(request.getTotalValue()) != 0) {
            throw new BusinessException("Total Value different from the sum of items");
        }
    }



}
