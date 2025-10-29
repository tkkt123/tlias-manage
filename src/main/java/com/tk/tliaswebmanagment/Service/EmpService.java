package com.tk.tliaswebmanagment.Service;

import com.tk.tliaswebmanagment.pojo.*;

import java.util.List;
import java.util.Map;

public interface EmpService {
    PageResult getEmpByPage(EmpQuerryParam empQuerryParam);

    Result insertEmp(Emp emp);
    
    Result updateEmp(Emp emp);

    Result deleteEmps(List<Integer> ids);

    Emp getEmpById(Integer id);

    List<Emp> getEmpList();

    JobOption getEmpJobData();

    List<Map<String, Object>> getEmpGenderData();
}
