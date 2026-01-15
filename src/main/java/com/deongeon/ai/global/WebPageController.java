package com.deongeon.ai.global;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebPageController {

    @GetMapping("/login")
    public String login() {
        // static/login.html 로 포워딩
        return "forward:/login.html";
    }
}
