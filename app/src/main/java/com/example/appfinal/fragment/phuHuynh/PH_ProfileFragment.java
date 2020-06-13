package com.example.appfinal.fragment.phuHuynh;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.appfinal.R;
import com.example.appfinal.object.PhuHuynh;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class PH_ProfileFragment extends Fragment implements View.OnClickListener {
    @Nullable
    private EditText etName, etOld, etPhone, etStudent;
    private TextView tEmail, tName, tClass, tEdit, tGVCN;
    private Button btnSave, btnRedo, btnCancel;
    private PhuHuynh phuHuynh, phuHuynhFake;
    private String key;
    private DatabaseReference myRef;

    public void setPhuhuynh(PhuHuynh ph) {
        this.phuHuynh = ph;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_ph_fragment_profile, container, false);
        init(view);

        setInfo(phuHuynh);

        tEdit.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        btnRedo.setOnClickListener(this);
        btnCancel.setOnClickListener(this);

        return view;
    }

    private void init(View view) {
        myRef = FirebaseDatabase.getInstance().getReference("PhuHuynh");

        tEmail = view.findViewById(R.id.textViewEmailPH);
        etName = view.findViewById(R.id.editTextNamePH);
        etOld = view.findViewById(R.id.editTextOldPH);
        etPhone = view.findViewById(R.id.editTextPhonePH);
        etStudent = view.findViewById(R.id.editTextNameStudentPH);
        tClass = view.findViewById(R.id.textViewClassPH);
        btnRedo = view.findViewById(R.id.buttonRedoPH);
        btnSave = view.findViewById(R.id.buttonSavePH);
        btnCancel = view.findViewById(R.id.buttonCancelPH);
        tName = view.findViewById(R.id.textViewNamePH);
        tEdit = view.findViewById(R.id.textViewEditPH);
        tGVCN = view.findViewById(R.id.textViewGVCNPH);

        phuHuynhFake = phuHuynh;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textViewEditPH:
                etName.setFocusableInTouchMode(true);
                etStudent.setFocusableInTouchMode(true);
                etOld.setFocusableInTouchMode(true);
                etPhone.setFocusableInTouchMode(true);
                setVisibilityButton(View.VISIBLE);
                break;
            case R.id.buttonSavePH:
                getInfo(phuHuynhFake);
                myRef.child(key).setValue(phuHuynhFake);
                tName.setText(phuHuynhFake.getHoTenPH());

                setFocusEditText();
                setVisibilityButton(View.INVISIBLE);
                break;
            case R.id.buttonRedoPH:
                getInfo(phuHuynhFake);
                etName.setText("");
                etOld.setText("");
                etPhone.setText("");
                etStudent.setText("");
                break;
            case R.id.buttonCancelPH:
                setInfo(phuHuynhFake);
                setFocusEditText();
                setVisibilityButton(View.INVISIBLE);
                break;
            default:
                break;
        }
    }

    private void getInfo(PhuHuynh phuHuynh1) {
        phuHuynh1.setTuoi(Integer.valueOf(etOld.getText().toString()));
        phuHuynh1.setHoTenHS(etStudent.getText().toString());
        phuHuynh1.setHoTenPH(etName.getText().toString());
        phuHuynh1.setSoDienThoai(etPhone.getText().toString());
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
        etStudent.setFocusable(false);
    }

    private void setInfo(PhuHuynh ph) {
        tName.setText(ph.getHoTenPH());
        tEmail.setText(ph.getEmail());
        etName.setText(ph.getHoTenPH());
        etOld.setText(ph.getTuoi().toString());
        etPhone.setText(ph.getSoDienThoai());
        etStudent.setText(ph.getHoTenHS());
        tClass.setText(ph.getLop());
        tGVCN.setText(ph.getGVCN());
    }

}
