package com.tk.tliaswebmanagment.Service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.tk.tliaswebmanagment.Mapper.OperateLogMapper;
import com.tk.tliaswebmanagment.Service.OperateLogService;
import com.tk.tliaswebmanagment.pojo.OperateLog;
import com.tk.tliaswebmanagment.pojo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OperateLogServiceImpl implements OperateLogService {
    @Autowired
    private OperateLogMapper operateLogMapper;

    @Override
    public PageResult selectLogsPage(Integer page, Integer size) {
        PageHelper.startPage(page, size);
        List<OperateLog> list = operateLogMapper.selectPage();
        Page<OperateLog> p=(Page<OperateLog>)list;
        return new PageResult(p.getTotal(), p.getResult());
    }
}
