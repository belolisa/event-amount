package service;

import dao.EventCounterDao;
import dao.EventCounterDaoBean;
import event.Event;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EventCountServiceSystemTest {

    private EventCounterServiceBean eventCounterServiceBean;

    @Before
    public void init() {
        EventCounterDao eventCounterDao = new EventCounterDaoBean();
        eventCounterServiceBean = new EventCounterServiceBean();
        eventCounterServiceBean.setEventCounterDao(eventCounterDao);
        eventCounterServiceBean.init();
    }

    private Runnable generateRunnable() {
        return new Runnable() {

            public void run() {
                for (int i = 0; i < 10000; i++) {
                    eventCounterServiceBean.onEvent(new Event());
                }

            }
        };
    }

    private void generateMessages() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        ExecutorService ex = Executors.newFixedThreadPool(3);
        ex.submit(generateRunnable());
        ex.submit(generateRunnable());
        ex.submit(generateRunnable());
        countDownLatch.countDown();

        Thread.sleep(1000);
    }


    @Test
    public void testGetEventNumberPerMinute() throws Exception {
        generateMessages();
        Assert.assertEquals(30000, eventCounterServiceBean.getEventNumberPerMinute());
        Assert.assertEquals(30000, eventCounterServiceBean.getEventNumberPerHour());
        Assert.assertEquals(30000, eventCounterServiceBean.getEventNumberPerDay());
    }
}
