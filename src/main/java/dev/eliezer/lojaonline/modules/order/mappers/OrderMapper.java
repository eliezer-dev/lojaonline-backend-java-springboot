package dev.eliezer.lojaonline.modules.order.mappers;

import dev.eliezer.lojaonline.integrations.pagarMe.Entity.PagarMeInvoicesEntity;
import dev.eliezer.lojaonline.integrations.pagarMe.dtos.PagarMeResponseDTO;
import dev.eliezer.lojaonline.modules.order.dtos.OrderInstallmentsResponseDTO;
import dev.eliezer.lojaonline.modules.order.dtos.OrderItemResponseDTO;
import dev.eliezer.lojaonline.modules.order.dtos.OrderResponseDTO;
import dev.eliezer.lojaonline.modules.order.entities.OrderEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderMapper {
    
    public OrderResponseDTO toOrderResponseDTO(OrderEntity orderEntity, List<OrderItemResponseDTO> orderItemResponseDTOList,
                                               List<OrderInstallmentsResponseDTO> orderInstallmentsResponseDTOList) {
       
        OrderResponseDTO orderResponseDTO = new OrderResponseDTO();
        
        orderResponseDTO.setId(orderEntity.getId());
        
        if (orderEntity.getUser() != null && orderEntity.getUser().getId() != null ) {
            orderResponseDTO.setUserId(orderEntity.getUser().getId()); 
        }

        orderResponseDTO.setOrderItems(orderItemResponseDTOList);

        orderResponseDTO.setInvoiceNumber(orderEntity.getInvoiceNumber());

        orderResponseDTO.setCreateAt(orderEntity.getCreateAt());

        orderResponseDTO.setUpdateAt(orderEntity.getUpdateAt());

        orderResponseDTO.setTotalValue(orderEntity.getTotalValue());

        orderResponseDTO.setCanceled(orderEntity.getCanceled());

        orderResponseDTO.setCanceledAt(orderEntity.getCanceledAt());

        orderResponseDTO.setCanceledBy(orderEntity.getCanceledBy());

        orderResponseDTO.setCancellationReason(orderEntity.getCancellationReason());

        orderResponseDTO.setOrderInstallments(orderInstallmentsResponseDTOList);

        return orderResponseDTO;
        
        
    }

    public OrderResponseDTO toOrderResponseDTO (OrderEntity orderEntity, List<OrderItemResponseDTO> orderItemResponseDTOList,
                                                List<OrderInstallmentsResponseDTO> orderInstallmentsResponseDTOList, PagarMeResponseDTO pagarMeResponseDTO) {

        OrderResponseDTO orderResponseDTO = new OrderResponseDTO();
        orderResponseDTO = toOrderResponseDTO(orderEntity, orderItemResponseDTOList, orderInstallmentsResponseDTOList);
        orderResponseDTO.setPagarMe(pagarMeResponseDTO);

        return orderResponseDTO;
    }

}
