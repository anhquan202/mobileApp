package huce.nhom15.mobileapp.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.style.BackgroundColorSpan;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import huce.nhom15.mobileapp.R;
import huce.nhom15.mobileapp.model.Customer;
import huce.nhom15.mobileapp.view.API.IApiService;
import huce.nhom15.mobileapp.view.ModelRespone.LoginRespone;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity{

    private EditText edUserEmail;
    private EditText edPassword;
    private Button btnLogin, btnForgotPwd;
    private TextView tvHeader;
    private ImageView imgCart;
    ConstraintLayout cl;
    public Boolean isLoginSuccess = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        resetUIHeader();
        ConstraintLayout constraintLayout = findViewById(R.id.loginLayout);
        constraintLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                HideKeyBoard(view);
                return false;
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
        btnForgotPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), SetNewpassActivity.class);
                startActivity(intent);
            }
        });

    }
    public void HideKeyBoard( View view){
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void resetUIHeader(){
        cl = findViewById(R.id.header);
        cl.setBackgroundColor(Color.TRANSPARENT);
        tvHeader = cl.findViewById(R.id.tvHeader);
        tvHeader.setText(getString(R.string.txtLogin));
        imgCart = cl.findViewById(R.id.imgShoppingCart);
        imgCart.setVisibility(View.INVISIBLE);

    }
    public void init() {
        try {
            btnLogin = findViewById(R.id.login);
            btnForgotPwd = findViewById(R.id.btnForgotPass);
            edUserEmail = findViewById(R.id.edUsername);
            edPassword = findViewById(R.id.edPassword);
        } catch (Exception ex) {
            Log.e("Init", ex.getMessage());
        }
    }

    public boolean login() {
        //init();
        String email = edUserEmail.getText().toString().trim();
        String password = edPassword.getText().toString().trim();
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(LoginActivity.this, "Email or password is not empty", Toast.LENGTH_SHORT).show();
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(LoginActivity.this, "Email has incorrect format", Toast.LENGTH_SHORT).show();

        }else {
            CallAPI(email, password);
        }
        return false;
    }
    public void CallAPI(String email, String password){
        Call<LoginRespone> call = IApiService.api.login(email, password);
        call.enqueue(new Callback<LoginRespone>() {
            @Override
            public void onResponse(Call<LoginRespone> call, Response<LoginRespone> response) {
                LoginRespone loginRespone = response.body();
                if (response.isSuccessful()) {
                    if (loginRespone.getError().equals("200")) {
                        Customer customer = loginRespone.getCustomer();
                        Toast.makeText(LoginActivity.this, loginRespone.getMessage(), Toast.LENGTH_SHORT).show();
                        //Toast.makeText(LoginActivity.this, customer.toString(), Toast.LENGTH_SHORT).show();
                        Gson gson = new Gson();
                        String user = gson.toJson(customer);

                        saveLoginState();
                        saveUsername(user);
                        finish();
                        isLoginSuccess = true;

                    } else {
                        Toast.makeText(LoginActivity.this, loginRespone.getMessage(), Toast.LENGTH_SHORT).show();
                        isLoginSuccess = false;
                    }

                } else {
                    Toast.makeText(LoginActivity.this, loginRespone.getMessage(), Toast.LENGTH_SHORT).show();
                    isLoginSuccess = false;
                }
            }
            @Override
            public void onFailure(Call<LoginRespone> call, Throwable t) {
//                Toast.makeText(LoginActivity.this, "Vui lòng kiểm tra lại mạng của bạn"  , Toast.LENGTH_SHORT).show();
                Toast.makeText(LoginActivity.this, t.getMessage() , Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void saveLoginState() {
        SharedPreferences.Editor editor = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE).edit();
        editor.putBoolean("isLoggedIn", true);
        editor.apply();
    }
    private void saveUsername(String user) {
        SharedPreferences.Editor editor = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE).edit();
        editor.putString("username", user);
        editor.apply();
    }


}