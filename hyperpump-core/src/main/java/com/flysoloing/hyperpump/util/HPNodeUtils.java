package com.flysoloing.hyperpump.util;

/**
 * @author laitao
 * @since 2016-05-13 00:50:00
 */
public class HPNodeUtils {

    /**
     * 获取目标节点全路径
     *
     * @param rootNodeName 根节点名称
     * @param targetNodeName 目标节点名称
     * @return 目标节点全路径
     */
    public static String getFullPath(String rootNodeName, String targetNodeName) {
        return String.format("/%s/%s", rootNodeName, targetNodeName);
    }

    public static String getFullPath(String nodeName) {
        return String.format("/%s", nodeName);
    }
}
