package huce.nhom15.mobileapp.view.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.databinding.BindingAdapter;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import huce.nhom15.mobileapp.R;
import huce.nhom15.mobileapp.view.API.IApiService;
import huce.nhom15.mobileapp.view.adapter.CategoryAdapter;
import huce.nhom15.mobileapp.viewmodel.CategoryViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CategoryFragment extends Fragment {
    private View view;
    private RecyclerView recyclerView;
    private Context ct;
    private CategoryAdapter categoryAdapter;
    private List<CategoryViewModel> categoryViewModels;
    private ProgressBar progressBar;

    public CategoryFragment(Context ct) {
        this.ct = ct;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_category, container, false);
        khoiTao();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(ct, 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        callApi();
        return view;
    }

    private void callApi() {
        progressBar.setVisibility(View.VISIBLE);
        IApiService.api.getListCategory().enqueue(new Callback<List<CategoryViewModel>>() {
            @Override
            public void onResponse(Call<List<CategoryViewModel>> call, Response<List<CategoryViewModel>> response) {
                progressBar.setVisibility(View.INVISIBLE);
                categoryViewModels = response.body();
                if(categoryViewModels != null){
                    categoryAdapter = new CategoryAdapter(categoryViewModels, ct);
                    recyclerView.setAdapter(categoryAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<CategoryViewModel>> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(ct, "Mất kết nối, vui lòng kiểm tra mạng của bạn", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void khoiTao() {
        recyclerView = view.findViewById(R.id.rcvCategory);
        progressBar = view.findViewById(R.id.progressBarCategory);
    }

    @BindingAdapter("image")
    public static void loadImage(ImageView view, String url){
        Glide.with(view.getContext()).load(url).into(view);
    }
}