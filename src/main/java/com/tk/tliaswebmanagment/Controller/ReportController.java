package com.tk.tliaswebmanagment.Controller;

import com.tk.tliaswebmanagment.Service.EmpService;
import com.tk.tliaswebmanagment.Service.StudentService;
import com.tk.tliaswebmanagment.pojo.JobOption;
import com.tk.tliaswebmanagment.pojo.Result;
import com.tk.tliaswebmanagment.pojo.StudentOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class ReportController {
    @Autowired
    private EmpService empService;
    @Autowired
    private StudentService studentService;



    @GetMapping("/report/empJobData")
    public Result getEmpJobData() {
        JobOption jobOption = empService.getEmpJobData();
        return Result.success(jobOption);
    }

    @GetMapping("/report/empGenderData")
    public Result getEmpGenderData() {
        List<Map<String,Object>> genderData = empService.getEmpGenderData();
        return Result.success(genderData);
    }

    @GetMapping("/report/studentCountData")
    public Result getStudentCountData() {
        StudentOption studentCountData = studentService.getStudentCountData();
        return Result.success(studentCountData);
    }

    @GetMapping("/report/studentDegreeData")
    public Result getStudentDegreeData() {
        List<Map<String,Object>> studentDegreeData = studentService.getStudentDegreeData();
        return Result.success(studentDegreeData);
    }



}
