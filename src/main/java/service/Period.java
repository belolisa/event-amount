package service;

public enum Period {

    MINUTE(60),
    HOUR(3600),
    DAY(86400);

    private int seconds;

    Period(int seconds) {
        this.seconds = seconds;
    }

    public int getSeconds() {
        return seconds;
    }
}
