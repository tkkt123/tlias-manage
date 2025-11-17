package com.tk.tliaswebmanagment.Mapper;

import com.tk.tliaswebmanagment.pojo.Clazz;
import com.tk.tliaswebmanagment.pojo.ClazzQueryParam;
import org.apache.ibatis.annotations.*;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface ClazzMapper {
    List<Clazz> getClazzsPage(@Param("clazzQueryParam") ClazzQueryParam clazzQueryParam, @Param("currentDate") LocalDate currentDate);

    @Insert("insert into clazz (name, room, subject, master_id, begin_date, end_date, create_time, update_time) " +
            "values (#{name}, #{room}, #{subject}, #{masterId}, #{beginDate}, #{endDate}, #{createTime}, #{updateTime}) ")
    void insert(Clazz clazz);

    @Select("select * from clazz where id = #{id}")
    Clazz selectById(Integer id);

    void update(Clazz clazz);

    @Delete("delete from clazz where id = #{id}")
    void delete(Integer id);

    @Select("select count(*) from student where clazz_id = #{id}")
    long countClazzStudent(Integer id);

    @Select("select * from clazz")
    List<Clazz> getClazzsList();
}
