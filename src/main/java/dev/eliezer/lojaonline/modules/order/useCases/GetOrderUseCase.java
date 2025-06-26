package dev.eliezer.lojaonline.modules.order.useCases;

import dev.eliezer.lojaonline.modules.order.dtos.OrderInstallmentsResponseDTO;
import dev.eliezer.lojaonline.modules.order.dtos.OrderItemResponseDTO;
import dev.eliezer.lojaonline.modules.order.dtos.OrderResponseDTO;
import dev.eliezer.lojaonline.modules.order.entities.OrderEntity;
import dev.eliezer.lojaonline.modules.order.mappers.OrderMapper;
import dev.eliezer.lojaonline.modules.order.repositories.OrderInstallmentsRepository;
import dev.eliezer.lojaonline.modules.order.repositories.OrderItemRepository;
import dev.eliezer.lojaonline.modules.order.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GetOrderUseCase {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderInstallmentsRepository orderInstallmentsRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private OrderMapper orderMapper;

    private List<OrderEntity> orderEntityList;

    private List<OrderInstallmentsResponseDTO> orderInstallmentsList;


    public List<OrderResponseDTO> execute() {
        List<OrderResponseDTO> orderResponse = new ArrayList<>();
        orderRepository.findAll().forEach(order -> {
            List<OrderInstallmentsResponseDTO> orderInstallments = getOrderInstallments(order.getId());
            List<OrderItemResponseDTO> orderItem = getOrderItems(order.getId());
            orderResponse.add(orderMapper.toOrderResponseDTO(order, orderItem, orderInstallments));
        });

        return orderResponse;
    }

    private List<OrderInstallmentsResponseDTO> getOrderInstallments(Long orderId) {

        List<OrderInstallmentsResponseDTO> orderInstallments = new ArrayList<>();

        orderInstallmentsRepository.findAllByOrder_Id(orderId).forEach(order -> {
            orderInstallments.add(new OrderInstallmentsResponseDTO(order));

        });

        return orderInstallments;
    }

    private List<OrderItemResponseDTO> getOrderItems(Long orderId) {

        List<OrderItemResponseDTO> orderItemResponseDTOS = new ArrayList<>();

        orderItemRepository.findAllByOrder_Id(orderId).forEach(order -> {
            orderItemResponseDTOS.add(new OrderItemResponseDTO(order));

        });

        return orderItemResponseDTOS;
    }


}