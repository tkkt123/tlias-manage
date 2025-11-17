package com.tk.tliaswebmanagment.test;

import com.tk.tliaswebmanagment.Service.ClazzService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
public class testTest {
    @Autowired
    private ApplicationContext context;

    @Test
    public void test() {
        Object b = context.getBean("clazzController");
        System.out.println(b);
    }
}
