package huce.nhom15.mobileapp.view.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import huce.nhom15.mobileapp.R;
import huce.nhom15.mobileapp.view.API.IApiService;
import huce.nhom15.mobileapp.view.adapter.SanPhamTCAdapter;
import huce.nhom15.mobileapp.view.viewmodel.SanPhamViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {
    private RecyclerView rcv_tc;
    private List<SanPhamViewModel> sanphams;
    private SanPhamTCAdapter sanPhamTCAdapter;
    private Context ct;
    private ProgressBar progressBar;
    public HomeFragment(Context ct) {
        this.ct = ct;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);
        rcv_tc = view.findViewById(R.id.rcv_TC);
        progressBar = view.findViewById(R.id.progressBar_TC);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(ct, 2);
        rcv_tc.setLayoutManager(gridLayoutManager);
        callApi();
        return view;
    }
    private void callApi(){
        progressBar.setVisibility(View.VISIBLE);
        IApiService.api.getListSanPham().enqueue(new Callback<List<SanPhamViewModel>>() {
            @Override
            public void onResponse(Call<List<SanPhamViewModel>> call, Response<List<SanPhamViewModel>> response) {
                progressBar.setVisibility(View.INVISIBLE);
                sanphams = response.body();
                sanPhamTCAdapter = new SanPhamTCAdapter(sanphams, ct);
                rcv_tc.setAdapter(sanPhamTCAdapter);
            }

            @Override
            public void onFailure(Call<List<SanPhamViewModel>> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(ct, "onFail", Toast.LENGTH_SHORT).show();
            }
        });
    }
}