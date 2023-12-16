package huce.nhom15.mobileapp.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
<<<<<<< HEAD
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import huce.nhom15.mobileapp.R;
=======

import android.content.Intent;
import android.media.MediaCodec;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import huce.nhom15.mobileapp.R;
import huce.nhom15.mobileapp.model.Customer;
>>>>>>> 372c82f (Update project)
import huce.nhom15.mobileapp.view.API.IApiService;
import huce.nhom15.mobileapp.view.ModelRespone.LoginRespone;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
<<<<<<< HEAD

public class LoginActivity extends AppCompatActivity{
=======
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {
>>>>>>> 372c82f (Update project)

    private EditText edUserEmail;
    private EditText edPassword;
    private Button btnLogin, btnForgotPwd;
<<<<<<< HEAD
    public Boolean isLoginSuccess = false;
=======

>>>>>>> 372c82f (Update project)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        ConstraintLayout cl = findViewById(R.id.header);
<<<<<<< HEAD
        TextView tvHeader = cl.findViewById(R.id.tvHeader);
        tvHeader.setText(getString(R.string.txtLogin));
        ConstraintLayout constraintLayout = findViewById(R.id.loginLayout);
        constraintLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                HideKeyBoard(view);
                return false;
            }
        });
        SharedPreferences prefs = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        if (prefs.getBoolean("isLoggedIn", false)) {
            // Nếu đã đăng nhập, xóa giá trị username
            clearUsername();
        }

=======
        TextView tvHeadedr = cl.findViewById(R.id.tvHeader);
        tvHeadedr.setText(getString(R.string.txtLogin));
>>>>>>> 372c82f (Update project)
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
<<<<<<< HEAD
        btnForgotPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SetNewpassActivity.class);
                startActivity(intent);
            }
        });

    }
    public void HideKeyBoard( View view){
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    public void init() {
        try {
            btnLogin = findViewById(R.id.login);
            btnForgotPwd = findViewById(R.id.btnForgotPass);
=======
//        btnForgotPwd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
//                startActivity(intent);
//            }
//        });

    }
    public void init() {
        try {
            btnLogin = findViewById(R.id.login);
//            btnSignup = findViewById(R.id.btnSignup);
>>>>>>> 372c82f (Update project)
            edUserEmail = findViewById(R.id.edUsername);
            edPassword = findViewById(R.id.edPassword);
        } catch (Exception ex) {
            Log.e("Init", ex.getMessage());
        }
    }

<<<<<<< HEAD
    public boolean login() {
=======
    public void login() {
>>>>>>> 372c82f (Update project)
        init();
        String email = edUserEmail.getText().toString().trim();
        String password = edPassword.getText().toString().trim();
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(LoginActivity.this, "Email or password is not empty", Toast.LENGTH_SHORT).show();
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(LoginActivity.this, "Email has incorrect format", Toast.LENGTH_SHORT).show();

        }else {
<<<<<<< HEAD
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
                        String username = loginRespone.getUser().toString();
                        Toast.makeText(LoginActivity.this, loginRespone.getMessage(), Toast.LENGTH_SHORT).show();
                        saveLoginState();
                        saveUsername(username);
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
                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void clearUsername() {
        SharedPreferences.Editor editor = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE).edit();
        editor.remove("username");
        editor.apply();
    }
    // Lưu trạng thái đăng nhập
    private void saveLoginState() {
        SharedPreferences.Editor editor = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE).edit();
        editor.putBoolean("isLoggedIn", true);
        editor.apply();
    }
    private void saveUsername(String username) {
        SharedPreferences.Editor editor = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE).edit();
        editor.putString("username", username);
        editor.apply();
=======
            Call<LoginRespone> call = IApiService.api.login(email, password);
            call.enqueue(new Callback<LoginRespone>() {
                @Override
                public void onResponse(Call<LoginRespone> call, Response<LoginRespone> response) {
                    LoginRespone loginRespone = response.body();
                    if (response.isSuccessful()){
                        if (loginRespone.getError().equals("200")) {
                            Toast.makeText(LoginActivity.this, loginRespone.getMessage(), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        }else {
                            Toast.makeText(LoginActivity.this, loginRespone.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                    else {
                        Toast.makeText(LoginActivity.this, loginRespone.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<LoginRespone> call, Throwable t) {
                    Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

>>>>>>> 372c82f (Update project)
    }


}