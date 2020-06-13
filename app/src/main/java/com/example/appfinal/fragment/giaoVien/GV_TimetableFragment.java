package com.example.appfinal.fragment.giaoVien;

import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.appfinal.R;
import com.example.appfinal.dao.BuoiHocDAO;
import com.example.appfinal.object.BuoiHocGV;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class GV_TimetableFragment extends Fragment {
    private TableLayout timetable;
    private String name;
    private ArrayList<BuoiHocGV> arrBuoiHoc;
    public static Handler handler;

    public void setName(String name) {
        this.name = name;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_gv_fragment_timetable, container, false);
        init(view);

        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                if (msg.what == BuoiHocDAO.GET_BUOIHOC_GV) {
                    arrBuoiHoc = msg.getData().getParcelableArrayList("buoihocgv");
                    initTable();
                }
                return false;
            }
        });

        return view;
    }

    private void init(View view) {
        timetable = view.findViewById(R.id.timetable_gv);
        arrBuoiHoc = new ArrayList<>();
        BuoiHocDAO buoiHocDAO = new BuoiHocDAO();
        buoiHocDAO.getBuoiHocGV(name);
    }

    private void initTable() {
        int m_width = Resources.getSystem().getDisplayMetrics().widthPixels;
        int soDong = 4, soCot = 5;
        int width = m_width / soCot;
        int height;
        int height_nor = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 70, getResources().getDisplayMetrics());
        int height_thu = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, getResources().getDisplayMetrics());

        for (int i = 0; i < soDong; i++) {
            TableRow tableRow = new TableRow(getContext());
            for (int j = 0; j < soCot; j++) {
                final TextView label = new TextView(getContext());
                height = height_nor;
                if (i == 0) {
                    height = height_thu;
                    label.setText("   Thứ " + (j + 2));
                    label.setTypeface(Typeface.DEFAULT_BOLD);
                }
                TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(width, height);
                label.setLayoutParams(layoutParams);

                for (int k = 0; k < arrBuoiHoc.size(); k++) {
                    int thu = Integer.parseInt(arrBuoiHoc.get(k).getThu()); //=j+2,i=0
                    int buoi = 0; //=i
                    String s_buoi = arrBuoiHoc.get(k).getBuoi();
                    switch (s_buoi) {
                        case "sáng":
                            buoi = 1;
                            break;
                        case "chiều":
                            buoi = 2;
                            break;
                        case "tối":
                            buoi = 3;
                            break;
                        default:
                            break;
                    }
                    String time = arrBuoiHoc.get(k).getThoiGian();
                    String lop = arrBuoiHoc.get(k).getLop();

                    if (j + 2 == thu && i == buoi) label.setText("\n" + time + "\n" + lop);
                }
                label.setBackgroundResource(R.drawable.bg_white_stroke);
                tableRow.addView(label);
            }
            timetable.addView(tableRow);
        }
    }

}
