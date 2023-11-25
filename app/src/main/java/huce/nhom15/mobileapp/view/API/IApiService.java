package huce.nhom15.mobileapp.view.API;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import huce.nhom15.mobileapp.view.ModelRespone.LoginRespone;
import huce.nhom15.mobileapp.view.ModelRespone.RegisterRespone;
import huce.nhom15.mobileapp.viewmodel.CategoryViewModel;
import huce.nhom15.mobileapp.viewmodel.SanPhamViewModel;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface IApiService {
    Gson gson = new GsonBuilder().setDateFormat("dd-MM-yyyy").create();
    IApiService api = new Retrofit.Builder()
            .baseUrl("http://192.168.50.1:8080/backend/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(IApiService.class);

    @GET("getListSP.php")
    Call<List<SanPhamViewModel>> getListSanPham();

    @GET("searchSPByName.php")
    Call<List<SanPhamViewModel>> getListSanPhamSearch(@Query("tensp") String tensp, @Query("page") int page);
    @GET("getListCategory.php")
    Call<List<CategoryViewModel>> getListCategory();
    @GET("getListProductCategory.php")
    Call<List<SanPhamViewModel>> getListProductCategory(@Query("LSP_MaLoai") int maLoai, @Query("page") int page);

    @FormUrlEncoded
    @POST("login.php")
    Call<LoginRespone> login(
            @Field("KH_Email") String email,
            @Field("KH_Password") String password
            );

    @FormUrlEncoded
    @POST("register.php")
    Call<RegisterRespone> register(
            @Field("KH_HoTen") String username,
            @Field("KH_SDT") String phone,
            @Field("KH_GioiTinh") String gender,
            @Field("KH_Email") String email,
            @Field("KH_Password") String password
    );
}
