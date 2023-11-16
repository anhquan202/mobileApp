package huce.nhom15.mobileapp.view.API;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import huce.nhom15.mobileapp.view.viewmodel.SanPhamViewModel;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public interface IApiService {
    Gson gson = new GsonBuilder().setDateFormat("dd-MM-yyyy").create();
    IApiService api = new Retrofit.Builder()
            .baseUrl("http://192.168.50.1:8080/backend/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(IApiService.class);

    @GET("getListSP.php")
    Call<List<SanPhamViewModel>> getListSanPham();
}