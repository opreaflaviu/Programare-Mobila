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

public class RegisterFragment extends BaseFragment {

    AppCompatEditText mUserNameInput;
    AppCompatEditText mPasswordInput1;
    AppCompatEditText mPasswordInput2;
    AppCompatButton mRegisterButton;
    AppCompatButton mBackButton;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUI();
        onRegisterButtonClick();
        onBackButtonClick();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private void setUI() {
        mUserNameInput = getActivity().findViewById(R.id.register_user_name);
        mPasswordInput1 = getActivity().findViewById(R.id.register_password1);
        mPasswordInput2 = getActivity().findViewById(R.id.register_password2);
        mRegisterButton = getActivity().findViewById(R.id.register_register_button);
        mBackButton = getActivity().findViewById(R.id.register_back_button);
    }

    void onRegisterButtonClick() {
        mRegisterButton.setOnClickListener(v -> register());
    }

    void register() {
        String username = mUserNameInput.getText().toString();
        String password1 = mPasswordInput1.getText().toString();
        String password2 = mPasswordInput2.getText().toString();

        if (!TextUtils.isEmpty(username)) {
            if (!TextUtils.isEmpty(password1)) {
                if (!TextUtils.isEmpty(password2)) {
                    if (password1.equals(password2)) {
                        Toast.makeText(getContext(), "Username registered", Toast.LENGTH_SHORT).show();
                        writeSharedPref(username, password1);
                        Intent intent = new Intent(getActivity(), ProductsActivity.class);
                        RegisterFragment.this.startActivity(intent);


                    } else {
                        Toast.makeText(getContext(), "Different passwords", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Password empty", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getContext(), "Password empty", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getContext(), "Username empty", Toast.LENGTH_SHORT).show();
        }
        removeText();
    }

    void onBackButtonClick() {
        mBackButton.setOnClickListener(v -> {
            ((AuthentificationNavigation) getActivity()).goBack();
            removeText();
        });
    }

    private void removeText() {
        mUserNameInput.setText("");
        mPasswordInput1.setText("");
        mPasswordInput2.setText("");
    }

    private void writeSharedPref(String username, String password) {
        SharedPreferences sharedPreferencesUser = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferencesUser.edit();
        editor.putString(getString(R.string.saved_username), username);
        editor.putString(getString(R.string.saved_password), password);
        editor.commit();

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_register;
    }
}
