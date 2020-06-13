package com.example.appfinal.fragment.phuHuynh;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appfinal.PhuHuynhActivity;
import com.example.appfinal.R;
import com.example.appfinal.dao.LessonDAO;
import com.example.appfinal.dao.TestDAO;
import com.example.appfinal.object.Lesson;
import com.example.appfinal.object.LessonAdapter;
import com.example.appfinal.object.Test;
import com.example.appfinal.object.TestAdapter;

import java.util.ArrayList;
import java.util.Collections;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import es.dmoral.toasty.Toasty;

public class PH_LearnFragment extends Fragment implements LessonAdapter.OnItemListener, TestAdapter.OnItemListener {
    private TextView t_lop;
    private ArrayList<Lesson> arrLesson;
    private ArrayList<Test> arrTest;
    private String mTen_lop;

    public static Handler handler;

    public void setLop(String lop) {
        this.mTen_lop = lop;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.user_ph_fragment_learn, container, false);
        init(view);

        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                if (msg.what == LessonDAO.GET_LESSON_PH) {
                    arrLesson = msg.getData().getParcelableArrayList("lesson2");
                    setUpLessons(view);
                } else if (msg.what == LessonDAO.GET_LESSON_PH_FAIL) {
                    Toasty.error(getActivity(), "Tải thất bại", Toast.LENGTH_SHORT).show();
                } else if (msg.what == TestDAO.GET_LIST_TEST_PH) {
                    arrTest = msg.getData().getParcelableArrayList("test2");
                    setUpTests(view);
                }
                return false;
            }
        });
        return view;
    }

    private void init(View view) {
        t_lop = view.findViewById(R.id.text_view_lop_ph);
        t_lop.setText(mTen_lop);
        arrLesson = new ArrayList<>();
        arrTest = new ArrayList<>();

        LessonDAO lessonDAO = new LessonDAO(mTen_lop);
        lessonDAO.getListLesson();

        TestDAO testDAO = new TestDAO(mTen_lop);
        testDAO.getListTest();

    }

    private void setUpLessons(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.layout_list_lessonPH);
        LinearLayoutManager lm = new LinearLayoutManager(view.getContext());
        lm.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(lm);
        LessonAdapter lessonAdapter = new LessonAdapter(view.getContext(), arrLesson);
        lessonAdapter.setOnItemClickListener(this);
        recyclerView.setAdapter(lessonAdapter);
    }

    private void setUpTests(View view) {
        RecyclerView recyclerView2 = view.findViewById(R.id.layout_list_testPH);
        LinearLayoutManager lm = new LinearLayoutManager(view.getContext());
        lm.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView2.setLayoutManager(lm);
        TestAdapter testAdapter = new TestAdapter(view.getContext(), arrTest);
        testAdapter.setOnItemClickListener(this);
        recyclerView2.setAdapter(testAdapter);
    }

    @Override
    public void onItemLessonClick(int position) {
        PhuHuynhActivity phuHuynhActivity = (PhuHuynhActivity) getActivity();
        if (phuHuynhActivity != null) {
            phuHuynhActivity.setLesson(arrLesson.get(position));
            phuHuynhActivity.change(PhuHuynhActivity.CHANGE_TO_LESSON);
        }
    }

    @Override
    public void onItemTestClick(int position) {
        PhuHuynhActivity phuHuynhActivity2= (PhuHuynhActivity) getActivity();
        if (phuHuynhActivity2 != null) {
            phuHuynhActivity2.setTest(arrTest.get(position));
            phuHuynhActivity2.change(PhuHuynhActivity.CHANGE_TO_TEST);
        }
    }
}
