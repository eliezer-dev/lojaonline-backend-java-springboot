package dev.eliezer.lojaonline.modules.order.controller;

import dev.eliezer.lojaonline.exceptions.UnauthorizedAccessException;
import dev.eliezer.lojaonline.modules.order.dtos.CreateOrderDTO;
import dev.eliezer.lojaonline.modules.order.dtos.OrderCancellationDTO;
import dev.eliezer.lojaonline.modules.order.dtos.OrderResponseDTO;
import dev.eliezer.lojaonline.modules.order.dtos.UpdateOrderDTO;
import dev.eliezer.lojaonline.modules.order.entities.OrderEntity;
import dev.eliezer.lojaonline.modules.order.repositories.OrderRepository;
import dev.eliezer.lojaonline.modules.order.useCases.CancelOrderUseCase;
import dev.eliezer.lojaonline.modules.order.useCases.CreateOrderUseCase;
import dev.eliezer.lojaonline.modules.order.useCases.GetOrderUseCase;
import dev.eliezer.lojaonline.modules.order.useCases.UpdateOrderUseCase;
import dev.eliezer.lojaonline.modules.product.entities.ProductEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static dev.eliezer.lojaonline.utils.RequestUtils.*;

@RestController
@RequestMapping("/orders")
@Tag(name = "Orders", description = "RESTful API for managing orders.")
public class OrderRestController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CreateOrderUseCase createOrderUseCase;

    @Autowired
    private UpdateOrderUseCase updateOrderUseCase;

    @Autowired
    private GetOrderUseCase getOrderUseCase;

    @Autowired
    private CancelOrderUseCase cancelOrderUseCase;


    @GetMapping
    @Operation(summary = "Get all orders", description = "Retrieve a list of all orders")
    @ApiResponse(responseCode = "200", description = "Operation successfully", content = {
            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = OrderResponseDTO.class)))})
    @ApiResponse(responseCode = "422", description = "Invalid order data provided", content = {
            @Content(mediaType = "text/plain", schema = @Schema(example = "Resource id not found."))})
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<List<OrderResponseDTO>> index(@RequestParam(value = "id", defaultValue = "0") Long productId,
                                                        @RequestParam(value = "name", defaultValue = "") String productName,
                                                        @RequestParam(value = "sku", defaultValue = "") String productSku,
                                                        @RequestParam(value = "type", defaultValue = "") String productType,
                                                        @RequestParam(value = "orderBy", defaultValue = "id") String fieldOrder,
                                                        @RequestParam(value = "sort", defaultValue = "asc") String sortDirection,
                                                        HttpServletRequest request
    ) {
        if (!isUserAdmin(request)) throw new UnauthorizedAccessException();
        var result = getOrderUseCase.execute();
        return ResponseEntity.ok().body(result);
    }



    @PostMapping
    @Operation(summary = "Create a new order", description = "Create a new order and return the created order data")
    @ApiResponse(responseCode = "201", description = "Order created successfully", content = {
            @Content(schema = @Schema(implementation = OrderResponseDTO.class))})
    @ApiResponse(responseCode = "422", description = "Invalid order data provided", content = {
            @Content(schema = @Schema(implementation = Object.class))})
    public ResponseEntity<OrderResponseDTO> create(@Valid @RequestBody CreateOrderDTO orderDTO, HttpServletRequest request) {

//        if (!isClientUser(request)) {
//            throw new UnauthorizedAccessException();
//        }

        OrderResponseDTO response = createOrderUseCase.execute(orderDTO);

        return ResponseEntity.ok().body(response);
    }


    @PutMapping("/{orderId}")
    @Operation(summary = "Update a new order", description = "Update a order and return the created order data")
    @ApiResponse(responseCode = "201", description = "Order updated successfully", content = {
            @Content(schema = @Schema(implementation = OrderResponseDTO.class))})
    @ApiResponse(responseCode = "422", description = "Invalid order data provided", content = {
            @Content(schema = @Schema(implementation = Object.class))})
    public ResponseEntity<OrderResponseDTO> update(@Valid @PathVariable Long orderId, @RequestBody UpdateOrderDTO dataReq, HttpServletRequest request) {

        if (!isUserAdmin(request)) {
            throw new UnauthorizedAccessException();
        }

        OrderResponseDTO orderUpdated =  updateOrderUseCase.execute(dataReq, orderId);
        return ResponseEntity.ok().body(orderUpdated);
    }

    @DeleteMapping("/{orderId}")
    @Operation(summary = "Cancel a order by id", description = "Cancel a order specified by id")
    @ApiResponse(responseCode = "201", description = "Order canceled successfully", content = {
            @Content(schema = @Schema(example = "The order was successfully canceled."))})
    public ResponseEntity<String> cancel (@Valid @PathVariable Long orderId, @RequestBody OrderCancellationDTO dataReq, HttpServletRequest request) {

        if (!isUserAdmin(request)) {
            throw new UnauthorizedAccessException();
        }

        Long userId = extractUserIdOfRequest(request);

        cancelOrderUseCase.execute(orderId, userId, dataReq);
        return ResponseEntity.ok().body("The order was successfully canceled.");
    }




}
