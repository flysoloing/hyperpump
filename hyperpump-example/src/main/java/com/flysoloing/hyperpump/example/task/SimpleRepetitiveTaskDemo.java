package com.flysoloing.hyperpump.example.task;

import com.flysoloing.hyperpump.base.AbstractSimpleRepetitiveTask;
import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author laitao
 * @since 2016-06-18 16:53:46
 */
public class SimpleRepetitiveTaskDemo extends AbstractSimpleRepetitiveTask {

    private static final Logger logger = LoggerFactory.getLogger(SimpleRepetitiveTaskDemo.class);

    public void process() {
        logger.info("SimpleRepetitiveTaskDemo process start...");
        try {
            Thread.sleep(RandomUtils.nextLong(1000L, 5000L));
        } catch (InterruptedException e) {
            logger.error("", e);
        }
        logger.info("SimpleRepetitiveTaskDemo process end...");
    }
}
