package com;

import android.app.Activity;
import android.content.Intent;

import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lenovo.ekrili.ClientSpaceActivity;
import com.example.lenovo.ekrili.R;

import java.util.ArrayList;
import java.util.List;

import fragments.WelcomeScreenOne;
import fragments.WelcomeScreenThree;
import fragments.WelcomeScreenTwo;
import generateclass.SessionManager;


public class HommeScreen extends AppCompatActivity {
    public static Activity HommeScreenAct ;
    private ViewPager viewPager ;
    private LinearLayout dotsLayout;
    private List<Fragment> layouts  ;
    private Button next ;
    private SessionManager sessionManager ;
    private TextView dots [] ;
    private MyViewPageAdapter myViewPageAdapter ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        HommeScreenAct = this ;

        sessionManager = new SessionManager(this);

        if(sessionManager.IsLogin()){

            LunchClientSpace();
        }

        else{

            if(!sessionManager.IsFirstTimeLunch()){

                LunchMainActivity();

            }
        }






        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        setContentView(R.layout.activity_homme_screen);

        FillList();

        myViewPageAdapter = new MyViewPageAdapter(getSupportFragmentManager() , layouts);

        viewPager = findViewById(R.id.view_pager) ;

        dotsLayout = findViewById(R.id.layoutDots);

        next = findViewById(R.id.btn_next) ;


        addBottomDots(0);

        viewPager.setAdapter(myViewPageAdapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                addBottomDots(position);
                if(position==layouts.size()-1){
                    next.setVisibility(View.VISIBLE);}
                    else{

                    next.setVisibility(View.GONE);

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sessionManager.setKEY_IS_First_Time_Lunch();
                LunchMainActivity();
            }
        });

    }

    private void addBottomDots(int currentPage) {
        dots = new TextView[layouts.size()];

        int[] colors = getResources().getIntArray(R.array.colors);


        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {


            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            if(i==currentPage)
            dots[i].setTextColor(colors[0]);
            else
                dots[i].setTextColor(colors[1]);

            dotsLayout.addView(dots[i]);
        }


    }

    private void LunchMainActivity(){

        Intent intent = new Intent(this , MainActivity.class) ;
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    private class MyViewPageAdapter extends FragmentPagerAdapter{

        List<Fragment> fragments ;


        public MyViewPageAdapter(FragmentManager fm , List<Fragment> fragments) {
            super(fm);
            this.fragments = fragments;

        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }

    public void FillList(){

        layouts = new ArrayList<>() ;
        layouts.add(new WelcomeScreenOne());
        layouts.add(new WelcomeScreenTwo());
        layouts.add(new WelcomeScreenThree());




    }

    private void LunchClientSpace(){
        Intent intent = new Intent(this , ClientSpaceActivity.class) ;
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();

    }



}
