package com.example.appfinal.object;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.appfinal.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class StatusAdapter extends RecyclerView.Adapter<StatusAdapter.StatusViewHolder> {
    private Context mContext;
    private ArrayList<Status> arrStatus;
    private OnItemClickListenser mListener;

    public StatusAdapter(Context mContext, ArrayList<Status> arrStatus) {
        this.mContext = mContext;
        this.arrStatus = arrStatus;
    }

    @NonNull
    @Override
    public StatusViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_list_status, parent, false);
        return new StatusViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StatusViewHolder holder, final int position) {
        Status stt = arrStatus.get(position);
        holder.tenhs.setText(stt.getHocVien());
        holder.status.setText(stt.getTinhHinhHocTap());
        holder.status.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                arrStatus.get(position).setTinhHinhHocTap(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return arrStatus.size();
    }

    public class StatusViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tenhs;
        EditText status;

        StatusViewHolder(@NonNull View itemView) {
            super(itemView);
            tenhs = itemView.findViewById(R.id.label_namehs);
            status = itemView.findViewById(R.id.label_status);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    mListener.onItemStatusClick(position);
                }
            }
        }
    }

    public interface OnItemClickListenser {
        void onItemStatusClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListenser listener) {
        mListener = listener;
    }


}
