package dev.eliezer.lojaonline.modules.user.controller;

import dev.eliezer.lojaonline.exceptions.UnauthorizedAccessException;
import dev.eliezer.lojaonline.modules.user.dtos.UpdateUserRequestDTO;
import dev.eliezer.lojaonline.modules.user.dtos.UserResponseDTO;
import dev.eliezer.lojaonline.modules.user.dtos.CreateUserRequestDTO;
import dev.eliezer.lojaonline.modules.user.entities.UserEntity;
import dev.eliezer.lojaonline.modules.user.useCases.CreateUserUseCase;
import dev.eliezer.lojaonline.modules.user.useCases.InactivateUserUseCase;
import dev.eliezer.lojaonline.modules.user.useCases.GetUsersUseCase;
import dev.eliezer.lojaonline.modules.user.useCases.UpdateUserUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
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

import static dev.eliezer.lojaonline.utils.RequestUtils.isUserAdmin;
import static dev.eliezer.lojaonline.utils.RequestUtils.extractUserIdOfRequest;


@RestController
@RequestMapping("/users")
@Tag(name = "Users", description = "RESTful API for managing users.")
public class UserRestController {

    @Autowired
    private CreateUserUseCase createUserUseCase;

    @Autowired
    private GetUsersUseCase getUsersUseCase;

    @Autowired
    private UpdateUserUseCase updateUserUseCase;

    @Autowired
    private InactivateUserUseCase inactivateUserUseCase;

    @GetMapping
    @Operation(summary = "Get all users", description = "Retrieve a list of all registered users")
    @ApiResponse(responseCode = "200", description = "Operation sucessfully", content = {
            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = UserEntity.class)))})
    @ApiResponse(responseCode = "422", description = "Invalid user data provided", content = {
            @Content(mediaType = "text/plain", schema = @Schema(example = "[\n\"Email example@user.com not found.\",\n\"Resource id not found.\"\n]"))})
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<List<UserResponseDTO>> index(@RequestParam(value = "id", defaultValue = "0") Long id,
                                                       @RequestParam(value = "email", defaultValue = "") String email,
                                                       @RequestParam(value = "name", defaultValue = "") String name,
                                                       HttpServletRequest request){

          if (!isUserAdmin(request)) {
            throw new UnauthorizedAccessException();
        }


        var result = getUsersUseCase.execute(id, email, name);
        return ResponseEntity.ok().body(result);
    }

    @PostMapping
    @Operation(summary = "Create a new user", description = "Create a new user and return the created user data")
    @ApiResponse(responseCode = "201", description = "User created successfully", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDTO.class))})
    @ApiResponse(responseCode = "422", description = "Invalid user data provided", content =
    @Content(
            mediaType = "text/plain",
            array = @ArraySchema(schema = @Schema(implementation = CreateUserRequestDTO.class)),

            examples = {
                    @ExampleObject(
                            name = "RequiredFieldsMissing",
                            value = "[\"email not provided\", \"fullname not provided\", \"password not provided\"]"
                    ),
                    @ExampleObject(
                            name = "OtherErrorMessages",
                            value = "[\"email is alright in use.\"]"
                    )
            }
    ))
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<UserResponseDTO> create(@Valid @RequestBody CreateUserRequestDTO createUserData, HttpServletRequest request) {

//        if (!isUserAdmin(request)) {
//            throw new UnauthorizedAccessException();
//        }

        var result = createUserUseCase.execute(createUserData);
        return ResponseEntity.ok().body(result);
    }



    @PutMapping("/{userId}")
    @Operation(summary = "Update a user", description = "Update a user and return the user data updated")
    @ApiResponse(responseCode = "201", description = "User updated successfully", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDTO.class))})
    @ApiResponse(responseCode = "422", description = "user id not found", content = {
            @Content(mediaType = "text/plain", schema = @Schema(example = "Resource id not found."))})
    @ApiResponse(responseCode = "422", description = "Invalid user data provided", content = {
            @Content(mediaType = "text/plain", schema = @Schema(example = "email is alright in use."))})
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<UserResponseDTO> update(@PathVariable Long userId, HttpServletRequest request,
                                                  @Valid @RequestBody UpdateUserRequestDTO userDataUpdate) {

        Boolean isUserAdminResult = isUserAdmin(request);

        /* Qualquer usuário pode alterar informações dele mesmo e apenas usuários administradores podem alterar informações
        de outros usuários */
        if (!isUserAdminResult && !extractUserIdOfRequest(request).equals(userId)){
            throw new UnauthorizedAccessException();
        }

        /* Apenas usuários admin podem alterar a role de um usuário */
        if (!isUserAdminResult && userDataUpdate.getUserRole() != null) {
            throw new UnauthorizedAccessException();
        }

        var result = updateUserUseCase.execute(userDataUpdate, userId);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/{userId}")
    @Operation(summary = "Inactivate a user", description = "Inactivate a user")
    @ApiResponse(responseCode = "200", description = "User inactivated successfully", content = {
            @Content(schema = @Schema(implementation = UserEntity.class))})
    @ApiResponse(responseCode = "404", description = "user id not found", content = {
            @Content(mediaType = "text/plain", schema = @Schema(example = "Resource id not found."))})
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<String> inactivate(@PathVariable Long userId, HttpServletRequest request) {

        if (!isUserAdmin(request)) throw new UnauthorizedAccessException();

        inactivateUserUseCase.execute(userId);

        return ResponseEntity.ok().body("User with id " + userId +  " inactivated successfully.");
    }



}
