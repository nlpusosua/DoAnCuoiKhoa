package com.example.PhongTroOnline.service;


import com.example.PhongTroOnline.entity.User;
import com.example.PhongTroOnline.exception.BadRequestException;
import com.example.PhongTroOnline.model.request.LoginRequest;
import com.example.PhongTroOnline.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final HttpSession session;

    public void login(LoginRequest request) {
        // Kiem tra xem user co ton tai trong database khong
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadRequestException("Email incorrect"));

        // Kiểm tra xem password có khớp với password trong database không
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadRequestException("Password incorrect");
        }
        // Lưu thông tin user vào trong session để sử dụng ở các request tiếp theo
        session.setAttribute("currentUser", user);
        System.out.println("User saved to session: " + user);
    }

// đăng xuất
    public void logout(){
        session.setAttribute("currentUser", null);
    }
}