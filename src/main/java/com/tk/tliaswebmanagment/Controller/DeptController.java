package com.tk.tliaswebmanagment.Controller;

import com.tk.tliaswebmanagment.Service.DeptService;
import com.tk.tliaswebmanagment.pojo.Dept;
import com.tk.tliaswebmanagment.pojo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DeptController {
    @Autowired
    private DeptService deptService;

    //部门列表查询
    @GetMapping("/depts")
    public Result getDepts() {
        List<Dept> list=deptService.findall();
        return Result.success(list);
    }

    //部门删除
    @DeleteMapping("/depts")
    public Result deleteDept(@RequestParam("id")Integer id) {
        deptService.deleteDept(id);
        System.out.println(id);
        return Result.success();
    }

    //部门添加
    @PostMapping("/depts")
    public Result addDept(@RequestBody Dept dept) {
        System.out.println(dept);
        deptService.addDept(dept);
        return Result.success();
    }

    //部门修改
    @PutMapping("/depts")
    public Result updateDept(@RequestBody Dept dept){
        deptService.updateDept(dept);
        return Result.success();
    }

    //根据id查询部门信息
    @GetMapping("/depts/{id}")
    public Result getDeptById(@PathVariable("id") Integer id) {
        //这里注意RequestParam和PathVariable的区别
        //RequestParam是从请求参数中获取值，PathVariable是从路径中获取值
        // depts?id=1  depts/1
        Dept dept = deptService.getDeptById(id);
        return Result.success(dept);
    }
}
