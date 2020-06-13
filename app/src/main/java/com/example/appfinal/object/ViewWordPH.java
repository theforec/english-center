package com.example.appfinal.object;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appfinal.R;

public class ViewWordPH {
    private TextView textView;
    private ImageView imageView;
    private View view;

    public View getView() {
        return view;
    }

    @SuppressLint("InflateParams")
    public ViewWordPH(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.item_list_words_ph,null);

        textView=view.findViewById(R.id.text_view_word_item);
        imageView=view.findViewById(R.id.image_view_word_item);
    }

    public TextView getTextView() {
        return textView;
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public void setView(View view) {
        this.view = view;
    }
}
