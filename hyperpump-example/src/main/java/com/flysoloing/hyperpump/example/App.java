package com.flysoloing.hyperpump.example;

import com.flysoloing.hyperpump.base.AbstractBaseTask;
import com.flysoloing.hyperpump.executor.ExecutorNodeConf;
import com.flysoloing.hyperpump.executor.ExecutorNodeService;
import com.flysoloing.hyperpump.scheduler.SchedulerNodeConf;
import com.flysoloing.hyperpump.scheduler.SchedulerNodeService;
import com.flysoloing.hyperpump.task.TaskNodeConf;
import com.flysoloing.hyperpump.task.TaskNodeScheduler;
import com.flysoloing.hyperpump.task.TaskNodeService;
import com.flysoloing.hyperpump.registry.RegistryCenter;
import com.flysoloing.hyperpump.registry.RegistryCenterConf;

/**
 * Hello world!
 *
 */
public class App {

    private RegistryCenterConf registryCenterConf = new RegistryCenterConf("localhost:4181", "hyperpump", 1000, 3, 3000);

    private RegistryCenter registryCenter = new RegistryCenter(registryCenterConf);

    private TaskNodeConf taskNodeConf = new TaskNodeConf("hptasktest01", AbstractBaseTask.class, "0/5 * * * * ?");

    private SchedulerNodeConf schedulerNodeConf = new SchedulerNodeConf("schedulerNodeConf01");

    private ExecutorNodeConf executorNodeConf = new ExecutorNodeConf("executorNodeConf01");

    public static void main( String[] args ) {
        new App().init();
    }

    public void init() {
        //初始化注册中心
        registryCenter.init();
        taskNodeConf.setDescription("this is my first test");

        //初始化任务节点服务
        new TaskNodeService(registryCenter, taskNodeConf).init();
        //初始化任务节点调度器
        new TaskNodeScheduler(registryCenter, taskNodeConf).init();

        //初始化调度器节点服务
        new SchedulerNodeService(registryCenter, schedulerNodeConf).init();

        //初始化执行器节点服务
        new ExecutorNodeService(registryCenter, executorNodeConf).init();

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
