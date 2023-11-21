package huce.nhom15.mobileapp.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

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
import huce.nhom15.mobileapp.view.API.IApiService;
import huce.nhom15.mobileapp.view.ModelRespone.LoginRespone;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {

    private EditText edUserEmail;
    private EditText edPassword;
    private Button btnLogin, btnForgotPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        ConstraintLayout cl = findViewById(R.id.header);
        TextView tvHeadedr = cl.findViewById(R.id.tvHeader);
        tvHeadedr.setText(getString(R.string.txtLogin));
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
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
            edUserEmail = findViewById(R.id.edUsername);
            edPassword = findViewById(R.id.edPassword);
        } catch (Exception ex) {
            Log.e("Init", ex.getMessage());
        }
    }

    public void login() {
        init();
        String email = edUserEmail.getText().toString().trim();
        String password = edPassword.getText().toString().trim();
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(LoginActivity.this, "Email or password is not empty", Toast.LENGTH_SHORT).show();
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(LoginActivity.this, "Email has incorrect format", Toast.LENGTH_SHORT).show();

        }else {
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

    }


}