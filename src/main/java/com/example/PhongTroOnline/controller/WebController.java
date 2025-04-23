package com.example.PhongTroOnline.controller;

import com.example.PhongTroOnline.entity.ImageRoom;
import com.example.PhongTroOnline.entity.Room;
import com.example.PhongTroOnline.entity.User;
import com.example.PhongTroOnline.repository.UserRepository;
import com.example.PhongTroOnline.service.ImageRoomService;
import com.example.PhongTroOnline.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class WebController {
    private final RoomService roomService;
    private final ImageRoomService imageRoomService;
    @Autowired
    UserRepository userRepository;

    @GetMapping("/")
    public String getHomePage(Model model) {
        List<Room> listRoom = roomService.getRoom(true, 2, 8).getContent();
        List<Room> listFavRoom = roomService.getRoomsWithMostFavorites(2, 8).getContent();
        model.addAttribute("listRoom", listRoom);
        model.addAttribute("listFavRoom", listFavRoom);
        return "web/index";
    }


    @GetMapping("/room/{id}/{slug}")
    public String getHousePage(Model model, @PathVariable Integer id, @PathVariable String slug) {
        Room room = roomService.getRoom(id, slug, true);
        List<Room> listFavRoomHouse = roomService.getRoomsWithMostFavorites(1, 4).getContent();
        model.addAttribute("room", room);
        model.addAttribute("listFavRoomHouse", listFavRoomHouse);
        return "web/house";
    }


    @GetMapping("/login")
    public String getLoginPage() {
        return "web/login";
    }

    @GetMapping("/phong-tro-cho-ban")
    public String getPagePhongTroChoBan(Model model,
                                        @RequestParam(required = false, defaultValue = "1") int page,
                                        @RequestParam(required = false, defaultValue = "12") int pageSize) {
        Page<Room> roomPage = roomService.getRoomsWithMostFavorites(page, pageSize);
        model.addAttribute("roomPage", roomPage);
        model.addAttribute("currentPage", page);
        return "web/phongtrochoban";
    }

    @GetMapping("/bang-gia-dich-vu")
    public String getDichVuPage() {
        System.out.println("Entering /bang-gia-dich-vu endpoint...");
        return "web/bangdichvu";
    }

    @GetMapping("/sale")
    public String getSalePage() {
        System.out.println("Entering /sale endpoint...");
        return "web/sale";
    }

    @GetMapping("/infor/{id}")
    public String getInfor(Model model, @PathVariable int id) {
        Optional<User> user = userRepository.findById(id);
        model.addAttribute("user", user);
        return "web/infor";
    }
    @GetMapping("/search")
    public String search(@RequestParam(value = "keyword", required = false) String keyword,
                         @RequestParam(defaultValue = "0") int page,
                         @RequestParam(defaultValue = "10") int pageSize,
                         Model model) {
        Page<Room> roomPage = roomService.searchByTitle(keyword != null ? keyword.trim() : "",
                PageRequest.of(page, pageSize));
        model.addAttribute("roomPage", roomPage);
        model.addAttribute("keyword", keyword);

        if (roomPage.isEmpty()) {
            model.addAttribute("message", "Không có kết quả tìm kiếm");
        }
        return "web/search";
    }

    @GetMapping("/register")
    public String getRegisterPage() {
        return "web/register";
    }

}
