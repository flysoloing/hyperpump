package com.flysoloing.hyperpump.console.controller;

import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.velocity.VelocityConfigurer;

/**
 * @author laitao
 * @since 2016-06-22 17:27:29
 */
@Controller
public class ConsoleController {

    private static Logger logger = LoggerFactory.getLogger(ConsoleController.class);

    private VelocityConfigurer velocityConfigurer;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        logger.info("访问首页");
        VelocityEngine velocityEngine = velocityConfigurer.getVelocityEngine();
        return "index";
    }

    public VelocityConfigurer getVelocityConfigurer() {
        return velocityConfigurer;
    }

    public void setVelocityConfigurer(VelocityConfigurer velocityConfigurer) {
        this.velocityConfigurer = velocityConfigurer;
    }
}
