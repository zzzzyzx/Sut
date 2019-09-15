package com.java;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component("dao")
public class IndexDao {
    String a;

    @PostConstruct
    public void inti(){
        System.out.println("init");
    }
    public IndexDao(){
        System.out.println("IndexDao");
    }
}
