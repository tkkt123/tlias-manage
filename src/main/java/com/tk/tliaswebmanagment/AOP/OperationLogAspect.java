package com.tk.tliaswebmanagment.AOP;

import com.tk.tliaswebmanagment.Mapper.OperateLogMapper;
import com.tk.tliaswebmanagment.pojo.OperateLog;
import com.tk.tliaswebmanagment.utils.CurrentHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;

@Slf4j
@Aspect//切面
@Component
public class OperationLogAspect {
    @Autowired
    private OperateLogMapper operateLogMapper;

    //@Around("execution(* com.tk.tliaswebmanagment.Controller.*.*(..))")
    @Around("@annotation(com.tk.tliaswebmanagment.anno.Log)")
    public Object logOperation(ProceedingJoinPoint joinPoint) throws Throwable {

        long startTime = System.currentTimeMillis();

        Object result = joinPoint.proceed();

        long endTime = System.currentTimeMillis();
        //构建日志对象
        OperateLog operateLog = new OperateLog();
        operateLog.setOperateTime(LocalDateTime.now());
        operateLog.setCostTime(endTime - startTime);
        operateLog.setMethodParams(Arrays.toString(joinPoint.getArgs()));
        operateLog.setClassName(joinPoint.getTarget().getClass().getName());
        operateLog.setMethodName(joinPoint.getSignature().getName());
        operateLog.setReturnValue(result!=null?result.toString():"void");
        operateLog.setOperateEmpId(getOperateEmpId());

        operateLogMapper.insert(operateLog);
        return result;
    }

    private Integer getOperateEmpId() {
        return CurrentHolder.getCurrentId();
    }
}












//五种通知类型
//@Around:环绕通知，目标方法前后都执行，要自己调用proceed方法，返回类型 要是Object
//@Before：之前执行                                          |
//@After：之后执行                                           |
//@AfterReturning：结果返回后执行，但要是异常就不执行              |-->返回值都是void
//@AfterThrowing：只有出现异常才会运行                          |

//切入点表达式：
//两种：基于方法签名execution，基于@annotation注解
//execution（访问修饰符？ 返回值 包名.类名.？方法名（方法参数） throws 异常？）
//annotation里面就要放你需要的切入的类的注解，所以要自己定义注解来标识