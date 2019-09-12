package com.jt.controller;

import com.jt.service.UserService;

public class UserController {
    private UserService userservice;

    public UserController(UserService userservice) {
        this.userservice = userservice;
    }

    public void find(){
        userservice.fand();
    }

}
