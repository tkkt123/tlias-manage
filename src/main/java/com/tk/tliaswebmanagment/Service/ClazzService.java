package com.tk.tliaswebmanagment.Service;

import com.tk.tliaswebmanagment.pojo.Clazz;
import com.tk.tliaswebmanagment.pojo.ClazzQueryParam;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ClazzService {
    List<Clazz> getClazzsPage(ClazzQueryParam clazzQueryParam);
}
