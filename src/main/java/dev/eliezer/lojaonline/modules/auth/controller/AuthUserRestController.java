package dev.eliezer.lojaonline.modules.auth.controller;

import dev.eliezer.lojaonline.modules.auth.dto.AuthUserRequestDTO;
import dev.eliezer.lojaonline.modules.auth.dto.AuthUserResponseDTO;
import dev.eliezer.lojaonline.modules.auth.useCases.AuthUserUseCase;
import dev.eliezer.lojaonline.modules.shared.entities.UserToken;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users/auth")
@Tag(name = "Users Auth", description = "RESTful API for authenticating users")
public class AuthUserRestController {

    @Autowired
    private AuthUserUseCase authUserUseCase;

    @PostMapping
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<UserToken> auth (@Valid @RequestBody AuthUserRequestDTO userAuth) {
        var authenticatedUser = authUserUseCase.execute(userAuth, 0L);
        return ResponseEntity.ok().body(authenticatedUser);
    }
}
