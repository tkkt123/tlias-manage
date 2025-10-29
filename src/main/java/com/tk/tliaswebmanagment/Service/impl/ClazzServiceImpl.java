package com.tk.tliaswebmanagment.Service.impl;

import com.github.pagehelper.PageHelper;
import com.tk.tliaswebmanagment.Mapper.ClazzMapper;
import com.tk.tliaswebmanagment.Service.ClazzService;
import com.tk.tliaswebmanagment.pojo.Clazz;
import com.tk.tliaswebmanagment.pojo.ClazzQueryParam;
import com.tk.tliaswebmanagment.pojo.Result;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ClazzServiceImpl implements ClazzService {
    @Autowired
    ClazzMapper clazzMapper;

    @Override
    public List<Clazz> getClazzsPage(ClazzQueryParam clazzQueryParam) {
        PageHelper.startPage(clazzQueryParam.getPage(), clazzQueryParam.getPageSize());
        return clazzMapper.getClazzsPage(clazzQueryParam, LocalDate.now());
    }

    @Override
    public Result insertClazz(Clazz clazz) {
        clazz.setCreateTime(LocalDateTime.now());
        clazz.setUpdateTime(LocalDateTime.now());
        clazzMapper.insert(clazz);
        return Result.success();
    }

    @Override
    public Clazz getClazzById(Integer id) {
        return clazzMapper.selectById(id);
    }

    @Override
    public Result updateClazz(Clazz clazz) {
        clazz.setUpdateTime(LocalDateTime.now());
        clazzMapper.update(clazz);
        return Result.success();
    }
}
