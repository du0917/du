package com.example.day03text1;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

public class HomeActivity extends AppCompatActivity {

    private FrameLayout mFrameLayout;
    private TabLayout mTabLayout;
    private HomeFragment mHomeFragment;
    private AddressFragment mAddressFragment;
    private FragmentManager mManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();
    }

    private void initView() {
        mFrameLayout = (FrameLayout) findViewById(R.id.FrameLayout);
        mTabLayout = (TabLayout) findViewById(R.id.TabLayout);
        mTabLayout.addTab(mTabLayout.newTab().setText("首页"));
        mTabLayout.addTab(mTabLayout.newTab().setText("通讯录"));
        mManager = getSupportFragmentManager();
        mHomeFragment = new HomeFragment();
        mAddressFragment = new AddressFragment();
        mManager.beginTransaction().replace(R.id.FrameLayout, mHomeFragment).commit();
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition()==0){
                    mManager.beginTransaction().replace(R.id.FrameLayout, mHomeFragment).commit();
                }else {
                    mManager.beginTransaction().replace(R.id.FrameLayout,mAddressFragment).commit();

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
