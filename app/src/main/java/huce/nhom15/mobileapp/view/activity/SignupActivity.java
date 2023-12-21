package huce.nhom15.mobileapp.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import huce.nhom15.mobileapp.R;
import huce.nhom15.mobileapp.model.Customer;
import huce.nhom15.mobileapp.view.API.IApiService;
import huce.nhom15.mobileapp.view.ModelRespone.RegisterRespone;
import huce.nhom15.mobileapp.view.fragment.DetailUserFragment;
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
    public Boolean isSignupSuccess = false;
    private TextView tvHeader;
    private ImageView imgCart;
    ConstraintLayout cl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        init();
        resetUIHeader();
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
    public void resetUIHeader(){
        cl =findViewById(R.id.header);
        cl.setBackgroundColor(Color.TRANSPARENT);
        tvHeader = cl.findViewById(R.id.tvHeader);
        tvHeader.setText(getString(R.string.txtSignup));
        imgCart = cl.findViewById(R.id.imgShoppingCart);
        imgCart.setVisibility(View.INVISIBLE);

    }
    public void init() {
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

    public boolean register() {
        init();
        String username = edUsername.getText().toString().trim();
        String phone = edPhone.getText().toString().trim();
        int selected = genderGroup.getCheckedRadioButtonId();
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
                        IApiService.api.getUser(email).enqueue(new Callback<Customer>() {
                            @Override
                            public void onResponse(Call<Customer> call, Response<Customer> response) {
                                Customer customer = response.body();
                                if(customer != null){
                                    Gson gson = new Gson();
                                    String user = gson.toJson(customer);
                                    saveRegisterState();
                                    saveUsername(user);
                                    finish();
                                    isSignupSuccess = true;
                                }
                            }

                            @Override
                            public void onFailure(Call<Customer> call, Throwable t) {
                                Toast.makeText(SignupActivity.this, "Lấy thông tin khách hàng khong thành công", Toast.LENGTH_SHORT).show();
                            }
                        });

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
    }
}