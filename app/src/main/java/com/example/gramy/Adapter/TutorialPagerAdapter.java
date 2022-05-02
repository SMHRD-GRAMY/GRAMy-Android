package com.example.gramy.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.gramy.TutorialFragment.TutorialFragmentPage1;
import com.example.gramy.TutorialFragment.TutorialFragmentPage2;
import com.example.gramy.TutorialFragment.TutorialFragmentPage3;
import com.example.gramy.TutorialFragment.TutorialFragmentPage4;

import java.util.ArrayList;

public class TutorialPagerAdapter extends FragmentStateAdapter {

    ArrayList<Fragment> fragments = new ArrayList<Fragment>();

    public TutorialPagerAdapter(@NonNull FragmentActivity fragmentActivity, ArrayList<Fragment> fragments){
        super(fragmentActivity);
        this.fragments = fragments;
    }
    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch(position){
            case 0:
                return new TutorialFragmentPage1();
            case 1:
                return new TutorialFragmentPage2();
            case 2:
                return new TutorialFragmentPage3();
            default:
                return new TutorialFragmentPage4();
        }
    }

    @Override
    public int getItemCount() {
        return fragments.size();
    }
}
