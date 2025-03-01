package com.example.PhongTroOnline.rest;

import com.example.PhongTroOnline.model.request.UpdateUserInfor;
import com.example.PhongTroOnline.model.request.UpdateUserPass;
import com.example.PhongTroOnline.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserApi {
    private final UserService userService;


    // Sửa tt user
    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@Valid @RequestBody UpdateUserInfor request) {
        userService.updateUser(request);
        return ResponseEntity.ok("update successfully");
    }

    // Sửa pass
    @PutMapping("/update/password")
    public ResponseEntity<?> updatePassword(@Valid @RequestBody UpdateUserPass request) {

        userService.updatePassword(request);
        return ResponseEntity.ok("update successfully");
    }

}
