package com.example.appfinal.dao;

import android.os.Bundle;
import android.os.Message;

import com.example.appfinal.fragment.giaoVien.GV_LessonFragment;
import com.example.appfinal.object.Video;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;

public class VideoDAO {
    private DatabaseReference myRef;
    public static final int GET_LIST_VIDEOS = 48;

    public VideoDAO() {
        myRef = FirebaseDatabase.getInstance().getReference("videos");
    }

    public void getListVideos() {
        final ArrayList<Video> arrVideos = new ArrayList<>();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Video video = data.getValue(Video.class);
                    arrVideos.add(video);
                }
                if (GV_LessonFragment.handler != null) {
                    Message msg = new Message();
                    msg.what = GET_LIST_VIDEOS;
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList("videos", arrVideos);
                    msg.setData(bundle);
                    msg.setTarget(GV_LessonFragment.handler);
                    msg.sendToTarget();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
