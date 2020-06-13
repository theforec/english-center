package com.example.appfinal.fragment.main;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.appfinal.MainActivity;
import com.example.appfinal.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ForgotpasswordFragment extends Fragment implements View.OnClickListener {
    @Nullable
    private Button btnCall;
    private TextView tBack;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment_forgotpassword, container, false);
        init(view);
        if (btnCall != null) {
            btnCall.setOnClickListener(this);
        }
        tBack.setOnClickListener(this);
        return view;
    }

    private void init(View view) {
        btnCall = view.findViewById(R.id.buttonCall);
        tBack = view.findViewById(R.id.textViewBacktoLogin);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonCall:
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:0945700754"));
                startActivity(intent);
                break;
            case R.id.textViewBacktoLogin:
                MainActivity mainActivity = (MainActivity) getActivity();
                if (mainActivity != null) {
                    mainActivity.change(2);
                }
                break;
            default:
                break;
        }
    }
}
