package com.java;


import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Test {
    public static void main(String[] args) {
        //DefaultListableBeanFactory
        AnnotationConfigApplicationContext a = new AnnotationConfigApplicationContext(cfg.class);
        Object dao = a.getBean("dao");
    }
}
