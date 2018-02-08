package dao;

import service.Period;

public interface EventCounterDao {

    void saveAmount(long time, int amount);

    int provideByPeriod(Period time);

}
