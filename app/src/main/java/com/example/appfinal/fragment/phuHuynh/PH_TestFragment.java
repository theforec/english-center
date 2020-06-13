package com.example.appfinal.fragment.phuHuynh;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appfinal.PhuHuynhActivity;
import com.example.appfinal.R;
import com.example.appfinal.dao.SelectImageDAO;
import com.example.appfinal.object.SelectImage;
import com.example.appfinal.object.Test;
import com.example.appfinal.object.ViewImage;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import es.dmoral.toasty.Toasty;

public class PH_TestFragment extends Fragment implements View.OnClickListener {
    private Test mTest;
    private TextView t_topic, t_date, t_test;
    private ArrayList<SelectImage> arrImages;
    private ArrayList<SelectImage> arrAnswer;
    private ArrayList<SelectImage> arrResult;
    private LinearLayout layout_images;
    private PopupWindow popupTableImage = null;

    public static Handler handler;

    public void setTest(Test test) {
        this.mTest = test;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_ph_fragment_test, container, false);
        init(view);
        setData();

        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                if (msg.what == SelectImageDAO.GET_LIST_IMAGE_PH) {
                    arrImages = msg.getData().getParcelableArrayList("images");
                }

                return false;
            }
        });


        return view;
    }

    private void setData() {
        t_topic.setText(mTest.getTopic());
        t_date.setText("Ngày: " + mTest.getDate());
        t_test.setText(mTest.getTestName());
        arrResult = mTest.getArrImages();
    }

    private void init(View view) {
        arrImages = new ArrayList<>();
        arrAnswer = new ArrayList<>();
        arrResult = new ArrayList<>();
        layout_images = view.findViewById(R.id.layout_add_image_test_ph);
        TextView t_back = view.findViewById(R.id.text_view_back_to_learn_ph);
        t_back.setOnClickListener(this);
        t_test = view.findViewById(R.id.text_view_test_ph);
        t_date = view.findViewById(R.id.text_view_date_ph);
        t_topic = view.findViewById(R.id.text_view_topic_test_ph);
        Button btn_pick_image = view.findViewById(R.id.button_pick_image_test_ph);
        Button btn_answer = view.findViewById(R.id.button_answer);
        Button btn_redo = view.findViewById(R.id.button_redo);
        btn_pick_image.setOnClickListener(this);
        btn_answer.setOnClickListener(this);
        btn_redo.setOnClickListener(this);

        SelectImageDAO selectImageDAO = new SelectImageDAO();
        selectImageDAO.getListImage();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_pick_image_test_ph:
                pickImage();
                break;
            case R.id.text_view_back_to_learn_ph:
                PhuHuynhActivity phuHuynhActivity = (PhuHuynhActivity) getActivity();
                if (phuHuynhActivity != null) {
                    phuHuynhActivity.change(PhuHuynhActivity.CHANGE_TO_LEARN);
                }
                break;
            case R.id.button_answer:
                Toasty.success(getContext(), "Trả lời đúng được " + checkResult() + " đáp án", Toast.LENGTH_SHORT).show();
                break;
            case R.id.button_redo:
                rePick();
                break;
            default:
                break;
        }
    }

    private void rePick() {
        layout_images.removeAllViewsInLayout();
    }

    private int checkResult() {
        int score = 0;
        if (arrAnswer.size() == 0) {
            Toasty.error(getContext(), "Chưa chọn ảnh", Toast.LENGTH_SHORT).show();
        } else if (arrAnswer.size() != arrResult.size()) {
            Toasty.error(getContext(), "Sai số lượng ảnh", Toast.LENGTH_SHORT).show();
        } else {
            for (int i = 0; i < arrAnswer.size(); i++) {
                for (int j = 0; j < arrAnswer.size(); j++) {
                    if (arrResult.get(i).getLinkAnh().equalsIgnoreCase(arrAnswer.get(j).getLinkAnh())) {
                        score++;
                        break;
                    }
                }
            }
        }
        return score;
    }

    private PopupWindow pickImage() {
        try {
            LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = layoutInflater.inflate(R.layout.popup_window_table_image, null);
            TableLayout table_image = layout.findViewById(R.id.table_image);
            int soCot = 3;
            int soDong = arrImages.size() / soCot;
            Collections.shuffle(arrImages);
            for (int i = 1; i <= soDong; i++) {
                TableRow tableRow = new TableRow(getContext());
                for (int j = 1; j <= soCot; j++) {
                    final ImageView imageView = new ImageView(getContext());

                    //convert dp sang pixcel
                    Resources r = getResources();
                    int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 106, r.getDisplayMetrics());
                    TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(px, px);
                    imageView.setLayoutParams(layoutParams);

                    final int vitri = soCot * (i - 1) + j - 1;
                    Picasso.with(getContext()).load(arrImages.get(vitri).getLinkAnh()).into(imageView);
                    imageView.setBackground(null);
                    tableRow.addView(imageView);

                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            arrAnswer.add(arrImages.get(vitri));

                            ViewImage viewImage = new ViewImage(getActivity());
                            Picasso.with(getActivity()).load(arrImages.get(vitri).getLinkAnh()).into(viewImage.getImageView());
                            layout_images.addView(viewImage.getView());

                            popupTableImage.dismiss();
                        }
                    });
                }
                table_image.addView(tableRow);
            }

            layout.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            popupTableImage = new PopupWindow(layout, FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT, true);

            popupTableImage.showAtLocation(getActivity().findViewById(R.id.layout_add_test_ph), Gravity.CENTER, 0, 0);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return popupTableImage;
    }
}
