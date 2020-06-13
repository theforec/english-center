package com.example.appfinal.fragment.giaoVien;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appfinal.GiaoVienActivity;
import com.example.appfinal.R;
import com.example.appfinal.object.GiaoVien;
import com.example.appfinal.object.Lop;
import com.example.appfinal.object.LopChatAdapter;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class GV_ChatFragment extends Fragment implements LopChatAdapter.OnItemListener {
    @Nullable
    private RecyclerView list_class;
    private ArrayList<Lop> arrLop;
    private GiaoVien giaoVien;

    public void setGiaoVien(GiaoVien gv) {
        this.giaoVien = gv;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_gv_fragment_chat, container, false);
        init(view);

        return view;
    }

    private void init(View view) {
        arrLop = new ArrayList<>();
        arrLop = giaoVien.getDSLopChuNhiem();
        list_class = view.findViewById(R.id.layout_list_chat_gv);
        LinearLayoutManager lm = new LinearLayoutManager(getContext());
        list_class.setLayoutManager(lm);
        LopChatAdapter lopChatAdapter = new LopChatAdapter(getContext(), arrLop);
        list_class.setAdapter(lopChatAdapter);
        lopChatAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onItemChatClassClick(int position) {
        GiaoVienActivity giaoVienActivity = (GiaoVienActivity) getActivity();
        giaoVienActivity.change(GiaoVienActivity.CHANGE_TO_USER);
        giaoVienActivity.setTenLop(arrLop.get(position).getTenLop());
    }
}
