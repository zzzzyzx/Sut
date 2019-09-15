package com.java;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.PriorityOrdered;
import org.springframework.stereotype.Component;

@Component
public class MYBeanPostProcessor implements BeanPostProcessor, PriorityOrdered {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (beanName.equals("dao"))
        System.out.println("postProcessBeforeInitialization0");
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (beanName.equals("dao"))
        System.out.println("postProcessAfterInitialization0");
        return bean;
    }

    @Override
    public int getOrder() {
        return 100;
    }
}
