package com.example.junyeop_imaciislab.moneyball.Moneyball;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.plus.Plus;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

public class LoginActivity extends Activity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, View.OnClickListener{
    Button btnSignup;
    Button btnLogin;
    LoginButton btnFb;
    SignInButton btnGoo;
    Button btnTwit;
    EditText idEditText;
    EditText pwEditText;

    CallbackManager callbackManager; // Facebook Login Control

    private String mFacebookAccessToken;
    private String id;
    private String pw;
    private static Twitter twitter;
    private static RequestToken requestToken;

    private SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    boolean mIntentInProgress;

    private static final int GOOGLE_SIGN_IN = 0;
    private static final int FB_SIGN_IN = 64206;
    private static final int TWITTER_SIGN_IN = 20;


    /**
     *
     * KIND OF LOGIN METHOD
     *
     * */
    private static final int KINDOFSNS_MONEYBALL = 0;
    private static final int KINDOFSNS_FACEBOOK = 1;
    private static final int KINDOFSNS_GOOGLE = 2;
    private static final int KINDOFSNS_TWITTER = 3;

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
        final String password = sharedPreferences.getString("password", null);
        if (!"".equalsIgnoreCase(username) && username != null && password != null ) { // Auto login
            id = username;
            pw = password;
            int kindofSNS = sharedPreferences.getBoolean("isfacebook", false) ? KINDOFSNS_FACEBOOK : sharedPreferences.getBoolean("isgoogle",false) ? KINDOFSNS_GOOGLE : sharedPreferences.getBoolean("istwitter",false) ? KINDOFSNS_TWITTER : KINDOFSNS_MONEYBALL;
            String query;
            query = getString(R.string.moneyball_server_url) + "/user/login?id=" + id + "&pw=" + pw + "&kindOfSNS=" + String.valueOf(kindofSNS);
            new LoginTask().execute(query);
        } else {
            setContentView(com.example.junyeop_imaciislab.moneyball.R.layout.activity_login);
            btnSignup = (Button) findViewById(R.id.btn_signup);
            btnLogin = (Button) findViewById(R.id.btn_login);
            btnFb = (LoginButton) findViewById(R.id.btn_fb);
            btnGoo = (SignInButton) findViewById(R.id.btn_goo);
            btnTwit = (Button) findViewById(R.id.btn_twit);
            idEditText = (EditText) findViewById(R.id.id_edit);
            pwEditText = (EditText) findViewById(R.id.pw_edit);


            DisplayMetrics dm = getResources().getDisplayMetrics();
            int size = Math.round(22 * dm.density);
            btnFb.setBackgroundResource(R.drawable.facebook_login);
            btnFb.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
            btnFb.setCompoundDrawablePadding(0);
            btnFb.setPadding(0, size, 0, size);
            btnFb.setText("");
            btnFb.setTextSize(0);
            btnFb.invalidate();

            ((TextView)btnGoo.getChildAt(0)).setText("");
            ((TextView)btnGoo.getChildAt(0)).setBackgroundResource(R.drawable.google_login);
            btnGoo.setOnClickListener(this);

            mIntentInProgress = false;

            Log.d("Facebook connection", "Button Set");

            btnSignup.setOnClickListener(new View.OnClickListener() {
                                             public void onClick(View v) {
                                                 Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                                                 startActivity(intent);
                                                 overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
                                             }
                                         }
            );

            btnLogin.setOnClickListener(new View.OnClickListener() {
                                            public void onClick(View v) {
                                                //id , password check후 true시, main으로 이동
                                                id = idEditText.getText().toString();
                                                pw = pwEditText.getText().toString();
                                                String query;
                                                query = getString(R.string.moneyball_server_url) + "/user/login?id=" + id + "&pw=" + pw + "&kindOfSNS=" + String.valueOf(KINDOFSNS_MONEYBALL);
                                                new LoginTask().execute(query);
                                                editor = sharedPreferences.edit();
                                                editor.putString("password", pw);
                                                editor.commit();
                                            }
                                        }
            );

            // Callback registration Facebook Login
            btnFb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (com.facebook.AccessToken.getCurrentAccessToken() == null) {
                        onFblogin();
                    }
                }
            });


            btnTwit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!isTwitterLoggedInAlready()) {
                        Thread twitterloginThread = new Thread() {
                            public void run() {
                                try {
                                    loginToTwitter();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        };
                        twitterloginThread.start();
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
                                                String str_id = json.getString("id");
                                                if(sharedPreferences.getString("username",null)==null && sharedPreferences.getString("password",null)==null) {
                                                    id = str_id;
                                                    pw = "facebook";
                                                    String query;
                                                    query = getString(R.string.moneyball_server_url) + "/user/login?id=" + id + "&pw=" + pw + "&kindOfSNS=" + String.valueOf(KINDOFSNS_FACEBOOK);
                                                    new LoginTask().execute(query);
                                                    editor.putBoolean("isfacebook", true);
                                                    editor.putString("password", pw);
                                                    editor.commit();
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
                        Log.d("Facebook connection", error.toString());
                    }
                });
    }


    /**
     *
     * For Google+ login
     *
     * */
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

        if(sharedPreferences.getString("username", null)==null) {
            id = accountName;
            pw = "google";
            String query;
            query = getString(R.string.moneyball_server_url) + "/user/login?id=" + id + "&pw=" + pw + "&kindOfSNS=" + String.valueOf(KINDOFSNS_GOOGLE);
            new LoginTask().execute(query);
            editor.putBoolean("isgoogle", true); editor.putString("password",pw); editor.commit();
        }
        Log.d("Google connection",accountName);
    }
    @Override
    public void onConnectionSuspended(int cause) {
        mGoogleApiClient.connect();
    }


    /**
     *
     * For Twitter login
     *
     * */
    private boolean isTwitterLoggedInAlready() {
        sharedPreferences = getSharedPreferences("login_info", MODE_PRIVATE);
        return sharedPreferences.getString("username", null) != null;
    }

    private void loginToTwitter() {
        // Check if already logged in
        if (!isTwitterLoggedInAlready()) {
            ConfigurationBuilder builder = new ConfigurationBuilder();

            builder.setOAuthConsumerKey(getString(R.string.twitter_consumer_key));
            builder.setOAuthConsumerSecret(getString(R.string.twitter_consumer_secret));
            Configuration configuration = builder.build();

            TwitterFactory factory = new TwitterFactory(configuration);
            twitter = factory.getInstance();

            try {
                requestToken = twitter.getOAuthRequestToken(getString(R.string.twitter_callback_url));

                Intent i = new Intent(LoginActivity.this, Twitter_WebViewActivity.class);
                i.putExtra("URL", requestToken.getAuthenticationURL());
                startActivityForResult(i, 20);

            } catch (TwitterException e) {
                e.printStackTrace();
            }
        } else {
            // user already logged into twitter
            //Toast.makeText(getApplicationContext(),"∑Œ±◊¿Œ ¡ﬂ ¿‘¥œ¥Ÿ.", Toast.LENGTH_LONG).show();
        }
    }


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
        else if (requestCode == TWITTER_SIGN_IN)
        {
            final String oauthVerifier = (String) data.getExtras().get(getString(R.string.twitter_oauth_verifier));
            Thread twitterSetReqThread = new Thread(){
                public void run(){
                    try
                    {
                        twitter4j.auth.AccessToken at = null;
                        at = twitter.getOAuthAccessToken(oauthVerifier);
                        editor = sharedPreferences.edit();
                        //editor.putString(getString(R.string.twitter_pref_key_oauth_token), at.getToken());
                        //editor.putString(getString(R.string.twitter_pref_key_oauth_secret), at.getTokenSecret());
                        final long userID = at.getUserId();
                        User user = twitter.showUser(userID);

                        Log.i("twitter_userId", String.valueOf(userID));

                        final String username = user.getName();

                        Log.i("twitter_userName", username);
                        //user_name.setText(username);
                        //editor.putString(getString(R.string.twitter_username), user.getName());

                        id = String.valueOf(userID);
                        pw = "twitter";
                        String query;
                        query = getString(R.string.moneyball_server_url) + "/user/login?id=" + id + "&pw=" + pw + "&kindOfSNS=" + String.valueOf(KINDOFSNS_TWITTER);
                        new LoginTask().execute(query);
                        editor.putBoolean("istwitter", true); editor.putString("password",pw);
                        editor.commit();
                    } catch(Exception e){
                        e.printStackTrace();
                    }
                }
            };
            twitterSetReqThread.start();

        }
    }

    /**
     *
     * For Moneyball login
     *
     * */
    private class LoginTask extends AsyncTask<String, Void, HttpResponse> {
        @Override
        protected HttpResponse doInBackground(String... urls) {
            HttpResponse response = null;
            HttpClient client = new DefaultHttpClient();
            HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000);
            HttpGet httpGet = new HttpGet(urls[0]);
            try {
                response = client.execute(httpGet);
            }
            catch(ClientProtocolException e){
                e.printStackTrace();
            }
            catch(IOException e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(HttpResponse response) {
            //super.onPostExecute(result);
            try{
                StatusLine statusLine = response.getStatusLine();
                if(statusLine.getStatusCode() == HttpStatus.SC_OK){
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    response.getEntity().writeTo(out);
                    String responseString = out.toString();
                    out.close();
                    JSONTokener tokener = new JSONTokener(responseString);
                    JSONObject finalResult = (JSONObject)tokener.nextValue();
                    if(finalResult.getBoolean("success")==true) {
                        editor= sharedPreferences.edit();
                        JSONObject dataObject = (JSONObject)finalResult.get("data");
                        JSONArray listObject = (JSONArray)dataObject.get("list");
                        String userid = ((JSONObject)listObject.get(0)).getString("id");
                        int userNum = ((JSONObject)listObject.get(0)).getInt("userNum");
                        editor.putString("username", userid);
                        editor.putInt("userNum",userNum);
                        editor.commit();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // LOGIN FAIL
                        Toast.makeText(LoginActivity.this,"LOGIN FAILED",Toast.LENGTH_SHORT);
                        Log.d("Moneyball login failed",finalResult.getString("errorMessage"));
                    }

                } else{
                    //Closes the connection.
                    response.getEntity().getContent().close();
                    throw new IOException(statusLine.getReasonPhrase());
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}