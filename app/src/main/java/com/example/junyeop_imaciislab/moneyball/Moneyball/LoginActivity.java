package com.example.junyeop_imaciislab.moneyball.Moneyball;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;

import com.example.junyeop_imaciislab.moneyball.R;

public class LoginActivity extends ActionBarActivity {
    Button btnSignin;
    Button btnLogin;
    Button btnFb;
    Button btnGoo;
    Button btnTwit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnSignin   = (Button) findViewById(R.id.btn_signin);
        btnLogin    = (Button) findViewById(R.id.btn_login);
        btnFb       = (Button) findViewById(R.id.btn_fb);
        btnGoo      = (Button) findViewById(R.id.btn_goo);
        btnTwit     = (Button) findViewById(R.id.btn_twit);

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
                                        }
                                    }
        );


    }


}
