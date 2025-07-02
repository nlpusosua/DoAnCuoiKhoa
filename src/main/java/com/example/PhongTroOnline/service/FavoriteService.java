package com.example.PhongTroOnline.service;

import com.example.PhongTroOnline.entity.Favorite;
import com.example.PhongTroOnline.entity.Room;
import com.example.PhongTroOnline.entity.User;
import com.example.PhongTroOnline.model.enums.FavoriteResult;
import com.example.PhongTroOnline.repository.FavoriteRepository;
import com.example.PhongTroOnline.repository.RoomRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class FavoriteService {
    private static final Logger logger = LoggerFactory.getLogger(FavoriteService.class);
    @Autowired
    private FavoriteRepository favoriteRepository;
    @Autowired
    private AuthService authService;
    @Autowired
    private RoomRepository roomRepository;
    public FavoriteResult addFavorite(int roomId) {
        try {
            User currentUser = authService.getCurrentUser();
            if (currentUser == null) {
                logger.warn("Current user is null");
                return FavoriteResult.ERROR;
            }
            // Kiểm tra phòng có tồn tại không
            Room room = roomRepository.findById(roomId).orElse(null);
            if (room == null) {
                logger.warn("Room with id {} not found", roomId);
                return FavoriteResult.ERROR;
            }
            // Tạo mới favorite
            Favorite favorite = new Favorite();
            favorite.setUser(currentUser);
            favorite.setRoom(room);
            favorite.setCreatedAt(LocalDateTime.now());
            favoriteRepository.save(favorite);
            logger.info("Favorite saved successfully for user {} and room {}", currentUser.getId(), roomId);
            return FavoriteResult.SAVE;
        } catch (Exception e) {
            logger.error("Error adding favorite: {}", e.getMessage(), e);
            return FavoriteResult.ERROR;
        }
    }

    // Thêm phương thức mới để kiểm tra nhanh phòng đã được lưu hay chưa
    public boolean isFavorited(int roomId) {
        try {
            User currentUser = authService.getCurrentUser();
            if (currentUser == null) {
                return false;
            }
            return favoriteRepository.findByUserIdAndRoomId(currentUser.getId(), roomId).isPresent();
        } catch (Exception e) {
            logger.error("Error checking favorite status: {}", e.getMessage(), e);
            return false;
        }
    }
    public FavoriteResult removeFavorite(int roomId) {
        try {
            User currentUser = authService.getCurrentUser();
            if (currentUser == null) {
                logger.warn("Current user is null");
                return FavoriteResult.ERROR;
            }

            Optional<Favorite> existingFavorite = favoriteRepository.findByUserIdAndRoomId(currentUser.getId(), roomId);
            if (existingFavorite.isEmpty()) {
                logger.warn("Favorite not found for user {} and room {}", currentUser.getId(), roomId);
                return FavoriteResult.NOT_FOUND;
            }

            favoriteRepository.delete(existingFavorite.get());
            logger.info("Favorite removed successfully for user {} and room {}", currentUser.getId(), roomId);
            return FavoriteResult.REMOVED;
        } catch (Exception e) {
            logger.error("Error removing favorite: {}", e.getMessage(), e);
            return FavoriteResult.ERROR;
        }
    }
}