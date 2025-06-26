package dev.eliezer.lojaonline.modules.system.status.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/system/status")
@Tag(name = "System Status", description = "RESTful API for show a system status")
public class StatusRestController {
    @GetMapping
    @Operation(summary = "Get a system status", description = "Retrieve a system status")
    public ResponseEntity<String> index() {
        return ResponseEntity.ok().body("system ok, versão gerada em 16/04/2025 23:05");
    }
}
