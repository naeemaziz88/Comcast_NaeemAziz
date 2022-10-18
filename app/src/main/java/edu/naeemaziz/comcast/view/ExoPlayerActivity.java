package edu.naeemaziz.comcast.view;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.media3.common.Format;
import androidx.media3.common.MediaItem;
import androidx.media3.common.MimeTypes;
import androidx.media3.common.TrackGroupArray;
import androidx.media3.exoplayer.DecoderReuseEvaluation;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.exoplayer.analytics.AnalyticsListener;
import androidx.media3.exoplayer.trackselection.DefaultTrackSelector;
import androidx.media3.ui.PlayerView;

import edu.naeemaziz.comcast.R;
import edu.naeemaziz.comcast.analytics.AnalyticsManager;
import edu.naeemaziz.comcast.utilities.Utility;



public class ExoPlayerActivity extends AppCompatActivity {

    private boolean playWhenReady = true;
    private int startItemIndex = 0;
    private long playbackPosition = 0;
    Format mformate;
    private final String TAG = ExoPlayerActivity.class.getSimpleName();
    int mbitrate = 0;

    protected @Nullable
    ExoPlayer player;
    PlayerView playerView;
    private TrackGroupArray lastSeenTrackGroupArray;
    ProgressBar progressBar;
    private Runnable currentPositionTracker;
    private final Handler mhandler = new Handler();
    String videoURL = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        hideSystemUi();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player_activity_view);
        progressBar = findViewById(R.id.progress_bar);
        playerView = findViewById(R.id.video_view);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            videoURL = extras.getString("URL");
        }
        //static comcast URL
        videoURL = "https://devstreaming-cdn.apple.com/videos/streaming/examples/bipbop_16x9/bipbop_16x9_variant.m3u8";

        if (!Utility.isNetwork(this)) {
            Utility.showNetworkErrorDialogue(this);
        }
    }

    public boolean initializePlayer() {
        if (player == null) {
            Intent intent = getIntent();
            lastSeenTrackGroupArray = null;
            DefaultTrackSelector trackSelector = new DefaultTrackSelector(this);
            player = new ExoPlayer.Builder(ExoPlayerActivity.this)
                    .setTrackSelector(trackSelector).build();
            player.setPlayWhenReady(playWhenReady);
            player.seekTo(playbackPosition);
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
            playerView.setPlayer(player);
            MediaItem mediaItem = new MediaItem.Builder().setUri(videoURL).
                    setMimeType(MimeTypes.APPLICATION_M3U8).
                    build();
            player.setMediaItem(mediaItem);
            player.prepare();
        }

        return true;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Build.VERSION.SDK_INT > 23) {
            initializePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT <= 23 || player == null) {
            initializePlayer();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Build.VERSION.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Build.VERSION.SDK_INT > 23) {
            releasePlayer();
        }
    }

    void releasePlayer() {
        if (player != null) {
            stopPositionTracker();
            startItemIndex = player.getCurrentMediaItemIndex();
            playbackPosition = Math.max(0, player.getContentPosition());
            playWhenReady = player.getPlayWhenReady();
            player.release();
            player = null;
        }
    }

    private void hideSystemUi() {
        try {
            this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            this.getSupportActionBar().hide();
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        } catch (NullPointerException e) {
        }
    }
    private void startPositionTracker() {
       currentPositionTracker = new Runnable() {
            @Override
            public void run() {
                if(player !=null){
                    AnalyticsManager.getInstance().logEvent(TAG, "Comcast Analytics : current player position = "+ player.getCurrentPosition());
                }
                mhandler.postDelayed(currentPositionTracker, 1000);
            }
        };
        mhandler.postDelayed(currentPositionTracker, 1000);
    }

    private void stopPositionTracker() {
        if (currentPositionTracker != null) {
            mhandler.removeCallbacks(currentPositionTracker);
            currentPositionTracker = null;
        }
    }
}
