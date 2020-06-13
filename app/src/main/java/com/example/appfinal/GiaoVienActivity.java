package com.example.appfinal;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.appfinal.fragment.giaoVien.GV_TestFragment;
import com.example.appfinal.fragment.giaoVien.GV_TimetableFragment;
import com.example.appfinal.fragment.giaoVien.GV_UserChatFragment;
import com.example.appfinal.fragment.giaoVien.GV_UserFragment;
import com.example.appfinal.object.Account;
import com.example.appfinal.object.GiaoVien;
import com.example.appfinal.object.Lesson;
import com.example.appfinal.fragment.giaoVien.GV_ChatFragment;
import com.example.appfinal.fragment.giaoVien.GV_LessonFragment;
import com.example.appfinal.fragment.giaoVien.GV_LearnFragment;
import com.example.appfinal.fragment.giaoVien.GV_ProfileFragment;
import com.example.appfinal.object.Lop;
import com.example.appfinal.object.Test;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import de.hdodenhof.circleimageview.CircleImageView;

public class GiaoVienActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    private GiaoVien giaoVien;
    private Account account;
    private String key;
    private Toolbar toolbar;
    private TextView tEmail;
    private CircleImageView avatar;
    private static Fragment currentFragment;
    private GV_ProfileFragment gv_profileFragment;
    private GV_LessonFragment gv_lessonFragment;
    private GV_ChatFragment gv_chatFragment;
    private GV_UserFragment gv_userFragment;
    private GV_LearnFragment gv_learnFragment;
    private GV_TimetableFragment gv_timetableFragment;
    private GV_TestFragment gv_testFragment;
    public static final int CHANGE_TO_LESSON = 1, CHANGE_TO_LEARN = 2, CHANGE_TO_TEST = 3;
    public static final int CHANGE_TO_CHAT = 4, CHANGE_TO_USER = 5;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_gv);

        //get data from LoginFragment
        Intent intent = getIntent();
        account = intent.getParcelableExtra("account");
        giaoVien = intent.getParcelableExtra("gv");
        key = intent.getStringExtra("key");

        initFragments();

        toolbar = findViewById(R.id.toolbar_user_gv);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.activity_layout_user_gv);
        NavigationView nav = findViewById(R.id.nav_views_user_gv);
        nav.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

                avatar = findViewById(R.id.imageViewUserGV);
                tEmail = findViewById(R.id.textViewNavUserGV);
                String url = "https://firebasestorage.googleapis.com/v0/b/fir-demo-e2d1c.appspot.com/o/images%2Favatars-000437232558-yuo0mv-t500x500.jpg?alt=media&token=c2b87dfc-e0c1-4597-a1f6-d8bb779f4fa3";
                Glide.with(getApplicationContext()).load(url).into(avatar);
                tEmail.setText(account.getEmail());
            }
        };
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_user_gv, gv_learnFragment).commit();
            currentFragment = gv_learnFragment;
            toolbar.setTitle("Dạy học");
            nav.setCheckedItem(R.id.nav_learn_gv);
        }
    }

    private void initFragments() {
        gv_learnFragment = new GV_LearnFragment();
        gv_learnFragment.setGiaoVien(giaoVien);
        gv_timetableFragment = new GV_TimetableFragment();
        gv_timetableFragment.setName(giaoVien.getHoTen());
        gv_lessonFragment = new GV_LessonFragment();
        gv_lessonFragment.setGiaoVien(giaoVien);
        gv_testFragment = new GV_TestFragment();
        gv_testFragment.setGiaoVien(giaoVien);
        gv_chatFragment = new GV_ChatFragment();
        gv_chatFragment.setGiaoVien(giaoVien);
        gv_userFragment = new GV_UserFragment();
        gv_userFragment.setSender(giaoVien.getEmail());
        gv_profileFragment = new GV_ProfileFragment();
        gv_profileFragment.setKey(key);
        gv_profileFragment.setGiaoVien(giaoVien);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_profile_gv:
                changeFragment(currentFragment, gv_profileFragment);
                toolbar.setTitle("Thông tin cá nhân");
                break;
            case R.id.nav_learn_gv:
                changeFragment(currentFragment, gv_learnFragment);
                toolbar.setTitle("Dạy học");
                break;
            case R.id.nav_tkb_gv:
                changeFragment(currentFragment,gv_timetableFragment );
                toolbar.setTitle("Thời khoá biểu");
                break;
            case R.id.nav_chat_gv:
                changeFragment(currentFragment, gv_chatFragment);
                toolbar.setTitle("Tin nhắn");
                break;
            case R.id.nav_logout_gv:
                new AlertDialog.Builder(this)
                        .setTitle("Đăng xuất")
                        .setMessage("Bạn có muốn đăng xuất?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(GiaoVienActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                drawer.closeDrawer(GravityCompat.START);
                            }
                        })
                        .show();
                break;
            default:
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void changeFragment(Fragment currentFrag, Fragment newFrag) {
        getSupportFragmentManager().beginTransaction().replace(currentFrag.getId(), newFrag).commit();
        currentFragment = newFrag;
    }

    public void changeFragmentAnim(Fragment newFrag, boolean hide) {
        if (hide) {
            getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left)
                    .replace(currentFragment.getId(), newFrag).commit();
            currentFragment = newFrag;
            Handler hl1 = new Handler();
            hl1.postDelayed(new Runnable() {
                @Override
                public void run() {
                    getSupportActionBar().hide();
                }
            }, 200);
        } else {
            getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                    .replace(currentFragment.getId(), newFrag).commit();
            currentFragment = newFrag;
            Handler hl2 = new Handler();
            hl2.postDelayed(new Runnable() {
                @Override
                public void run() {
                    getSupportActionBar().show();
                }
            }, 200);
        }
    }

    public void change(int fragmentNumber) {
        switch (fragmentNumber) {
            case CHANGE_TO_LESSON:
                changeFragmentAnim(gv_lessonFragment, true);
                break;
            case CHANGE_TO_LEARN:
                changeFragmentAnim(gv_learnFragment, false);
                break;
            case CHANGE_TO_TEST:
                changeFragmentAnim(gv_testFragment, true);
                break;
            case CHANGE_TO_USER:
                changeFragmentAnim(gv_userFragment, true);
                break;
            case CHANGE_TO_CHAT:
                changeFragmentAnim(gv_chatFragment, false);
                break;
            default:
                break;
        }
    }

    public void setLesson(Lesson lesson) {
        gv_lessonFragment.setLesson(lesson);
        gv_lessonFragment.setStatus(GV_LessonFragment.STATUS_LESSON_LOAD);
    }

    public void newLesson() {
        gv_lessonFragment.setStatus(GV_LessonFragment.STATUS_LESSON_NEW);
    }

    public void setTest(Test test) {
        gv_testFragment.setTest(test);
        gv_testFragment.setStatus(GV_TestFragment.STATUS_TEST_LOAD);
    }

    public void newTest() {
        gv_testFragment.setStatus(GV_TestFragment.STATUS_TEST_NEW);
    }

    public void setTenLop(String ten_lop) {
        gv_userFragment.setTenLop(ten_lop);
    }

}

