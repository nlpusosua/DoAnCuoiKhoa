package com.example.PhongTroOnline.model.enums;

public enum FavoriteResult {
    SAVED_NEW, // Lưu mới thành công
    ALREADY_SAVED, // Đã lưu từ trước
    REMOVED, // Đã xóa thành công
    NOT_FOUND, // Không tìm thấy bản ghi để xóa
    ERROR // Lỗi xảy ra
}
