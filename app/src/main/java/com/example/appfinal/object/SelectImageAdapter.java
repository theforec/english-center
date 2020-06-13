package com.example.appfinal.object;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appfinal.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SelectImageAdapter extends RecyclerView.Adapter<SelectImageAdapter.SelectImageViewHolder> {
    private Context mContext;
    private ArrayList<SelectImage> mArrImage;
    private OnItemClickListener mListener;

    public SelectImageAdapter(Context context, ArrayList<SelectImage> arrImage) {
        this.mContext = context;
        this.mArrImage = arrImage;
    }

    @NonNull
    @Override
    public SelectImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_list_image_gv, parent, false);
        return new SelectImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectImageViewHolder holder, int position) {
        SelectImage imageCurrent = mArrImage.get(position);
        holder.textView.setText(imageCurrent.getTenAnh());
        Picasso.with(mContext).load(imageCurrent.getLinkAnh()).fit().centerCrop().into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mArrImage.size();
    }


    public class SelectImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textView;
        ImageView imageView;

        SelectImageViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textViewNameImageItem);
            imageView = itemView.findViewById(R.id.imageViewImageItem);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    mListener.onItemImageClick(position);
                }
            }
        }
    }
    public interface OnItemClickListener{
        void onItemImageClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        mListener=listener;
    }
}
