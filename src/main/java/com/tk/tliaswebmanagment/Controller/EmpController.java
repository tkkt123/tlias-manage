package com.tk.tliaswebmanagment.Controller;

import com.tk.tliaswebmanagment.Service.EmpService;
import com.tk.tliaswebmanagment.anno.Log;
import com.tk.tliaswebmanagment.pojo.Emp;
import com.tk.tliaswebmanagment.pojo.EmpQuerryParam;
import com.tk.tliaswebmanagment.pojo.PageResult;
import com.tk.tliaswebmanagment.pojo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
public class EmpController {
    @Autowired
    private EmpService empService;

    // 分页查询员工
    @GetMapping("/emps")
    public Result getEmpByPage(EmpQuerryParam empQuerryParam) {
        PageResult pageResult = empService.getEmpByPage(empQuerryParam);
       return Result.success(pageResult);
    }

    // 新增员工
    @Log
    @PostMapping("/emps")
    public Result insertEmp(@RequestBody Emp emp) {
        System.out.println(emp.toString());
        return empService.insertEmp(emp);
    }

    // 更新指定的员工
    @Log
    @PutMapping("/emps")
    public Result updateEmp(@RequestBody Emp emp) {
        return empService.updateEmp(emp);
    }

    // 删除指定的员工
    @Log
    @DeleteMapping("/emps")
    public Result deleteEmps(@RequestBody(required = false) List<Integer> ids) {
        if (ids == null || ids.isEmpty()) {
            return Result.error("请选择要删除的员工");
        }
        return empService.deleteEmps(ids);
    }


    // 获取指定的员工
    @GetMapping("/emps/{id}")
    public Result getEmpById(@PathVariable Integer id) {
        Emp emp = empService.getEmpById(id);
        if (emp == null) {
            return Result.error("员工不存在");
        }
        return Result.success(emp);
    }

    // 获取所有员工
    @GetMapping("/emps/list")
    public Result getEmpList() {
        List<Emp> empList = empService.getEmpList();
        return Result.success(empList);
    }
}
