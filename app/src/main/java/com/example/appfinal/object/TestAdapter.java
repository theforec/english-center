package com.example.appfinal.object;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.appfinal.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.TestViewHolder> {
    private Context mContext;
    private ArrayList<Test> mArrTest;
    private OnItemListener mListener;

    public TestAdapter(Context mContext, ArrayList<Test> mArrTest) {
        this.mContext = mContext;
        this.mArrTest = mArrTest;
    }

    @NonNull
    @Override
    public TestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_list_test, parent, false);
        return new TestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TestViewHolder holder, int position) {
        holder.bindViewDataTest(mArrTest.get(position));
    }

    @Override
    public int getItemCount() {
        return mArrTest.size();
    }

    public class TestViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView test, date;

        TestViewHolder(@NonNull View itemView) {
            super(itemView);
            test = itemView.findViewById(R.id.label_test);
            date = itemView.findViewById(R.id.label_date_test);
            itemView.setOnClickListener(this);
        }

        void bindViewDataTest(Test mtest) {
            test.setText(mtest.getTestName());
            date.setText(mtest.getDate());
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    mListener.onItemTestClick(position);
                }
            }
        }
    }

    public interface OnItemListener {
        void onItemTestClick(int position);
    }

    public void setOnItemClickListener(OnItemListener listener) {
        mListener = listener;
    }

}
