package huce.nhom15.mobileapp.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;

import java.text.DecimalFormat;

import huce.nhom15.mobileapp.R;
import huce.nhom15.mobileapp.databinding.ActivityGioHangBinding;
import huce.nhom15.mobileapp.model.Customer;
import huce.nhom15.mobileapp.view.API.IApiService;
import huce.nhom15.mobileapp.view.adapter.GioHangAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GioHangActivity extends AppCompatActivity {
    private static ActivityGioHangBinding activityGioHangBinding;
    private GioHangAdapter gioHangAdapter;
    private TextView tvMaKH, tvTenNguoiDat;
    private EditText edtTenNguoiNhan, edtSDT, edtDiaChi;
    private Button btnBack, btnThanhToan;


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
        clickMuaHang();

    }

    private void clickMuaHang() {
        activityGioHangBinding.btnMuaHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainActivity.gioHangViewModels.size() <= 0){
                    Toast.makeText(GioHangActivity.this, "Vui lòng thêm sản phẩm vào giỏ hàng để mua hàng", Toast.LENGTH_SHORT).show();
                }
                else{
                    evenMuaHang();
                }

            }
        });
    }

    private void evenMuaHang() {
        if(isLoggedIn() || isSignup()){
            View bottomSheetHoaDon = getLayoutInflater().inflate(R.layout.bottom_sheet_hoadon, null);
            String user = getUserName();
            Gson gson = new Gson();
            Customer customer = gson.fromJson(user, Customer.class);

            initBottomSheetHoaDon(bottomSheetHoaDon);
            setViewBottomSheetHoaDon(customer);


            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(GioHangActivity.this);
            bottomSheetDialog.setContentView(bottomSheetHoaDon);

            clickBackButtonSheedHoaDon(bottomSheetDialog);
            clickThanhToanButtonSheedHoaDon();

            bottomSheetDialog.show();
        }
        else{
            Toast.makeText(this, "Vui lòng đăng nhập hoặc đăng ký một tài khoản để có thể mua hàng", Toast.LENGTH_SHORT).show();
            Intent in = new Intent(GioHangActivity.this, MainActivity.class);
            in.putExtra("notIsLoggin", true);
            startActivity(in);
        }
    }

    private void clickThanhToanButtonSheedHoaDon() {
        btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String KH_MaKH = tvMaKH.getText().toString().trim();
                String TenNguoiDat = tvTenNguoiDat.getText().toString().trim();
                String TenNguoiNhan = edtTenNguoiNhan.getText().toString().trim();
                String SDT = edtSDT.getText().toString().trim();
                String DiaChi = edtDiaChi.getText().toString().trim();
                if(KH_MaKH.isEmpty() || TenNguoiDat.isEmpty() || TenNguoiNhan.isEmpty() || SDT.isEmpty() || DiaChi.isEmpty()){
                    Toast.makeText(GioHangActivity.this, "Các trường không được để trống", Toast.LENGTH_SHORT).show();
                }
                else{
                    String reg = "^(0|\\+84)(\\s|\\.)?((3[2-9])|(5[689])|(7[06-9])|(8[1-689])|(9[0-46-9]))(\\d)(\\s|\\.)?(\\d{3})(\\s|\\.)?(\\d{3})$";
                    // Kiem tra dinh dang
                    boolean kt = SDT.matches(reg);

                    if (kt == false) {
                        Toast.makeText(GioHangActivity.this, "Số điện thoại không đúng định dạng và SDT phải có 10 số", Toast.LENGTH_SHORT).show();
                    } else {
                        IApiService.api.addHoaDon(Integer.parseInt(KH_MaKH), TenNguoiDat, TenNguoiNhan, SDT, DiaChi).enqueue(new Callback<Integer>() {
                            @Override
                            public void onResponse(Call<Integer> call, Response<Integer> response) {
                                int MaHD = response.body();
                                if(MaHD > 0){
                                    Gson gson = new Gson();
                                    String MangGioHang = gson.toJson(MainActivity.gioHangViewModels);
                                    IApiService.api.ThanhToan(MaHD, MangGioHang).enqueue(new Callback<String>() {
                                        @Override
                                        public void onResponse(Call<String> call, Response<String> response) {
                                            String success = response.body();
                                            Toast.makeText(GioHangActivity.this, success, Toast.LENGTH_SHORT).show();
                                            MainActivity.gioHangViewModels.clear();
                                            Intent in = new Intent(GioHangActivity.this, MainActivity.class);
                                            in.putExtra("purchased", true);
                                            startActivity(in);
                                        }

                                        @Override
                                        public void onFailure(Call<String> call, Throwable t) {
                                            Toast.makeText(GioHangActivity.this, "Thanh toán hóa đơn không thành công", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }

                            }

                            @Override
                            public void onFailure(Call<Integer> call, Throwable t) {
                                Toast.makeText(GioHangActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }
        });
    }

    private void clickBackButtonSheedHoaDon(BottomSheetDialog bottomSheetDialog) {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });
    }

    private void setViewBottomSheetHoaDon(Customer customer) {
        tvMaKH.setText(customer.getKH_MaKH()+"");
        tvTenNguoiDat.setText(customer.getKH_HoTen());
        edtSDT.setText(customer.getKH_SDT());
    }

    private void initBottomSheetHoaDon(View bottomSheetHoaDon) {

        tvMaKH = bottomSheetHoaDon.findViewById(R.id.tvMaKH);
        tvTenNguoiDat = bottomSheetHoaDon.findViewById(R.id.tvTenNguoiDat);
        edtTenNguoiNhan = bottomSheetHoaDon.findViewById(R.id.edtTenNguoiNhan);
        edtSDT = bottomSheetHoaDon.findViewById(R.id.edtSDT);
        edtDiaChi = bottomSheetHoaDon.findViewById(R.id.edtDiaChi);
        btnBack = bottomSheetHoaDon.findViewById(R.id.btnBack);
        btnThanhToan = bottomSheetHoaDon.findViewById(R.id.btnThanhToan);

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

    private boolean isLoggedIn() {
        SharedPreferences prefs = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        return prefs.getBoolean("isLoggedIn", false);
    }
    private boolean isSignup() {
        SharedPreferences prefs = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        return prefs.getBoolean("isSignup", false);
    }
    private String getUserName() {
        SharedPreferences prefs = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        return prefs.getString("username", "");
    }

}