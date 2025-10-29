package com.tk.tliaswebmanagment.Controller;

import com.tk.tliaswebmanagment.Service.ClazzService;
import com.tk.tliaswebmanagment.pojo.Clazz;
import com.tk.tliaswebmanagment.pojo.ClazzQueryParam;
import com.tk.tliaswebmanagment.pojo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class ClazzController {
    @Autowired
    ClazzService clazzService;

    @GetMapping("/clazzs")
    public Result getClazzsPage(ClazzQueryParam clazzQueryParam) {
        return Result.success(clazzService.getClazzsPage(clazzQueryParam));
    }

    @PostMapping("/clazzs")
    public Result insertClazz(@RequestBody Clazz clazz) {
        return clazzService.insertClazz(clazz);
    }

    @GetMapping("/clazzs/{id}")
    public Result getClazzById(@PathVariable Integer id) {
        Clazz clazz = clazzService.getClazzById(id);
        if (clazz == null) {
            return Result.error("班级不存在");
        }
        return Result.success(clazz);
    }

    @PutMapping("/clazzs")
    public Result updateClazz(@RequestBody Clazz clazz) {
        return clazzService.updateClazz(clazz);
    }

}
