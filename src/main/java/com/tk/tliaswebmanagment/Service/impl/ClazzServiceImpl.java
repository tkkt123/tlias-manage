package com.tk.tliaswebmanagment.Service.impl;

import com.github.pagehelper.PageHelper;
import com.tk.tliaswebmanagment.Mapper.ClazzMapper;
import com.tk.tliaswebmanagment.Service.ClazzService;
import com.tk.tliaswebmanagment.pojo.Clazz;
import com.tk.tliaswebmanagment.pojo.ClazzQueryParam;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ClazzServiceImpl implements ClazzService {
    @Autowired
    ClazzMapper clazzMapper;

    @Override
    public List<Clazz> getClazzsPage(ClazzQueryParam clazzQuerryParam) {
        PageHelper.startPage(clazzQuerryParam.getPage(), clazzQuerryParam.getPageSize());
        return clazzMapper.getClazzsPage(clazzQuerryParam, LocalDate.now());
    }
}
