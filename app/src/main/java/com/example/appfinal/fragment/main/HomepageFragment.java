package com.example.appfinal.fragment.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.example.appfinal.R;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class HomepageFragment extends Fragment {
    private ViewFlipper viewFlipper;
    private final static int TIME_FLIP_IMAGE = 4000;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment_homepage, container, false);

        initViewFlipper(view);

        initYoutube(view);
        initYoutube2(view);

        return view;
    }

    private void initViewFlipper(View view) {
        viewFlipper = view.findViewById(R.id.viewFlipper);
        int[] images = {R.drawable.slide1, R.drawable.slide2, R.drawable.slide3};
        //loop better
        for (int image : images) {
            flipperImage(image);
        }
    }

    private void initYoutube(View view) {
        YouTubePlayerView youTubePlayerView = view.findViewById(R.id.video_home);
        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(final YouTubePlayer youTubePlayer) {
                youTubePlayer.cueVideo("MmDoI2dGgSE", 0);
            }
        });
    }

    private void initYoutube2(View view) {
        YouTubePlayerView youTubePlayerView = view.findViewById(R.id.video_home_2);

        getLifecycle().addObserver(youTubePlayerView);
        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(YouTubePlayer youTubePlayer) {
                youTubePlayer.cueVideo("tInpuiItF9g", 0);
            }
        });
    }

    private void flipperImage(int image) {
        ImageView imageView = new ImageView(getActivity());
        imageView.setBackgroundResource(image);

        viewFlipper.addView(imageView);
        viewFlipper.setFlipInterval(TIME_FLIP_IMAGE);
        viewFlipper.setAutoStart(true);

        //animation
        viewFlipper.setInAnimation(getActivity(), android.R.anim.fade_in);
        viewFlipper.setOutAnimation(getActivity(), android.R.anim.fade_out);
    }
}
