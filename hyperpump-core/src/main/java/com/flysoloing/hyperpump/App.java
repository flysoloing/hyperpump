package com.flysoloing.hyperpump;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        //01、√给hyperpump-core增加SLF4J统一日志
        //02、√给Scheduler节点增加Listener，监听Task节点状态的变化，并竞争获取当前批次的Task任务
        //03、√实现Scheduler节点增加taskList，分为待执行和执行中节点，已执行的需要清除
        //04、给hyperpump-core增加统一异常处理
        //05、√写一个高位自动填充0的工具方法，如：“1”自动填充为“00000000000001”
        //06、√区分TaskStatus和NodeStatus状态枚举
        //07、√将PORT修改为PID，PID为JVM示例的PID，用以区分
        //08、给Scheduler设置不同的调度策略，比如公平策略，其他权重策略等等
        //09、增加连接管理，如果客户端失去连接，应该将对应的SchedulerNode节点和ExecutorNode节点删除
        //10、√增加ExecutorNode节点任务执行完成后回调更新ExecutorNode节点状态机制
        //11、任务注册后不需要立即执行内部调度任务（InternalScheduleTask），改为由控制台操作开启后开始运行，例如：从disable状态改为normal状态后
        //12、应用示例本地需缓存所有的TaskNode节点、SchedulerNode节点和ExecutorNode节点，需要增加三个监听器来控制缓存的添加和移除
        //13、TaskNode需要设置任务类型，可以设置任务执行次数，以及批量任务和顺序批量任务类型等等
        //14、注册节点之前，先清除当前服务器IP下所有不可用的节点
        //15、当应用停止之前，将所有需要关闭的节点的节点状态改为不可用（Disabled）
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

        String path = "/SCHEDULERS/SCHEDULER_10.13.38.58_223800_schedulerNodeConf01/taskQueue/TASK_hptasktest01/taskStatus";
        String regEx = "/SCHEDULERS/SCHEDULER_.*/taskQueue/TASK_.*/taskStatus";
        long start = System.currentTimeMillis();
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(path);
        System.out.println(matcher.matches());
        long end = System.currentTimeMillis();
        System.out.println(end - start);

        for (int i = 0; i < 100; i++) {
            long start1 = System.currentTimeMillis();
            matcher.matches();
            long end1 = System.currentTimeMillis();
            System.out.println(end1 - start1);
        }
    }
}
