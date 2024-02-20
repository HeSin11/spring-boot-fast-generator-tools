package com.generator.tools.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloFreeMarkerController {

    @GetMapping("/hello")
    public  String test(Model model){
        model.addAttribute("hello", "Hello World FreeMarker");
        return "index";
    }
}
