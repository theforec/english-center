package com.example.appfinal.object;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appfinal.R;

public class ViewWordGV {
    private EditText editText;
    private Button button;
    private ImageView imageView;
    private TextView textView;
    private View view;

    @SuppressLint("InflateParams")
    public ViewWordGV(final Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.item_list_words_gv, null);

        editText = view.findViewById(R.id.editTextWord);
        button = view.findViewById(R.id.buttonPickImageWord);
        imageView = view.findViewById(R.id.imageViewImageWord);
        textView = view.findViewById(R.id.textViewNameImageWord);

    }

    public EditText getEditText() {
        return editText;
    }

    public void setEditText(EditText editText) {
        this.editText = editText;
    }

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
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


    public View getView() {
        return view;
    }
}
