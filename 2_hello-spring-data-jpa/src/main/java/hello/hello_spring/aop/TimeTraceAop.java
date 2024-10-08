package hello.hello_spring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TimeTraceAop {

    @Around("execution(* hello.hello_spring..*(..))")
    public Object execute(ProceedingJoinPoint jointPoint) throws Throwable {
        long start = System.currentTimeMillis();

        System.out.println("START: " + jointPoint.toLongString());
        try {
            return jointPoint.proceed();
        } finally {
            long end = System.currentTimeMillis();
            System.out.println("END: " + jointPoint.toString() + " => " + ( end - start));
        }
    }
}
