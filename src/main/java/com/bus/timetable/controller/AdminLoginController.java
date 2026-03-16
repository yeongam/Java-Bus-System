package com.bus.timetable.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AdminLoginController {

    @Value("${admin.auth.key}")
    private String authKey;

    @GetMapping("/admin/login")
    public String loginPage(HttpSession session) {
        if (Boolean.TRUE.equals(session.getAttribute("adminAuthenticated"))) {
            return "redirect:/admin";
        }
        return "admin/login";
    }

    @PostMapping("/admin/login")
    public String login(@RequestParam String key, HttpSession session, Model model) {
        if (authKey.equals(key.trim())) {
            session.setAttribute("adminAuthenticated", true);
            return "redirect:/admin";
        }
        model.addAttribute("error", "인증키가 올바르지 않습니다.");
        return "admin/login";
    }

    @PostMapping("/admin/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/admin/login";
    }
}
