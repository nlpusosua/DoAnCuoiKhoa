package com.example.PhongTroOnline.model.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoginRequest {
    @NotEmpty(message = "Email ko để trống")
    @Email(message = "Email ko đúng định dạng")
    String email;
    @NotEmpty(message = "Mật khẩu ko đc để trống")
    String password;
}