package com.example.appfinal.fragment.main;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.appfinal.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class AboutusFragment extends Fragment implements View.OnClickListener {
    private ImageView imvYoutube, imvGoogle, imvFacebook;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment_aboutus, container, false);
        init(view);

        imvFacebook.setOnClickListener(this);
        imvYoutube.setOnClickListener(this);
        imvGoogle.setOnClickListener(this);

        return view;
    }

    private void init(View view) {
        imvFacebook = view.findViewById(R.id.imageViewFacebook);
        imvYoutube = view.findViewById(R.id.imageViewYoutube);
        imvGoogle = view.findViewById(R.id.imageViewGooglePlus);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageViewFacebook:
                Intent intent1 = new Intent();
                intent1.setAction(Intent.ACTION_VIEW);
                intent1.setData(Uri.parse("https://www.facebook.com/jaxtinacenter"));
                startActivity(intent1);
                break;

            case R.id.imageViewYoutube:
                Intent intent2 = new Intent();
                intent2.setAction(Intent.ACTION_VIEW);
                intent2.setData(Uri.parse("https://www.youtube.com/channel/UCgYiqFZll4CeKqxtDum54Og"));
                startActivity(intent2);
                break;
            case R.id.imageViewGooglePlus:
                Intent intent3 = new Intent();
                intent3.setAction(Intent.ACTION_VIEW);
                intent3.setData(Uri.parse("https://plus.google.com/+JaxtinaEnglish"));
                startActivity(intent3);
                break;
            default:
                break;
        }
    }
}
