package com.example.appfinal.fragment.main;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appfinal.GiaoVienActivity;
import com.example.appfinal.MainActivity;
import com.example.appfinal.PhuHuynhActivity;
import com.example.appfinal.dao.AccountDAO;
import com.example.appfinal.dao.GiaoVienDAO;
import com.example.appfinal.dao.PhuHuynhDAO;
import com.example.appfinal.object.Account;
import com.example.appfinal.R;
import com.example.appfinal.object.GiaoVien;
import com.example.appfinal.object.PhuHuynh;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import es.dmoral.toasty.Toasty;

public class LoginFragment extends Fragment implements View.OnClickListener {
    private boolean checkLogin;
    @Nullable
    private Button btnLogin;
    private EditText etEmailLogin, etPassWordLogin;
    private CheckBox cbRemember;
    private TextView tForgotPassWord;
    private SharedPreferences sharedPreferences;
    private ProgressDialog progressDialog;
    private Account account;
    private PhuHuynh ph;
    private GiaoVien gv;
    private String key;
    public static Handler handler;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment_login, container, false);

        init(view);
        LoadSharedReferences();

        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {

                progressDialog.dismiss(); //tắt loading progress

                if (msg.what == AccountDAO.GET_ACCOUNT) { //đăng nhập thành công
                    account = msg.getData().getParcelable("account");

                    if (account.getRole() == Account.ROLE_PH) {
                        PhuHuynhDAO phuHuynhDAO = new PhuHuynhDAO();
                        phuHuynhDAO.getPhuHuynh(account.getEmail());
                    } else if (account.getRole() == Account.ROLE_GV) {
                        GiaoVienDAO giaoVienDAO = new GiaoVienDAO();
                        giaoVienDAO.getGiaoVien(account.getEmail());
                    }
                    SetSharedReferences(sharedPreferences, account.getEmail(), account.getPassword()); //lưu pass
                } else if (msg.what == AccountDAO.GET_ACCOUNT_ERROR) {
                    checkLogin = true;

                    if (TextUtils.isEmpty(etEmailLogin.getText().toString().trim())) {
                        etEmailLogin.setError("Nhập Email");
                        checkLogin = false;
                    }
                    if (TextUtils.isEmpty(etPassWordLogin.getText().toString().trim())) {
                        etPassWordLogin.setError("Nhập Password");
                        checkLogin = false;
                    }
                    if (checkLogin) {
                        Toasty.error(getActivity(), "Tài khoản hoặc mật khẩu không chính xác", Toast.LENGTH_SHORT).show();
                    }

                } else if (msg.what == PhuHuynhDAO.GET_PHUHUYNH) {
                    ph = msg.getData().getParcelable("ph");
                    key = msg.getData().getString("key");

                    Intent intent = new Intent(getActivity(), PhuHuynhActivity.class);
                    intent.putExtra("account", account);
                    intent.putExtra("ph", ph);
                    intent.putExtra("key", key);

                    startActivity(intent);
                    getActivity().finish();
                } else if (msg.what == GiaoVienDAO.GET_GIAOVIEN) {
                    gv = msg.getData().getParcelable("gv");
                    key = msg.getData().getString("key");

                    Intent intent = new Intent(getActivity(), GiaoVienActivity.class);
                    intent.putExtra("account", account);
                    intent.putExtra("gv", gv);
                    intent.putExtra("key", key);

                    startActivity(intent);
                    getActivity().finish();
                }
                return false;
            }
        });

        if (btnLogin != null) {
            btnLogin.setOnClickListener(this);
        }
        tForgotPassWord.setOnClickListener(this);
        return view;
    }

    private void LoadingLogin() {
        progressDialog.setMessage("Đang đăng nhập ...");
        progressDialog.setCancelable(true);
        progressDialog.setCanceledOnTouchOutside(true);
        progressDialog.show();
    }

    private void init(View view) {
        etEmailLogin = view.findViewById(R.id.editTextEmailLogin);
        etPassWordLogin = view.findViewById(R.id.editTextPasswordLogin);
        tForgotPassWord = view.findViewById(R.id.textViewForgotPassWord);
        btnLogin = view.findViewById(R.id.buttonLogin);
        cbRemember = view.findViewById(R.id.checkboxRemember);

        account = new Account();
        ph = new PhuHuynh();
        gv = new GiaoVien();
        progressDialog = new ProgressDialog(getActivity());
    }

    private void LoadSharedReferences() {
        sharedPreferences = this.getActivity().getSharedPreferences("dataLogin", Context.MODE_PRIVATE);
        etEmailLogin.setText(sharedPreferences.getString("taikhoan", ""));
        etPassWordLogin.setText(sharedPreferences.getString("matkhau", ""));
        cbRemember.setChecked(sharedPreferences.getBoolean("checked", false));
    }

    private void SetSharedReferences(SharedPreferences sharedPreferences, String em, String pw) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (cbRemember.isChecked()) {
            editor.putString("taikhoan", em);
            editor.putString("matkhau", pw);
            editor.putBoolean("checked", true);
            editor.commit();
        } else {
            editor.remove("taikhoan");
            editor.remove("matkhau");
            editor.remove("checked");
            editor.commit();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonLogin:
                String em = etEmailLogin.getText().toString();
                String pw = etPassWordLogin.getText().toString();

                AccountDAO accountDAO = new AccountDAO();
                accountDAO.getAccount(em, pw);

                LoadingLogin();
                break;
            case R.id.textViewForgotPassWord:
                MainActivity mainActivity = (MainActivity) getActivity();
                if (mainActivity != null) {
                    mainActivity.change(1);
                }
                break;
            default:
                break;
        }
    }
}
