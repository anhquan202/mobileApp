package huce.nhom15.mobileapp.viewmodel;


import android.view.View;
import android.widget.ImageView;


import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;

import java.io.Serializable;
import java.text.DecimalFormat;



public class GioHangViewModel implements Serializable{
    private String id;
    private String anhsp;
    private String tensp;
    private String giasp;
    private String soluong;
    private String soluongban;
    public GioHangViewModel() {
    }

    public GioHangViewModel(String id, String anhsp, String tensp, String giasp, String soluong, String soluongban) {
        this.id = id;
        this.anhsp = anhsp;
        this.tensp = tensp;
        this.giasp = giasp;
        this.soluong = soluong;
        this.soluongban = soluongban;
    }

    public String getSoluongban() {
        return soluongban;
    }

    public void setSoluongban(String soluongban) {
        this.soluongban = soluongban;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAnhsp() {
        return anhsp;
    }

    public void setAnhsp(String anhsp) {
        this.anhsp = anhsp;
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public String getGiasp() {
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        return decimalFormat.format(Integer.parseInt(giasp));
    }

    public void setGiasp(String giasp) {
        this.giasp = giasp;
    }

    public String getSoluong() {
        return soluong;
    }

    public void setSoluong(String soluong) {
        this.soluong = soluong;
    }

    @Override
    public String toString() {
        return  "id='" + id + '\'' +
                ", tensp='" + tensp + '\'' +
                ", giasp='" + giasp + '\'' +
                ", soluong='" + soluong + '\'' +
                '}';
    }

    @BindingAdapter("img_url")
    public static void loadImage(ImageView view, String url){
        Glide.with(view.getContext()).load(url).into(view);
    }

    public void onLongClickItem(View view){

    }
}
