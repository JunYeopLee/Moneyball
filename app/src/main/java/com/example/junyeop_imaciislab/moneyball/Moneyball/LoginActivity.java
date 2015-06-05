package com.example.junyeop_imaciislab.moneyball.Moneyball;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.content.IntentSender.SendIntentException;

import com.example.junyeop_imaciislab.moneyball.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.plus.Plus;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class LoginActivity extends ActionBarActivity  implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, View.OnClickListener{
    Button btnSignin;
    Button btnLogin;

    LoginButton btnFb;
    CallbackManager callbackManager; // Facebook Login Control
    private String mFacebookAccessToken;

    private SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    SignInButton btnGoo;

    Button btnTwit;

    boolean mIntentInProgress;

    private static final int GOOGLE_SIGN_IN = 0;
    private static final int FB_SIGN_IN = 64206;

    /* Client used to interact with Google APIs. */
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FacebookSdk.sdkInitialize(this); // initialize facebook sdk

        super.onCreate(savedInstanceState);

        mGoogleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(Plus.API).addScope(new Scope("profile")).build(); // Initailize google sdk

        mFacebookAccessToken="";
        sharedPreferences = getSharedPreferences("login_info", MODE_PRIVATE);
        final String username = sharedPreferences.getString("username", null);

        if (!"".equalsIgnoreCase(username) && username != null) { // Auto login
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            setContentView(com.example.junyeop_imaciislab.moneyball.R.layout.activity_login);
            btnSignin = (Button) findViewById(R.id.btn_signin);
            btnLogin = (Button) findViewById(R.id.btn_login);
            btnFb = (LoginButton) findViewById(R.id.btn_fb);
            btnGoo = (SignInButton) findViewById(R.id.btn_goo);
            btnTwit = (Button) findViewById(R.id.btn_twit);

            btnGoo.setOnClickListener(this); mIntentInProgress = false;

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
                    if(AccessToken.getCurrentAccessToken()==null) {
                        onFblogin();
                    }
                }
            });
        }
    }

    /**
     *
     *
     *  For Facebook Login
     *
     * */
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
                                                Log.d("Facebook connect", mFacebookAccessToken);
                                                if(sharedPreferences.getString("username",null)==null) {
                                                    editor.putString("username", str_id);
                                                    editor.putBoolean("isfacebook", true);
                                                    editor.commit();
                                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                                    startActivity(intent);
                                                    finish();
                                                }
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



    /**
     *
     * For Google+ login
     *
     * */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GOOGLE_SIGN_IN) {
            Log.d("Google connection","Request code " + String.valueOf(requestCode));
            mIntentInProgress = false;
            if (!mGoogleApiClient.isConnected()) {
                mGoogleApiClient.reconnect();
            }
        }
        else if(requestCode == FB_SIGN_IN){
            Log.d("Facebook connection","Request code " + String.valueOf(requestCode));
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
    }
    @Override
    protected void onStop() {
        super.onStop();
        //mGoogleApiClient.disconnect();
    }
    @Override
    public void onClick(View v) {
        mGoogleApiClient.connect();
    }
    @Override
    public void onConnectionFailed(ConnectionResult result) {
        if (!mIntentInProgress && result.hasResolution()) {
            try {
                mIntentInProgress = true;
                result.startResolutionForResult(this, GOOGLE_SIGN_IN);
            } catch (SendIntentException e) {
                // The intent was canceled before it was sent.  Return to the default
                // state and attempt to connect to get an updated ConnectionResult.
                mIntentInProgress = false;
                mGoogleApiClient.connect();
            }
        }
    }
    @Override
    public void onConnected(Bundle connectionHint) {
        // We've resolved any connection errors.  mGoogleApiClient can be used to
        // access Google APIs on behalf of the user.
        Log.d("Google connection","hhhh");
        editor= sharedPreferences.edit();
        String accountName = Plus.AccountApi.getAccountName(mGoogleApiClient);
        if(sharedPreferences.getString("username",null)==null) {
            editor.putString("username", accountName);
            editor.putBoolean("isgoogle", true);
            editor.commit();
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        Log.d("Google connection",accountName);
    }
    @Override
    public void onConnectionSuspended(int cause) {
        mGoogleApiClient.connect();
    }
}