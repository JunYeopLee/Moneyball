package com.example.junyeop_imaciislab.moneyball.Moneyball;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.junyeop_imaciislab.moneyball.R;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.KeyStore;

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
                if (checkPassword(pws, pwcs) == false) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(SignupActivity.this);
                    alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    alert.setMessage("비밀번호를 잘못 입력하셨습니다");
                    alert.show();
                } else if (checkID(ids) == false) {

                } else {
                    String query;
                    query = getString(R.string.moneyball_server_url) + "/user/signUp?id=" + ids + "&pw=" + pws + "&kindOfSNS=" + String.valueOf(0);
                    new SignupTask().execute(query);
                }
            }
        });

        LinearLayout signupLayout = (LinearLayout) findViewById(R.id.signup_activity);
        signupLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
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

    private boolean checkPassword(String pw,String pwc) {
        return pw.compareTo(pwc)==0;
    }

    private boolean checkID(String id) {
        return true;
    }

    /**
     *
     * For Moneyball login
     *
     * */
    private class SignupTask extends AsyncTask<String, Void, HttpResponse> {
        @Override
        protected HttpResponse doInBackground(String... urls) {
            HttpResponse response = null;
            HttpClient client = getHttpClient();
            //HttpClient client = new DefaultHttpClient();
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
                        final AlertDialog.Builder alert = new AlertDialog.Builder(SignupActivity.this);
                        alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                                startActivity(intent);
                                overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
                                finish();
                            }
                        });
                        alert.setMessage("가입이 완료 되었습니다");

                        SignupActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                alert.show();
                            }
                        });
                    } else {
                        final AlertDialog.Builder alert = new AlertDialog.Builder(SignupActivity.this);
                        alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        alert.setMessage(finalResult.getString("errorMessage"));
                        SignupActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                alert.show();
                            }
                        });
                    }

                } else {
                    //Closes the connection.
                    response.getEntity().getContent().close();
                    throw new IOException(statusLine.getReasonPhrase());
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            return response;
        }

        @Override
        protected void onPostExecute(HttpResponse response) {
            //super.onPostExecute(result);
        }

        /**
         *
         * For HTTPS protocol
         *
         * */
        private HttpClient getHttpClient() {
            try {
                KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
                trustStore.load(null, null);

                SSLSocketFactory sf = new SFSSLSocketFactory(trustStore);
                sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

                HttpParams params = new BasicHttpParams();
                HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
                HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);

                SchemeRegistry registry = new SchemeRegistry();
                registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
                registry.register(new Scheme("https", sf, 443));

                ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);

                return new DefaultHttpClient(ccm, params);
            } catch (Exception e) {
                return new DefaultHttpClient();
            }
        }
    }
}
