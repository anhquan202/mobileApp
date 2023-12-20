package huce.nhom15.mobileapp.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import huce.nhom15.mobileapp.R;
import huce.nhom15.mobileapp.view.activity.LoginActivity;
import huce.nhom15.mobileapp.view.activity.SignupActivity;

public class UserFragment extends Fragment {

    private Button btnLogin, btnSignup;
    private Boolean isUpdateOrder = false;


    public UserFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ConstraintLayout view = (ConstraintLayout) inflater.inflate(R.layout.fragment_user, container, false);
        btnLogin = view.findViewById(R.id.btnLogin);
        btnSignup = view.findViewById(R.id.btnSignup);

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                requireActivity().finish();
            }
        });
        navigateToUserProfile();
        return view;
    }

    public void onClickBtnLogin() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isUpdateOrder = true;
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
                isUpdateOrder = true;
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
        if (isLoggedIn()|| isSignup()){
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.blankaccount, new DetailUserFragment())
                    .addToBackStack(null)
                    .commit();
            btnLogin.setEnabled(false);
            btnSignup.setEnabled(false);
        }
        else {
            onClickBtnLogin();
            onClickBtnSignup();
        }
    }

    public void onResume() {
        super.onResume();
        navigateToUserProfile();
        updateOrderFragment();
    }

    private void updateOrderFragment() {
        if(isUpdateOrder){
            OrderFragment.evenShowOrder();
            isUpdateOrder = false;
        }
    }

}