package huce.nhom15.mobileapp.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import huce.nhom15.mobileapp.R;
import huce.nhom15.mobileapp.view.API.IApiService;
import huce.nhom15.mobileapp.view.ModelRespone.RegisterRespone;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SignupActivity extends AppCompatActivity {
    private EditText edUsername;
    private EditText edPhone;
    private EditText edEmail;
    private EditText edPassword;
    private Button btnSignup;
    private RadioGroup genderGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        init();
        ConstraintLayout cl = findViewById(R.id.header);
        TextView tvHeadedr = cl.findViewById(R.id.tvHeader);
        tvHeadedr.setText(getString(R.string.txtSignup));
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });
    }
    public void init(){
        try {
            edUsername = findViewById(R.id.edUsername);
            edPhone = findViewById(R.id.edPhone);
            genderGroup = findViewById(R.id.rdGender);
            edEmail = findViewById(R.id.edEmail);
            edPassword = findViewById(R.id.edPassword);
            btnSignup = findViewById(R.id.btnSignup);

        } catch (Exception ex) {
            Log.e("Init", ex.getMessage());
        }
    }
    public void register(){
        init();
        String username = edUsername.getText().toString().trim();
        String phone = edPhone.getText().toString().trim();
        int selected = genderGroup.getCheckedRadioButtonId();
        RadioButton radioButton= findViewById(selected);
        String gender = radioButton.getText().toString();
        String email = edEmail.getText().toString().trim();
        String password = edPassword.getText().toString().trim();
        if(username.isEmpty() && phone.isEmpty() && gender.isEmpty() && email.isEmpty() && password.isEmpty()){
            Toast.makeText(SignupActivity.this, "Please enter fully your information", Toast.LENGTH_SHORT).show();
        }
        else{
            Call<RegisterRespone> call = IApiService.api.register(username, phone, gender, email, password);
            call.enqueue(new Callback<RegisterRespone>() {
                @Override
                public void onResponse(Call<RegisterRespone> call, Response<RegisterRespone> response) {
                    RegisterRespone registerRespone = response.body();
                    if(response.isSuccessful()){
                        Toast.makeText(SignupActivity.this, registerRespone.getMessage(), Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(SignupActivity.this, registerRespone.getError(), Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<RegisterRespone> call, Throwable t) {
                    Toast.makeText(SignupActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}