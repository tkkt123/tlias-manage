package com.tk.tliaswebmanagment.Controller;

import com.tk.tliaswebmanagment.Service.ClazzService;
import com.tk.tliaswebmanagment.pojo.Clazz;
import com.tk.tliaswebmanagment.pojo.ClazzQueryParam;
import com.tk.tliaswebmanagment.pojo.PageResult;
import com.tk.tliaswebmanagment.pojo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class ClazzController {
    @Autowired
    ClazzService clazzService;

    @GetMapping("/clazzs")
    public Result getClazzsPage(ClazzQueryParam clazzQueryParam) {
        PageResult pageResult = clazzService.getClazzsPage(clazzQueryParam);
        return Result.success(pageResult);
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

    @DeleteMapping("/clazzs/{id}")
    public Result deleteClazzs(@PathVariable(required = true) Integer id) {
        if (id == null) {
            return Result.error("请选择要删除的班级");
        }
        return clazzService.deleteClazz(id);
    }

    @GetMapping("/clazzs/list")
    public Result getClazzsList() {
        List<Clazz> clazzsList = clazzService.getClazzsList();
        return Result.success(clazzsList);
    }
}
