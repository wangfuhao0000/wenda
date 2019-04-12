package cug.wfh.wenda.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestCOntroller {

    private static Logger logger = LoggerFactory.getLogger(TestCOntroller.class);

    @RequestMapping("/test")
    @ResponseBody
    public String testAspect() {
        logger.info("testAspect method");
        return "test aspect";
    }
}
