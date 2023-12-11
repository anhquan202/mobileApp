package huce.nhom15.mobileapp.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import huce.nhom15.mobileapp.R;
import huce.nhom15.mobileapp.view.API.IApiService;
import huce.nhom15.mobileapp.view.ModelRespone.LoginRespone;
import huce.nhom15.mobileapp.view.ModelRespone.SetNewPassRespone;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SetNewpassActivity extends AppCompatActivity {
    private EditText edUserEmail;
    private EditText edPassword;
    private Button btnSetNewpwd, btnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_newpass);
        init();
        ConstraintLayout cl = findViewById(R.id.header);
        TextView tvHeadedr = cl.findViewById(R.id.tvHeader);
        tvHeadedr.setText(getString(R.string.txtforgotPass));
        btnSetNewpwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setnewpwd();
            }
        });
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inten = new Intent(view.getContext(), SignupActivity.class);
                startActivity(inten);
            }
        });
    }
    public void init(){
        try {
            edUserEmail = findViewById(R.id.edEmail);
            edPassword = findViewById(R.id.edPassword);
            btnSetNewpwd = findViewById(R.id.btnsetnewpwd);
            btnSignup = findViewById(R.id.btnSignup);

        } catch (Exception ex) {
            Log.e("Init", ex.getMessage());
        }
    }
    public void setnewpwd(){
        init();
        String email = edUserEmail.getText().toString().trim();
        String password = edPassword.getText().toString().trim();
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(SetNewpassActivity.this, "Email or password is not empty", Toast.LENGTH_SHORT).show();
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(SetNewpassActivity.this, "Email has incorrect format", Toast.LENGTH_SHORT).show();

        }else {
            Call<SetNewPassRespone> call = IApiService.api.setnewpwd(email, password);
            call.enqueue(new Callback<SetNewPassRespone>() {
                @Override
                public void onResponse(Call<SetNewPassRespone> call, Response<SetNewPassRespone> response) {
                    SetNewPassRespone setNewPassRespone = response.body();
                    if (response.isSuccessful()) {
                        if (setNewPassRespone.getError().equals("200")) {
                            Toast.makeText(SetNewpassActivity.this, setNewPassRespone.getMessage(), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SetNewpassActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(SetNewpassActivity.this, setNewPassRespone.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    } else {
                        Toast.makeText(SetNewpassActivity.this, setNewPassRespone.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<SetNewPassRespone> call, Throwable t) {
                    Toast.makeText(SetNewpassActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}