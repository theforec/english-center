package com.example.appfinal.fragment.giaoVien;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appfinal.GiaoVienActivity;
import com.example.appfinal.R;
import com.example.appfinal.dao.HocVienDAO;
import com.example.appfinal.dao.SelectImageDAO;
import com.example.appfinal.dao.VideoDAO;
import com.example.appfinal.object.GiaoVien;
import com.example.appfinal.object.Lesson;
import com.example.appfinal.object.Lop;
import com.example.appfinal.object.LopAdapter;
import com.example.appfinal.object.SelectImage;
import com.example.appfinal.object.SelectImageAdapter;
import com.example.appfinal.object.Status;
import com.example.appfinal.object.StatusAdapter;
import com.example.appfinal.object.Video;
import com.example.appfinal.object.VideoAdapter;
import com.example.appfinal.object.ViewWordGV;
import com.example.appfinal.object.Word;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageTask;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
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

public class GV_LessonFragment extends Fragment implements View.OnClickListener,
        SelectImageAdapter.OnItemClickListener, LopAdapter.OnItemListener, VideoAdapter.OnItemListener {
    @Nullable

    private EditText et_lesson, et_book, et_topic, et_activities, et_homeworks;
    private TextView t_date, btn_back;
    private Button btn_date, btn_class, btn_add_word, btn_video;
    private Button btn_upload, btn_cancel;
    private YouTubePlayerView video_lesson;
    private String link_video;

    private Calendar calendar;
    private int year, month, day;

    private Button buttonPickImage;
    private ImageView imageViewPickImage;
    private TextView textViewNameImage;

    private LinearLayout layoutWords;
    private ArrayList<Word> arrWords;
    private ArrayList<SelectImage> arrImage;
    private ArrayList<Lop> arrLop;
    private ArrayList<Video> arrVideo;
    private ArrayList<Status> arrStatus;
    private ArrayList<String> arrHocVien;

    private PopupWindow popupListLop = null;
    private PopupWindow popupListImage = null;
    private PopupWindow popupListVideo = null;

    private ProgressBar pbUpload, pbImgLoading;
    private ProgressDialog pdLoading;
    private static final int DELAY_PROGRESS = 500;

    private DatabaseReference databaseRef;
    private StorageTask uploadTask;

    private GiaoVien giaoVien;
    private Lesson lesson;
    private int STATUS_LESSON = 1;
    public final static int STATUS_LESSON_NEW = 1, STATUS_LESSON_LOAD = 2;

    private Handler handler_change_video;
    private static final int CHANGE_VIDEO_ID = 56709;
    private static final String KEY_CHANGE_VIDEO_ID = "change_video_id";
    public static Handler handler;

    public void setLesson(Lesson ls) {
        this.lesson = ls;
    }

    public void setStatus(int x) {
        this.STATUS_LESSON = x;
    }

    public void setGiaoVien(GiaoVien gv) {
        this.giaoVien = gv;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.user_gv_fragment_lesson, container, false);
        init(view);

        if (STATUS_LESSON == STATUS_LESSON_LOAD) {
            setData();
            setupTableStatus(view);
        } else if (STATUS_LESSON == STATUS_LESSON_NEW) {
            lesson = new Lesson();
            setData();
        }
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {

                if (msg.what == SelectImageDAO.GET_LIST_IMAGE) {
                    arrImage = msg.getData().getParcelableArrayList("images");
                    showListImage(arrImage, buttonPickImage);
                    pdLoading.dismiss();
                } else if (msg.what == VideoDAO.GET_LIST_VIDEOS) {
                    arrVideo = msg.getData().getParcelableArrayList("videos");
                } else if (msg.what == HocVienDAO.GET_LIST_HOCVIEN) {
                    arrHocVien = msg.getData().getStringArrayList("hocvien");
                    arrStatus = new ArrayList<>();
                    if (arrHocVien != null) {
                        for (int i = 0; i < arrHocVien.size(); i++) {
                            Status newstt = new Status();
                            newstt.setHocVien(arrHocVien.get(i));
                            arrStatus.add(newstt);
                        }
                    }
                    setupTableStatus(view);
                } else if (msg.what == HocVienDAO.GET_LIST_HOCVIEN_FAIL) {
                    Toasty.error(getActivity(), "Tải ảnh thất bại, vui lòng xem lại mạng", Toast.LENGTH_SHORT).show();
                }

                return false;
            }
        });

        //set onclickListener
        //region
        btn_back.setOnClickListener(this);
        btn_date.setOnClickListener(this);
        btn_class.setOnClickListener(this);
        btn_add_word.setOnClickListener(this);
        btn_video.setOnClickListener(this);

        btn_upload.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);

        //endregion

        return view;
    }

    @SuppressLint("CutPasteId")
    private void init(View view) {
        databaseRef = FirebaseDatabase.getInstance().getReference();

        et_lesson = view.findViewById(R.id.editTextLessonGV);
        btn_date = view.findViewById(R.id.buttonPickDateGV);
        t_date = view.findViewById(R.id.textViewDateGV);
        btn_class = view.findViewById(R.id.buttonPickClassGV);
        et_book = view.findViewById(R.id.editTextBookGV);
        et_topic = view.findViewById(R.id.editTextTopicGV);
        btn_add_word = view.findViewById(R.id.buttonAddWord);
        btn_back = view.findViewById(R.id.textViewBackToLesson);
        btn_video = view.findViewById(R.id.buttonPickVideoGV);
        video_lesson = view.findViewById(R.id.video_lesson_gv);
        getLifecycle().addObserver(video_lesson);
        video_lesson.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(final YouTubePlayer youTubePlayer) {
                youTubePlayer.cueVideo(link_video, 0);

                handler_change_video = new Handler(new Handler.Callback() {
                    @Override
                    public boolean handleMessage(@NonNull Message msg) {
                        if (msg.what == CHANGE_VIDEO_ID) {
                            String new_id = msg.getData().getString(KEY_CHANGE_VIDEO_ID);
                            youTubePlayer.cueVideo(new_id, 0);
                        }
                        return false;
                    }
                });
            }
        });

        et_activities = view.findViewById(R.id.editTextActivities);
        et_homeworks = view.findViewById(R.id.editTextHomeWorksGV);
        pbUpload = view.findViewById(R.id.progressBarUploadGV);
        btn_upload = view.findViewById(R.id.buttonUploadLessonGV);
        btn_cancel = view.findViewById(R.id.buttonCancelLessonGV);

        pdLoading = new ProgressDialog(getContext());
        arrLop = new ArrayList<>();
        arrVideo = new ArrayList<>();
        arrImage = new ArrayList<>();
        arrWords = new ArrayList<>();
        arrHocVien = new ArrayList<>();
        layoutWords = view.findViewById(R.id.layout_listword);

        VideoDAO videoDAO = new VideoDAO();
        videoDAO.getListVideos();
        //day picker
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

    }

    private void setupTableStatus(View view) {
        RecyclerView recyclerViewStatus = view.findViewById(R.id.layout_litstatus);
        LinearLayoutManager lm = new LinearLayoutManager(getContext());
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewStatus.setLayoutManager(lm);
        StatusAdapter statusAdapter = new StatusAdapter(getContext(), arrStatus);
        recyclerViewStatus.setAdapter(statusAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textViewBackToLesson:
            case R.id.buttonCancelLessonGV:
                GiaoVienActivity giaoVienActivity = (GiaoVienActivity) getActivity();
                if (giaoVienActivity != null) {
                    giaoVienActivity.change(GiaoVienActivity.CHANGE_TO_LEARN);
                }
                break;
            case R.id.buttonPickDateGV:
                PickDate();
                break;
            case R.id.buttonPickClassGV:
                arrLop = giaoVien.getDSLopChuNhiem();
                showListLop(arrLop);
                break;
            case R.id.buttonAddWord:
                addWord();
                break;
            case R.id.buttonPickVideoGV:
                showListVideo(arrVideo);
                break;
            case R.id.buttonUploadLessonGV:
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Xác nhận")
                        .setMessage("Đăng bài học hôm nay?")
                        .setPositiveButton("ĐỒNG Ý", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                uploadLesson();
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

    private void addWord() {
        final ViewWordGV viewWord = new ViewWordGV(getContext());
        viewWord.getButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonPickImage = viewWord.getButton();
                imageViewPickImage = viewWord.getImageView();
                textViewNameImage = viewWord.getTextView();

                SelectImageDAO selectImageDAO = new SelectImageDAO();
                selectImageDAO.getListImage();
                LoadingData("ảnh");
            }
        });
        layoutWords.addView(viewWord.getView());
    }

    private void clearData() {
        et_lesson.setText("Lesson: ");
        btn_date.setText("Chọn ngày");
        t_date.setText("Nội dung bài học ngày: ");
        btn_class.setText("chọn lớp");
        et_book.setText("");
        et_topic.setText("");
        if (!arrWords.isEmpty()) {
            arrWords.clear();
        }
        link_video = "";
        et_activities.setText("");
        et_homeworks.setText("");
    }

    private void setEditable(boolean b) {
        et_lesson.setFocusable(b);
        btn_date.setEnabled(b);
        btn_class.setEnabled(b);
        et_book.setFocusable(b);
        et_topic.setFocusable(b);
        btn_add_word.setEnabled(b);
        et_activities.setFocusable(b);
        et_homeworks.setFocusable(b);
        if (b) {
            btn_upload.setVisibility(View.VISIBLE);
            btn_cancel.setVisibility(View.VISIBLE);
        } else {
            btn_upload.setVisibility(View.INVISIBLE);
            btn_cancel.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        et_lesson.setText(lesson.getLesson());
        et_book.setText(lesson.getBook());
        et_topic.setText(lesson.getTopic());
        arrWords = lesson.getDanhSachTu();
        for (int i = 0; i < arrWords.size(); i++) {
            ViewWordGV viewWordGV = new ViewWordGV(getContext());
            viewWordGV.getEditText().setText(arrWords.get(i).getTu());
        }
        et_activities.setText(lesson.getHoatDong());
        et_homeworks.setText(lesson.getBaiTap());
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void setData() {
        et_lesson.setText(lesson.getLesson());
        et_book.setText(lesson.getBook());
        et_topic.setText(lesson.getTopic());

        btn_date.setText(lesson.getNgay());
        t_date.setText("Nội dung bài học ngày: " + lesson.getNgay());
        btn_class.setText(lesson.getLop());
        arrWords = lesson.getDanhSachTu();
        for (int i = 0; i < arrWords.size(); i++) {
            ViewWordGV viewWordGV = new ViewWordGV(getContext());
            viewWordGV.getEditText().setText(arrWords.get(i).getTu());
            Picasso.with(getContext()).load(arrWords.get(i).getLinkAnh()).into(viewWordGV.getImageView());
            layoutWords.addView(viewWordGV.getView());
        }
        link_video = lesson.getLinkVideo();
        et_activities.setText(lesson.getHoatDong());
        et_homeworks.setText(lesson.getBaiTap());
        arrStatus = lesson.getTinhHinhLopHoc();
    }

    private void uploadLesson() {
        Lesson lesson = new Lesson();
        setDataLesson(lesson);
        String lessonID = databaseRef.push().getKey();
        databaseRef.child("BaiHoc").child(lesson.getLop()).child(lessonID).setValue(lesson)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toasty.success(getContext(), "Đăng thành công", Toast.LENGTH_SHORT).show();
                            GiaoVienActivity giaoVienActivity = (GiaoVienActivity) getActivity();
                            if (giaoVienActivity != null) {
                                clearData();
                                giaoVienActivity.change(GiaoVienActivity.CHANGE_TO_LEARN);
                            }
                        }
                    }
                });
    }

    private void setDataLesson(Lesson lesson) {
        lesson.setLesson(et_lesson.getText().toString());
        lesson.setNgay(btn_date.getText().toString());
        lesson.setLop(btn_class.getText().toString());
        lesson.setBook(et_book.getText().toString());
        lesson.setTopic(et_topic.getText().toString());
        lesson.setDanhSachTu(arrWords);
        lesson.setHoatDong(et_activities.getText().toString());
        lesson.setLinkVideo(link_video);
        lesson.setBaiTap(et_homeworks.getText().toString());
        lesson.setTinhHinhLopHoc(arrStatus);
    }

    private void PickDate() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year, month, dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                String date = simpleDateFormat.format(calendar.getTime());
                btn_date.setText(date);
                t_date.setText("Nội dung bài học ngày: " + date);
            }
        }, year, month, day);
        datePickerDialog.show();
    }

    private PopupWindow showListLop(ArrayList<Lop> arrayList) {
        try {
            LayoutInflater layoutInflaterLop = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = layoutInflaterLop.inflate(R.layout.popup_window_class_gv, null);
            final RecyclerView lvData = layout.findViewById(R.id.recyclerViewListClass);
            LinearLayoutManager lm = new LinearLayoutManager(getContext());
            lm.setOrientation(LinearLayoutManager.VERTICAL);
            lvData.setLayoutManager(lm);

            LopAdapter listLopAdapter = new LopAdapter(getContext(), arrayList);
            lvData.setAdapter(listLopAdapter);
            listLopAdapter.setOnItemClickListener(GV_LessonFragment.this);

            layout.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            popupListLop = new PopupWindow(layout, FrameLayout.LayoutParams.MATCH_PARENT, 600, true);
            popupListLop.showAsDropDown(btn_class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return popupListLop;
    }

    private PopupWindow showListImage(ArrayList<SelectImage> arrayList, Button buttonPickImage) {
        try {
            LayoutInflater layoutInflaterImage = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = layoutInflaterImage.inflate(R.layout.popup_window_image_gv, null);
            final RecyclerView lvData = layout.findViewById(R.id.recyclerViewListImage);
            LinearLayoutManager lm = new LinearLayoutManager(getContext());
            lm.setOrientation(LinearLayoutManager.VERTICAL);
            lvData.setLayoutManager(lm);

            SelectImageAdapter listImageAdapter = new SelectImageAdapter(getContext(), arrayList);
            lvData.setAdapter(listImageAdapter);
            listImageAdapter.setOnItemClickListener(GV_LessonFragment.this);

            layout.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            popupListImage = new PopupWindow(layout, FrameLayout.LayoutParams.MATCH_PARENT, 725, true);
            popupListImage.showAsDropDown(buttonPickImage);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return popupListImage;
    }

    private PopupWindow showListVideo(ArrayList<Video> arrayList) {
        try {
            LayoutInflater layoutInflaterImage = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = layoutInflaterImage.inflate(R.layout.popup_window_video_gv, null);
            final RecyclerView lvData = layout.findViewById(R.id.recyclerViewListVideoGV);
            LinearLayoutManager lm = new LinearLayoutManager(getContext());
            lm.setOrientation(LinearLayoutManager.VERTICAL);
            lvData.setLayoutManager(lm);

            VideoAdapter videoAdapter = new VideoAdapter(getContext(), arrayList);
            lvData.setAdapter(videoAdapter);
            videoAdapter.setOnItemClickListener(GV_LessonFragment.this);

            layout.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            popupListVideo = new PopupWindow(layout, FrameLayout.LayoutParams.MATCH_PARENT, 725, true);
            popupListVideo.showAsDropDown(btn_video);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return popupListVideo;
    }

    @Override
    public void onItemImageClick(int position) {
        Word word = new Word();
        word.setTu(arrImage.get(position).getTenAnh());
        word.setLinkAnh(arrImage.get(position).getLinkAnh());
        arrWords.add(word);

        textViewNameImage.setText(word.getTu() + ".jpg");
        Picasso.with(getContext()).load(word.getLinkAnh()).into(imageViewPickImage);
        popupListImage.dismiss();
    }

    @Override
    public void onItemClassClick(int position) {
        btn_class.setText(arrLop.get(position).getTenLop());

        HocVienDAO hocVienDAO = new HocVienDAO();
        hocVienDAO.getListHocVien(arrLop.get(position).getTenLop());
        popupListLop.dismiss();

    }

    @Override
    public void onItemVideoClick(int position) {
        link_video = arrVideo.get(position).getLinkVideo();
        Message msg = new Message();
        msg.what = CHANGE_VIDEO_ID;
        msg.setTarget(handler_change_video);
        Bundle bundle = new Bundle();
        bundle.putString(KEY_CHANGE_VIDEO_ID, link_video);
        msg.setData(bundle);
        msg.sendToTarget();

        btn_video.setAllCaps(false);
        btn_video.setText(arrVideo.get(position).getTenVideo());

        popupListVideo.dismiss();
    }

    private void LoadingData(String name) {
        pdLoading.setMessage("Đang tải danh sách " + name + " ...");
        pdLoading.setCancelable(true);
        pdLoading.setCanceledOnTouchOutside(true);
        pdLoading.show();
    }


/*
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null && data.getData() != null) {
            mImageUri = data.getData();
            if (requestCode == OPEN_IMAGE_1) {
                Picasso.with(getContext()).load(mImageUri).into(imvImage1);
            }
        }
    }

    private String getFileExtentions(Uri uri) {
        ContentResolver cR = getContext().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void openfileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, REQUEST_CODE_IMAGE);
    }
    private void uploadFile() {
        if (imvImage1 != null) {
            StorageReference fileRef = storageRef.child(System.currentTimeMillis() + "." + getFileExtentions(mImageUri));
            uploadTask = fileRef.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    pbUpload.setProgress(0);
                                }
                            }, DELAY_PROGRESS);
                            Toast.makeText(getActivity(), "Upload thành công", Toast.LENGTH_SHORT).show();
                            //Lesson lesson = new Lesson(taskSnapshot.getUploadSessionUri().toString());
                            //SelectVideo selectVideo = new SelectVideo("", taskSnapshot.getUploadSessionUri().toString());
                            SelectLop selectLop = new SelectLop("Kendy B4", "");
                            String uploadId = databaseRef.push().getKey();
                            databaseRef.child(uploadId).setValue(selectLop);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(), "Lỗi upload", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            pbUpload.setProgress((int) progress);
                        }
                    });
        } else {
            Toast.makeText(getContext(), "ko ảnh", Toast.LENGTH_SHORT).show();
        }

    }*/

}
