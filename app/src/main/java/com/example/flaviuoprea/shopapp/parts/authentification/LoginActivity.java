package com.example.flaviuoprea.shopapp.parts.authentification;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.example.flaviuoprea.shopapp.R;

public class LoginActivity extends AppCompatActivity implements AuthentificationNavigation{


    private ViewPager mViewPager;
    private AuthentificationPageAdapter mAuthenticationPageAdapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mViewPager = findViewById(R.id.authentication_view_pager);

        mAuthenticationPageAdapter = new AuthentificationPageAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mAuthenticationPageAdapter);


    }

    @Override
    public void goToRegister() {
        mViewPager.setCurrentItem(AuthentificationPageAdapter.REGISTER_FRAGMENT);
    }

    @Override
    public void goBack() {
        mViewPager.setCurrentItem(AuthentificationPageAdapter.LOGIN_FRAGMENT);
    }

    public Context getLoginActivityContext(){
        return this;
    }
}
