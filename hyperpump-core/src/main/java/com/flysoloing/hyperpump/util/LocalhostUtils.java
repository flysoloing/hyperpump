package com.flysoloing.hyperpump.util;

import com.flysoloing.hyperpump.exception.HPExceptionHandler;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author laitao
 * @date 2016-05-30 19:02:28
 */
public class LocalhostUtils {

    /**
     * 获取本地IP
     *
     * @return 本地IP
     */
    public static String getIp() {
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            return inetAddress.getHostAddress();
        } catch (UnknownHostException e) {
            HPExceptionHandler.handleException(e);
        }
        return null;
    }

    /**
     * 获取当前进程PID
     *
     * @return 进程PID
     */
    public static String getPid() {
        String name = ManagementFactory.getRuntimeMXBean().getName();
        return name.split("@")[0];
    }
}
