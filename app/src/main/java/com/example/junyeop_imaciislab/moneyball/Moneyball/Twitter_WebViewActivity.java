package com.example.junyeop_imaciislab.moneyball.Moneyball;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.junyeop_imaciislab.moneyball.R;
import com.example.junyeop_imaciislab.moneyball.common.utill.Constants;

import twitter4j.Twitter;
import twitter4j.auth.RequestToken;

public class Twitter_WebViewActivity extends Activity {
    SharedPreferences mSharedPreferences;
    private Intent mIntent;
    private static Twitter twitter;
    private static RequestToken requestToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twitter__web_view);
        mIntent = getIntent();
        String url = (String)mIntent.getExtras().get("URL");
        WebView webView = (WebView) findViewById(R.id.webview);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, final String url) {
                if (url.contains(Constants.TWITTER_CALLBACK_URL)) {
                    Uri uri = Uri.parse(url);
                    String oauthVerifier = uri.getQueryParameter(Constants.URL_TWITTER_OAUTH_VERIFIER);
                    mIntent.putExtra(Constants.URL_TWITTER_OAUTH_VERIFIER, oauthVerifier);
                    setResult(RESULT_OK, mIntent);
                    finish();
                    return true;
                }

                return false;
            }
        });
        webView.loadUrl(url);
    }
}
