package com.bank.controller.admin;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@PreAuthorize("hasAnyRole('ADMIN')")
@RequestMapping("/admin")
@RestController
public class AdminController {

    @GetMapping("/dashboard")
    public String securedHello() {
        return "dashboard";
    }

}