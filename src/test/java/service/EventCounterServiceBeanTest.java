package service;

import dao.EventCounterDao;
import event.Event;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class EventCounterServiceBeanTest {
    @Mock
    private EventCounterDao eventCounterDao;

    @InjectMocks
    private EventCounterServiceBean eventCounterServiceBean;

    @Test
    public void testGetEventNumberPerMinute() throws Exception {
        Mockito.when(eventCounterDao.provideByPeriod(Period.MINUTE)).thenReturn(1);
        eventCounterServiceBean.onEvent(new Event());
        int eventNumberPerMinute = eventCounterServiceBean.getEventNumberPerMinute();
        Assert.assertEquals(1, eventNumberPerMinute);
        Mockito.verify(eventCounterDao).provideByPeriod(Period.MINUTE);
    }

}