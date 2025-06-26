package dev.eliezer.lojaonline.modules.user.controller;

import dev.eliezer.lojaonline.exceptions.BusinessException;
import dev.eliezer.lojaonline.modules.image.entities.ImageEntity;
import dev.eliezer.lojaonline.modules.image.useCases.GetImageUseCase;
import dev.eliezer.lojaonline.modules.user.dtos.UserResponseDTO;
import dev.eliezer.lojaonline.modules.user.entities.UserEntity;
import dev.eliezer.lojaonline.modules.user.repositories.UserRepository;
import dev.eliezer.lojaonline.modules.user.useCases.UploadUserImageUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/users/images")
@Tag(name = "User Image Profile", description = "RESTful API for managing image of user profile.")
public  class UserImageRestController {
    @Autowired
    private UploadUserImageUseCase uploadUserImageUseCase;

    @Autowired
    private GetImageUseCase getImageUseCase;

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    @Operation(summary = "Upload user image",
            description = "Uploads an image file for the specified user"
    )

    @ApiResponse(responseCode = "201", description = "image uploaded successfully", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDTO.class))})
    @ApiResponse(responseCode = "422", description = "Invalid user data provided", content = {
            @Content(mediaType = "text/plain", schema = @Schema(example = "Resource id not found."))})
//    @RequestBody(content =
//    @Content(
//            mediaType = "multipart/form-data",
//            schema = @Schema(
//                    type = "file",
//                    properties = {
//                            @SchemaProperty(
//                                    name = "userImage",
//                                    schema = @Schema(
//                                            description = "User image file to be uploaded",
//                                            type = "string",
//                                            format = "binary"
//                                    )
//                            )
//                    }
//
//            )
//    ))
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<UserResponseDTO> upload(@RequestParam("userImage") MultipartFile file,
                                                  HttpServletRequest request) throws IOException {
        Long userid = Long.valueOf(request.getAttribute("user_id").toString());
        return ResponseEntity.ok().body(uploadUserImageUseCase.execute(userid, file));
    }


    @GetMapping
    @Operation(summary = "Get a user image", description = "Get a image of user")
    @ApiResponse(responseCode = "200", description = "Operation sucessfully", content = {
            @Content(mediaType = "application/json", schema = @Schema(example = "String Base64"))})
    @ApiResponse(responseCode = "422", description = "Invalid user data provided", content = {
            @Content(mediaType = "text/plain", schema = @Schema(example = "Resource id not found."))})
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<byte[]> find(HttpServletRequest request) {
        Long userid = Long.valueOf(request.getAttribute("user_id").toString());

        UserEntity userFound = userRepository.findById(userid).orElseThrow(() -> new BusinessException("token data is invalid."));

        ImageEntity userImageData = getImageUseCase.execute(userFound.getIdImage());

        MediaType imageType = MediaType.parseMediaType(userImageData.getImageType());

        byte[] userImage = userImageData.getImageData();

        return ResponseEntity.ok().contentType(imageType).body(userImage);
    }

}
