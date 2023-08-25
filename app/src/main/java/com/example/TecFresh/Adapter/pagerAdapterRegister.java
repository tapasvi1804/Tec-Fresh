package com.example.TecFresh.Adapter;

import androidx.fragment.app.FragmentPagerAdapter;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.TecFresh.Customer.customerFragRegister;
import com.example.TecFresh.Shopkeeper.shopkeeperFragRegister;


public class pagerAdapterRegister extends FragmentPagerAdapter{


    private int numOfTabs;

    public pagerAdapterRegister(FragmentManager fm, int numOfTabs){

        super(fm,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.numOfTabs = numOfTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                return new customerFragRegister();
            case 1:
                return new shopkeeperFragRegister();
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return numOfTabs;
    }


}
