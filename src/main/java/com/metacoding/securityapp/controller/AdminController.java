package com.metacoding.securityapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AdminController {

    @GetMapping("/admin/main") // 관리자 권한 있으면 갈 수 있음.
    public @ResponseBody String adminMain() {
        return "<h1>admin page</h1>";
    }
}