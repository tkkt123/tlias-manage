package com.tk.tliaswebmanagment.Controller;

import com.tk.tliaswebmanagment.Service.EmpService;
import com.tk.tliaswebmanagment.pojo.JobOption;
import com.tk.tliaswebmanagment.pojo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class ReportController {
    @Autowired
    EmpService empService;

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


}
