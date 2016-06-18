package com.flysoloing.hyperpump.example.task;

import com.flysoloing.hyperpump.base.AbstractSimpleOneOffTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author laitao
 * @since 2016-06-18 16:53:46
 */
public class SimpleOneOffTaskDemo extends AbstractSimpleOneOffTask {

    private static final Logger logger = LoggerFactory.getLogger(SimpleOneOffTaskDemo.class);

    protected void process() {
        logger.info("start processing the task with task data");
    }
}
