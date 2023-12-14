package huce.nhom15.mobileapp.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import huce.nhom15.mobileapp.R;
import huce.nhom15.mobileapp.view.activity.LoginActivity;
import huce.nhom15.mobileapp.view.activity.SignupActivity;

public class UserFragment extends Fragment {

    private Button btnLogin, btnSignup;


    public UserFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ConstraintLayout view = (ConstraintLayout) inflater.inflate(R.layout.fragment_user, container, false);
        btnLogin = view.findViewById(R.id.btnLogin);
        btnSignup = view.findViewById(R.id.btnSignup);

        if (isLoggedIn() || isSignup()) {
            // Nếu đã đăng nhập, chuyển đến UserProfileFragment
            navigateToUserProfile();
        } else {
            // Nếu chưa đăng nhập, hiển thị giao diện UserFragment
            onClickBtnLogin();
            onClickBtnSignup();
        }

        return view;
    }

    public void onClickBtnLogin() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Gọi phương thức navigateToUserProfile của UserFragment
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    public void onClickBtnSignup() {
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Gọi phương thức navigateToUserProfile của UserFragment
                Intent intent = new Intent(getActivity(), SignupActivity.class);
                startActivity(intent);
            }
        });
    }

    private boolean isLoggedIn() {
        SharedPreferences prefs = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        return prefs.getBoolean("isLoggedIn", false);
    }
    private boolean isSignup() {
        SharedPreferences prefs = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        return prefs.getBoolean("isSignup", false);
    }

    private void navigateToUserProfile() {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.blankaccount, new DetailUserFragment(), "UserFragment")
                .addToBackStack("UserFragment")
                .commit();
        btnLogin.setEnabled(false);
        btnSignup.setEnabled(false);
    }

    public void onResume() {
        super.onResume();
        onClickBtnLogin();
        onClickBtnSignup();
    }

}