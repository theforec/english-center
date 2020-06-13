package com.example.appfinal;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.appfinal.fragment.phuHuynh.PH_TestFragment;
import com.example.appfinal.fragment.phuHuynh.PH_TimetableFragment;
import com.example.appfinal.object.Account;
import com.example.appfinal.object.Lesson;
import com.example.appfinal.object.PhuHuynh;
import com.example.appfinal.fragment.phuHuynh.PH_ChatFragment;
import com.example.appfinal.fragment.phuHuynh.PH_LearnFragment;
import com.example.appfinal.fragment.phuHuynh.PH_LessonFragment;
import com.example.appfinal.fragment.phuHuynh.PH_ProfileFragment;
import com.example.appfinal.object.Test;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

public class PhuHuynhActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    private Account account;
    private PhuHuynh phuHuynh;
    private Toolbar toolbar;
    private String key;
    public final static int CHANGE_TO_LEARN = 45;
    public final static int CHANGE_TO_LESSON = 46;
    public final static int CHANGE_TO_TEST = 47;

    Fragment currentFragment;
    PH_ProfileFragment ph_profileFragment;
    PH_LearnFragment ph_learnFragment;
    PH_TimetableFragment ph_timetableFragment;
    PH_LessonFragment ph_lessonFragment;
    PH_TestFragment ph_testFragment;
    PH_ChatFragment ph_chatFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_ph);

        //get Account from LoginFragment
        Intent intent = getIntent();
        account = intent.getParcelableExtra("account");
        phuHuynh = intent.getParcelableExtra("ph");
        key = intent.getStringExtra("key");

        initFragment();

        toolbar = findViewById(R.id.toolbar_user_ph);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.activity_layout_user_ph);
        NavigationView nav = findViewById(R.id.nav_views_user_ph);
        nav.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                TextView mail = findViewById(R.id.textViewNavUserPH);
                mail.setText(account.getEmail());
            }
        };
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_user_ph, ph_profileFragment).commit();
            currentFragment = ph_profileFragment;
            nav.setCheckedItem(R.id.nav_profile_ph);
        }
    }

    private void initFragment() {
        ph_learnFragment = new PH_LearnFragment();
        ph_learnFragment.setLop(phuHuynh.getLop());
        ph_timetableFragment = new PH_TimetableFragment();
        ph_timetableFragment.setTenLop(phuHuynh.getLop());
        ph_chatFragment = new PH_ChatFragment();
        ph_chatFragment.setSender(phuHuynh.getHoTenHS(), phuHuynh.getEmail(), phuHuynh.getLop());
        ph_chatFragment.setGvcn(phuHuynh.getGVCN());
        ph_profileFragment = new PH_ProfileFragment();
        ph_profileFragment.setPhuhuynh(phuHuynh);
        ph_profileFragment.setKey(key);
        ph_lessonFragment = new PH_LessonFragment();
        ph_testFragment = new PH_TestFragment();
    }

    public void change(int fragmentNumber) {
        switch (fragmentNumber) {
            case CHANGE_TO_LESSON:
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left)
                        .replace(currentFragment.getId(), ph_lessonFragment).commit();
                currentFragment = ph_lessonFragment;
                Handler hl1 = new Handler();
                hl1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getSupportActionBar().hide();
                    }
                }, 200);
                break;
            case CHANGE_TO_LEARN:
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                        .replace(currentFragment.getId(), ph_learnFragment).commit();
                currentFragment = ph_learnFragment;
                Handler hl2 = new Handler();
                hl2.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getSupportActionBar().show();
                    }
                }, 200);
                break;
            case CHANGE_TO_TEST:
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left)
                        .replace(currentFragment.getId(), ph_testFragment).commit();
                currentFragment = ph_testFragment;
                Handler hl3 = new Handler();
                hl3.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getSupportActionBar().hide();
                    }
                }, 200);
            default:
                break;
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_profile_ph:
                changeFragment(currentFragment, ph_profileFragment);
                toolbar.setTitle("Thông tin cá nhân");
                break;
            case R.id.nav_learn_ph:
                changeFragment(currentFragment, ph_learnFragment);
                toolbar.setTitle("Học tập");
                break;
            case R.id.nav_tkb_ph:
                changeFragment(currentFragment, ph_timetableFragment);
                toolbar.setTitle("Thời khoá biểu");
                break;
            case R.id.nav_chat_ph:
                changeFragment(currentFragment, ph_chatFragment);
                toolbar.setTitle("Chat");
                break;
            case R.id.nav_logout_ph:
                new AlertDialog.Builder(this)
                        .setTitle("Đăng xuất")
                        .setMessage("Bạn có muốn đăng xuất?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(PhuHuynhActivity.this, MainActivity.class);
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
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void changeFragment(Fragment currentFrag, Fragment newFrag) {
        getSupportFragmentManager().beginTransaction().replace(currentFrag.getId(), newFrag).commit();
        currentFragment = newFrag;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void setLesson(Lesson lesson) {
        ph_lessonFragment.setLesson(lesson);
    }

    public void setTest(Test test) {
        ph_testFragment.setTest(test);
    }
}

