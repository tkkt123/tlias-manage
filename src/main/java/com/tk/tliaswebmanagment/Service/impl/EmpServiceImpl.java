package com.tk.tliaswebmanagment.Service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.tk.tliaswebmanagment.Mapper.EmpMapper;
import com.tk.tliaswebmanagment.Service.EmpService;
import com.tk.tliaswebmanagment.pojo.Emp;
import com.tk.tliaswebmanagment.pojo.EmpQuerryParam;
import com.tk.tliaswebmanagment.pojo.PageResult;
import com.tk.tliaswebmanagment.pojo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class EmpServiceImpl implements EmpService {
    @Autowired
    EmpMapper empMapper;

    @Override
    public PageResult getEmpByPage(EmpQuerryParam empQuerryParam) {
        Integer page = empQuerryParam.getPage();
        Integer pageSize = empQuerryParam.getPageSize();
        PageHelper.startPage(page, pageSize);
        // 获取分页数据
        List<Emp> list = empMapper.getEmpByPage(empQuerryParam);
        Page<Emp> p=(Page<Emp>)list;
        return new PageResult(p.getTotal(), p.getResult());
    }
    
     @Override
    public Result addEmp(Emp emp) {
        // 设置创建时间和更新时间
        emp.setCreateDate(LocalDateTime.now());
        emp.setUpdateDate(LocalDateTime.now());
        empMapper.addEmp(emp);
        return Result.success();
    }
    
    @Override
    public Result updateEmp(Emp emp) {
        // 设置更新时间
        emp.setUpdateDate(LocalDateTime.now());
        empMapper.updateEmp(emp);
        return Result.success();
    }
    
    @Override
    public Result deleteEmps(List<Integer> ids) {
        empMapper.deleteEmps(ids);
        return Result.success();
    }
}
