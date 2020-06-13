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

public class LopAdapter extends RecyclerView.Adapter<LopAdapter.LopViewHolder> {
    private Context mContext;
    private ArrayList<Lop> arrLop;
    private OnItemListener mListener;

    public LopAdapter(Context mContext, ArrayList<Lop> arrLop) {
        this.mContext = mContext;
        this.arrLop = arrLop;
    }

    @NonNull
    @Override
    public LopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_list_class_gv, parent, false);
        return new LopViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LopViewHolder holder, int position) {
        Lop lopCurrent = arrLop.get(position);
        holder.textView.setText(lopCurrent.getTenLop());
    }

    @Override
    public int getItemCount() {
        return arrLop.size();
    }


    public class LopViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textView;

        LopViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textViewClassItem);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    mListener.onItemClassClick(position);
                }
            }
        }
    }

    public interface OnItemListener {
        void onItemClassClick(int position);
    }

    public void setOnItemClickListener(OnItemListener listener) {
        mListener = listener;
    }

}

