package huce.nhom15.mobileapp.view.activity;

import static huce.nhom15.mobileapp.R.drawable.product_notfound;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


import huce.nhom15.mobileapp.R;
import huce.nhom15.mobileapp.view.API.IApiService;
import huce.nhom15.mobileapp.view.PaginationScrollListener;
import huce.nhom15.mobileapp.view.adapter.ProductCategoryAdapter;

import huce.nhom15.mobileapp.view.adapter.SanPhamSearchAdapter;
import huce.nhom15.mobileapp.viewmodel.CategoryViewModel;
import huce.nhom15.mobileapp.viewmodel.SanPhamViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryActivity extends AppCompatActivity {
    private int maLoai;
    private TextView tvHeaderProductCategory, tvSoSP;
    private ImageView clickBack, imgShoppingCart;
    private ProductCategoryAdapter productCategoryAdapter;
    private  List<SanPhamViewModel> sanphams;
    private Context ct;
    private boolean isLoading;
    private boolean isLastPage;
    private int currentPage = 1;
    private Handler handler;
    private RecyclerView rcvProductCategory;
    private ProgressBar pgbProductCategoryr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        khoiTao();
        onClickBack();
        clickImgShoppingCart();

        tvSoSP.setText(MainActivity.gioHangViewModels.size()+"");

        CategoryViewModel categoryViewModel = (CategoryViewModel) getIntent().getExtras().get("category");
        tvHeaderProductCategory.setText(categoryViewModel.getTenLoaiSP());
        maLoai = Integer.parseInt(categoryViewModel.getId());

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        rcvProductCategory.setLayoutManager(gridLayoutManager);

        ct = this;

        callApi(maLoai, currentPage);
        recycleViewScroll(gridLayoutManager);
    }

    private void clickImgShoppingCart() {
        imgShoppingCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(CategoryActivity.this, GioHangActivity.class);
                startActivity(in);
            }
        });
    }

    private void recycleViewScroll(GridLayoutManager gridLayoutManager) {
        rcvProductCategory.clearOnScrollListeners();
        rcvProductCategory.addOnScrollListener(new PaginationScrollListener(gridLayoutManager) {
            @Override
            public void loadMoreItem() {
                isLoading = true;
                currentPage+=1;
                loadNextPage(maLoai, currentPage);
            }
            @Override
            public boolean isLoading() {
                return isLoading;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }
        });
    }

    private void loadNextPage(int maLoai, int currentPage){
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                IApiService.api.getListProductCategory(maLoai, currentPage).enqueue(new Callback<List<SanPhamViewModel>>() {
                    @Override
                    public void onResponse(Call<List<SanPhamViewModel>> call, Response<List<SanPhamViewModel>> response) {
                        List<SanPhamViewModel> list = response.body();
                        if(list.size() > 0){
                            productCategoryAdapter.removeFooterLoading();
                            sanphams.addAll(list);
                            productCategoryAdapter.notifyDataSetChanged();
                            isLoading=false;
                            productCategoryAdapter.addFooterLoading();
                        }else{
                            isLastPage = true;
                            productCategoryAdapter.removeFooterLoading();
                            Toast.makeText(ct, "Đã load hết dữ liệu", Toast.LENGTH_SHORT).show();
                        }

                    }
                    @Override
                    public void onFailure(Call<List<SanPhamViewModel>> call, Throwable t) {
                        Toast.makeText(CategoryActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }, 2000);

    }

    private void callApi(int maLoai, int currentPage) {
        pgbProductCategoryr.setVisibility(View.VISIBLE);
        IApiService.api.getListProductCategory(maLoai, currentPage).enqueue(new Callback<List<SanPhamViewModel>>() {
            @Override
            public void onResponse(Call<List<SanPhamViewModel>> call, Response<List<SanPhamViewModel>> response) {
                pgbProductCategoryr.setVisibility(View.INVISIBLE);
                sanphams = response.body();
                if(sanphams.size() > 0){
                    productCategoryAdapter = new ProductCategoryAdapter(sanphams, ct);
                    rcvProductCategory.setAdapter(productCategoryAdapter);
                    productCategoryAdapter.addFooterLoading();
                }
                else{
                    Toast.makeText(CategoryActivity.this, "Sản phẩm thuộc loại này đã hết", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<SanPhamViewModel>> call, Throwable t) {
                pgbProductCategoryr.setVisibility(View.INVISIBLE);
                Toast.makeText(ct, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void onClickBack() {
        clickBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void khoiTao() {
        tvHeaderProductCategory = findViewById(R.id.tvHeader);
        clickBack = findViewById(R.id.imgBack);
        pgbProductCategoryr = findViewById(R.id.pgbProductCategory);
        rcvProductCategory = findViewById(R.id.rcvProductCategory);
        imgShoppingCart = findViewById(R.id.imgShoppingCart);
        tvSoSP = findViewById(R.id.headerProductCategory).findViewById(R.id.tvSoSP);
    }

    @Override
    protected void onResume() {
        super.onResume();
        tvSoSP.setText(MainActivity.gioHangViewModels.size()+"");
    }
}