package com.example.appfinal.fragment.main;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appfinal.R;
import com.example.appfinal.object.Register;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import es.dmoral.toasty.Toasty;

public class RegisterFragment extends Fragment implements View.OnClickListener {
    private EditText etName, etPhone, etMail;
    private String name, phone, mail, address;
    private Button btnSendInfo, btnAddress;
    private DatabaseReference myRef;

    private PopupWindow popupWindow = null;
    private TextView address1, address2, address3, address4, address5, address6, address7, address8, address9, address10;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment_register, container, false);

        init(view);
        btnSendInfo.setOnClickListener(this);
        btnAddress.setOnClickListener(this);


        return view;
    }

    private void setInfo() {
        name = etName.getText().toString().trim();
        phone = etPhone.getText().toString().trim();
        mail = etMail.getText().toString().trim();
        address = btnAddress.getText().toString().trim();
    }

    private void init(View view) {
        myRef = FirebaseDatabase.getInstance().getReference();
        etName = view.findViewById(R.id.editTextNameRegister);
        etPhone = view.findViewById(R.id.editTextPhoneRegister);
        etMail = view.findViewById(R.id.editTextEmailRegister);
        btnSendInfo = view.findViewById(R.id.buttonSendInfoRegister);
        btnAddress = view.findViewById(R.id.buttonAddressRegister);
    }

    private void UploadRegister(Register _register) {
        myRef.child("Register").push().setValue(_register);

    }


    private PopupWindow selectAddress() {
        try {
            LayoutInflater mInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = mInflater.inflate(R.layout.popup_window_address, null);

            //ánh xạ + set on click
            //region
            address1 = layout.findViewById(R.id.address1);
            address2 = layout.findViewById(R.id.address2);
            address3 = layout.findViewById(R.id.address3);
            address4 = layout.findViewById(R.id.address4);
            address5 = layout.findViewById(R.id.address5);
            address6 = layout.findViewById(R.id.address6);
            address7 = layout.findViewById(R.id.address7);
            address8 = layout.findViewById(R.id.address8);
            address9 = layout.findViewById(R.id.address9);
            address10 = layout.findViewById(R.id.address10);

            address1.setOnClickListener(this);
            address2.setOnClickListener(this);
            address3.setOnClickListener(this);
            address4.setOnClickListener(this);
            address5.setOnClickListener(this);
            address6.setOnClickListener(this);
            address7.setOnClickListener(this);
            address8.setOnClickListener(this);
            address9.setOnClickListener(this);
            address10.setOnClickListener(this);
            //endregion

            layout.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            popupWindow = new PopupWindow(layout, FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT, true);

            Drawable background = getResources().getDrawable(R.drawable.bg_white_stroke);
            popupWindow.setBackgroundDrawable(background);
            popupWindow.showAtLocation(getActivity().findViewById(R.id.layout_register), Gravity.CENTER, 0, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return popupWindow;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonSendInfoRegister:
                Register register = new Register();
                int check = 1;
                setInfo();
                if (TextUtils.isEmpty(name)) {
                    etName.setError("Nhập Họ tên");
                    check = 0;
                }
                if (TextUtils.isEmpty(phone)) {
                    etPhone.setError("Nhập Số điện thoại");
                    check = 0;
                }
                if (TextUtils.isEmpty(mail)) {
                    etMail.setError("Nhập Email");
                    check = 0;
                }
                if (check == 1) {
                    register.setEmail(mail);
                    register.setName(name);
                    register.setPhone(phone);
                    register.setAddress(address);
                    UploadRegister(register);
                    Toasty.success(getContext(),"Đăng ký thành công", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.buttonAddressRegister:
                selectAddress();
                break;
            case R.id.address1:
                btnAddress.setText(address1.getText().toString());
                popupWindow.dismiss();
                break;
            case R.id.address2:
                btnAddress.setText(address2.getText().toString());
                popupWindow.dismiss();
                break;
            case R.id.address3:
                btnAddress.setText(address3.getText().toString());
                popupWindow.dismiss();
                break;
            case R.id.address4:
                btnAddress.setText(address4.getText().toString());
                popupWindow.dismiss();
                break;
            case R.id.address5:
                btnAddress.setText(address5.getText().toString());
                popupWindow.dismiss();
                break;
            case R.id.address6:
                btnAddress.setText(address6.getText().toString());
                popupWindow.dismiss();
                break;
            case R.id.address7:
                btnAddress.setText(address7.getText().toString());
                popupWindow.dismiss();
                break;
            case R.id.address8:
                btnAddress.setText(address8.getText().toString());
                popupWindow.dismiss();
                break;
            case R.id.address9:
                btnAddress.setText(address9.getText().toString());
                popupWindow.dismiss();
                break;
            case R.id.address10:
                btnAddress.setText(address10.getText().toString());
                popupWindow.dismiss();
                break;
            default:
                break;
        }
    }
}
