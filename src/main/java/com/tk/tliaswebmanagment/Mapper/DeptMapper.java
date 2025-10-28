package com.tk.tliaswebmanagment.Mapper;

import com.tk.tliaswebmanagment.pojo.Dept;
import org.apache.ibatis.annotations.*;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Mapper
public interface DeptMapper {
    @Select("select * from dept order by update_time")
    List<Dept> findall();

    @Delete("delete from dept where id=#{id}")
    void deleteDept(int id);

    @Insert("insert into dept(name,create_time,update_time) values(#{name},#{createTime},#{updateTime})")
    void addDept(Dept dept);

    @Update("update dept set name=#{name},update_time=#{updateTime} where id=#{id}")
    void updateDept(Dept dept);

    @Select("select * from dept where id=#{id}")
    Dept getDeptById(Integer id);
}
