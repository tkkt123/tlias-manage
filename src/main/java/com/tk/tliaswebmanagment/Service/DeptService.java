package com.tk.tliaswebmanagment.Service;

import com.tk.tliaswebmanagment.pojo.Dept;

import java.util.List;

public interface DeptService {
    List<Dept> findall();

    void deleteDept(int id);

    void addDept(Dept dept);

    void updateDept(Dept dept);

    Dept getDeptById(Integer id);
}
