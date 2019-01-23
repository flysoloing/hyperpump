package com.flysoloing.hyperpump.example.task;

import com.flysoloing.hyperpump.base.AbstractBatchRepetitiveTask;
import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author laitao
 * @date 2016-06-21 17:43:02
 */
public class BatchRepetitiveTaskDemo extends AbstractBatchRepetitiveTask<Fish> {

    private static final Logger logger = LoggerFactory.getLogger(BatchRepetitiveTaskDemo.class);

    public List<Fish> fetch() {
        List<Fish> fishs = new ArrayList<Fish>();
        for (int i = 0; i < 500; i++) {
            Fish fish = new Fish("goldfish"+i);
            fishs.add(fish);
        }
        return fishs;
    }

    public void process(Fish fish) {
        logger.info("BatchRepetitiveTaskDemo process start, {}...", fish.getName());
        try {
            Thread.sleep(RandomUtils.nextLong(500L, 1000L));
        } catch (InterruptedException e) {
            logger.error("", e);
        }
        logger.info("BatchRepetitiveTaskDemo process end, {}...", fish.getName());
    }
}
