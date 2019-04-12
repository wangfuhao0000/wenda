package cug.wfh.wenda.aspect;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class LogAspect {

    private static Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @Before("execution(* cug.wfh.wenda.controller.*.*(..))")
    public void Before() {
        logger.info("exe before");
    }

    @Before("execution(* cug.wfh.wenda.controller.*.*(..))")
    public void BeforeMethod() {
        logger.info("before method");
    }

    @After("execution(* cug.wfh.wenda.controller.*.*(..))")
    public void AfterMethod() {
        logger.info("after method");
    }
}
