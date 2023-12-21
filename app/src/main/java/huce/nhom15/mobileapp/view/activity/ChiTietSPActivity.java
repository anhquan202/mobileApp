package huce.nhom15.mobileapp.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.text.DecimalFormat;

import huce.nhom15.mobileapp.R;
import huce.nhom15.mobileapp.databinding.ActivityChiTietSpBinding;
import huce.nhom15.mobileapp.view.API.IApiService;
import huce.nhom15.mobileapp.viewmodel.ChiTietSPViewModel;
import huce.nhom15.mobileapp.viewmodel.GioHangViewModel;
import huce.nhom15.mobileapp.viewmodel.SanPhamViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChiTietSPActivity extends AppCompatActivity {
    private ActivityChiTietSpBinding activityChiTietSpBinding;
    private SanPhamViewModel sanPhamViewModel;
    private Context ct;
    private int soLuongMua=1;
    private String giaConvert;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityChiTietSpBinding = ActivityChiTietSpBinding.inflate(getLayoutInflater());
        setContentView(activityChiTietSpBinding.getRoot());

        ct = this;
        Bundle bundle = getIntent().getExtras();
        sanPhamViewModel = (SanPhamViewModel) bundle.getSerializable("SP");
        ganThongTin();
        xuLySoLuongMua();

        giaConvert = sanPhamViewModel.getGiasp().replace(",", "");

        int maSP = Integer.parseInt(sanPhamViewModel.getId());
        callApi(maSP);

        clickBack();
        clickHome();
        clickGioHang();
        clickThemGioHang();
    }

    private void clickGioHang() {
        activityChiTietSpBinding.header.imgShoppingCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(ChiTietSPActivity.this, GioHangActivity.class);
                startActivity(in);
            }
        });
    }

    private void clickThemGioHang() {
        activityChiTietSpBinding.btnThemGioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainActivity.gioHangViewModels.size() > 0){
                    boolean exists = false;
                    for(int i=0; i<MainActivity.gioHangViewModels.size(); i++){
                        if(MainActivity.gioHangViewModels.get(i).getId().equals(sanPhamViewModel.getId())){
                            String soLuongMuaMoi = (Integer.parseInt(MainActivity.gioHangViewModels.get(i).getSoluong()) + soLuongMua)+"";
                            MainActivity.gioHangViewModels.get(i).setSoluong(soLuongMuaMoi);
                            if(Integer.parseInt(MainActivity.gioHangViewModels.get(i).getSoluong()) >= Integer.parseInt(sanPhamViewModel.getSoluong())){
                                MainActivity.gioHangViewModels.get(i).setSoluong(sanPhamViewModel.getSoluong());
                            }
                            MainActivity.gioHangViewModels.get(i).setGiasp((Integer.parseInt(giaConvert)*Integer.parseInt(MainActivity.gioHangViewModels.get(i).getSoluong())+""));
                            exists = true;
                        }
                    }
                    if(exists == false){
                        long giaMoi = soLuongMua * Integer.parseInt(giaConvert);
                        MainActivity.gioHangViewModels.add(new GioHangViewModel(sanPhamViewModel.getId(), sanPhamViewModel.getAnhsp(), sanPhamViewModel.getTensp(), giaMoi+"", soLuongMua+"", sanPhamViewModel.getSoluong()));

                    }
                }else{
                    long giaMoi = soLuongMua * Integer.parseInt(giaConvert);
                    MainActivity.gioHangViewModels.add(new GioHangViewModel(sanPhamViewModel.getId(), sanPhamViewModel.getAnhsp(), sanPhamViewModel.getTensp(), giaMoi+"", soLuongMua+"", sanPhamViewModel.getSoluong()));
                }
                activityChiTietSpBinding.header.tvSoSP.setText(MainActivity.gioHangViewModels.size()+"");
                showDialogConfirm();

            }
        });
    }

    private void showDialogConfirm() {
        final Dialog dialog = new Dialog(ChiTietSPActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_them_giohang);

        Window window = dialog.getWindow();
        if(window == null){
            return;
        }

        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = Gravity.CENTER;
        window.setAttributes(windowAttributes);

        Button btnXemGioHang = dialog.findViewById(R.id.btnXemGioHang);
        Button btnTiepTucMua = dialog.findViewById(R.id.btnTiepTucMua);
        TextView tvTongSP = dialog.findViewById(R.id.tvTongSP);
        TextView tvTongTien = dialog.findViewById(R.id.tvTongTien);

        int tongSP = 0;
        for(int i=0; i<MainActivity.gioHangViewModels.size(); i++){
            tongSP += Integer.parseInt(MainActivity.gioHangViewModels.get(i).getSoluong());
        }
        tvTongSP.setText("Tổng | "+tongSP+" sản phẩm");

        long tongTien = 0;
        for(int i=0; i<MainActivity.gioHangViewModels.size(); i++){
            int giaMua = Integer.parseInt(MainActivity.gioHangViewModels.get(i).getGiasp().replace(",", ""));
            tongTien += giaMua;
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        tvTongTien.setText(decimalFormat.format(tongTien)+"Đ");

        btnXemGioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent in = new Intent(ChiTietSPActivity.this, GioHangActivity.class);
                startActivity(in);
            }
        });
        btnTiepTucMua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void clickHome() {
        activityChiTietSpBinding.imgHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(ChiTietSPActivity.this, MainActivity.class);
                startActivity(in);
                finish();
            }
        });
    }

    private void clickBack() {
        activityChiTietSpBinding.header.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void xuLySoLuongMua() {
        if(soLuongMua<=1){
            activityChiTietSpBinding.tvGiamSoLuongMua.setVisibility(View.INVISIBLE);
        }
        activityChiTietSpBinding.tvTangSoLuongMua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityChiTietSpBinding.tvGiamSoLuongMua.setVisibility(View.VISIBLE);
                soLuongMua++;
                activityChiTietSpBinding.tvSoLuongMua.setText(soLuongMua+"");
                if(soLuongMua >= Integer.parseInt(sanPhamViewModel.getSoluong())){
                    activityChiTietSpBinding.tvTangSoLuongMua.setVisibility(View.INVISIBLE);
                }
            }
        });
        activityChiTietSpBinding.tvGiamSoLuongMua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityChiTietSpBinding.tvTangSoLuongMua.setVisibility(View.VISIBLE);
                soLuongMua--;
                activityChiTietSpBinding.tvSoLuongMua.setText(soLuongMua+"");
                if(soLuongMua<=1){
                    activityChiTietSpBinding.tvGiamSoLuongMua.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    private void callApi(int maSP) {
        IApiService.api.getThongTinCTSP(maSP).enqueue(new Callback<ChiTietSPViewModel>() {
            @Override
            public void onResponse(Call<ChiTietSPViewModel> call, Response<ChiTietSPViewModel> response) {
                ChiTietSPViewModel chiTietSPViewModel = response.body();
                if(chiTietSPViewModel != null){
                    activityChiTietSpBinding.setChiTietSPViewModel(chiTietSPViewModel);
                }
            }

            @Override
            public void onFailure(Call<ChiTietSPViewModel> call, Throwable t) {
                Toast.makeText(ct, "Call API fail", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void ganThongTin() {
        Glide.with(ct).load(sanPhamViewModel.getAnhsp()).into(activityChiTietSpBinding.imgChiTietSP);
        activityChiTietSpBinding.tvTenSPOfChiTietSanPham.setText(sanPhamViewModel.getTensp());
        activityChiTietSpBinding.tvGiaSPOfChiTietSanPham.setText("Giá: " + sanPhamViewModel.getGiasp() + " Đ");
        activityChiTietSpBinding.tvSoLuongSP.setText(sanPhamViewModel.getSoluong());
        activityChiTietSpBinding.tvSoLuongMua.setText(soLuongMua+"");
        activityChiTietSpBinding.header.tvHeader.setText("Chi tiết sản phẩm");
        activityChiTietSpBinding.header.tvSoSP.setText(MainActivity.gioHangViewModels.size()+"");
    }

    @Override
    protected void onResume() {
        super.onResume();
        activityChiTietSpBinding.header.tvSoSP.setText(MainActivity.gioHangViewModels.size()+"");
    }
}