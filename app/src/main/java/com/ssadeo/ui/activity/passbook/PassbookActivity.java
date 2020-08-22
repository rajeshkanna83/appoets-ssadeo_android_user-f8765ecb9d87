package com.ssadeo.ui.activity.passbook;

import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.widget.Toolbar;

import com.ssadeo.user.R;
import com.ssadeo.base.BaseActivity;
import com.ssadeo.ui.fragment.coupon_history.CouponHistoryFragment;
import com.ssadeo.ui.fragment.wallet_history.WalletHistoryFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PassbookActivity extends BaseActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.container)
    ViewPager container;
    TabPagerAdapter adapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_passbook;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tabs.addTab(tabs.newTab().setText(getString(R.string.wallet_history)));
        tabs.addTab(tabs.newTab().setText(getString(R.string.coupon_history)));

        adapter = new TabPagerAdapter(getSupportFragmentManager(), tabs.getTabCount());
        container.setAdapter(adapter);
        container.canScrollHorizontally(0);
        container.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                container.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public class TabPagerAdapter extends FragmentStatePagerAdapter {
        int mNumOfTabs;

        public TabPagerAdapter(FragmentManager fm, int NumOfTabs) {
            super(fm);
            this.mNumOfTabs = NumOfTabs;
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    return new WalletHistoryFragment();
                case 1:
                    return new CouponHistoryFragment();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return mNumOfTabs;
        }
    }
}
