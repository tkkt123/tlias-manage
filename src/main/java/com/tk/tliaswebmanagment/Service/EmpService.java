package com.tk.tliaswebmanagment.Service;

import com.tk.tliaswebmanagment.pojo.Emp;
import com.tk.tliaswebmanagment.pojo.EmpQuerryParam;
import com.tk.tliaswebmanagment.pojo.PageResult;
import com.tk.tliaswebmanagment.pojo.Result;

import java.util.List;

public interface EmpService {
    PageResult getEmpByPage(EmpQuerryParam empQuerryParam);

    Result insertEmp(Emp emp);
    
    Result updateEmp(Emp emp);

    Result deleteEmps(List<Integer> ids);

    Emp getEmpById(Integer id);

    List<Emp> getEmpList();
}
