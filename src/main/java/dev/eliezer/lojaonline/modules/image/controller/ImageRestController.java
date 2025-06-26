package dev.eliezer.lojaonline.modules.image.controller;


import dev.eliezer.lojaonline.modules.image.entities.ImageEntity;
import dev.eliezer.lojaonline.modules.image.useCases.GetImageUseCase;
import dev.eliezer.lojaonline.modules.product.entities.ProductEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/images")
@Tag(name = "Images", description = "RESTful API for managing images.")
public class ImageRestController {
    @Autowired
    private GetImageUseCase getImageUseCase;

    @GetMapping("/{id}")
    @Operation(summary = "Get a image", description = "Retrieve a specific category based on its ID")
    @ApiResponse(responseCode = "201", description = "Operation successful", content = {
            @Content(schema = @Schema(example = "Base64 String text"))})
    @ApiResponse(responseCode = "422", description = "Invalid image data provided", content = {
            @Content(schema = @Schema(implementation = Object.class))})
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) {
        ImageEntity image = getImageUseCase.execute(id);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(image.getImageType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + image.getFilename() + "\"")
                .body(image.getImageData());
    }
}
