package com.flysoloing.hyperpump;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );

        //----------------待完成工作列表----------------
        //01、给hyperpump-core增加SLF4J统一日志
        //02、给Scheduler节点增加Listener，监听Task节点状态的变化，并竞争获取当前批次的Task任务
        //03、实现Scheduler节点增加taskList，分为待执行和执行中节点，已执行的需要清除
        //04、给hyperpump-core增加统一异常处理
        //05、写一个高位自动填充0的工具方法，如：“1”自动填充为“00000000000001”
        //06、区分TaskStatus和NodeStatus状态枚举
        //07、将PORT修改为PID，PID为JVM示例的PID，用以区分
        //08、给Scheduler设置不同的调度策略，比如公平策略，其他权重策略等等
        //09、增加连接管理，如果客户端失去连接，应该将对应的SchedulerNode节点和ExecutorNode节点删除
        //10、
        //11、
        //12、
        //13、
        //14、
        //15、
        //16、
        //17、
        //18、
        //19、
        //20、
        //21、
        //22、
        //23、
        //24、
        //25、
        //26、
        //27、
        //28、
        //29、
        //30、
    }
}
