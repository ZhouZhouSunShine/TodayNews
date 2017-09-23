package com.example.fanjinwei_firstpriject.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.andy.library.ChannelBean;
import com.example.fanjinwei_firstpriject.MyFragment;
import com.example.fanjinwei_firstpriject.fragment.CaiJingFragment;
import com.example.fanjinwei_firstpriject.fragment.GuoJiFragment;
import com.example.fanjinwei_firstpriject.fragment.GuoNeiFragment;
import com.example.fanjinwei_firstpriject.fragment.JunShiFragment;
import com.example.fanjinwei_firstpriject.fragment.KeJiFragment;
import com.example.fanjinwei_firstpriject.fragment.SheHuiFragment;
import com.example.fanjinwei_firstpriject.fragment.ShiShangFragment;
import com.example.fanjinwei_firstpriject.fragment.TiYuFragment;
import com.example.fanjinwei_firstpriject.fragment.YuLeFragment;

import java.util.List;

/**
 * Created by 范晋炜 on 2017/8/9 0009.
 * com.example.fanjinwei_firstpriject.adapter
 * MyPagerAdapter
 */


public class MyPagerAdapter extends FragmentPagerAdapter {

    //创建头部
//    private String[] titles = {"推荐","社会","国内","国际","娱乐","体育","军事","科技","财经","时尚"};

    private FragmentManager mFragmentManager;
    List<ChannelBean> mChannelBeanList;

    public MyPagerAdapter(FragmentManager fm, List<ChannelBean> mChannelBeanList) {
        super(fm);
        this.mFragmentManager = mFragmentManager;
        this.mChannelBeanList = mChannelBeanList;
    }

    @Override
    public Fragment getItem(int position) {
        /*Bundle bundle = new Bundle();
        bundle.putString("text",titles[position]);
        myFragment.setArguments(bundle);*/

        if (position == 1) {
            return new SheHuiFragment();
        } else if (position == 2) {
            return new GuoNeiFragment();
        } else if (position == 3) {
            return new GuoJiFragment();
        } else if (position == 4) {
            return new YuLeFragment();
        } else if (position == 5) {
            return new TiYuFragment();
        } else if (position == 6) {
            return new JunShiFragment();
        } else if (position == 7) {
            return new KeJiFragment();
        } else if (position == 8) {
            return new CaiJingFragment();
        } else if (position == 9) {
            return new ShiShangFragment();
        }

        return new MyFragment();
    }

    @Override
    public int getCount() {
        return mChannelBeanList != null ? mChannelBeanList.size() : 0;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mChannelBeanList.get(position).getName();
    }
}
