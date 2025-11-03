package com.tk.tliaswebmanagment.Service;


import com.tk.tliaswebmanagment.pojo.OperateLog;
import com.tk.tliaswebmanagment.pojo.PageResult;

import java.util.List;

public interface OperateLogService {

    PageResult selectLogsPage(Integer page, Integer size);
}
