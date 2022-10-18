package edu.naeemaziz.comcast.analytics;

import android.util.Log;

public class AnalyticsManager implements IAnalytics {

    private static AnalyticsManager mInstance;

    public static AnalyticsManager getInstance() {
        if (mInstance == null)
            mInstance = new AnalyticsManager();
        return mInstance;
    }

    @Override
    public void logEvent(String key, int value) {
        Log.d(key, String.valueOf(value));
    }

    @Override
    public void logEvent(String key, String value) {
        Log.d(key, value);
    }

    @Override
    public void logEvent(String key, boolean value) {
        Log.d(key, String.valueOf(value));
    }

    @Override
    public void logError(String key, Exception e) {
        Log.d(key, e.getMessage());
    }
}
