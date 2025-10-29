package com.tk.tliaswebmanagment.Service;

import com.tk.tliaswebmanagment.pojo.Clazz;
import com.tk.tliaswebmanagment.pojo.ClazzQueryParam;
import com.tk.tliaswebmanagment.pojo.Result;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ClazzService {
    List<Clazz> getClazzsPage(ClazzQueryParam clazzQueryParam);

    Result insertClazz(Clazz clazz);

    Clazz getClazzById(Integer id);

    Result updateClazz(Clazz clazz);
}
