package com.example.appfinal.fragment.phuHuynh;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.appfinal.PhuHuynhActivity;
import com.example.appfinal.R;
import com.example.appfinal.object.Lesson;
import com.example.appfinal.object.Status;
import com.example.appfinal.object.StatusAdapter;
import com.example.appfinal.object.ViewWordPH;
import com.example.appfinal.object.Word;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static android.provider.Settings.Global.AIRPLANE_MODE_ON;

public class PH_LessonFragment extends Fragment implements View.OnClickListener {

    private TextView tv_back, tv_lop, tv_lesson, tv_date, tv_book, tv_topic, tv_activities, tv_homeworks;
    private String link_video;
    private YouTubePlayerView video_lesson;
    private LinearLayout layoutWords;
    private ArrayList<Word> arrWords;
    private ArrayList<Status> arrStatus;

    private Lesson mLesson;

    public void setLesson(Lesson lesson) {
        this.mLesson = lesson;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.user_ph_fragment_lesson, null);
        init(view);
        setData(mLesson);
        setTableStatus(view);

        return view;
    }

    @SuppressLint("SetTextI18n")
    private void setData(Lesson lesson) {
        tv_lop.setText(tv_lop.getText().toString() + lesson.getLop());
        tv_lesson.setText(lesson.getLesson());
        tv_date.setText(tv_date.getText().toString() + ": " + lesson.getNgay());
        tv_book.setText(tv_book.getText().toString() + lesson.getBook());
        tv_topic.setText(tv_topic.getText().toString() + lesson.getTopic());
        tv_activities.setText(lesson.getHoatDong());
        tv_homeworks.setText(lesson.getBaiTap());
        link_video = lesson.getLinkVideo();
        getLifecycle().addObserver(video_lesson);
        video_lesson.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(YouTubePlayer youTubePlayer) {
                youTubePlayer.cueVideo(link_video, 0);
            }
        });
        arrStatus = lesson.getTinhHinhLopHoc();
        arrWords = lesson.getDanhSachTu();
        for (int i = 0; i < arrWords.size(); i++) {
            ViewWordPH viewWordPH = new ViewWordPH(getContext());
            viewWordPH.getTextView().setText("- " + arrWords.get(i).getTu());
            Picasso.with(getContext()).load(arrWords.get(i).getLinkAnh()).into(viewWordPH.getImageView());
            layoutWords.addView(viewWordPH.getView());
        }
    }

    private void setTableStatus(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.layout_litstatus_ph);
        LinearLayoutManager lm = new LinearLayoutManager(getContext());
        lm.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(lm);
        StatusAdapter statusAdapter = new StatusAdapter(getContext(), arrStatus);
        recyclerView.setAdapter(statusAdapter);
    }

    private void init(View view) {
        tv_back = view.findViewById(R.id.textViewBackToLearnPH);
        tv_back.setOnClickListener(this);
        tv_lop = view.findViewById(R.id.text_view_lop_ph);
        tv_lesson = view.findViewById(R.id.text_view_lesson_ph);
        tv_date = view.findViewById(R.id.text_view_date_ph);
        tv_book = view.findViewById(R.id.text_view_book_ph);
        tv_topic = view.findViewById(R.id.text_view_topic_ph);
        tv_activities = view.findViewById(R.id.text_view_activities_ph);
        tv_homeworks = view.findViewById(R.id.text_view_homeworks_ph);
        video_lesson = view.findViewById(R.id.video_lesson_ph);
        arrWords = new ArrayList<>();
        arrStatus = new ArrayList<>();
        layoutWords = view.findViewById(R.id.layout_listword_ph);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textViewBackToLearnPH:
                PhuHuynhActivity phuHuynhActivity = (PhuHuynhActivity) getActivity();
                if (phuHuynhActivity != null) {
                    phuHuynhActivity.change(PhuHuynhActivity.CHANGE_TO_LEARN);
                }
        }
    }

}
