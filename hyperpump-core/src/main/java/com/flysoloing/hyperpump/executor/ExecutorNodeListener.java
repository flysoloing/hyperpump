package com.flysoloing.hyperpump.executor;

import com.flysoloing.hyperpump.common.Constants;
import com.flysoloing.hyperpump.common.TaskStatus;
import com.flysoloing.hyperpump.exception.HPExceptionHandler;
import com.flysoloing.hyperpump.listener.AbstractNodeListener;
import com.flysoloing.hyperpump.registry.RegistryCenter;
import com.flysoloing.hyperpump.scheduler.SchedulerNode;
import com.flysoloing.hyperpump.util.NodeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 执行器节点监听器
 *
 * @author laitao
 * @since 2016-05-26 01:41:36
 */
public class ExecutorNodeListener extends AbstractNodeListener<ExecutorNode> {

    private static final Logger logger = LoggerFactory.getLogger(ExecutorNodeService.class);

    public ExecutorNodeListener(RegistryCenter registryCenter, ExecutorNode executorNode) {
        super(registryCenter, executorNode);
    }

    protected void dataChanged(RegistryCenter registryCenter, ExecutorNode executorNode, TreeCacheEvent event, String path) {
        ChildData data = event.getData();
        TreeCacheEvent.Type type = event.getType();
        if (data != null) {
            String value = new String(data.getData(), Charset.forName(Constants.CHARSET_NAME_UTF8));
            logger.info("The executor node listener - '{}' received event, type = {}, path = {}, value = {}", executorNode.getRootNodePath(), type, path, value);

            String taskNodeName = StringUtils.isBlank(path) ? "" : NodeUtils.parseTaskNodeName(path);

            String regEx = "/EXECUTORS/EXECUTOR_.*/taskArea/TASK_.*/taskStatus";
            Pattern pattern = Pattern.compile(regEx);
            Matcher matcher = pattern.matcher(path);

            //条件：type = NODE_ADDED, path = /EXECUTORS/EXECUTOR_${IP:PID:OBJ_NAME}/taskArea/TASK_${taskName}/taskStatus, value = running
            if (matcher.matches() && type == TreeCacheEvent.Type.NODE_ADDED && value.equals(TaskStatus.RUNNING.getStatus())) {
                executeTaskClass(registryCenter, executorNode, taskNodeName);
            }

            //条件：type = NODE_UPDATED, path = /EXECUTORS/EXECUTOR_${IP:PID:OBJ_NAME}/taskArea/TASK_${taskName}/taskStatus, value = completed
            if (matcher.matches() && type == TreeCacheEvent.Type.NODE_UPDATED && value.equals(TaskStatus.COMPLETED.getStatus())) {
                resetTaskStatus(registryCenter, executorNode, taskNodeName);
                clearTaskArea(registryCenter, executorNode, taskNodeName);
            }
        }
    }

    /**
     * 执行任务
     *
     * @param registryCenter 注册中心
     * @param executorNode 执行器节点
     * @param taskNodeName 任务节点名称
     */
    private void executeTaskClass(RegistryCenter registryCenter, ExecutorNode executorNode, String taskNodeName) {
        //开始执行对应的TaskClass任务，任务执行完后回调更新ExecutorNode节点的状态
        logger.info("executeTaskClass() {}", taskNodeName);
        //TODO
        String taskClassName = registryCenter.get(executorNode.getTaskClassNodePath(taskNodeName));
        if (StringUtils.isBlank(taskClassName)) {
            logger.info("the task class is blank");
            return;
        }
        try {
            Class taskClass = Class.forName(taskClassName);
            logger.info("execute task class - '{}'", taskClass.getCanonicalName());
            //TODO 通过反射方式执行，两种方式：类似Spring的ReflectUtils工具类和Guava的Reflection工具类
            Constructor constructor = taskClass.getConstructor();
            Object obj = constructor.newInstance();
        } catch (ClassNotFoundException e) {
            logger.error("ClassNotFoundException", e);
            HPExceptionHandler.handleException(e);
        } catch (NoSuchMethodException e) {
            logger.error("NoSuchMethodException", e);
            HPExceptionHandler.handleException(e);
        } catch (InvocationTargetException e) {
            logger.error("InvocationTargetException", e);
            HPExceptionHandler.handleException(e);
        } catch (InstantiationException e) {
            logger.error("InstantiationException", e);
            HPExceptionHandler.handleException(e);
        } catch (IllegalAccessException e) {
            logger.error("IllegalAccessException", e);
            HPExceptionHandler.handleException(e);
        }
    }

    /**
     * 更新对应的SchedulerNode节点的任务状态为已完成（completed）
     *
     * @param registryCenter 注册中心
     * @param executorNode 执行器节点
     * @param taskNodeName 任务节点名称
     */
    private void resetTaskStatus(RegistryCenter registryCenter, ExecutorNode executorNode, String taskNodeName) {
        logger.info("resetTaskStatus() {}", taskNodeName);
        String schedulerNodeName = registryCenter.get(executorNode.getTaskRefererNodePath(taskNodeName));
        SchedulerNode schedulerNode = NodeUtils.restoreSchedulerNode(schedulerNodeName);
        if (schedulerNode == null)
            return;
        registryCenter.update(schedulerNode.getTaskStatusNodePath(taskNodeName), TaskStatus.COMPLETED.getStatus());
    }

    /**
     * //清除任务区里已完成（completed）的任务
     *
     * @param registryCenter 注册中心
     * @param executorNode 执行器节点
     * @param taskNodeName 任务节点名称
     */
    private void clearTaskArea(RegistryCenter registryCenter, ExecutorNode executorNode, String taskNodeName) {
        logger.info("clearTaskArea() {}", taskNodeName);
        registryCenter.remove(executorNode.getTaskNameNodePath(taskNodeName));
    }
}
