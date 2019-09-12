package com.jt.service.impl;

import com.jt.dao.UserDao;
import com.jt.service.UserService;

public class UserServiceImpl implements UserService {
    private UserDao dao;

    public void setDao(UserDao dao) {
        this.dao = dao;
    }

    public void fand(){
        dao.find();
    }
}
