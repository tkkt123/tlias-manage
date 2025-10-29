package com.tk.tliaswebmanagment.Mapper;

import com.tk.tliaswebmanagment.pojo.Emp;
import com.tk.tliaswebmanagment.pojo.EmpQuerryParam;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface EmpMapper {
    List<Emp> getEmpByPage(EmpQuerryParam empQuerryParam);

    // 插入员工时，返回自增的id
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("INSERT INTO emp (username, password, name, gender, phone, job, salary, image, entry_date, dept_id, create_time, update_time) " +
            "VALUES (#{username}, #{password}, #{name}, #{gender}, #{phone}, #{job}, #{salary}, #{image}, #{entryDate}, #{deptId}, #{createTime}, #{updateTime})")
    void insertEmp(Emp emp);

    void updateEmp(Emp emp);

    void deleteEmps(@Param("ids") List<Integer> ids);

    Emp getEmpById(Integer id);

    List<Emp> getEmpList();

    @MapKey("pos")
    List<Map<String, Object>> countEmpJobData();

    @MapKey("name")
    List<Map<String, Object>> countEmpGenderData();
}
