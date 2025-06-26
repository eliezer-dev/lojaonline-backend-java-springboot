package dev.eliezer.lojaonline.modules.config.headerMenu.controller;

import dev.eliezer.lojaonline.modules.config.headerMenu.dtos.CategoryItemRequestDTO;
import dev.eliezer.lojaonline.modules.config.headerMenu.dtos.CategoryItemResponseDTO;
import dev.eliezer.lojaonline.modules.config.headerMenu.useCases.GetCategoriesUseCase;
import dev.eliezer.lojaonline.modules.config.headerMenu.useCases.InsertCategoryItemUseCase;
import dev.eliezer.lojaonline.modules.config.headerMenu.useCases.UpdateCategoryItemUseCase;
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
@RequestMapping("config")
@Tag(name = "Configurations of Store", description = "RESTful API for managing store configurations.")
public class HeaderMenuRestController {

    @Autowired
    InsertCategoryItemUseCase insertCategoryItemUseCase;

    @Autowired
    UpdateCategoryItemUseCase updateCategoryItemUseCase;

    @Autowired
    GetCategoriesUseCase getCategoriesUseCase;

    @GetMapping("/header/menu/categories")
    @Operation(summary = "Get all categories item", description = "Retrieve a list of all categories item of header menu")
    @ApiResponse(responseCode = "200", description = "Operation sucessfully", content = {
            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = CategoryItemResponseDTO.class)))})
    @ApiResponse(responseCode = "422", description = "Invalid category item data provided", content = {
            @Content(mediaType = "text/plain", schema = @Schema(example = "Resource id not found."))})
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<List<CategoryItemResponseDTO>> index() {
        var result = getCategoriesUseCase.execute();
        return ResponseEntity.ok().body(result);
    }


    
    @PostMapping("/header/menu/categories")
    @Operation(summary = "Insert a category item into header menu", description = "Insert a category item into header menu" +
            "and returns all registered categories")
    @ApiResponse(responseCode = "201", description = "Category item inserted successfully", content = {
            @Content(schema = @Schema(implementation = CategoryItemResponseDTO.class))})
    @ApiResponse(responseCode = "422", description = "Invalid category data provided", content = {
            @Content(schema = @Schema(implementation = Object.class))})
    public ResponseEntity<List<CategoryItemResponseDTO>> createCategoryItem(@Valid @RequestBody CategoryItemRequestDTO request) {

        List<CategoryItemResponseDTO> response  = insertCategoryItemUseCase.execute(request);

        return ResponseEntity.ok().body(response);
    }


    @PutMapping("/header/menu/categories/{categoryId}")
    @Operation(summary = "Insert a category item into header menu", description = "Insert a category item into header menu" +
            "and returns all registered categories")
    @ApiResponse(responseCode = "201", description = "Category item inserted successfully", content = {
            @Content(schema = @Schema(implementation = CategoryItemResponseDTO.class))})
    @ApiResponse(responseCode = "422", description = "Invalid category data provided", content = {
            @Content(schema = @Schema(implementation = Object.class))})
    public ResponseEntity<List<CategoryItemResponseDTO>> updateCategoryItem(@Valid
                                                                            @PathVariable Long categoryId,
                                                                            @RequestBody CategoryItemRequestDTO request) {

        List<CategoryItemResponseDTO> response  = updateCategoryItemUseCase.execute(categoryId, request);

        return ResponseEntity.ok().body(response);
    }

}
