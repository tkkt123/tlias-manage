package com.tk.tliaswebmanagment.Service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.tk.tliaswebmanagment.Mapper.ClazzMapper;
import com.tk.tliaswebmanagment.Service.ClazzService;
import com.tk.tliaswebmanagment.pojo.Clazz;
import com.tk.tliaswebmanagment.pojo.ClazzQueryParam;
import com.tk.tliaswebmanagment.pojo.PageResult;
import com.tk.tliaswebmanagment.pojo.Result;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClazzServiceImpl implements ClazzService {
    @Autowired
    ClazzMapper clazzMapper;

    @Override
    public PageResult getClazzsPage(ClazzQueryParam clazzQueryParam) {
        PageHelper.startPage(clazzQueryParam.getPage(), clazzQueryParam.getPageSize());
        List<Clazz> clazzs = clazzMapper.getClazzsPage(clazzQueryParam, LocalDate.now());
        Page<Clazz> p=(Page<Clazz>)clazzs;
        return new PageResult(p.getTotal(), p.getResult());
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

    @Transactional
    @Override
    public Result deleteClazz(Integer id) {
        if (id == null) {
            return Result.error("请选择要删除的班级");
        }
        long count = clazzMapper.countClazzStudent(id);
        if (count > 0) {
            return Result.error("对不起, 该班级下有学生, 不能直接删除");
        }
        clazzMapper.delete(id);
        return Result.success();
    }

    @Override
    public List<Clazz> getClazzsList() {
        return clazzMapper.getClazzsList();
    }
}
