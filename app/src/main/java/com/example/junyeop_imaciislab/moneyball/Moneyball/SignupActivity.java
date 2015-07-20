package com.example.junyeop_imaciislab.moneyball.Moneyball;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.junyeop_imaciislab.moneyball.R;

public class SignupActivity extends Activity {

    private EditText idEdit;
    private EditText pwEdit;
    private EditText pwcEdit;
    private Button signUpbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        idEdit = (EditText)findViewById(R.id.id_edit);
        pwEdit = (EditText)findViewById(R.id.pw_edit);
        pwcEdit = (EditText)findViewById(R.id.pwcheck_edit);
        signUpbtn = (Button)findViewById(R.id.btn_signup_insignup);
        signUpbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ids = idEdit.getText().toString();
                String pws = pwEdit.getText().toString();
                String pwcs = pwcEdit.getText().toString();
                if( pws.compareTo(pwcs) != 0 ) { // Mismatch
                    // TODO : 비밀번호체크 경고창
                } else { // Accepted
                    // TODO : 서버로 쿼리
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_signup, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
    }
}
