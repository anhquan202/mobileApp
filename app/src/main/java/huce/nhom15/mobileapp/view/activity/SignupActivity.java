package huce.nhom15.mobileapp.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

<<<<<<< HEAD
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
=======
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
>>>>>>> 372c82f (Update project)
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import huce.nhom15.mobileapp.R;
import huce.nhom15.mobileapp.view.API.IApiService;
import huce.nhom15.mobileapp.view.ModelRespone.RegisterRespone;
<<<<<<< HEAD
import huce.nhom15.mobileapp.view.fragment.DetailUserFragment;
=======
>>>>>>> 372c82f (Update project)
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
<<<<<<< HEAD
    public Boolean isSignupSuccess = false;

=======
>>>>>>> 372c82f (Update project)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        init();
        ConstraintLayout cl = findViewById(R.id.header);
<<<<<<< HEAD
        TextView tvHeader = cl.findViewById(R.id.tvHeader);
        tvHeader.setText(getString(R.string.txtSignup));
        ConstraintLayout constraintLayout = findViewById(R.id.registerLayout);
        constraintLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                HideKeyBoard(view);
                return false;
            }
        });
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (register()) {
                    // Nếu đăng nhập thành công, lưu trạng thái và chuyển đến UserProfileActivity
                    saveRegisterState();
                    finish();
                }
                else {

                }
            }
        });
    }

    private void HideKeyBoard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void init() {
=======
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
>>>>>>> 372c82f (Update project)
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
<<<<<<< HEAD

    public boolean register() {
=======
    public void register(){
>>>>>>> 372c82f (Update project)
        init();
        String username = edUsername.getText().toString().trim();
        String phone = edPhone.getText().toString().trim();
        int selected = genderGroup.getCheckedRadioButtonId();
<<<<<<< HEAD
        RadioButton radioButton = findViewById(selected);
        String gender = radioButton.getText().toString();
        String email = edEmail.getText().toString().trim();
        String password = edPassword.getText().toString().trim();
        CallAPISignup(username, phone, gender, email, password);
        return false;
    }
    public void CallAPISignup(String username, String phone, String gender, String email, String password){
        Call<RegisterRespone> call = IApiService.api.register(username, phone, gender, email, password);
        call.enqueue(new Callback<RegisterRespone>() {
            @Override
            public void onResponse(Call<RegisterRespone> call, Response<RegisterRespone> response) {
                RegisterRespone registerRespone = response.body();
                if (response.isSuccessful()) {
                    if (registerRespone.getError().equals("200")) {
                        Toast.makeText(SignupActivity.this, registerRespone.getMessage(), Toast.LENGTH_SHORT).show();
                        saveRegisterState();
                        saveUsername(username);
                        finish();
                        isSignupSuccess = true;
                    } else {
                        Toast.makeText(SignupActivity.this, registerRespone.getMessage(), Toast.LENGTH_SHORT).show();
                        isSignupSuccess = false;
                    }
                } else {
                    Toast.makeText(SignupActivity.this, registerRespone.getMessage(), Toast.LENGTH_SHORT).show();
                    isSignupSuccess = false;
                }
            }

            @Override
            public void onFailure(Call<RegisterRespone> call, Throwable t) {
                Toast.makeText(SignupActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void saveRegisterState() {
        SharedPreferences.Editor editor = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE).edit();
        editor.putBoolean("isSignup", true);
        editor.apply();
    }
    private void saveUsername(String username) {
        SharedPreferences.Editor editor = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE).edit();
        editor.putString("username", username);
        editor.apply();
=======
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
>>>>>>> 372c82f (Update project)
    }
}