package com.longer.aspect;

import com.longer.bean.RecordBean;
import com.longer.service.RecordService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

@Aspect
@Component
public class ExRequestAspect {

    private static final Logger log = LoggerFactory.getLogger(ExRequestAspect.class);
    public static long startTime;
    public static long endTime;
    public long recordid;

    @Autowired
    RecordService recordService;

    RecordBean recordBean = new RecordBean();

    /*@PointCut注解表示表示横切点，哪些方法需要被横切*/
    /*切点表达式*/
    @Pointcut("execution(public * com.longer.controller.ExController.*(..))")
    /*切点签名*/
    public void print() {

    }

    /*@Before注解表示在具体的方法之前执行*/
    @Before("print()")
    public void before(JoinPoint joinPoint) {
        log.info("前置切面before……");
        startTime = System.currentTimeMillis();
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String requestURI = request.getRequestURI();
        String remoteAddr = request.getRemoteAddr();   //这个方法取客户端ip"不够好"
        String requestMethod = request.getMethod();
        String declaringTypeName = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        StringBuilder ars = new StringBuilder();
        for(Object o : args) {
            ars.append(o.toString() + " ; ");
        }
        log.info("请求url=" + requestURI + ",客户端ip=" + remoteAddr + ",请求方式=" + requestMethod + ",请求的类名=" + declaringTypeName + ",方法名=" + methodName + ",入参=" + ars.toString());

        recordBean.setRequrl(requestURI);
        recordBean.setReqAddress(remoteAddr);
        recordBean.setReqMethod(requestMethod);
        recordBean.setReqClass(declaringTypeName);
        recordBean.setReqClassMethod(methodName);
        recordBean.setReqClassMethodColumn(ars.toString());
        recordService.addRecord(recordBean);
        recordBean.setId(recordService.selectMaxId().getId());
    }

    /*@After注解表示在方法执行之后执行*/
    @After("print()")
    public void after() {
        endTime = System.currentTimeMillis() - startTime;
        log.info("后置切面after……");
    }

    /*@AfterReturning注解用于获取方法的返回值*/
    @AfterReturning(pointcut = "print()", returning = "object")
    public void getAfterReturn(Object object) {
        log.info("本次接口耗时={}ms", endTime);
        log.info("afterReturning={}", object.toString());
        recordBean.setTotaltime(Long.toString(endTime) + "ms");
        recordBean.setResContent(object.toString());
        recordBean.setOperatedatetime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        recordService.updateRecordById(recordBean);
    }
}
