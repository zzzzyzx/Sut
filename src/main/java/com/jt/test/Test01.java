package com.jt.test;

import com.jt.controller.UserController;
import org.spring.util.BeanFactory;

public class Test01 {
    public static void main(String[] args) {
        BeanFactory beanFactory = new BeanFactory("spring.xml");
        UserController userController = (UserController) beanFactory.getBean("userController");
        userController.find();
    }
}
