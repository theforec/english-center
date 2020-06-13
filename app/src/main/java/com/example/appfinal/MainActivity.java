package com.example.appfinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.appfinal.fragment.main.AboutusFragment;
import com.example.appfinal.fragment.main.ForgotpasswordFragment;
import com.example.appfinal.fragment.main.HomepageFragment;
import com.example.appfinal.fragment.main.LoginFragment;
import com.example.appfinal.fragment.main.RegisterFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    Toolbar toolbar;
    Fragment currentFragment;
    HomepageFragment homepageFragment;
    LoginFragment loginFragment;
    ForgotpasswordFragment forgotpasswordFragment;
    RegisterFragment registerFragment;
    AboutusFragment aboutusFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        //tool bar
        NavigationView nav = findViewById(R.id.nav_main_views);
        nav.setNavigationItemSelectedListener(this);

        //tao toggle menu
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);

        toggle.syncState();

        if (savedInstanceState == null) { //vừa mở app là mở homepage
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_main, homepageFragment).commit();
            currentFragment = homepageFragment;
            nav.setCheckedItem(R.id.nav_homepage);
        }
    }

    private void init() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = (DrawerLayout) findViewById(R.id.activity_layout_main);

        homepageFragment = new HomepageFragment();
        loginFragment = new LoginFragment();
        forgotpasswordFragment = new ForgotpasswordFragment();
        registerFragment = new RegisterFragment();
        aboutusFragment = new AboutusFragment();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_homepage:
                changeFragmentTo(homepageFragment);
                toolbar.setTitle("Trang tâm tiếng anh Jaxtina");
                break;
            case R.id.nav_login:
                changeFragmentTo(loginFragment);
                toolbar.setTitle("Đăng nhập");
//                toolbar.setBackgroundColor();
                break;
            case R.id.nav_register:
                changeFragmentTo( registerFragment);
                toolbar.setTitle("Đăng ký khoá học");
                break;
            case R.id.nav_aboutus:
                changeFragmentTo(aboutusFragment);
                toolbar.setTitle("Thông tin trung tâm");
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

    public void changeFragmentTo(Fragment newFragment) {
        getSupportFragmentManager().beginTransaction().replace(currentFragment.getId(), newFragment).commit();
        currentFragment = newFragment;
    }

    public void change(int fragmentNumber) {
        switch (fragmentNumber) {
            case 1:
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left)
                        .replace(currentFragment.getId(), forgotpasswordFragment).commit();
                currentFragment = forgotpasswordFragment;
                break;
            case 2:
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                        .replace(currentFragment.getId(), loginFragment).commit();
                currentFragment = loginFragment;
                break;
            default:
                break;
        }
    }

}
