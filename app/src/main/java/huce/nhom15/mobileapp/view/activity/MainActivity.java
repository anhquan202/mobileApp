package huce.nhom15.mobileapp.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import huce.nhom15.mobileapp.R;
import huce.nhom15.mobileapp.view.adapter.ViewPagerAdapter;
import huce.nhom15.mobileapp.view.animation.DepthPageTransformer;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottom_nav;
    private ViewPager2 mViewPager2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottom_nav = findViewById(R.id.bottom_nav);
        mViewPager2 = findViewById(R.id.view_pager);
        setUpViewPage();

        mViewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position){
                    case 0:
                        bottom_nav.getMenu().findItem(R.id.action_home).setChecked(true);
                        break;
                    case 1:
                        bottom_nav.getMenu().findItem(R.id.action_category).setChecked(true);
                        break;
                    case 2:
                        bottom_nav.getMenu().findItem(R.id.action_order).setChecked(true);
                        break;
                    case 3:
                        bottom_nav.getMenu().findItem(R.id.action_user).setChecked(true);
                        break;
                }
            }
        });
        mViewPager2.setPageTransformer(new DepthPageTransformer());
        bottom_nav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if(R.id.action_home == item.getItemId()){
                    mViewPager2.setCurrentItem(0);
                }
                else if(R.id.action_category == item.getItemId()){
                    mViewPager2.setCurrentItem(1);
                }
                else if(R.id.action_order == item.getItemId()){
                    mViewPager2.setCurrentItem(2);
                }
                else{
                    mViewPager2.setCurrentItem(3);
                }
                return true;
            }
        });
//        arrowleft.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(view.getContext(), CategoryActivity.class);
//                startActivity(intent);
//            }
//        });
    }

    private void setUpViewPage() {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this);
        mViewPager2.setAdapter(viewPagerAdapter);
    }
}