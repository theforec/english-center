package com.example.appfinal.fragment.giaoVien;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
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
import es.dmoral.toasty.Toasty;

public class GV_UserUserFragment extends Fragment implements UserAdapter.OnItemListener {
    private String ten_lop;
    private TextView text_view_lop;
    private ArrayList<User> arrUser;
    private RecyclerView list_user;
    public static Handler handler;
    private GV_UserFragment gv_userFragment;

    public void setGv_userFragment(GV_UserFragment gv_userFragment) {
        this.gv_userFragment = gv_userFragment;
    }

    public void setTenLop(String ten_lop) {
        this.ten_lop = ten_lop;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_gv_fragment_user_user, container, false);
        init(view);

        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                if (msg.what == HocVienDAO.GET_LIST_USER) {
                    arrUser = msg.getData().getParcelableArrayList("users");
                    setUpListUser();
                } else if (msg.what == HocVienDAO.GET_LIST_USER_FAIL) {
                    Toasty.error(getActivity(), "Tải thất bại", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });

        text_view_lop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GiaoVienActivity giaoVienActivity = (GiaoVienActivity) getActivity();
                giaoVienActivity.change(GiaoVienActivity.CHANGE_TO_CHAT);
            }
        });
        return view;
    }

    private void setUpListUser() {
        LinearLayoutManager lm = new LinearLayoutManager(getContext());
        list_user.setLayoutManager(lm);

        UserAdapter userAdapter = new UserAdapter(getContext(), arrUser);
        list_user.setAdapter(userAdapter);
        userAdapter.setOnItemClickListener(this);
    }

    private void init(View view) {
        arrUser = new ArrayList<>();
        HocVienDAO hocVienDAO = new HocVienDAO();
        hocVienDAO.getListUser(ten_lop);

        text_view_lop = view.findViewById(R.id.text_view_back_to_chat_gv);
        text_view_lop.setText(ten_lop);
        list_user = view.findViewById(R.id.layout_list_user_gv);
    }

    @Override
    public void onItemUserClick(int position) {
        if (this.gv_userFragment != null) {
            gv_userFragment.change(GV_UserFragment.CHANGE_TO_USER_CHAT);
            gv_userFragment.setReceiver(arrUser.get(position).getHoTen(), arrUser.get(position).getEmail());
        }
    }

}
