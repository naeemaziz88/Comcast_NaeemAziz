package edu.naeemaziz.comcast.analytics;

public interface IAnalytics {

    void logEvent(String key, int value);

    void logEvent(String key, String value);

    void logEvent(String key, boolean value);

    void logError(String key, Exception e);

}
