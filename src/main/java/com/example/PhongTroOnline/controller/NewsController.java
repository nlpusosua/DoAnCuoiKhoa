package com.example.PhongTroOnline.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/news")
public class NewsController {
    @GetMapping
    public String getIndexNews(){
        return "admin/news/index";
    }
    @GetMapping("/create")
    public String getCreateNews(){
        return "admin/news/create";
    }
    @GetMapping("/{id}")
    public String getDetailNews(@PathVariable Integer id){
        return "admin/news/detail";
    }
}
