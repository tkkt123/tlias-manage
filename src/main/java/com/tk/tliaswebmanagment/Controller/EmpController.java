package com.tk.tliaswebmanagment.Controller;

import com.tk.tliaswebmanagment.Service.EmpService;
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

    @GetMapping("/emps")
    public Result getEmpByPage(EmpQuerryParam empQuerryParam) {
        PageResult pageResult = empService.getEmpByPage(empQuerryParam);
       return Result.success(pageResult);
    }
    
    @PostMapping("/emps")
    public Result insertEmp(@RequestBody Emp emp) {
        System.out.println(emp.toString());
        return empService.insertEmp(emp);
    }
    
    @PutMapping("/emps")
    public Result updateEmp(@RequestBody Emp emp) {
        return empService.updateEmp(emp);
    }
    
    @DeleteMapping("/emps")
    public Result deleteEmps(@RequestBody(required = false) List<Integer> ids) {
        if (ids == null || ids.isEmpty()) {
            return Result.error("请选择要删除的员工");
        }
        return empService.deleteEmps(ids);
    }
}
