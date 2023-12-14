package huce.nhom15.mobileapp.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import java.text.DecimalFormat;

import huce.nhom15.mobileapp.R;
import huce.nhom15.mobileapp.databinding.ActivityGioHangBinding;
import huce.nhom15.mobileapp.view.adapter.GioHangAdapter;

public class GioHangActivity extends AppCompatActivity {
    private static ActivityGioHangBinding activityGioHangBinding;
    private GioHangAdapter gioHangAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityGioHangBinding = ActivityGioHangBinding.inflate(getLayoutInflater());
        setContentView(activityGioHangBinding.getRoot());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        activityGioHangBinding.rcvGioHang.setLayoutManager(linearLayoutManager);
        activityGioHangBinding.rcvGioHang.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        gioHangAdapter = new GioHangAdapter(MainActivity.gioHangViewModels, getApplication(), new GioHangAdapter.onClickItem() {
            @Override
            public void onLongClickItemGioHang(int position) {

                showDialogConfirm(position);
            }
        });
        activityGioHangBinding.rcvGioHang.setAdapter(gioHangAdapter);

        checkCart();
        ganThongTin();
        clickBack();

    }

    private void showDialogConfirm(int position) {
        final Dialog dialog = new Dialog(GioHangActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_confirm_giohang);

        Window window = dialog.getWindow();
        if(window == null){
            return;
        }

        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = Gravity.CENTER;
        window.setAttributes(windowAttributes);

        Button btnBoQua = dialog.findViewById(R.id.btnBoQua);
        Button btnXacNhan = dialog.findViewById(R.id.btnXacNhan);
        btnBoQua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if(MainActivity.gioHangViewModels.size() <= 0){
                    activityGioHangBinding.imgGioHangTrong.setVisibility(View.VISIBLE);
                }
                else{
                    MainActivity.gioHangViewModels.remove(position);
                    gioHangAdapter.notifyItemRemoved(position);
                    ganThongTin();
                    if(MainActivity.gioHangViewModels.size() <= 0){
                        activityGioHangBinding.imgGioHangTrong.setVisibility(View.VISIBLE);
                        activityGioHangBinding.scrollViewGioHang.setVisibility(View.INVISIBLE);
                    }
                }

            }
        });
        dialog.show();
    }

    private void clickBack() {
        activityGioHangBinding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public static void ganThongTin() {
        activityGioHangBinding.tvTiTleTongTien.setText("Tổng tiền ("+MainActivity.gioHangViewModels.size()+"): ");

        if(MainActivity.gioHangViewModels.size()>0){
            long tongTien = 0;
            for(int i=0; i<MainActivity.gioHangViewModels.size(); i++){
                int giaMua = Integer.parseInt(MainActivity.gioHangViewModels.get(i).getGiasp().replace(",", ""));
                tongTien += giaMua;
            }
            DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
            activityGioHangBinding.tvTongTienThanhToan.setText(decimalFormat.format(tongTien)+"Đ");
        }else{
            activityGioHangBinding.tvTongTienThanhToan.setText("0Đ");
        }
    }

    private void checkCart() {
        if(MainActivity.gioHangViewModels.size() <= 0){
            activityGioHangBinding.imgGioHangTrong.setVisibility(View.VISIBLE);
            activityGioHangBinding.scrollViewGioHang.setVisibility(View.INVISIBLE);
        }
        else{
            activityGioHangBinding.imgGioHangTrong.setVisibility(View.INVISIBLE);
            activityGioHangBinding.scrollViewGioHang.setVisibility(View.VISIBLE);
        }
    }
}