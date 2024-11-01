package com.example.PhongTroOnline.controller;

import com.example.PhongTroOnline.entity.Room;
import com.example.PhongTroOnline.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class WebController {
    private final RoomService roomService;


    @GetMapping("/")
    public String getHomePage(Model model) {
        List<Room> listRoom = roomService.getRoom(true, 2, 8).getContent();
        List<Room> listFavRoom = roomService.getRoomsWithMostFavorites(2,8).getContent();
        model.addAttribute("listRoom", listRoom);
        model.addAttribute("ListFavRoom", listFavRoom);
        return "web/index";
    }

    @GetMapping("/room/{id}/{slug}")
    public String getHousePage(Model model, @PathVariable Integer id, @PathVariable String slug){
        Room room = roomService.getRoom(id, slug, true);
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
}
