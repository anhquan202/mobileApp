package huce.nhom15.mobileapp.view.activity;

import static huce.nhom15.mobileapp.R.drawable.product_notfound;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import huce.nhom15.mobileapp.R;
import huce.nhom15.mobileapp.view.API.IApiService;
import huce.nhom15.mobileapp.view.PaginationScrollListener;
import huce.nhom15.mobileapp.view.adapter.SanPhamSearchAdapter;
import huce.nhom15.mobileapp.viewmodel.SanPhamViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {
    private String tensp;
    private EditText edtsearch;
    private SanPhamSearchAdapter sanPhamSearchAdapter;
    private List<SanPhamViewModel> sanphams;
    private Context ct;
    private boolean isLoading;
    private boolean isLastPage;
    private int currentPage = 1;
    private Handler handler;
    private RecyclerView rcvSearch;
    private CircleImageView circleImageView;
    private ProgressBar progressBar;
    private boolean productNotFound = true;
    private ScrollView scrollView;
    private ImageView pNotFound;
    private FrameLayout foundProduct;
    private RelativeLayout layoutIconGioHang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        khoiTao();
        back();
        tensp = getIntent().getStringExtra("tensp");
        edtsearch.setText(tensp);
        edtsearch.clearFocus();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        rcvSearch.setLayoutManager(gridLayoutManager);
        ct = this;
        callApi(tensp, currentPage);
        editorEditText();
        recycleViewScroll(gridLayoutManager);

        clickGioHang();
    }

    private void clickGioHang() {
        layoutIconGioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(SearchActivity.this, GioHangActivity.class);
                startActivity(in);
            }
        });
    }

    private void recycleViewScroll(GridLayoutManager gridLayoutManager) {
        rcvSearch.clearOnScrollListeners();
        rcvSearch.addOnScrollListener(new PaginationScrollListener(gridLayoutManager) {
            @Override
            public void loadMoreItem() {
                isLoading = true;
                currentPage+=1;
                loadNextPage(tensp, currentPage);
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

    private void editorEditText() {
        edtsearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN)) {
                    InputMethodManager imm = (InputMethodManager) edtsearch.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(edtsearch.getWindowToken(), 0);
                    edtsearch.clearFocus();
                    currentPage = 1;
                    isLoading=false;
                    isLastPage=false;
                    productNotFound = true;
                    pNotFound.setVisibility(View.GONE);
                    scrollView.setVisibility(View.VISIBLE);
                    if(sanphams != null){
                        sanphams.clear();
                        sanPhamSearchAdapter.notifyDataSetChanged();
                    }

                    tensp = edtsearch.getText().toString();

                    callApi(tensp, currentPage);
                    return true;
                }
                return false;
            }
        });
    }
    private void loadNextPage(String tensp, int currentPage){
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                IApiService.api.getListSanPhamSearch(tensp, currentPage).enqueue(new Callback<List<SanPhamViewModel>>() {
                    @Override
                    public void onResponse(Call<List<SanPhamViewModel>> call, Response<List<SanPhamViewModel>> response) {
                        List<SanPhamViewModel> list = response.body();
                        if(list.size() > 0){
                            sanPhamSearchAdapter.removeFooterLoading();
                            sanphams.addAll(list);
                            sanPhamSearchAdapter.notifyDataSetChanged();
                            isLoading=false;
                            sanPhamSearchAdapter.addFooterLoading();
                        }else{
                            isLastPage = true;
                            sanPhamSearchAdapter.removeFooterLoading();
                            Toast.makeText(ct, "Đã load hết dữ liệu", Toast.LENGTH_SHORT).show();
                        }

                    }
                    @Override
                    public void onFailure(Call<List<SanPhamViewModel>> call, Throwable t) {
                        Toast.makeText(SearchActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }, 2000);
    }

    private void callApi(String tensp, int currentPage) {
        progressBar.setVisibility(View.VISIBLE);
        IApiService.api.getListSanPhamSearch(tensp, currentPage).enqueue(new Callback<List<SanPhamViewModel>>() {
            @Override
            public void onResponse(Call<List<SanPhamViewModel>> call, Response<List<SanPhamViewModel>> response) {
                progressBar.setVisibility(View.INVISIBLE);
                sanphams = response.body();
                if(sanphams.size() > 0){
                    sanPhamSearchAdapter = new SanPhamSearchAdapter(sanphams, ct);
                    rcvSearch.setAdapter(sanPhamSearchAdapter);
                    sanPhamSearchAdapter.addFooterLoading();
                    productNotFound = false;
                }
                else{
                    isLastPage = true;
                    if(productNotFound){

                        pNotFound.setVisibility(View.VISIBLE);
                        pNotFound.setImageResource(product_notfound);
                        scrollView.setVisibility(View.GONE);
                        sanPhamSearchAdapter = new SanPhamSearchAdapter();
                        rcvSearch.setAdapter(sanPhamSearchAdapter);
                    }
                }

            }

            @Override
            public void onFailure(Call<List<SanPhamViewModel>> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(SearchActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void back() {
        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void khoiTao() {
        edtsearch = findViewById(R.id.edtsearch);
        rcvSearch = findViewById(R.id.rcv_TC);
        circleImageView = findViewById(R.id.imglogo);
        progressBar = findViewById(R.id.progressBar_TC);
        scrollView = findViewById(R.id.scrollView);
        pNotFound = findViewById(R.id.productnotfound);
        foundProduct = findViewById(R.id.foundproduct);
        layoutIconGioHang = findViewById(R.id.include).findViewById(R.id.layoutIconGioHang);
    }
}