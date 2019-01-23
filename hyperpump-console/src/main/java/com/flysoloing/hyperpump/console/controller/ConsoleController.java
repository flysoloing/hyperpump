package com.flysoloing.hyperpump.console.controller;

import com.flysoloing.commons.enhancement.VelocityViewResolverSupport;
import com.flysoloing.hyperpump.console.domain.ZK;
import com.flysoloing.hyperpump.console.service.ZKService;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.view.velocity.VelocityConfigurer;
import org.springframework.web.servlet.view.velocity.VelocityLayoutView;
import org.springframework.web.servlet.view.velocity.VelocityLayoutViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author laitao
 * @date 2016-06-22 17:27:29
 */
@Controller
public class ConsoleController extends VelocityViewResolverSupport {

    private static Logger logger = LoggerFactory.getLogger(ConsoleController.class);

    @Autowired
    private ZKService zkService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        logger.info("访问首页");
        List<ZK> zkList = zkService.selectAll();
        logger.info("The zk namespace size is {}", zkList.size());
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
        model.put("test", "embed velocity engine render测试");
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
        model.put("test", "embed velocity engine render222222222测试");
        try {
            VelocityLayoutView velocityLayoutView = (VelocityLayoutView)velocityLayoutViewResolver.resolveViewName("index", new Locale("en"));
            velocityLayoutView.render(model, request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        PrintWriter stringWriter = null;
        try {
            stringWriter = response.getWriter();
            return stringWriter.toString();//TODO
        } catch (IOException e) {
            e.printStackTrace();
            return "9999999";
        } finally {
            if (stringWriter != null) {
                stringWriter.close();
            }
        }

    }

    @RequestMapping(value = "/renderTest3", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, String> renderTest3(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("test", "embed velocity engine render3333333333333测试");
        String velocityConfigurerName = "velocityConfigurer";
        String name = "index";
        Map<String, String> map = new HashMap<String, String>();
        String result = null;
        try {
            result = render(velocityConfigurerName, name, model, request, response);
        } catch (Exception e) {
            logger.error("", e);
        }
        map.put("result", result);
        return map;
    }

    @RequestMapping(value = "/navTest", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, String> navTest(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("test", "navTest|embed velocity engine render3333333333333测试");
        String velocityConfigurerName = "velocityConfigurer";
        String name = "taskNodeList";
        Map<String, String> map = new HashMap<String, String>();
        String result = null;
        try {
            result = render(velocityConfigurerName, name, model, request, response);
        } catch (Exception e) {
            logger.error("", e);
        }
        map.put("result", result);
        return map;
    }

    @RequestMapping(value = "/namespace/{namespace}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, String> namespace(@PathVariable String namespace, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("test", "navTest|embed velocity engine render3333333333333测试");
        model.put("namespace", namespace);
        String velocityConfigurerName = "velocityConfigurer";
        String name = "namespace";
        Map<String, String> map = new HashMap<String, String>();
        String result = null;
        try {
            result = render(velocityConfigurerName, name, model, request, response);
        } catch (Exception e) {
            logger.error("", e);
        }
        map.put("result", result);
        return map;
    }

    @RequestMapping(value = "/configure/{namespace}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, String> configure(@PathVariable String namespace, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("test", "navTest|embed velocity engine render3333333333333测试");
        model.put("namespace", namespace);
        String velocityConfigurerName = "velocityConfigurer";
        String name = "configure";
        Map<String, String> map = new HashMap<String, String>();
        String result = null;
        try {
            result = render(velocityConfigurerName, name, model, request, response);
        } catch (Exception e) {
            logger.error("", e);
        }
        map.put("result", result);
        return map;
    }
}
