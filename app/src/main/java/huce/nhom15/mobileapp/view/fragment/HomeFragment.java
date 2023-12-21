package huce.nhom15.mobileapp.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import huce.nhom15.mobileapp.R;
import huce.nhom15.mobileapp.view.API.IApiService;
import huce.nhom15.mobileapp.view.activity.GioHangActivity;
import huce.nhom15.mobileapp.view.activity.MainActivity;
import huce.nhom15.mobileapp.view.activity.SearchActivity;
import huce.nhom15.mobileapp.view.adapter.SanPhamTCAdapter;
import huce.nhom15.mobileapp.viewmodel.SanPhamViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {
    private View view;
    private RecyclerView rcv_tc;
    private List<SanPhamViewModel> sanphams;
    private SanPhamTCAdapter sanPhamTCAdapter;
    private Context ct;
    private ProgressBar progressBar;
    private EditText edtsearch;
    private ImageView imgGioHang;
    private RelativeLayout layoutIconGioHang;
    private TextView tvSoSP;
    public HomeFragment(Context ct) {
        this.ct = ct;
    }

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home,container,false);
        khoiTao();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(ct, 2);
        rcv_tc.setLayoutManager(gridLayoutManager);

        tvSoSP.setText(MainActivity.gioHangViewModels.size()+"");

        callApi();
        layoutIconGioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(ct, GioHangActivity.class);
                startActivity(in);
            }
        });
        timKiemSanPham();
        return view;
    }

    private void khoiTao() {
        rcv_tc = view.findViewById(R.id.rcv_TC);
        edtsearch = view.findViewById(R.id.edtsearch);
        progressBar = view.findViewById(R.id.progressBar_TC);
        layoutIconGioHang = view.findViewById(R.id.toolbarTC).findViewById(R.id.layoutIconGioHang);
        tvSoSP = view.findViewById(R.id.toolbarTC).findViewById(R.id.tvSoSP);
    }

    private void timKiemSanPham() {
        edtsearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN)) {

                    String enteredText = edtsearch.getText().toString();
                    InputMethodManager imm = (InputMethodManager) ct.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(edtsearch.getWindowToken(), 0);
                    Intent in = new Intent(ct, SearchActivity.class);
                    in.putExtra("tensp", enteredText);
                    edtsearch.setText("");
                    edtsearch.clearFocus();
                    startActivity(in);
                    // Trả về true để báo rằng bạn đã xử lý sự kiện
                    return true;
                }
                // Trả về false để cho phép xử lý mặc định của EditText
                return false;
            }
        });
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
                Toast.makeText(ct, "Mất kết nối, vui lòng kiểm tra mạng của bạn", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        tvSoSP.setText(MainActivity.gioHangViewModels.size()+"");
    }
}