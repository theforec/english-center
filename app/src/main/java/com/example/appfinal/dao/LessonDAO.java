package com.example.appfinal.dao;

import android.os.Bundle;
import android.os.Message;

import com.example.appfinal.object.Lesson;
import com.example.appfinal.object.Status;
import com.example.appfinal.object.Word;
import com.example.appfinal.fragment.giaoVien.GV_LearnFragment;
import com.example.appfinal.fragment.phuHuynh.PH_LearnFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;

public class LessonDAO {
    private DatabaseReference myRef;
    private String ten_lop;
    public final static int GET_LIST_LESSON = 8;
    public final static int GET_LESSON_PH = 42;
    public final static int GET_LESSON_PH_FAIL = 43;

    public LessonDAO(String tenlop) {
        this.ten_lop = tenlop;
        myRef = FirebaseDatabase.getInstance().getReference("BaiHoc");
    }

    public void getListLesson() {
        final ArrayList<Lesson> arrLesson = new ArrayList<>();
        myRef.child(ten_lop).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Lesson lesson = new Lesson();
                    for (DataSnapshot dataChild : data.getChildren()) {
                        if (dataChild.getKey().equals("baiTap")) {
                            lesson.setBaiTap(dataChild.getValue(String.class));
                        }
                        if (dataChild.getKey().equals("book")) {
                            lesson.setBook(dataChild.getValue(String.class));
                        }
                        if (dataChild.getKey().equals("hoatDong")) {
                            lesson.setHoatDong(dataChild.getValue(String.class));
                        }
                        if (dataChild.getKey().equals("linkVideo")) {
                            lesson.setLinkVideo(dataChild.getValue(String.class));
                        }
                        if (dataChild.getKey().equals("lesson")) {
                            lesson.setLesson(dataChild.getValue(String.class));
                        }
                        if (dataChild.getKey().equals("lop")) {
                            lesson.setLop(dataChild.getValue(String.class));
                        }
                        if (dataChild.getKey().equals("ngay")) {
                            lesson.setNgay(dataChild.getValue(String.class));
                        }
                        if (dataChild.getKey().equals("topic")) {
                            lesson.setTopic(dataChild.getValue(String.class));
                        }
                        if (dataChild.getKey().equals("danhSachTu")) {
                            ArrayList<Word> arrWord = new ArrayList<>();
                            for (DataSnapshot dsWord : dataChild.getChildren()) {
                                Word word = dsWord.getValue(Word.class);
                                arrWord.add(word);
                            }
                            lesson.setDanhSachTu(arrWord);
                        }
                        if (dataChild.getKey().equals("tinhHinhLopHoc")) {
                            ArrayList<Status> arrStatus = new ArrayList<>();
                            for (DataSnapshot dsStatus : dataChild.getChildren()) {
                                Status status = dsStatus.getValue(Status.class);
                                arrStatus.add(status);
                            }
                            lesson.setTinhHinhLopHoc(arrStatus);
                        }
                    }
                    arrLesson.add(lesson);
                }

                if (GV_LearnFragment.handler != null) {
                    Message msg = new Message();
                    msg.what = GET_LIST_LESSON;
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList("lesson", arrLesson);
                    msg.setData(bundle);
                    msg.setTarget(GV_LearnFragment.handler);
                    msg.sendToTarget();
                }
                if (PH_LearnFragment.handler != null) {
                    Message msg = new Message();
                    msg.what = GET_LESSON_PH;
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList("lesson2", arrLesson);
                    msg.setData(bundle);
                    msg.setTarget(PH_LearnFragment.handler);
                    msg.sendToTarget();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
