package service;

import dao.EventCounterDao;
import event.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class EventCounterServiceBean implements EventCounterService {

    private static final int SECOND = 1;

    @Autowired
    private EventCounterDao eventCounterDao;

    private volatile AtomicInteger count = new AtomicInteger();

    @PostConstruct
    public void init() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(new Runnable() {
            public void run() {
                schedulerTask();
            }
        }, SECOND, SECOND, TimeUnit.SECONDS);
    }

    void setEventCounterDao(EventCounterDao eventCounterDao) {
        this.eventCounterDao = eventCounterDao;
    }

    public void onEvent(Event e) {
        count.getAndIncrement();
    }

    public int getEventNumberPerMinute() {
        return eventCounterDao.provideByPeriod(Period.MINUTE);
    }

    public int getEventNumberPerHour() {
        return eventCounterDao.provideByPeriod(Period.HOUR);
    }

    public int getEventNumberPerDay() {
        return eventCounterDao.provideByPeriod(Period.DAY);
    }

    private void schedulerTask() {
        eventCounterDao.saveAmount(System.currentTimeMillis(), count.getAndSet(0));
    }

}


