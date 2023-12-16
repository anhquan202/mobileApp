package huce.nhom15.mobileapp.view.API;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Currency;
import java.util.List;

import huce.nhom15.mobileapp.model.Customer;
import huce.nhom15.mobileapp.view.ModelRespone.LoginRespone;
import huce.nhom15.mobileapp.view.ModelRespone.RegisterRespone;
<<<<<<< HEAD
import huce.nhom15.mobileapp.view.ModelRespone.SetNewPassRespone;
=======
>>>>>>> 372c82f (Update project)
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
<<<<<<< HEAD
            .baseUrl("http://192.168.98.27:8081/backend/")
=======
            .baseUrl("http://192.168.98.91:8081/backend/")
>>>>>>> 372c82f (Update project)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(IApiService.class);

    @GET("getListSP.php")
    Call<List<SanPhamViewModel>> getListSanPham();
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
<<<<<<< HEAD
    @FormUrlEncoded
    @POST("setNewPassword.php")
    Call<SetNewPassRespone> setnewpwd(
            @Field("KH_Email") String email,
            @Field("KH_Password") String password
    );
    @GET("searchSPByName.php")
    Call<List<SanPhamViewModel>> getListSanPhamSearch(String tensp, int currentPage);
=======
>>>>>>> 372c82f (Update project)
}
