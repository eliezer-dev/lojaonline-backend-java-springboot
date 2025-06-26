package dev.eliezer.lojaonline.modules.order.useCases;

import dev.eliezer.lojaonline.exceptions.NotFoundException;
import dev.eliezer.lojaonline.modules.order.dtos.*;
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
public class UpdateOrderUseCase {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private OrderInstallmentsRepository orderInstallmentsRepository;

    @Autowired
    private OrderMapper orderMapper;

    private OrderEntity orderTarget;

    private List<OrderItemResponseDTO> orderItemResponseDTOList;

    private List<OrderInstallmentsResponseDTO> orderInstallmentsResponseDTOList;


    public OrderResponseDTO execute (UpdateOrderDTO dataReq, Long orderId) {

        this.orderTarget =  orderRepository.findById(orderId).orElseThrow(() -> new NotFoundException(orderId));

        if (dataReq.getInvoiceNumber() != null && !dataReq.getInvoiceNumber().isEmpty()) {
            this.orderTarget.setInvoiceNumber(dataReq.getInvoiceNumber());
        }

        getOrderItems();

        getOrderInstallments ();

        OrderEntity orderUpdated = orderRepository.save(orderTarget);

        return  orderMapper.toOrderResponseDTO(orderUpdated, orderItemResponseDTOList, orderInstallmentsResponseDTOList);

    }


    private void getOrderItems() {
        this.orderItemResponseDTOList = new ArrayList<>();


        orderItemRepository.findAllByOrder_Id(this.orderTarget.getId()).forEach(orderItem -> {
                    this.orderItemResponseDTOList.add(new OrderItemResponseDTO(orderItem));
                }

        );
    }

    private void getOrderInstallments () {
        this.orderInstallmentsResponseDTOList = new ArrayList<>();

        orderInstallmentsRepository.findAllByOrder_Id(this.orderTarget.getId()).forEach(orderInstallment -> {
                    this.orderInstallmentsResponseDTOList.add(new OrderInstallmentsResponseDTO(orderInstallment));
                }

        );
    }

}
