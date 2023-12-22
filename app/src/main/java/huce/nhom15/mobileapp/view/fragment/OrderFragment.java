package huce.nhom15.mobileapp.view.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.List;

import huce.nhom15.mobileapp.R;
import huce.nhom15.mobileapp.model.Customer;
import huce.nhom15.mobileapp.view.API.IApiService;
import huce.nhom15.mobileapp.view.adapter.OrderAdapter;
import huce.nhom15.mobileapp.viewmodel.OrderViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OrderFragment extends Fragment {
    private View view;
    private static List<OrderViewModel> orderViewModels;
    private static OrderAdapter orderAdapter;
    private static Context ct;
    private static RecyclerView rcvOrder;
    private static ImageView imgEmptyOrder;
    private static ScrollView scrollViewOrder;
    private static TextView notLogged;
    private static FrameLayout logged;

    public OrderFragment(Context ct) {
        this.ct = ct;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_order, container, false);
        khoiTao();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ct);
        rcvOrder.setLayoutManager(linearLayoutManager);
        rcvOrder.addItemDecoration(new DividerItemDecoration(ct, DividerItemDecoration.VERTICAL));

        evenShowOrder();
        return view;
    }

    private void khoiTao() {
        rcvOrder = view.findViewById(R.id.rcvOrder);
        imgEmptyOrder = view.findViewById(R.id.imgEmptyOrder);
        scrollViewOrder = view.findViewById(R.id.scrollViewOrder);
        notLogged = view.findViewById(R.id.notLogged);
        logged = view.findViewById(R.id.logged);
    }

    public static void evenShowOrder() {
        if(isLoggedIn() || isSignup()){
            notLogged.setVisibility(View.GONE);
            logged.setVisibility(View.VISIBLE);
            String user = getUserName();
            Gson gson = new Gson();
            Customer customer = gson.fromJson(user, Customer.class);
            IApiService.api.getOrder(customer.getKH_MaKH()).enqueue(new Callback<List<OrderViewModel>>() {
                @Override
                public void onResponse(Call<List<OrderViewModel>> call, Response<List<OrderViewModel>> response) {
                    orderViewModels = response.body();
                    if(orderViewModels != null){
                        if(orderViewModels.size() <= 0){
                            imgEmptyOrder.setVisibility(View.VISIBLE);
                            scrollViewOrder.setVisibility(View.INVISIBLE);
                        }else{
                            imgEmptyOrder.setVisibility(View.INVISIBLE);
                            scrollViewOrder.setVisibility(View.VISIBLE);
                            orderAdapter = new OrderAdapter(orderViewModels, ct);
                            rcvOrder.setAdapter(orderAdapter);
                        }

                    }
                }

                @Override
                public void onFailure(Call<List<OrderViewModel>> call, Throwable t) {
                    Toast.makeText(ct, "Mất kết nối, vui lòng kiểm tra mạng của bạn", Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            notLogged.setVisibility(View.VISIBLE);
            logged.setVisibility(View.GONE);
        }
    }
    private static boolean isLoggedIn() {
        SharedPreferences prefs = ct.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        return prefs.getBoolean("isLoggedIn", false);
    }
    private static boolean isSignup() {
        SharedPreferences prefs = ct.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        return prefs.getBoolean("isSignup", false);
    }
    private static String getUserName() {
        SharedPreferences prefs = ct.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        return prefs.getString("username", "");
    }
}