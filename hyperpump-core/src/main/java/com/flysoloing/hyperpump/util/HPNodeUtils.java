package com.flysoloing.hyperpump.util;

import com.flysoloing.hyperpump.common.Constants;
import com.flysoloing.hyperpump.executor.ExecutorNode;
import com.flysoloing.hyperpump.executor.ExecutorNodeConf;
import com.flysoloing.hyperpump.scheduler.SchedulerNode;
import com.flysoloing.hyperpump.scheduler.SchedulerNodeConf;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * @author laitao
 * @since 2016-05-13 00:50:00
 */
public class HPNodeUtils {

    /**
     * 获取节点路径
     *
     * @param firstNodeName 第一级节点名称
     * @return 节点路径
     */
    public static String getPath(String firstNodeName) {
        return String.format("/%s", firstNodeName);
    }

    /**
     * 获取节点路径
     *
     * @param firstNodeName 第一级节点名称
     * @param secondNodeName 第二级节点名称
     * @return 节点路径
     */
    public static String getPath(String firstNodeName, String secondNodeName) {
        return String.format("/%s/%s", firstNodeName, secondNodeName);
    }

    /**
     * 获取节点路径
     *
     * @param firstNodeName 第一级节点名称
     * @param secondNodeName 第二级节点名称
     * @param thirdNodeName 第三级节点名称
     * @return 节点路径
     */
    public static String getPath(String firstNodeName, String secondNodeName, String thirdNodeName) {
        return String.format("/%s/%s/%s", firstNodeName, secondNodeName, thirdNodeName);
    }

    /**
     * 获取节点路径
     *
     * @param firstNodeName 第一级节点名称
     * @param secondNodeName 第二级节点名称
     * @param thirdNodeName 第三级节点名称
     * @param fourthNodeName 第四级节点名称
     * @return 节点路径
     */
    public static String getPath(String firstNodeName, String secondNodeName, String thirdNodeName, String fourthNodeName) {
        return String.format("/%s/%s/%s/%s", firstNodeName, secondNodeName, thirdNodeName, fourthNodeName);
    }

    /**
     * 获取节点路径
     *
     * @param firstNodeName 第一级节点名称
     * @param secondNodeName 第二级节点名称
     * @param thirdNodeName 第三级节点名称
     * @param fourthNodeName 第四级节点名称
     * @param fifthNodeName 第五级节点名称
     * @return 节点路径
     */
    public static String getPath(String firstNodeName, String secondNodeName, String thirdNodeName, String fourthNodeName, String fifthNodeName) {
        return String.format("/%s/%s/%s/%s/%s", firstNodeName, secondNodeName, thirdNodeName, fourthNodeName, fifthNodeName);
    }

    /**
     * 将batchNo做加1操作，长度为19位，不得大于{@code Long.MAX_VALUE}
     *
     * <P>如果batchNo大于{@code Long.MAX_VALUE}，则重新从1开始
     *
     * @param batchNo 批次号
     * @return 加1后的batchNo
     */
    public static String incrBatchNo(long batchNo) {
        if (batchNo == Long.MAX_VALUE)
            return String.format("%019d", 1L);
        batchNo += 1L;
        return String.format("%019d", batchNo);
    }

    /**
     * 根据SchedulerNode节点名称恢复一个SchedulerNode对象
     *
     * @param schedulerNodePath SchedulerNode节点路径
     * @return SchedulerNode对象
     */
    public static SchedulerNode restoreSchedulerNode(String schedulerNodePath) {
        if (StringUtils.isBlank(schedulerNodePath))
            return null;
        List<String> list = Splitter.on(Constants.SEPARATOR_UNDERLINE).splitToList(schedulerNodePath);
        if (list.isEmpty())
            return null;
        SchedulerNodeConf schedulerNodeConf = new SchedulerNodeConf(list.get(3));
        schedulerNodeConf.setIp(list.get(1));
        schedulerNodeConf.setPid(list.get(2));
        return new SchedulerNode(schedulerNodeConf);
    }

    /**
     * 根据ExecutorNode节点名称恢复一个ExecutorNode对象
     *
     * @param executorNodePath ExecutorNode节点路径
     * @return ExecutorNode对象
     */
    public static ExecutorNode restoreExecutorNode(String executorNodePath) {
        if (StringUtils.isBlank(executorNodePath))
            return null;
        List<String> list = Splitter.on(Constants.SEPARATOR_UNDERLINE).splitToList(executorNodePath);
        if (list.isEmpty())
            return null;
        ExecutorNodeConf executorNodeConf = new ExecutorNodeConf(list.get(3));
        executorNodeConf.setIp(list.get(1));
        executorNodeConf.setPid(list.get(2));
        return new ExecutorNode(executorNodeConf);
    }

}
