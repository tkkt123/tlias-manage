package com.tk.tliaswebmanagment.Mapper;

import com.tk.tliaswebmanagment.pojo.Clazz;
import com.tk.tliaswebmanagment.pojo.ClazzQueryParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface ClazzMapper {
    List<Clazz> getClazzsPage(@Param("clazzQueryParam") ClazzQueryParam clazzQueryParam, @Param("currentDate") LocalDate currentDate);
}
