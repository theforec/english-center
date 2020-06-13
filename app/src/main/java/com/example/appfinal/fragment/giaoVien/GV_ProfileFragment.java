package com.example.appfinal.fragment.giaoVien;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.appfinal.R;
import com.example.appfinal.object.GiaoVien;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class GV_ProfileFragment extends Fragment implements View.OnClickListener {
    @Nullable
    private EditText etName, etOld, etPhone, etAddress;
    private TextView tEmail, tName, tEdit, tDSLop;
    private Button btnSave, btnRedo, btnCancel;
    private GiaoVien giaoVien, giaoVienFake;
    private String key;
    private DatabaseReference myRef;

    public void setGiaoVien(GiaoVien gv) {
        this.giaoVien = gv;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_gv_fragment_profile, container, false);

        init(view);

        setInfo(giaoVien);

        tEdit.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        btnRedo.setOnClickListener(this);
        btnCancel.setOnClickListener(this);

        return view;
    }

    private void init(View view) {
        myRef = FirebaseDatabase.getInstance().getReference("GiaoVien");

        tName = view.findViewById(R.id.textViewNameGV);
        tEdit = view.findViewById(R.id.textViewEditGV);
        tDSLop = view.findViewById(R.id.textviewDSLopChuNhiemGV);
        tEmail = view.findViewById(R.id.textViewEmailGV);
        etName = view.findViewById(R.id.editTextNameGV);
        etOld = view.findViewById(R.id.editTextOldGV);
        etPhone = view.findViewById(R.id.editTextPhoneGV);
        etAddress = view.findViewById(R.id.editTextAddressGV);
        btnSave = view.findViewById(R.id.buttonSaveGV);
        btnRedo = view.findViewById(R.id.buttonRedoGV);
        btnCancel = view.findViewById(R.id.buttonCancelGV);

        giaoVienFake = giaoVien;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textViewEditGV:
                etName.setFocusableInTouchMode(true);
                etOld.setFocusableInTouchMode(true);
                etPhone.setFocusableInTouchMode(true);
                etAddress.setFocusableInTouchMode(true);
                setVisibilityButton(View.VISIBLE);
                break;
            case R.id.buttonSaveGV:
                getInfo(giaoVienFake);
                myRef.child(key).setValue(giaoVienFake);
                tName.setText(giaoVienFake.getHoTen());

                setFocusEditText();
                setVisibilityButton(View.INVISIBLE);
                break;
            case R.id.buttonRedoGV:
                getInfo(giaoVienFake);
                etName.setText("");
                etOld.setText("");
                etPhone.setText("");
                etAddress.setText("");
                break;
            case R.id.buttonCancelGV:
                tDSLop.setText("Danh sách lớp chủ nhiệm: ");
                setInfo(giaoVienFake);
                setFocusEditText();
                setVisibilityButton(View.INVISIBLE);
                break;
            default:
                break;
        }
    }

    private void getInfo(GiaoVien giaoVienFake1) {
        giaoVienFake1.setTuoi(Integer.valueOf(etOld.getText().toString()));
        giaoVienFake1.setSoDienThoai(etPhone.getText().toString());
        giaoVienFake1.setHoTen(etName.getText().toString());
        giaoVienFake1.setDiaChi(etAddress.getText().toString());
    }

    private void setVisibilityButton(int invisible) {
        btnSave.setVisibility(invisible);
        btnRedo.setVisibility(invisible);
        btnCancel.setVisibility(invisible);
    }

    private void setFocusEditText() {
        etName.setFocusable(false);
        etOld.setFocusable(false);
        etPhone.setFocusable(false);
        etAddress.setFocusable(false);
    }

    private void setInfo(GiaoVien gv) {
        tName.setText(gv.getHoTen());
        tEmail.setText(gv.getEmail());
        etName.setText(gv.getHoTen());
        etOld.setText(gv.getTuoi().toString());
        etPhone.setText(gv.getSoDienThoai());
        etAddress.setText(gv.getDiaChi());
        String ds = "", noi = ", ";
        for (int i = 0; i < gv.getDSLopChuNhiem().size(); i++) {
            if (i == gv.getDSLopChuNhiem().size() - 1) {
                noi = "";
            }
            ds += gv.getDSLopChuNhiem().get(i).getTenLop() + noi;
        }

        tDSLop.setText(tDSLop.getText().toString() + ds);
    }
}
