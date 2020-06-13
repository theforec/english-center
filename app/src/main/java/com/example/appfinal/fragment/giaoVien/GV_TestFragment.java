package com.example.appfinal.fragment.giaoVien;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appfinal.GiaoVienActivity;
import com.example.appfinal.R;
import com.example.appfinal.dao.SelectImageDAO;
import com.example.appfinal.object.GiaoVien;
import com.example.appfinal.object.Lop;
import com.example.appfinal.object.LopAdapter;
import com.example.appfinal.object.SelectImage;
import com.example.appfinal.object.Test;
import com.example.appfinal.object.ViewImage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import es.dmoral.toasty.Toasty;

public class GV_TestFragment extends Fragment implements View.OnClickListener, LopAdapter.OnItemListener {
    private TextView tBack;
    private EditText eTest, eTopic;
    private Button btnDate, btnClass, btnAdd, btnUpload, btnCancel;
    private Calendar calendar;
    private LinearLayout layout_images;
    private ArrayList<Lop> arrLops;
    private ArrayList<SelectImage> arrImages;
    private ArrayList<SelectImage> arrImagesUpload;
    private PopupWindow popupListLop = null;
    private PopupWindow popupTableImage = null;
    private ProgressDialog pdLoading;

    private GiaoVien giaoVien;
    private Test mTest;
    private DatabaseReference myRef;
    public static final int STATUS_TEST_LOAD = 53, STATUS_TEST_NEW = 54;
    private int STATUS_TEST;
    public static Handler handler;

    public void setGiaoVien(GiaoVien gv) {
        this.giaoVien = gv;
    }

    public void setTest(Test test) {
        this.mTest = test;
    }

    public void setStatus(int stt) {
        STATUS_TEST = stt;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_gv_fragment_test, container, false);
        init(view);

