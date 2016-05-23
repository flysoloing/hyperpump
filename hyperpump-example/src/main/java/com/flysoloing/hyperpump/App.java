package com.flysoloing.hyperpump;

import com.flysoloing.hyperpump.base.AbstractBaseTask;
import com.flysoloing.hyperpump.task.TaskConf;
import com.flysoloing.hyperpump.task.TaskNodeService;
import com.flysoloing.hyperpump.task.TaskScheduler;
import com.flysoloing.hyperpump.registry.RegistryCenter;
import com.flysoloing.hyperpump.registry.RegistryCenterConf;

/**
 * Hello world!
 *
 */
public class App {

    private RegistryCenterConf registryCenterConf = new RegistryCenterConf("localhost:4181", "hyperpump", 1000, 3, 3000);

    private RegistryCenter registryCenter = new RegistryCenter(registryCenterConf);

    private TaskConf taskConf = new TaskConf("hptasktest", AbstractBaseTask.class, "0/5 * * * * ?");

    public static void main( String[] args ) {
        new App().init();
    }

    public void init() {
        //初始化注册中心
        registryCenter.init();
        taskConf.setDescription("this is my first test");

        new TaskNodeService(registryCenter, taskConf).registerNodeInfo();
//        new TaskNodeService(registryCenter, taskConf).registerNodeInfo();
//        new TaskNodeService(registryCenter, taskConf).registerNodeInfo();

        new TaskScheduler(registryCenter, taskConf).init();
//        new TaskScheduler(registryCenter, taskConf).init();
//        new TaskScheduler(registryCenter, taskConf).init();

        //注册任务节点
//        String nodePath = "/taskSet/task1";
//        String value = "";
//        registryCenter.persist(nodePath, value);
//
//        nodePath = "/taskSet/task1/taskClass";
//        value = "Test.class";
//        registryCenter.persist(nodePath, value);
//
//        String t = registryCenter.get(nodePath);
//        System.out.println(t);
//
//        value = "Test.class;Test2.class;";
//        registryCenter.update(nodePath, value);
//
//        t = registryCenter.get(nodePath);
//        System.out.println(t);
//
//        List<String> childrenPaht = registryCenter.getChildrenPaths("/taskSet");
//        for (String path : childrenPaht) {
//            System.out.println(path);
//        }

        //注册调度器节点

        //注册执行器节点

        //关闭注册中心
//        registryCenter.close();
    }
}
