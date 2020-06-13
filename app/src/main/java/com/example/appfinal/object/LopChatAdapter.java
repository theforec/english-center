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

public class LopChatAdapter extends RecyclerView.Adapter<LopChatAdapter.LopChatViewHolder> {
    private Context mContext;
    private ArrayList<Lop> arrLop;
    private OnItemListener mListener;

    public LopChatAdapter(Context mContext, ArrayList<Lop> arrLop) {
        this.mContext = mContext;
        this.arrLop = arrLop;
    }

    @NonNull
    @Override
    public LopChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_list_class_chat_gv, parent, false);
        return new LopChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LopChatViewHolder holder, int position) {
        holder.ten_lop.setText(arrLop.get(position).getTenLop());
    }

    @Override
    public int getItemCount() {
        return arrLop.size();
    }


    public class LopChatViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView ten_lop;

        LopChatViewHolder(@NonNull View itemView) {
            super(itemView);
            ten_lop = itemView.findViewById(R.id.text_view_class_chat_gv);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    mListener.onItemChatClassClick(position);
                }
            }
        }
    }

    public interface OnItemListener {
        void onItemChatClassClick(int position);
    }

    public void setOnItemClickListener(OnItemListener listener) {
        mListener = listener;
    }

}

