package com.example.appfinal.fragment.giaoVien;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.appfinal.GiaoVienActivity;
import com.example.appfinal.R;
import com.example.appfinal.dao.HocVienDAO;
import com.example.appfinal.object.User;
import com.example.appfinal.object.UserAdapter;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class GV_UserFragment extends Fragment {
    private String ten_lop;
    private String sender;
    private static Fragment current_fragment;
    private GV_UserUserFragment gv_userUserFragment;
    private GV_UserChatFragment gv_userChatFragment;
    static final int CHANGE_TO_USER_USER = 2223, CHANGE_TO_USER_CHAT = 2224;

    public void setTenLop(String ten_lop) {
        this.ten_lop = ten_lop;
    }

    public void setSender(String email) {
        this.sender = email;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_gv_fragment_user, container, false);
        init();

        return view;
    }

    private void init() {
        gv_userUserFragment = new GV_UserUserFragment();
        gv_userUserFragment.setGv_userFragment(this);
        gv_userUserFragment.setTenLop(ten_lop);
        gv_userChatFragment = new GV_UserChatFragment();
        gv_userChatFragment.setGv_userFragment(this);
        gv_userChatFragment.setSender(sender);

        getFragmentManager().beginTransaction().replace(R.id.fragment_container_user, gv_userUserFragment).commit();
        current_fragment = gv_userUserFragment;
    }

    private void changeFragment(Fragment newFragment, boolean go) {
        if (go) {
            getFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left)
                    .replace(current_fragment.getId(), newFragment).commit();
            current_fragment = newFragment;
        } else {
            getFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                    .replace(current_fragment.getId(), newFragment).commit();
            current_fragment = newFragment;
        }

    }

    public void change(int fragment) {
        switch (fragment) {
            case CHANGE_TO_USER_USER:
                changeFragment(gv_userUserFragment, false);
                break;
            case CHANGE_TO_USER_CHAT:
                changeFragment(gv_userChatFragment, true);
                break;
            default:
                break;
        }
    }

    void setReceiver(String name, String email) {
        gv_userChatFragment.setReceiver(name, email, ten_lop);
    }
}
