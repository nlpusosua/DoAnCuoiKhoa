package com.example.PhongTroOnline.config;

import com.example.PhongTroOnline.entity.User;
import com.example.PhongTroOnline.model.enums.UserRole;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthorizationInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handle)throws Exception {
        System.out.println("AuthenticationInterceptor");
        // Lấy thông tin user từ session
        User user = (User) request.getSession().getAttribute("currentUser");

        // Nếu currentUser == null => báo lỗi 401
        if (user == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
        // kiểm tra quyền user
        if (!user.getRole().equals(UserRole.ADMIN)){
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return false;
        }
        return true;
    }
}
