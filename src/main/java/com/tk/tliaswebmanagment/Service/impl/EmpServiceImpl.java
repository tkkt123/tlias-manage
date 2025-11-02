package com.tk.tliaswebmanagment.Service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.tk.tliaswebmanagment.Mapper.EmpExprMapper;
import com.tk.tliaswebmanagment.Mapper.EmpMapper;
import com.tk.tliaswebmanagment.Service.EmpService;
import com.tk.tliaswebmanagment.pojo.*;
import com.tk.tliaswebmanagment.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class EmpServiceImpl implements EmpService {
    @Autowired
    EmpMapper empMapper;
    @Autowired
    EmpExprMapper empExprMapper;

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

    @Transactional
    @Override
    public Result insertEmp(Emp emp) {
        // 设置创建时间和更新时间
        emp.setCreateTime(LocalDateTime.now());
        emp.setUpdateTime(LocalDateTime.now());
        // 插入员工
        empMapper.insertEmp(emp);
        // 插入员工工作经历
        if (emp.getExprList() != null && !emp.getExprList().isEmpty()) {
//            循环插入
//            for (EmpExpr empExpr : emp.getExprList()) {
//                empExpr.setEmpId(emp.getId());
//                empExprMapper.insertEmpExpr(empExpr);
//            }
//             批量插入
            for (EmpExpr empExpr : emp.getExprList()) {
                empExpr.setEmpId(emp.getId());
            }
            empExprMapper.insertBatch(emp.getExprList());
            return Result.success();
        }
        else{
            System.out.println("员工" + emp.getName() + "没有工作经历");
        }
        return Result.success();
    }

    @Transactional
    @Override
    public Result updateEmp(Emp emp) {
        //设置更新时间
        emp.setUpdateTime(LocalDateTime.now());
        empMapper.updateEmp(emp);
        // 更新员工工作经历(先删除再插入)
        if (emp.getExprList() != null && !emp.getExprList().isEmpty()) {
            empExprMapper.deleteExprByEmpId(emp.getId());
            for (EmpExpr empExpr : emp.getExprList()) {
                empExpr.setEmpId(emp.getId());
            }
            empExprMapper.insertBatch(emp.getExprList());
        }
        return Result.success();
    }

    @Transactional
    @Override
    public Result deleteEmps(List<Integer> ids) {
        empMapper.deleteEmps(ids);
        // 删除员工工作经历
        empExprMapper.deleteExprByEmpIds(ids);
        return Result.success();
    }

    @Override
    public Emp getEmpById(Integer id) {
        return empMapper.getEmpById(id);
    }

    @Override
    public List<Emp> getEmpList() {
        return empMapper.getEmpList();
    }


    // 获取员工岗位数据
    @Override
    public JobOption getEmpJobData() {
        List<Map<String,Object>> list = empMapper.countEmpJobData();
        List jobList = list.stream().map(map -> map.get("pos")).toList();
        List numList = list.stream().map(map -> map.get("num")).toList();
        return new JobOption(jobList, numList);
    }

    @Override
    public List<Map<String, Object>> getEmpGenderData() {
        return empMapper.countEmpGenderData();
    }

    @Override
    public LoginInfo login(Emp emp) {
        LoginInfo info = empMapper.selectByUsernameAndPassword(emp);
        if(info!=null){
            String token= JwtUtils.createToken(info.getId(),info.getUsername());
            info.setToken(token);
            return info;
        }
        return null;
    }
}
