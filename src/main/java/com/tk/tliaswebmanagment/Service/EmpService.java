package com.tk.tliaswebmanagment.Service;

import com.tk.tliaswebmanagment.pojo.Emp;
import com.tk.tliaswebmanagment.pojo.EmpQuerryParam;
import com.tk.tliaswebmanagment.pojo.PageResult;
import com.tk.tliaswebmanagment.pojo.Result;

import java.time.LocalDate;
import java.util.List;

public interface EmpService {
    PageResult getEmpByPage(EmpQuerryParam empQuerryParam);

    Result addEmp(Emp emp);
    
    Result updateEmp(Emp emp);

    Result deleteEmps(List<Integer> ids);
}
