package dao;

import org.junit.Assert;
import org.junit.Test;
import service.Period;

public class EventCounterDaoBeanTest {

    private EventCounterDao dao = new EventCounterDaoBean();

    @Test
    public void testProvideByPeriodEmpty() throws Exception {
        Assert.assertEquals(0, dao.provideByPeriod(Period.MINUTE));
        Assert.assertEquals(0, dao.provideByPeriod(Period.HOUR));
        Assert.assertEquals(0, dao.provideByPeriod(Period.DAY));
    }

    @Test
    public void provideByPeriod() throws Exception {
        dao.saveAmount(System.currentTimeMillis(), 10);
        Thread.sleep(1000);
        dao.saveAmount(System.currentTimeMillis(), 10);
        Thread.sleep(1000);
        dao.saveAmount(System.currentTimeMillis(), 10);
        Assert.assertEquals(30, dao.provideByPeriod(Period.MINUTE));
        Assert.assertEquals(30, dao.provideByPeriod(Period.HOUR));
        Assert.assertEquals(30, dao.provideByPeriod(Period.DAY));
    }

}