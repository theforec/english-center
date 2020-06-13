package com.example.appfinal.object;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appfinal.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class LessonAdapter extends RecyclerView.Adapter<LessonAdapter.LessonViewHolder> {
    private Context mContext;
    private ArrayList<Lesson> mArrLesson;
    private OnItemListener mListener;

    public LessonAdapter(Context mContext, ArrayList<Lesson> mArrLesson) {
        this.mContext = mContext;
        this.mArrLesson = mArrLesson;
    }

    @NonNull
    @Override
    public LessonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_list_lesson, parent, false);
        return new LessonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LessonViewHolder holder, int position) {
        holder.bindViewDataLesson(mArrLesson.get(position));
    }

    @Override
    public int getItemCount() {
        return mArrLesson.size();
    }

    public class LessonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tLesson, tDate;
        ImageView imageView;

        LessonViewHolder(@NonNull View itemView) {
            super(itemView);
            tLesson = itemView.findViewById(R.id.label_lesson);
            tDate = itemView.findViewById(R.id.label_date_lesson);
            imageView = itemView.findViewById(R.id.label_image);
            itemView.setOnClickListener(this);
        }

        void bindViewDataLesson(Lesson lesson){
            tDate.setText(lesson.getNgay());
            tLesson.setText(lesson.getLesson());
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    mListener.onItemLessonClick(position);
                }
            }
        }
    }

    public interface OnItemListener {
        void onItemLessonClick(int position);
    }

    public void setOnItemClickListener(OnItemListener listener) {
        mListener = listener;
    }

}
