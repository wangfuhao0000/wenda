package cug.wfh.wenda.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Controller
public class IndexController {

    private static Logger logger = LoggerFactory.getLogger(IndexController.class);

    @RequestMapping(path = {"/user/{userid}"}, method = RequestMethod.GET)
    @ResponseBody
    public String userInfo(@PathVariable("userid") int userId,
                           @RequestParam(name = "page", defaultValue = "1") int page) {
        logger.info("execute userInfo method");
        return String.format("You are user %d in page %d", userId, page);
    }

    @RequestMapping(path = {"/admin"}, method = RequestMethod.GET)
    @ResponseBody
    public String admin(@RequestParam("key") String key) {
        if ("admin".equals(key)) {
            logger.info("admin method");
            return "hello admin";
        }
        throw new IllegalArgumentException("参数不对");
    }

    //可以自定义处理错误的方式
    @ExceptionHandler
    @ResponseBody
    public String error(Exception e) {
        return "error: " + e.getMessage();
    }

}
