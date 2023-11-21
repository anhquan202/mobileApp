package huce.nhom15.mobileapp.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import huce.nhom15.mobileapp.databinding.ActivityChiTietSpBinding;
import huce.nhom15.mobileapp.viewmodel.SanPhamViewModel;

public class ChiTietSPActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        huce.nhom15.mobileapp.databinding.ActivityChiTietSpBinding activityChiTietSpBinding = ActivityChiTietSpBinding.inflate(getLayoutInflater());
        setContentView(activityChiTietSpBinding.getRoot());
        Bundle bundle = getIntent().getExtras();
        SanPhamViewModel sanPhamViewModel = (SanPhamViewModel) bundle.getSerializable("SP");
        activityChiTietSpBinding.tvTest.setText(sanPhamViewModel.toString());
    }
}