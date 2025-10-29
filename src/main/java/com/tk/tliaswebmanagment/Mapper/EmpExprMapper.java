package com.tk.tliaswebmanagment.Mapper;

import com.tk.tliaswebmanagment.pojo.EmpExpr;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EmpExprMapper {
    @Insert("insert into tlias.emp_expr (emp_id, begin, `end` , company, job) values(#{empId}, #{begin}, #{end}, #{company}, #{job})")
    void insertEmpExpr(EmpExpr empExpr);

    @Delete("delete from tlias.emp_expr where emp_id = #{empId}")
    void deleteExprByEmpId(Integer empId);

    void insertBatch(List<EmpExpr> exprList);

    void deleteExprByEmpIds(List<Integer> ids);
}
