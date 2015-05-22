package com.example.junyeop_imaciislab.moneyball.Moneyball;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.junyeop_imaciislab.moneyball.R;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

public class LoginActivity extends ActionBarActivity {
    Button btnSignin;
    Button btnLogin;

    LoginButton btnFb;
    CallbackManager callbackManager; // Facebook Login Control

    Button btnGoo;

    Button btnTwit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext()); // initialize facebook sdk


        setContentView(R.layout.activity_login);


        callbackManager = CallbackManager.Factory.create();
        btnSignin = (Button) findViewById(R.id.btn_signin);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnFb = (LoginButton) findViewById(R.id.btn_fb);
        btnGoo = (Button) findViewById(R.id.btn_goo);
        btnTwit = (Button) findViewById(R.id.btn_twit);
        android.util.Log.d("Facebook connection", "Button Set");
        btnSignin.setOnClickListener(new OnClickListener() {
                                         public void onClick(View v) {
                                             Intent intent = new Intent(LoginActivity.this, SigninActivity.class);
                                             startActivity(intent);
                                         }
                                     }
        );

        btnLogin.setOnClickListener(new OnClickListener() {
                                        public void onClick(View v) {
                                            //id , password check후 true시, main으로 이동
                                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }
        );

        // Callback registration Facebook Login
        btnFb.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                /*
                AccessToken accessToken = loginResult.getAccessToken();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                */
                android.util.Log.d("Facebook connnection", "[+]Success!!");
            }

            @Override
            public void onCancel() {
                // App code
                android.util.Log.d("Facebook connnection", "[-]Cancel!!");
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                android.util.Log.d("Facebook connnection", "[-]Error!!");
            }
        });

    }

}
