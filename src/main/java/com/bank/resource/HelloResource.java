package com.bank.resource;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@PreAuthorize("hasAnyRole('ADMIN')")
@RequestMapping("/a")
@RestController
public class HelloResource {

    @GetMapping("/dashboard")
    public String securedHello() {
        return "dashboard";
    }

}