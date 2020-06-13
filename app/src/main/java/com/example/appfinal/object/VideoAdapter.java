package com.example.appfinal.object;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.appfinal.R;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {
    private Context mContext;
    private ArrayList<Video> mArrVideo;
    private OnItemListener mListener;

    public VideoAdapter(Context mContext, ArrayList<Video> mArrVideo) {
        this.mContext = mContext;
        this.mArrVideo = mArrVideo;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_list_video_gv, parent, false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, final int position) {
        final Video video = mArrVideo.get(position);
        holder.ten_video.setText(video.getTenVideo());
        holder.video_view.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(YouTubePlayer youTubePlayer) {
                youTubePlayer.cueVideo(video.getLinkVideo(), 0);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mArrVideo.size();
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView ten_video;
        YouTubePlayerView video_view;

        VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            ten_video = itemView.findViewById(R.id.item_video_name_gv);
            video_view = itemView.findViewById(R.id.item_video_gv);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    mListener.onItemVideoClick(position);
                }
            }
        }
    }

    public interface OnItemListener {
        void onItemVideoClick(int position);
    }

    public void setOnItemClickListener(OnItemListener listener) {
        mListener = listener;
    }
}
