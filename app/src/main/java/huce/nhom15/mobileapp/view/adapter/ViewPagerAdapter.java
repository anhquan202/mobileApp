package huce.nhom15.mobileapp.view.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import huce.nhom15.mobileapp.view.fragment.CategoryFragment;
import huce.nhom15.mobileapp.view.fragment.HomeFragment;
import huce.nhom15.mobileapp.view.fragment.OrderFragment;
import huce.nhom15.mobileapp.view.fragment.UserFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {
    private Context ct;
    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {

        super(fragmentActivity);
        ct = fragmentActivity;
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new HomeFragment(ct);
            case 1:
                return new CategoryFragment(ct);
            case 2:
                return new OrderFragment(ct);
            case 3:
                return new UserFragment();
            default:
                return new HomeFragment(ct);
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}

