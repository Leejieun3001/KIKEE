package kidskeeper.sungshin.or.kr.kikee.Adult.AdultHome;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kidskeeper.sungshin.or.kr.kikee.Adult.Community.CommunityFragment;
import kidskeeper.sungshin.or.kr.kikee.Adult.Mypage.MyPageFragment;
import kidskeeper.sungshin.or.kr.kikee.R;

public class AdultHomeActivity extends AppCompatActivity {

    //    @BindView(R.id.adultHome_button_camera)
//    Button buttonCamera;

    @BindView(R.id.adultHome_viewpager_viewpager)
    ViewPager viewPager;
    @BindView(R.id.adultHome_tablayout_tablayout)
    TabLayout tabLayout;

    String TAG = "AdultHomeActivity";

    private final long FINISH_INTERVAL_TIME = 2000;
    private long backPressedTime = 0;

    private final int ADULTHOME = 0;
    private final int BOARD = 1;
    private final int MYPAGE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adult_home);
        ButterKnife.bind(this);

        setupViewPager();
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();

        //    clickEvent();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int drawable;
                switch (tab.getPosition()) {
                    case ADULTHOME:

                }
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

//    void clickEvent() {
//
//        buttonCamera.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(), CameraActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        });
//
//    }

    private void setupTabIcons() {
        tabLayout.getTabAt(ADULTHOME).setIcon(R.drawable.home);
        tabLayout.getTabAt(BOARD).setIcon(R.drawable.community);
        tabLayout.getTabAt(MYPAGE).setIcon(R.drawable.settings);
    }

    private void setupViewPager() {
        ViewPagerAapter adapter = new ViewPagerAapter(getSupportFragmentManager());
        adapter.addFrag(new AdultHomeFragment());
        adapter.addFrag(new CommunityFragment());
        adapter.addFrag(new MyPageFragment());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

    }

    @Override
    public void onBackPressed() {
        long tempTime = System.currentTimeMillis();
        long intervalTime = tempTime - backPressedTime;

        if (0 <= intervalTime && FINISH_INTERVAL_TIME >= intervalTime) {
            super.onBackPressed();
        } else {
            this.backPressedTime = tempTime;
            Toast.makeText(getApplicationContext(), "뒤로 가기 키를 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();
        }

    }

    class ViewPagerAapter extends FragmentPagerAdapter {
        private final List<Fragment> fragmentsList = new ArrayList<>();

        private ViewPagerAapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentsList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentsList.size();
        }

        private void addFrag(Fragment fragment) {
            fragmentsList.add(fragment);
        }
    }
}
