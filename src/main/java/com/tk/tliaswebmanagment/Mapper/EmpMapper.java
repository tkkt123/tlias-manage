package com.tk.tliaswebmanagment.Mapper;

import com.tk.tliaswebmanagment.pojo.Emp;
import com.tk.tliaswebmanagment.pojo.EmpQuerryParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface EmpMapper {
    List<Emp> getEmpByPage(EmpQuerryParam empQuerryParam);

    void addEmp(Emp emp);
    
    void updateEmp(Emp emp);

    void deleteEmps(@Param("ids") List<Integer> ids);
}
