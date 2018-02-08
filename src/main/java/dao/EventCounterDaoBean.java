package dao;

import org.springframework.stereotype.Component;
import service.Period;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class EventCounterDaoBean implements EventCounterDao {

    private static final int CONVERSATION_VALUE = 1000;

    private Map<Long, Integer> cache = new ConcurrentHashMap<Long, Integer>();

    @PostConstruct
    public void init() {
        ScheduledExecutorService eraseScheduler = Executors.newScheduledThreadPool(1);
        eraseScheduler.scheduleAtFixedRate(new Runnable() {
            public void run() {
                dropOldContent();
            }
        }, 24, 1, TimeUnit.HOURS);
    }

    public void saveAmount(long time, int amount) {
        //extract in onMessage
        long seconds = convertToSeconds(time);
        cache.put(seconds, amount);
    }

    public int provideByPeriod(Period time) {
        int resultAmount = 0;
        int periodInSec = time.getSeconds();
        long currentSecond = convertToSeconds(System.currentTimeMillis());
        for (long i = currentSecond; i >= currentSecond - periodInSec; i--) {
            Integer integer = cache.get(i);
            if (integer != null) {
                resultAmount += integer;
            }
        }
        return resultAmount;
    }

    private long convertToSeconds(long millis) {
        return millis / CONVERSATION_VALUE;
    }

    private void dropOldContent() {
        long dayAgoSecond = convertToSeconds(System.currentTimeMillis() - Period.DAY.getSeconds());
        for (long i = dayAgoSecond; i >= dayAgoSecond - Period.HOUR.getSeconds(); i--) {
            cache.remove(i);
        }
    }
}
