package com.example.TecFresh.Others;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.TecFresh.Adapter.pagerAdapterRegister;
import com.example.TecFresh.R;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;


public class userTabRegister extends AppCompatActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_ui);

        TabLayout tabLayout = findViewById(R.id.tabBarRegister);
        TabItem customerTab = findViewById(R.id.customer_tab_register);
        TabItem shopkeeperTab = findViewById(R.id.shopkeeper_tab_register);

        final ViewPager viewPager = findViewById(R.id.view_pager_register);

        pagerAdapterRegister PagerAdapter = new pagerAdapterRegister(getSupportFragmentManager(),tabLayout.getTabCount());

        viewPager.setAdapter(PagerAdapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

    }


}
