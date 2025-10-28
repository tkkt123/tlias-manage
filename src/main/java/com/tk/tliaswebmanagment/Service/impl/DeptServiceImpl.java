package com.tk.tliaswebmanagment.Service.impl;

import com.tk.tliaswebmanagment.Mapper.DeptMapper;
import com.tk.tliaswebmanagment.Service.DeptService;
import com.tk.tliaswebmanagment.pojo.Dept;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DeptServiceImpl implements DeptService {
    @Autowired
    DeptMapper deptMapper;

    @Override
    public List<Dept> findall() {
        return deptMapper.findall();
    }

    @Override
    public void deleteDept(int id) {
        deptMapper.deleteDept(id);
    }

    @Override
    public void insertDept(Dept dept) {
        dept.setCreateTime(LocalDateTime.now());
        dept.setUpdateTime(LocalDateTime.now());
        deptMapper.insertDept(dept);

    }

    @Override
    public void updateDept(Dept dept) {
        dept.setUpdateTime(LocalDateTime.now());
        deptMapper.updateDept(dept);
    }

    @Override
    public Dept getDeptById(Integer id) {
        return deptMapper.getDeptById(id);
    }
}
