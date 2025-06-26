package dev.eliezer.lojaonline.modules.product.controller;

import dev.eliezer.lojaonline.modules.product.dtos.*;
import dev.eliezer.lojaonline.modules.product.entities.ProductEntity;
import dev.eliezer.lojaonline.modules.product.useCases.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@Tag(name = "Products", description = "RESTful API for managing products.")
public class ProductRestController {
    @Autowired
    private CreateProductUseCase createProductUseCase;

    @Autowired
    private GetProductUseCase getProductUseCase;

    @Autowired
    private InactivateProductUseCase inactivateProductUseCase;

    @Autowired
    private UpdateProductUseCase updateProductUseCase;

    @Autowired
    private GetCategoryWithProductsUseCase getCategoryWithProductsUseCase;


    @PostMapping
    @Operation(summary = "Create a new product", description = "Create a new product and return the created product data")
    @ApiResponse(responseCode = "201", description = "Product created successfully", content = {
            @Content(schema = @Schema(implementation = ProductEntity.class))})
    @ApiResponse(responseCode = "422", description = "Invalid product data provided", content = {
            @Content(schema = @Schema(implementation = Object.class))})
    public ResponseEntity<ProductResponseDTO> create(@Valid @RequestBody ProductCreateRequestDTO product) {
        ProductResponseDTO productSaved = createProductUseCase.execute(product);
        return ResponseEntity.ok().body(productSaved);
    }

    @GetMapping
    @Operation(summary = "Get all products", description = "Retrieve a list of all products")
    @ApiResponse(responseCode = "200", description = "Operation sucessfully", content = {
            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ProductEntity.class)))})
    @ApiResponse(responseCode = "422", description = "Invalid product data provided", content = {
            @Content(mediaType = "text/plain", schema = @Schema(example = "Resource id not found."))})
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<List<ProductResponseDTO>> index(@RequestParam(value = "id", defaultValue = "0") Long productId,
                                                          @RequestParam(value = "name", defaultValue = "") String productName,
                                                          @RequestParam(value = "sku", defaultValue = "") String productSku,
                                                          @RequestParam(value = "type", defaultValue = "") String productType,
                                                          @RequestParam(value = "categoryId", defaultValue = "0") Long categoryId,
                                                          @RequestParam(value = "otherCategories", defaultValue = "false") Boolean otherCategories,

                                                          @RequestParam(value = "orderBy", defaultValue = "id") String fieldOrder,
                                                          @RequestParam(value = "sort", defaultValue = "asc") String sortDirection
                                                     ) {
        var result = getProductUseCase.execute(productId, productName, productSku, productType, categoryId, otherCategories, fieldOrder, sortDirection);
        return ResponseEntity.ok().body(result);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a product", description = "Update product specified by id and return product data")
    @ApiResponse(responseCode = "201", description = "Product updated successfully", content = {
            @Content(schema = @Schema(implementation = ProductEntity.class))})
    @ApiResponse(responseCode = "422", description = "Invalid product data provided", content = {
            @Content(schema = @Schema(implementation = Object.class))})
    public ResponseEntity<ProductResponseDTO> update(@Valid @PathVariable Long id, @RequestBody ProductUpdateRequestDTO product) {
        ProductResponseDTO productUpdated = updateProductUseCase.execute(id, product);
        return ResponseEntity.ok().body(productUpdated);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Inactivate a product by id", description = "Inactivate product specified by id")
    @ApiResponse(responseCode = "200", description = "Operation sucessfully", content = {
            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ProductEntity.class)))})
    @ApiResponse(responseCode = "422", description = "Invalid product data provided", content = {
            @Content(mediaType = "text/plain", schema = @Schema(example = "Resource id not found."))})
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<ProductEntity> inactivate(@PathVariable Long id) {
        var result = inactivateProductUseCase.execute(id);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/category")
    @Operation(summary = "Get all products grouped by categories", description = "Retrieve a list of all products grouped by categories")
    @ApiResponse(responseCode = "200", description = "Operation sucessfully", content = {
            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = CategoryResponseDTO.class)))})
    @ApiResponse(responseCode = "422", description = "Invalid category data provided", content = {
            @Content(mediaType = "text/plain", schema = @Schema(example = "Resource id not found."))})
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<List<CategoryWithProductsResponseDTO>> getProductGroupByCategories(
    ) {
        List<CategoryWithProductsResponseDTO>categoryWithProductsResponseDTOS = getCategoryWithProductsUseCase.execute();
        return ResponseEntity.ok().body(categoryWithProductsResponseDTOS);
    }

}
