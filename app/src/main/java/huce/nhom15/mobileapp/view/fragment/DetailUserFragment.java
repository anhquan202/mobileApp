package huce.nhom15.mobileapp.view.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import huce.nhom15.mobileapp.R;
import huce.nhom15.mobileapp.model.Customer;
import huce.nhom15.mobileapp.view.ModelRespone.LogoutListener;

public class DetailUserFragment extends Fragment implements LogoutListener {
    public DetailUserFragment() {
    }
    private LogoutListener logoutListener;
    private TextView txtUsername;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = (View)inflater.inflate(R.layout.fragment_detail_user, container, false);
        LinearLayoutCompat linearLayoutCompat = view.findViewById(R.id.logout);
        linearLayoutCompat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 onLogout();
            }
        });
        txtUsername = view.findViewById(R.id.txtusername);
        String user = getUserName();
        Gson gson = new Gson();
        Customer customer = gson.fromJson(user, Customer.class);
        txtUsername.setText(customer.getKH_HoTen());
        // Set thông tin người dùng cho TextView
        return view;
    }

    @Override
    public void onLogout() {
        clearLoginState();
        clearRegisterState();
        if (logoutListener != null) {
            logoutListener.onLogout();
        }
        clearUsername();
        popBackStackToUserFragment();
        OrderFragment.evenShowOrder();
        Toast.makeText(requireContext(), "Logged out successfully", Toast.LENGTH_SHORT).show();
    }
    private void clearLoginState() {
        SharedPreferences.Editor editor = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE).edit();
        editor.remove("isLoggedIn");
        editor.apply();
    }

    private void popBackStackToUserFragment() {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        UserFragment userFragment = new UserFragment();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.blankaccount, userFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    private void clearRegisterState() {
        SharedPreferences.Editor editor = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE).edit();
        editor.remove("isSignup");
        editor.apply();
    }
    private void clearUsername() {
        SharedPreferences.Editor editor = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE).edit();
        editor.remove("username");
        editor.apply();
    }
    private String getUserName() {
        SharedPreferences prefs = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        return prefs.getString("username", "");
    }

}