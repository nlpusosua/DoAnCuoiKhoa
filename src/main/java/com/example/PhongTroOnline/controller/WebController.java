package com.example.PhongTroOnline.controller;

import com.example.PhongTroOnline.entity.Room;
import com.example.PhongTroOnline.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class WebController {
    private final RoomService roomService;
    @GetMapping("/")
    public String getHomePage(Model model) {
        List<Room> listRoom = roomService.getRoomByName("name");
        model.addAttribute("listRoom", listRoom);
        return "web/index";
    }
}
