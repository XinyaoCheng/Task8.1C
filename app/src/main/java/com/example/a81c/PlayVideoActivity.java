package com.example.a81c;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class PlayVideoActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    YouTubePlayerView youTubePlayerView;
    String API_KEY = "AIzaSyAcu8onUS-gXCPgDg2qtpp8Ut-rLrMhzX8";
    Uri videoUri;
    YouTubePlayer.OnInitializedListener onInitializedListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);
        youTubePlayerView = findViewById(R.id.videoPlayer);
        youTubePlayerView.initialize(API_KEY, this);

    };

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        if(!b){
            String videoUrl = getIntent().getStringExtra("video_url");
            //String videoUrl = "https://www.youtube.com/watch?v=cHHLHGNpCSA";
            videoUri = Uri.parse(videoUrl);
            String videoId = videoUri.getQueryParameter("v");
            youTubePlayer.loadVideo(videoId);
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        Toast.makeText(this, "Failed to initialize YouTube Player", Toast.LENGTH_SHORT).show();
        Log.e("initial failed：", youTubeInitializationResult.toString());

    }

}