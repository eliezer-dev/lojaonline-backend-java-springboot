package dev.eliezer.lojaonline.modules.compositeProduct.controller;

import dev.eliezer.lojaonline.exceptions.UnauthorizedAccessException;
import dev.eliezer.lojaonline.modules.compositeProduct.dtos.CompositeProductUpdateDTO;
import dev.eliezer.lojaonline.modules.compositeProduct.dtos.ProductItemToCompositeProductDTO;
import dev.eliezer.lojaonline.modules.compositeProduct.entities.CompositeProductEntity;
import dev.eliezer.lojaonline.modules.compositeProduct.useCases.*;
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

import static dev.eliezer.lojaonline.utils.RequestUtils.isUserAdmin;


@RestController
@RequestMapping("/products/composite")
@Tag(name = "Composite Products", description = "RESTful API for managing composite product.")
public class CompositeProductRestController {

    @Autowired
    private UpdateCompositeProductUseCase updateCompositeProductUseCase;

    @Autowired
    private InsertItemsCompositeProductUseCase insertItemsCompositeProductUseCase;
    
    @Autowired
    private RemoveItemsCompositeProductUseCase removeItemsCompositeProductUseCase;


    @PutMapping("/{compositeProductId}")
    @Operation(summary = "Update a composite product", description = "Update composite product specified by id and return product data")
    @ApiResponse(responseCode = "201", description = "composite product updated successfully", content = {
            @Content(schema = @Schema(implementation = CompositeProductEntity.class))})
    @ApiResponse(responseCode = "422", description = "Invalid composite product data provided", content = {
            @Content(schema = @Schema(implementation = Object.class))})
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<CompositeProductEntity> updateCompositeProduct(@Valid @RequestBody CompositeProductUpdateDTO compositeProductUpdateDTO, HttpServletRequest request, @PathVariable Long compositeProductId) {

        if (!isUserAdmin(request)) {
            throw  new UnauthorizedAccessException();
        }

        CompositeProductEntity compositeProductUpdated = updateCompositeProductUseCase.execute(compositeProductId, compositeProductUpdateDTO);

        return ResponseEntity.ok().body(compositeProductUpdated);
    }


    @PostMapping("/items/{id}")
    @Operation(summary = "Create a new composite product", description = "Create a new composite product and return the created bundled product data")
    @ApiResponse(responseCode = "201", description = "Bundled product created successfully", content = {
            @Content(schema = @Schema(implementation = ProductEntity.class))})
    @ApiResponse(responseCode = "422", description = "Invalid bundled product data provided", content = {
            @Content(schema = @Schema(implementation = Object.class))})
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<ProductEntity> createBundledProductItem (@Valid @RequestBody ProductItemToCompositeProductDTO compositeProductItem, @PathVariable Long id, HttpServletRequest request) {

        if (!isUserAdmin(request)) {
            throw  new UnauthorizedAccessException();
        }

        ProductEntity compositeProductSaved = insertItemsCompositeProductUseCase.execute(compositeProductItem, id);

        return ResponseEntity.ok().body(compositeProductSaved);
    }

    @DeleteMapping("{compositeProductId}/item/{compositeItemId}")
    @Operation(summary = "Remove Item of Composite Product", description = "Remove composite item of composite producot specified by id")
    @ApiResponse(responseCode = "200", description = "Operation sucessfully", content = {
            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ProductEntity.class)))})
    @ApiResponse(responseCode = "422", description = "Invalid product data provided", content = {
            @Content(mediaType = "text/plain", schema = @Schema(example = "Resource id not found."))})
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<String> remove(@PathVariable Long compositeProductId, @PathVariable Long compositeItemId, HttpServletRequest request) {
        if (!isUserAdmin(request)){
            throw  new UnauthorizedAccessException();
        }

        var result = removeItemsCompositeProductUseCase.execute(compositeProductId, compositeItemId);
        return ResponseEntity.ok().body(result);
    }


}
