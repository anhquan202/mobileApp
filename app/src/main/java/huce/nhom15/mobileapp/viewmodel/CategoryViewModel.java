package huce.nhom15.mobileapp.viewmodel;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.io.Serializable;

import huce.nhom15.mobileapp.view.activity.CategoryActivity;

public class CategoryViewModel implements Serializable {
    private String id;
    private String anhLoaiSP;
    private String tenLoaiSP;

    public CategoryViewModel() {
    }

    public CategoryViewModel(String id, String anhLoaiSP, String tenLoaiSP) {
        this.id = id;
        this.anhLoaiSP = anhLoaiSP;
        this.tenLoaiSP = tenLoaiSP;
    }

    public String getAnhLoaiSP() {
        return anhLoaiSP;
    }

    public void setAnhLoaiSP(String anhLoaiSP) {
        this.anhLoaiSP = anhLoaiSP;
    }

    public String getTenLoaiSP() {
        return tenLoaiSP;
    }

    public void setTenLoaiSP(String tenLoaiSP) {
        this.tenLoaiSP = tenLoaiSP;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    public void onClickItemCategory(View view, CategoryViewModel categoryViewModel){
        Intent in = new Intent(view.getContext(), CategoryActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("category", categoryViewModel);
        in.putExtras(bundle);
        view.getContext().startActivity(in);
    }
}
