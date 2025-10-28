package com.tk.tliaswebmanagment.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {
    private Integer code;
    private String msg;
    private Object data;

    public static Result success(){
        Result result = new Result();
        result.setCode(1);
        result.setMsg("success");
        return result;
    }

    public static Result success(Object object){
        Result result = new Result();
        result.setData(object);
        result.setCode(1);
        result.setMsg("success");
        return result;
    }

    public static Result error(String message){
        Result result = new Result();
        result.setCode(0);
        result.setMsg("error");
        return result;
    }


}
