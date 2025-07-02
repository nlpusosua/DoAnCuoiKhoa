package com.example.PhongTroOnline.rest;
import com.example.PhongTroOnline.model.enums.FavoriteResult;
import com.example.PhongTroOnline.service.AuthService;
import com.example.PhongTroOnline.service.FavoriteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;

@RestController
@RequestMapping("/api/favorites")
public class FavoriteAPI {
    private static final Logger logger = LoggerFactory.getLogger(FavoriteAPI.class);
    @Autowired
    private FavoriteService favoriteService;
    @Autowired
    private AuthService authService;

    @PostMapping("/add")
    public ResponseEntity<?> addFavorite(@RequestParam(value = "roomId", required = true) String roomIdStr) {
        logger.info("Received favorite request for roomId: {}", roomIdStr);
        // Chuyển đổi an toàn từ String sang Integer
        Integer roomId = null;
        try {
            // Nếu roomIdStr vẫn chứa ${room.id}, đây là lỗi template
            if (roomIdStr.contains("${")) {
                logger.error("Invalid roomId format (template not rendered): {}", roomIdStr);
                return ResponseEntity.badRequest().body(new HashMap<String, Object>() {{
                    put("success", false);
                    put("message", "Định dạng ID phòng không hợp lệ");
                }});
            }
            roomId = Integer.parseInt(roomIdStr);
        } catch (NumberFormatException e) {
            logger.error("Failed to parse roomId: {}", roomIdStr);
            return ResponseEntity.badRequest().body(new HashMap<String, Object>() {{
                put("success", false);
                put("message", "ID phòng không hợp lệ");
            }});
        }

        // Kiểm tra user đã đăng nhập chưa
        if (authService.getCurrentUser() == null) {
            logger.warn("User not logged in");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new HashMap<String, Object>() {{
                        put("success", false);
                        put("message", "Vui lòng đăng nhập để lưu tin");
                        put("requireLogin", true);
                    }});
        }

        try {
            FavoriteResult result = favoriteService.addFavorite(roomId);
            switch (result) {
                case SAVE:
                    logger.info("Added favorite successfully for roomId: {}", roomId);
                    return ResponseEntity.ok(new HashMap<String, Object>() {{
                        put("success", true);
                        put("status", "new");
                        put("message", "Đã lưu tin thành công");
                    }});
                default:
                    logger.warn("Failed to add favorite for roomId: {}", roomId);
                    return ResponseEntity.badRequest().body(new HashMap<String, Object>() {{
                        put("success", false);
                        put("message", "Có lỗi xảy ra, vui lòng thử lại");
                    }});
            }
        } catch (Exception e) {
            logger.error("Error adding favorite: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(new HashMap<String, Object>() {{
                put("success", false);
                put("message", "Có lỗi xảy ra, vui lòng thử lại");
            }});
        }
    }


    @GetMapping("/check")
    public ResponseEntity<?> checkFavoriteStatus(@RequestParam(value = "roomId", required = true) String roomIdStr) {
        // Chuyển đổi an toàn từ String sang Integer
        Integer roomId = null;
        try {
            if (roomIdStr.contains("${")) {
                logger.error("Invalid roomId format (template not rendered): {}", roomIdStr);
                return ResponseEntity.badRequest().body(new HashMap<String, Object>() {{
                    put("success", false);
                    put("message", "Định dạng ID phòng không hợp lệ");
                }});
            }
            roomId = Integer.parseInt(roomIdStr);
        } catch (NumberFormatException e) {
            logger.error("Failed to parse roomId: {}", roomIdStr);
            return ResponseEntity.badRequest().body(new HashMap<String, Object>() {{
                put("success", false);
                put("message", "ID phòng không hợp lệ");
            }});
        }

        // Kiểm tra user đã đăng nhập chưa
        if (authService.getCurrentUser() == null) {
            return ResponseEntity.ok(new HashMap<String, Object>() {{
                put("success", true);
                put("favorited", false);
            }});
        }

        boolean isFavorited = favoriteService.isFavorited(roomId);
        return ResponseEntity.ok(new HashMap<String, Object>() {{
            put("success", true);
            put("favorited", isFavorited);
        }});
    }

    @DeleteMapping("/remove")
    public ResponseEntity<?> removeFavorite(@RequestParam(value = "roomId", required = true) String roomIdStr) {
        logger.info("Received remove favorite request for roomId: {}", roomIdStr);
        Integer roomId = null;
        try {
            if (roomIdStr.contains("${")) {
                logger.error("Invalid roomId format (template not rendered): {}", roomIdStr);
                return ResponseEntity.badRequest().body(new HashMap<String, Object>() {{
                    put("success", false);
                    put("message", "Định dạng ID phòng không hợp lệ");
                }});
            }
            roomId = Integer.parseInt(roomIdStr);
        } catch (NumberFormatException e) {
            logger.error("Failed to parse roomId: {}", roomIdStr);
            return ResponseEntity.badRequest().body(new HashMap<String, Object>() {{
                put("success", false);
                put("message", "ID phòng không hợp lệ");
            }});
        }
        try {
            FavoriteResult result = favoriteService.removeFavorite(roomId);
            switch (result) {
                case REMOVED:
                    logger.info("Removed favorite successfully for roomId: {}", roomId);
                    return ResponseEntity.ok(new HashMap<String, Object>() {{
                        put("success", true);
                        put("message", "Đã xóa tin khỏi danh sách lưu");
                    }});
                case NOT_FOUND:
                    logger.warn("Favorite not found for roomId: {}", roomId);
                    return ResponseEntity.ok(new HashMap<String, Object>() {{
                        put("success", false);
                        put("message", "Không tìm thấy tin đã lưu");
                    }});
                default:
                    logger.warn("Failed to remove favorite for roomId: {}", roomId);
                    return ResponseEntity.badRequest().body(new HashMap<String, Object>() {{
                        put("success", false);
                        put("message", "Có lỗi xảy ra, vui lòng thử lại");
                    }});
            }
        } catch (Exception e) {
            logger.error("Error removing favorite: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(new HashMap<String, Object>() {{
                put("success", false);
                put("message", "Có lỗi xảy ra, vui lòng thử lại");
            }});
        }
    }
}

