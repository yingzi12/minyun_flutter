package com.xinshijie.socket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @Autowired
    private IUserService userService;
    @GetMapping("/api/test")
    public String getTest(){
        userService.selectByUserName("dsf");
        return "ddddd6";
    }
}
