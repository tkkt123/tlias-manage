package com.tk.tliaswebmanagment.Mapper;

import com.tk.tliaswebmanagment.pojo.EmpExpr;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmpExprMapper {
    @Insert("insert into tlias.emp_expr (emp_id, begin, `end` , company, job) values(#{empId}, #{begin}, #{end}, #{company}, #{job})")
    void insertEmpExpr(EmpExpr empExpr);
}
