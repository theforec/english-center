package com.example.appfinal.object;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appfinal.R;

public class ViewImage {
    private ImageView imageView;
    private TextView textView;
    private View view;

    public View getView() {
        return view;
    }

    public ViewImage() {
    }

    public ViewImage(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.item_add_image_test, null);

        imageView = view.findViewById(R.id.image_view_add_image);
        textView = view.findViewById(R.id.text_view_iamge_name);
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public TextView getTextView() {
        return textView;
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
    }

    public void setView(View view) {
        this.view = view;
    }
}
