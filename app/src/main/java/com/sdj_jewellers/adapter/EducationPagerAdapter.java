package com.sdj_jewellers.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;

import com.sdj_jewellers.fragment.CaratFragment;
import com.sdj_jewellers.fragment.ClarityFragment;
import com.sdj_jewellers.fragment.ColourFragment;
import com.sdj_jewellers.fragment.CutFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 6/20/2017.
 */

public class EducationPagerAdapter extends FragmentPagerAdapter {
    private List<String> mListTitleTabs;
    private ArrayList<Fragment> fragments;

    public EducationPagerAdapter(List<String> listTitleTabs, android.support.v4.app.FragmentManager fm) {
        super(fm );
        this.mListTitleTabs = listTitleTabs;

        try {
            this.fragments = new ArrayList<Fragment>();
            fragments.add(ClarityFragment.newInstance("", ""));
            fragments.add(ColourFragment.newInstance("", ""));
            fragments.add(CutFragment.newInstance("", ""));
            fragments.add(CaratFragment.newInstance("", ""));

        } catch (ArrayIndexOutOfBoundsException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (mListTitleTabs == null || mListTitleTabs.isEmpty()) {
            return "";
        }
        return mListTitleTabs.get(position);
    }

    public void setPageTitle(int position,String title){
        mListTitleTabs.set(position,title);
    }
    @Override
    public int getCount() {
        if (mListTitleTabs == null || mListTitleTabs.isEmpty()) {
            return 0;
        }

        return mListTitleTabs.size();

    }

    @Override
    public Fragment getItem(int index) {
        return fragments.get(index);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
