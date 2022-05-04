package com.example.gramy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.example.gramy.Adapter.TutorialPagerAdapter;
import com.example.gramy.TutorialFragment.TutorialFragmentPage1;
import com.example.gramy.TutorialFragment.TutorialFragmentPage2;
import com.example.gramy.TutorialFragment.TutorialFragmentPage3;
import com.example.gramy.TutorialFragment.TutorialFragmentPage4;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

import java.util.ArrayList;

public class TutorialActivity extends AppCompatActivity {

    // 튜토리얼 끝나고 시작하기 버튼 누르면 로그인화면에서 홈화면으로 변경했습니다.

    private ViewPager2 vpTutorial;
    private TutorialPagerAdapter pagerAdapter;
    ArrayList<Fragment> fragList = new ArrayList<Fragment>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        vpTutorial = findViewById(R.id.vpTutorial);

        TutorialFragmentPage1 fragment1 = new TutorialFragmentPage1();
        TutorialFragmentPage2 fragment2 = new TutorialFragmentPage2();
        TutorialFragmentPage3 fragment3 = new TutorialFragmentPage3();
        TutorialFragmentPage4 fragment4 = new TutorialFragmentPage4();

        fragList.add(fragment1);
        fragList.add(fragment2);
        fragList.add(fragment3);
        fragList.add(fragment4);


        pagerAdapter = new TutorialPagerAdapter(this, fragList);

        vpTutorial.setAdapter(pagerAdapter);

        DotsIndicator indicator = findViewById(R.id.indicator);
        indicator.setViewPager2(vpTutorial);
    }
}