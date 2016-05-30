package com.flysoloing.hyperpump.util;

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
}
