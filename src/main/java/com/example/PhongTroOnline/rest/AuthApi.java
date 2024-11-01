package com.example.PhongTroOnline.rest;


import com.example.PhongTroOnline.model.request.LoginRequest;
import com.example.PhongTroOnline.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthApi {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        authService.login(request);
        return ResponseEntity.ok("Login successfully");
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(){
        authService.logout();
        return ResponseEntity.ok("Logout successfully");
    }

}