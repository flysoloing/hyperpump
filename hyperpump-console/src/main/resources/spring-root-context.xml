<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd
       http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop.xsd"
       default-autowire="byName">

    <aop:aspectj-autoproxy />

    <context:annotation-config/>

    <context:component-scan base-package="com.jd.baoxian.channel"/>

    <bean class="org.springframework.beans.factory.config.PropertyPlaceholderConfigurer">
        <property name="locations">
            <list>
                <value>classpath:props/*.properties</value>
            </list>
        </property>
    </bean>


    <bean id="umpAspect" class="com.jd.payment.paycommon.metrics.UmpAspect">
        <property name="umpKeyHead" value="baoxian_channel."/>
    </bean>

    <bean class="com.jd.ump.annotation.JAnnotation">
        <!-- 初始化系统的心跳 key-->
        <property name="systemKey" value="baoxian_channel.heartbeat.monitor"/>
        <!-- 初始化系统的jvm监控 key-->
        <property name="jvmKey" value="baoxian_channel.jvm.monitor"/>
    </bean>

    <bean class="com.jd.header.HeaderServer" init-method="start" destroy-method="stop"></bean>

    <import resource="classpath:spring/spring-baoxian-channel-*.xml"/>
    <import resource="classpath:spring/data/*.xml" />
</beans>