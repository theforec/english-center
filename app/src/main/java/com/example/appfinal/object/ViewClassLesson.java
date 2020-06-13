package com.example.appfinal.object;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.appfinal.GiaoVienActivity;
import com.example.appfinal.R;
import com.example.appfinal.fragment.giaoVien.GV_LearnFragment;

import java.util.ArrayList;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ViewClassLesson implements LessonAdapter.OnItemListener {
    private TextView textView;
    private RecyclerView recyclerView;
    private ArrayList<Lesson> arrLesson;
    private View view;
    private GV_LearnFragment mContext;

    @SuppressLint("InflateParams")
    public ViewClassLesson(final GV_LearnFragment context, ArrayList<Lesson> lessonArrayList) {
        mContext = context;
        arrLesson = lessonArrayList;
        LayoutInflater inflater = LayoutInflater.from(context.getContext());
        view = inflater.inflate(R.layout.item_view_class_lesson, null);
        textView = view.findViewById(R.id.itemNameClass);
        recyclerView = view.findViewById(R.id.layout_listClassLesson);

        if (lessonArrayList.size() != 0) {
            textView.setText(lessonArrayList.get(0).getLop());

            LessonAdapter listLessonAdapter = new LessonAdapter(context.getContext(), lessonArrayList);
            listLessonAdapter.setOnItemClickListener(ViewClassLesson.this);

            LinearLayoutManager lm = new LinearLayoutManager(context.getContext());
            lm.setOrientation(LinearLayoutManager.HORIZONTAL);
            recyclerView.setLayoutManager(lm);
            recyclerView.setAdapter(listLessonAdapter);
        }
    }

    @Override
    public void onItemLessonClick(int position) {
        GiaoVienActivity giaoVienActivity = (GiaoVienActivity) mContext.getActivity();
        if (giaoVienActivity != null) {
            giaoVienActivity.setLesson(arrLesson.get(position));
            giaoVienActivity.change(GiaoVienActivity.CHANGE_TO_LESSON);
        }
    }

    public View getView() {
        return view;
    }

    public TextView getTextView() {
        return textView;
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    public void setView(View view) {
        this.view = view;
    }
}
