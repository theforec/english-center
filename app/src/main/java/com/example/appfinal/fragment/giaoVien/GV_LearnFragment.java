package com.example.appfinal.fragment.giaoVien;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.appfinal.GiaoVienActivity;
import com.example.appfinal.R;
import com.example.appfinal.dao.LessonDAO;
import com.example.appfinal.dao.TestDAO;
import com.example.appfinal.object.GiaoVien;
import com.example.appfinal.object.Lesson;
import com.example.appfinal.object.Lop;
import com.example.appfinal.object.Test;
import com.example.appfinal.object.ViewClassLesson;
import com.example.appfinal.object.ViewClassTest;

import java.util.ArrayList;
import java.util.Collections;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class GV_LearnFragment extends Fragment implements View.OnClickListener {
    private TextView btnAddLesson, btnAddTest;
    private ArrayList<Lesson> arrLesson;
    private ArrayList<Test> arrTest;

    private LinearLayout layout_class_lesson, layout_class_test;
    private GiaoVien mGiaovien;
    public static Handler handler;

    public void setGiaoVien(GiaoVien gv) {
        this.mGiaovien = gv;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_gv_fragment_learn, container, false);
        init(view);

        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                if (msg.what == LessonDAO.GET_LIST_LESSON) {
                    arrLesson = msg.getData().getParcelableArrayList("lesson");
                    Collections.reverse(arrLesson);
                    if (arrLesson != null) {
                        ViewClassLesson viewClassLesson = new ViewClassLesson(getLearnFragment(), arrLesson);
                        layout_class_lesson.addView(viewClassLesson.getView());
                    }
                } else if (msg.what == TestDAO.GET_LIST_TEST) {
                    arrTest = msg.getData().getParcelableArrayList("test");
                    if (arrTest != null) {
                        ViewClassTest viewClassTest = new ViewClassTest(getLearnFragment(), arrTest);
                        layout_class_test.addView(viewClassTest.getView());
                    }
                }
                return false;
            }
        });

        btnAddLesson.setOnClickListener(this);
        btnAddTest.setOnClickListener(this);
        return view;
    }

    private void init(View view) {
        btnAddLesson = view.findViewById(R.id.buttonAddLessonGV);
        btnAddTest = view.findViewById(R.id.buttonAddTestGV);
        arrLesson = new ArrayList<>();
        arrTest = new ArrayList<>();
        ArrayList<Lop> arrLop;
        arrLop = mGiaovien.getDSLopChuNhiem();
        for (int i = 0; i < arrLop.size(); i++) {
            String ten_lop = arrLop.get(i).getTenLop();

            LessonDAO lessonDAO = new LessonDAO(ten_lop);
            lessonDAO.getListLesson();

            TestDAO testDAO = new TestDAO(ten_lop);
            testDAO.getListTest();
        }
        layout_class_lesson = view.findViewById(R.id.layout_list_class_lesson);
        layout_class_test = view.findViewById(R.id.layout_list_class_test);
    }

    private GV_LearnFragment getLearnFragment() {
        return this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonAddTestGV:
                GiaoVienActivity giaoVienActivity = (GiaoVienActivity) getActivity();
                giaoVienActivity.change(GiaoVienActivity.CHANGE_TO_TEST);
                giaoVienActivity.newTest();
                break;
            case R.id.buttonAddLessonGV:
                GiaoVienActivity giaoVienActivity2 = (GiaoVienActivity) getActivity();
                giaoVienActivity2.change(GiaoVienActivity.CHANGE_TO_LESSON);
                giaoVienActivity2.newLesson();
                break;
            default:
                break;
        }

    }
}