        if (STATUS_TEST == STATUS_TEST_LOAD) {
            setData();
        } else if (STATUS_TEST == STATUS_TEST_NEW) {
            setTest(new Test());
            setData();
        }

        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {

                if (msg.what == SelectImageDAO.GET_LIST_IMAGE) {
                    arrImages = msg.getData().getParcelableArrayList("images");
                }
                return false;
            }
        });


        tBack.setOnClickListener(this);
        btnDate.setOnClickListener(this);
        btnClass.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
        btnUpload.setOnClickListener(this);
        btnCancel.setOnClickListener(this);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        eTest.setText(mTest.getTestName());
        eTopic.setText(mTest.getTopic());
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void setData() {
        eTest.setText(mTest.getTestName());
        btnDate.setText(mTest.getDate());
        btnClass.setText(mTest.getLop());
        eTopic.setText(mTest.getTopic());
        arrImagesUpload = mTest.getArrImages();
        for (int i = 0; i < arrImagesUpload.size(); i++) {
            ViewImage viewImage = new ViewImage(getContext());
            viewImage.getTextView().setText(arrImagesUpload.get(i).getTenAnh() + ".jpg");
            Picasso.with(getContext()).load(arrImagesUpload.get(i).getLinkAnh()).into(viewImage.getImageView());
            layout_images.addView(viewImage.getView());
        }
    }

    private void init(View view) {
        myRef = FirebaseDatabase.getInstance().getReference();

        tBack = view.findViewById(R.id.text_view_back_to_learn_gv);
        eTest = view.findViewById(R.id.edit_text_test);
        eTopic = view.findViewById(R.id.edit_text_topic_test);
        btnDate = view.findViewById(R.id.button_pick_date_test);
        btnClass = view.findViewById(R.id.button_pick_class_test);
        btnAdd = view.findViewById(R.id.button_add_image_test);
        btnUpload = view.findViewById(R.id.button_upload_test);
        btnCancel = view.findViewById(R.id.button_cancel_test);

        pdLoading = new ProgressDialog(getContext());
        arrLops = new ArrayList<>();
        arrImagesUpload = new ArrayList<>();
        arrImages = new ArrayList<>();
        SelectImageDAO selectImageDAO = new SelectImageDAO();
        selectImageDAO.getListImage();
        layout_images = view.findViewById(R.id.layout_list_images_test);
    }

    private void LoadingImages() {
        pdLoading.setMessage("Đang tải danh sách ảnh ...");
        pdLoading.setCancelable(true);
        pdLoading.setCanceledOnTouchOutside(true);
        pdLoading.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_view_back_to_learn_gv:
            case R.id.button_cancel_test:
                GiaoVienActivity giaoVienActivity = (GiaoVienActivity) getActivity();
                giaoVienActivity.change(GiaoVienActivity.CHANGE_TO_LEARN);
                break;
            case R.id.button_pick_date_test:
                pickDate();
                break;
            case R.id.button_pick_class_test:
                arrLops = giaoVien.getDSLopChuNhiem();
                pickClass(arrLops);
                break;
            case R.id.button_add_image_test:
                LoadingImages();
                pickImage();
                btnUpload.setVisibility(View.VISIBLE);
                btnCancel.setVisibility(View.VISIBLE);
                break;
            case R.id.button_upload_test:
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Xác nhận")
                        .setMessage("Đăng bài kiểm tra?")
                        .setPositiveButton("ĐỒNG Ý", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                upLoadTest();
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("KHÔNG", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create().show();

                break;
            default:
                break;
        }
    }


    private PopupWindow pickClass(ArrayList<Lop> arrayList) {
        try {
            LayoutInflater layoutInflaterLop = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = layoutInflaterLop.inflate(R.layout.popup_window_class_gv, null);
            final RecyclerView lvData = layout.findViewById(R.id.recyclerViewListClass);
            LinearLayoutManager lm = new LinearLayoutManager(getContext());
            lm.setOrientation(LinearLayoutManager.VERTICAL);
            lvData.setLayoutManager(lm);

            LopAdapter listLopAdapter = new LopAdapter(getContext(), arrayList);
            lvData.setAdapter(listLopAdapter);
            listLopAdapter.setOnItemClickListener(GV_TestFragment.this);

            layout.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            popupListLop = new PopupWindow(layout, FrameLayout.LayoutParams.MATCH_PARENT, 600, true);
            popupListLop.showAsDropDown(btnClass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return popupListLop;
    }

    private PopupWindow pickImage() {
        try {
            LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = layoutInflater.inflate(R.layout.popup_window_table_image, null);
            TableLayout table_image = layout.findViewById(R.id.table_image);
            int soCot = 3;
            int soDong = arrImages.size() / soCot;
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
                            arrImagesUpload.add(arrImages.get(vitri));

                            ViewImage viewImage = new ViewImage(getActivity());
                            viewImage.getTextView().setText(arrImages.get(vitri).getTenAnh() + ".jpg");
                            Picasso.with(getActivity()).load(arrImages.get(vitri).getLinkAnh()).into(viewImage.getImageView());
                            layout_images.addView(viewImage.getView());

                            popupTableImage.dismiss();
                        }
                    });
                }
                table_image.addView(tableRow);
            }

            pdLoading.dismiss();

            layout.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            popupTableImage = new PopupWindow(layout, FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT, true);

            popupTableImage.showAtLocation(getActivity().findViewById(R.id.layout_add_test), Gravity.CENTER, 0, 100);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return popupTableImage;
    }

    private void pickDate() {
        int year, month, day;
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year, month, dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                String date = simpleDateFormat.format(calendar.getTime());
                btnDate.setText(date);
            }
        }, year, month, day);
        datePickerDialog.show();
    }

    private void getTest(Test test) {
        test.setLop(btnClass.getText().toString());
        test.setDate(btnDate.getText().toString());
        test.setArrImages(arrImagesUpload);
        test.setTestName(eTest.getText().toString());
        test.setTopic(eTopic.getText().toString());
    }

    private void upLoadTest() {
        Test test = new Test();
        getTest(test);
        myRef.child("BaiKiemTra").child(test.getLop()).push().setValue(test)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toasty.success(getContext(), "Đăng thành công", Toast.LENGTH_SHORT).show();
                            GiaoVienActivity giaoVienActivity2 = (GiaoVienActivity) getActivity();
                            giaoVienActivity2.change(GiaoVienActivity.CHANGE_TO_LEARN);
                        } else {
                            Toasty.error(getContext(), "Đăng thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onItemClassClick(int position) {
        btnClass.setText(arrLops.get(position).getTenLop());
        popupListLop.dismiss();
    }

}
