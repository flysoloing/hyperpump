package com.flysoloing.hyperpump.console.controller;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationObjectSupport;
import org.springframework.web.servlet.view.velocity.VelocityConfigurer;
import org.springframework.web.servlet.view.velocity.VelocityLayoutView;
import org.springframework.web.servlet.view.velocity.VelocityLayoutViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author laitao
 * @since 2016-06-22 17:27:29
 */
@Controller
public class ConsoleController extends WebApplicationObjectSupport {

    private static Logger logger = LoggerFactory.getLogger(ConsoleController.class);

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        logger.info("访问首页");
        return "index";
    }

    @RequestMapping(value = "/renderTest", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, String> renderTest() {
        WebApplicationContext webApplicationContext = getWebApplicationContext();
        VelocityConfigurer velocityConfigurer = (VelocityConfigurer)webApplicationContext.getBean("velocityConfigurer");
        VelocityEngine velocityEngine = velocityConfigurer.getVelocityEngine();
        Template template = velocityEngine.getTemplate("index.vm");
        StringWriter stringWriter = new StringWriter();
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("test", "embed velocity engine render");
        Context velocityContext = new VelocityContext(model);
        template.merge(velocityContext, stringWriter);
        String result = stringWriter.toString();
        Map<String, String> map = new HashMap<String, String>();
        map.put("result", result);
        return map;
    }

    @RequestMapping(value = "/renderTest2", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, String> renderTest2(HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> map = new HashMap<String, String>();
        String result = getRenderResult(request, response);
        map.put("result", result);
        return map;
    }

    private String getRenderResult(HttpServletRequest request, HttpServletResponse response) {
        WebApplicationContext webApplicationContext = getWebApplicationContext();
        VelocityLayoutViewResolver velocityLayoutViewResolver = (VelocityLayoutViewResolver)webApplicationContext.getBean("velocityLayoutViewResolver");
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("test", "embed velocity engine render222222222");
        try {
            VelocityLayoutView velocityLayoutView = (VelocityLayoutView)velocityLayoutViewResolver.resolveViewName("index", new Locale("en"));
            velocityLayoutView.render(model, request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        PrintWriter stringWriter;
        try {
            stringWriter = response.getWriter();
            return stringWriter.toString();//TODO
        } catch (IOException e) {
            e.printStackTrace();
            return "9999999";
        }

    }
}
