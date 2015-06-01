package com.example.junyeop_imaciislab.moneyball.Moneyball;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.junyeop_imaciislab.moneyball.R;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class LoginActivity extends ActionBarActivity {
    Button btnSignin;
    Button btnLogin;

    LoginButton btnFb;
    CallbackManager callbackManager; // Facebook Login Control
    private String mFacebookAccessToken;

    private SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    Button btnGoo;

    Button btnTwit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FacebookSdk.sdkInitialize(this); // initialize facebook sdk
        super.onCreate(savedInstanceState);

        sharedPreferences = getSharedPreferences("login_info", MODE_PRIVATE);
        final String username = sharedPreferences.getString("username", null);

        if (!"".equalsIgnoreCase(username) && username != null) { // Auto login
            Log.d("Facebook prefer", username);
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            setContentView(R.layout.activity_login);
            btnSignin = (Button) findViewById(R.id.btn_signin);
            btnLogin = (Button) findViewById(R.id.btn_login);
            btnFb = (LoginButton) findViewById(R.id.btn_fb);
            btnGoo = (Button) findViewById(R.id.btn_goo);
            btnTwit = (Button) findViewById(R.id.btn_twit);

            Log.d("Facebook connection", "Button Set");
            btnSignin.setOnClickListener(new View.OnClickListener() {
                                             public void onClick(View v) {
                                                 Intent intent = new Intent(LoginActivity.this, SigninActivity.class);
                                                 startActivity(intent);
                                             }
                                         }
            );

            btnLogin.setOnClickListener(new View.OnClickListener() {
                                            public void onClick(View v) {
                                                //id , password check후 true시, main으로 이동
                                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                        }
            );
            // Callback registration Facebook Login
            btnFb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onFblogin();
                    return;
                }
            });
        }
    }


    private void onFblogin()
    {
        callbackManager = CallbackManager.Factory.create();
        editor= sharedPreferences.edit();

        // Set permissions
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email", "user_photos", "public_profile"));
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.d("Facebook connect", "Successssss");
                        mFacebookAccessToken = loginResult.getAccessToken().toString();
                        GraphRequest.newMeRequest( loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(JSONObject json, GraphResponse response) {
                                        if (response.getError() != null) {
                                            // handle error
                                            System.out.println("ERROR");
                                        } else {
                                            System.out.println("Success");
                                            try {
                                                String jsonresult = String.valueOf(json);
                                                System.out.println("JSON Result" + jsonresult);

                                                String str_email = json.getString("email");
                                                String str_id = json.getString("id");
                                                String str_firstname = json.getString("first_name");
                                                String str_lastname = json.getString("last_name");
                                                Log.d("Facebook connect", str_email);
                                                Log.d("Facebook connect", str_firstname);
                                                Log.d("Facebook connect", str_lastname);
                                                Log.d("Facebook connect", str_id);
                                                Log.d("Facebook connect",mFacebookAccessToken);

                                                editor.putString("username",str_id);
                                                editor.commit();

                                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                                startActivity(intent);
                                                finish();

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }

                                }).executeAsync();
                    }

                    @Override
                    public void onCancel() {
                        Log.d("Facebook connection", "On cancel");
                    }

                    @Override
                    public void onError(FacebookException error) {
                        Log.d("Facebook connection",error.toString());
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

}