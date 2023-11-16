package huce.nhom15.mobileapp.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import huce.nhom15.mobileapp.R;
import huce.nhom15.mobileapp.databinding.ActivityChiTietSpBinding;
import huce.nhom15.mobileapp.view.viewmodel.SanPhamViewModel;

public class ChiTietSPActivity extends AppCompatActivity {
    private ActivityChiTietSpBinding activityChiTietSpBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityChiTietSpBinding = ActivityChiTietSpBinding.inflate(getLayoutInflater());
        setContentView(activityChiTietSpBinding.getRoot());
        Bundle bundle = getIntent().getExtras();
        SanPhamViewModel sanPhamViewModel = (SanPhamViewModel) bundle.getSerializable("SP");
        activityChiTietSpBinding.tvTest.setText(sanPhamViewModel.toString());
    }
}