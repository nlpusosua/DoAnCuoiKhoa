package com.example.PhongTroOnline.service;

import com.example.PhongTroOnline.entity.User;
import com.example.PhongTroOnline.exception.BadRequestException;
import com.example.PhongTroOnline.model.request.UpdateUserInfor;
import com.example.PhongTroOnline.model.request.UpdateUserPass;
import com.example.PhongTroOnline.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final HttpSession session;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void updatePassword(UpdateUserPass userRequest) {
        User user = userRepository.findById(userRequest.getId())
                .orElseThrow(() -> new BadRequestException("Id lỗi"));

        // check xem password giống k
        if (!bCryptPasswordEncoder.matches(userRequest.getOldPassword(), user.getPassword())) {
            throw new BadRequestException("mật khẩu cũ không đúng");
        }
        user.setPassword(bCryptPasswordEncoder.encode(userRequest.getNewPassword()));
        userRepository.save(user);
//      lưu thông tin user vào session dể sử dụng ở các request tiếp theo
        session.setAttribute("currentUser", user);
    }
    public void updateUser(UpdateUserInfor userRequest) {
        User user = userRepository.findById(userRequest.getId())
                .orElseThrow(() -> new BadRequestException("Id lỗi"));
        user.setName(userRequest.getUsername());
        userRepository.save(user);
//      lưu thông tin user vào session dể sử dụng ở các request tiếp theo
        session.setAttribute("currentUser", user);
    }
}
