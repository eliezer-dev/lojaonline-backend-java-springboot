package dev.eliezer.lojaonline.modules.product.controller;

import dev.eliezer.lojaonline.modules.product.dtos.CategoryCreateRequestDTO;
import dev.eliezer.lojaonline.modules.product.dtos.CategoryResponseDTO;
import dev.eliezer.lojaonline.modules.product.dtos.CategoryUpdateRequestDTO;
import dev.eliezer.lojaonline.modules.product.useCases.CreateCategoryUseCase;
import dev.eliezer.lojaonline.modules.product.useCases.GetCategoryUseCase;
import dev.eliezer.lojaonline.modules.product.useCases.UpdateCategoryUseCase;
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
@RequestMapping("categories")
@Tag(name = "Products Categories", description = "RESTful API for managing products categories.")
public class CategoryProductsRestController {
    @Autowired
    private CreateCategoryUseCase createCategoryUseCase;

    @Autowired
    private UpdateCategoryUseCase updateCategoryUseCase;

    @Autowired
    private GetCategoryUseCase getCategoryUseCase;

    @Autowired
    private GetCategoryUseCase getCategoryWithProductsUseCase;


    @PostMapping
    @Operation(summary = "Create a new category", description = "Create a new category and return the created category data")
    @ApiResponse(responseCode = "201", description = "Category created successfully", content = {
            @Content(schema = @Schema(implementation = CategoryCreateRequestDTO.class))})
    @ApiResponse(responseCode = "422", description = "Invalid category data provided", content = {
            @Content(schema = @Schema(implementation = Object.class))})
    public ResponseEntity<CategoryResponseDTO> execute(@Valid @RequestBody CategoryCreateRequestDTO categoryCreateRequestDTO) {
        CategoryResponseDTO categorySaved = createCategoryUseCase.execute(categoryCreateRequestDTO);

        return ResponseEntity.ok().body(categorySaved);
    }


    @GetMapping
    @Operation(summary = "Get all categories", description = "Retrieve a list of all categories")
    @ApiResponse(responseCode = "200", description = "Operation sucessfully", content = {
            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = CategoryResponseDTO.class)))})
    @ApiResponse(responseCode = "422", description = "Invalid category data provided", content = {
            @Content(mediaType = "text/plain", schema = @Schema(example = "Resource id not found."))})
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<List<CategoryResponseDTO>> index(@RequestParam(value = "id", defaultValue = "0") Long categoryId,
                                                     @RequestParam(value = "name", defaultValue = "") String categoryName,
                                                     @RequestParam(value = "visibleHome", defaultValue = "false") Boolean visibleHome,
                                                     @RequestParam(value = "orderBy", defaultValue = "id") String fieldOrder,
                                                     @RequestParam(value = "sort", defaultValue = "asc") String sortDirection
    ) {
        var result = getCategoryUseCase.execute();
        return ResponseEntity.ok().body(result);
    }

    @PutMapping({"/{categoryId}"})
    @Operation(summary = "Create a new category", description = "Create a new category and return the created category data")
    @ApiResponse(responseCode = "201", description = "Category created successfully", content = {
            @Content(schema = @Schema(implementation = CategoryCreateRequestDTO.class))})
    @ApiResponse(responseCode = "422", description = "Invalid category data provided", content = {
            @Content(schema = @Schema(implementation = Object.class))})
    public ResponseEntity<CategoryResponseDTO> execute(@Valid @PathVariable Long categoryId, @RequestBody CategoryUpdateRequestDTO categoryUpdateRequestDTO) {
        CategoryResponseDTO categorySaved = updateCategoryUseCase.execute(categoryId, categoryUpdateRequestDTO);

        return ResponseEntity.ok().body(categorySaved);
    }



}
