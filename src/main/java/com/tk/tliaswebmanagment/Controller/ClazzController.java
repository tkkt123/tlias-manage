package com.tk.tliaswebmanagment.Controller;

import com.tk.tliaswebmanagment.Service.ClazzService;
import com.tk.tliaswebmanagment.pojo.Clazz;
import com.tk.tliaswebmanagment.pojo.ClazzQueryParam;
import com.tk.tliaswebmanagment.pojo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ClazzController {
    @Autowired
    ClazzService clazzService;

    @GetMapping("/clazzs")
    public Result getClazzsPage(ClazzQueryParam clazzQueryParam) {
        System.out.println(clazzQueryParam);
        return Result.success(clazzService.getClazzsPage(clazzQueryParam));
    }

}
