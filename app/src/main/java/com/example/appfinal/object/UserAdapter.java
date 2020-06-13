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

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private Context mContext;
    private ArrayList<User> arrUser;
    private OnItemListener mListener;

    public UserAdapter(Context mContext, ArrayList<User> arrUser) {
        this.mContext = mContext;
        this.arrUser = arrUser;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_list_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        holder.user_name.setText(arrUser.get(position).getHoTen());
        holder.user_email.setText(arrUser.get(position).getEmail());
    }

    @Override
    public int getItemCount() {
        return arrUser.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView user_name, user_email;

        UserViewHolder(@NonNull View itemView) {
            super(itemView);
            user_email = itemView.findViewById(R.id.text_view_user_email_gv);
            user_name = itemView.findViewById(R.id.text_view_user_name_gv);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    mListener.onItemUserClick(position);
                }
            }
        }
    }

    public interface OnItemListener {
        void onItemUserClick(int position);
    }

    public void setOnItemClickListener(OnItemListener listener) {
        mListener = listener;
    }
}
