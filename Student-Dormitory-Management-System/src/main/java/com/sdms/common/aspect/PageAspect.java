package com.sdms.common.aspect;

import com.sdms.common.page.Page;
import com.sdms.common.page.PageRequest;
import lombok.val;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class PageAspect {

    @Pointcut("@annotation(com.sdms.common.annotation.PageRequestCheck)")
    public void withAnnotation() {
    }

    @Pointcut("execution(public com.sdms.common.page.Page com.sdms.service.impl.*.*(com.sdms.common.page.PageRequest))")
    public void matchMethod() {
    }
    //该代码片段的作用是在匹配到带有 @PageRequestCheck 注解的方法或指定方法的执行时，
    // 进行分页请求的校验。根据分页请求的参数，
    // 计算出偏移量和限制数，并进行条件判断。如果不满足分页条件，
    // 则返回一个空的 Page 对象；否则，继续执行切点方法并返回其结果。
    // 这种方式可以通过切面逻辑实现通用的分页请求校验，避免无效的分页请求导致不必要的数据查询和处理。
    @Around("withAnnotation()||matchMethod()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        val pageRequest = (PageRequest) joinPoint.getArgs()[0];
        val limit = pageRequest.getLimit();
        val offset = (pageRequest.getPage() - 1) * limit;
        if (offset < 0 || limit <= 0) {
            return Page.empty();
        } else {
            return joinPoint.proceed();
        }
    }
//    val limit = pageRequest.getLimit();
//    获取分页请求中的限制数。getLimit() 是一个方法，
//    用于获取分页请求中设置的每页记录数。
//
//    val offset = (pageRequest.getPage() - 1) * limit; 计算偏移量。
//    getPage() 用于获取分页请求中设置的页码。偏移量是根据页码和限制数计算得出的，
//    用于确定从数据源中获取数据的起始位置。
//
//    if (offset < 0 || limit <= 0) { 进行条件判断，检查偏移量是否小于 0 或者限制数是否小于等于 0。
//
//    如果偏移量小于 0 或者限制数小于等于 0，表示分页请求不合法或无效。在这种情况下，return Page.empty();
//    返回一个空的 Page 对象，表示没有符合条件的数据。
//
//    如果偏移量大于等于 0 并且限制数大于 0，表示分页请求合法。
//    在这种情况下，return joinPoint.proceed();
//    继续执行切点方法，并返回切点方法的执行结
}
