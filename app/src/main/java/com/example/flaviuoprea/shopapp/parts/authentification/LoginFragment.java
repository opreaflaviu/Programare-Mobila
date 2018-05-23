package com.example.flaviuoprea.shopapp.parts.authentification;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.flaviuoprea.shopapp.R;
import com.example.flaviuoprea.shopapp.parts.fragments.BaseFragment;
import com.example.flaviuoprea.shopapp.parts.products.ProductsActivity;


/**
 * Created by flaviuoprea on 11/12/17.
 */

public class LoginFragment extends BaseFragment {
    AppCompatEditText mUserNameInput;
    AppCompatEditText mPasswordInput;
    AppCompatButton mLoginButton;
    AppCompatButton mRegisterButton;

    String saved_username;
    String saved_password;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUI();
        onLoginButtonClick();
        onRegisterButtonClick();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private void setUI(){
        mUserNameInput = getActivity().findViewById(R.id.login_user_name);
        mPasswordInput = getActivity().findViewById(R.id.login_password1);
        mLoginButton = getActivity().findViewById(R.id.login_button);
        mRegisterButton = getActivity().findViewById(R.id.login_register_button);
    }


    void onLoginButtonClick(){
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    private void login(){
        String username = mUserNameInput.getText().toString();
        String password = mPasswordInput.getText().toString();
        readSharedPref();
        if (!TextUtils.isEmpty(username)){
            if (!TextUtils.isEmpty(password)){
                if (saved_username.equals(username) && saved_password.equals(password)) {
                    Intent intent = new Intent(getActivity(), ProductsActivity.class);
                    LoginFragment.this.startActivity(intent);
                } else {
                    Toast.makeText(getContext(), "Unregistered user", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getContext(), "Password empty", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getContext(), "Username empty", Toast.LENGTH_SHORT).show();
        }
        removeText();
    }

    void onRegisterButtonClick() {
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AuthentificationNavigation) getActivity()).goToRegister();
                removeText();
            }
        });
    }

    private void removeText(){
        mUserNameInput.setText("");
        mPasswordInput.setText("");
    }

    private void readSharedPref(){
        SharedPreferences sharedPreferencesUser = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        saved_username = sharedPreferencesUser.getString(getResources().getString(R.string.saved_username),"");
        saved_password = sharedPreferencesUser.getString(getResources().getString(R.string.saved_password),"");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_login;
    }
}
