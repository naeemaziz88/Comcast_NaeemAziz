# Comcast Exoplayer Demo

1.This app will use the Exoplayer library to stream the video with the followwing URL: 
https://devstreaming-cdn.apple.com/videos/streaming/examples/bipbop_16x9/bipbop_16x9_variant.m3u8

2.To create the UI, I also used dummy API to fetch the data from server and populate it. (MVVM approach)
https://livesocceherobucket.s3.eu-west-2.amazonaws.com/interview/demoapp.json

3. Created the interface to capture to log the events.

public interface IAnalytics {

    void logEvent(String key, int value);

    void logEvent(String key, String value);

    void logEvent(String key, boolean value);

    void logError(String key, Exception e);

}

4. using  AnalyticsManager to log the events
player.addAnalyticsListener(new AnalyticsListener() {
                @Override
                public void onPlaybackStateChanged(EventTime eventTime, int playbackState) {
                    AnalyticsListener.super.onPlaybackStateChanged(eventTime, playbackState);
                    if (playbackState == ExoPlayer.STATE_IDLE) {

                        ExoPlayerActivity.this.player.release();
                        progressBar.setVisibility(View.GONE);
                        AnalyticsManager.getInstance().logEvent(TAG, "Comcast Analytics: Server is not responding");
                        Toast.makeText(ExoPlayerActivity.this.getApplicationContext(), "Server is not responding", Toast.LENGTH_SHORT).show();
                        ExoPlayerActivity.this.finish();

                    } else if (playbackState == ExoPlayer.STATE_BUFFERING) {

                        progressBar.setVisibility(View.VISIBLE);
                        AnalyticsManager.getInstance().logEvent(TAG, "Comcast Analytics: Video is currently buffering and will begin playing");

                    } else if (playbackState == ExoPlayer.STATE_READY) {

                        progressBar.setVisibility(View.GONE);
                        startPositionTracker();
                        AnalyticsManager.getInstance().logEvent(TAG, "Comcast Analytics: Video is playing");

                    } else if (playbackState == ExoPlayer.STATE_ENDED) {

                        AnalyticsManager.getInstance().logEvent(TAG, "Comcast Analytics: player has finished playing the media");
                    }
                }

                @Override
                public void onVideoInputFormatChanged(EventTime eventTime, Format format, @Nullable DecoderReuseEvaluation decoderReuseEvaluation) {
                    AnalyticsListener.super.onVideoInputFormatChanged(eventTime, format, decoderReuseEvaluation);
                    mformate = format;
                    if(mbitrate != mformate.bitrate){
                        AnalyticsManager.getInstance().logEvent(TAG, "Comcast Analytics: previous bitrate = mbitrate,  new bitrate = mformate.bitrate");
                    }

                }

            });
            
5. Screenshot:
<img src="https://user-images.githubusercontent.com/116047657/196321231-17cd8f1e-8e37-4c5a-8098-7c411a1b16ec.png" width="300" height="600" />



