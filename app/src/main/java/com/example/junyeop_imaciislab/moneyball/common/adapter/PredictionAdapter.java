package com.example.junyeop_imaciislab.moneyball.common.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Handler;
import android.text.Html;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.junyeop_imaciislab.moneyball.Moneyball.SFSSLSocketFactory;
import com.example.junyeop_imaciislab.moneyball.R;
import com.example.junyeop_imaciislab.moneyball.common.view.CalculatorItemWrapper;
import com.example.junyeop_imaciislab.moneyball.common.view.MatchupPrediction;

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
import java.util.ArrayList;

/**
 * Created by junyeop_imaciislab on 2015. 5. 22..
 */
public class PredictionAdapter extends ArrayAdapter<MatchupPrediction> {
    private final Activity context;

    public ArrayList<MatchupPrediction> getMatchupPredictionsLists() {
        return matchupPredictionsLists;
    }

    private ArrayList<MatchupPrediction> matchupPredictionsLists;

    public PredictionAdapter(Activity context, ArrayList<MatchupPrediction> matchupPredictionsLists) {
        super(context, R.layout.score_prediction_item, matchupPredictionsLists);
        this.context = context;
        this.matchupPredictionsLists = matchupPredictionsLists;
    }


    @Override
    public View getView(final int position, View view, final ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.score_prediction_item, null, true);

        TextView tvStadium = (TextView) rowView.findViewById(R.id.tv_stadium);
        TextView tvTime = (TextView) rowView.findViewById(R.id.tv_match_time);
        ImageView imgTeam1 = (ImageView) rowView.findViewById(R.id.team1_logo);
        ImageView imgTeam2 = (ImageView) rowView.findViewById(R.id.team2_logo);
        TextView tvResult1 = (TextView) rowView.findViewById(R.id.tv_1result);
        TextView tvResult2 = (TextView) rowView.findViewById(R.id.tv_2result);
        Button btnResult3 = (Button) rowView.findViewById(R.id.btn_3result);
        Button btnResult4 = (Button) rowView.findViewById(R.id.btn_4result);
        Button btnResult5 = (Button) rowView.findViewById(R.id.btn_5result);
        Button btnProb1 = (Button) rowView.findViewById(R.id.btn_1prob);
        Button btnProb2 = (Button) rowView.findViewById(R.id.btn_2prob);
        Button btnProb3 = (Button) rowView.findViewById(R.id.btn_3prob);
        Button btnProb4 = (Button) rowView.findViewById(R.id.btn_4prob);
        Button btnProb5 = (Button) rowView.findViewById(R.id.btn_5prob);
        final Button PlusBt = (Button) rowView.findViewById(R.id.btn_wish_plus);
        ((TextView)rowView.findViewById(R.id.item_index)).setText(String.valueOf(position));
        MatchupPrediction tempObj = matchupPredictionsLists.get(position);
        tvStadium.setText(tempObj.getStadium());
        tvTime.setText(tempObj.getTime());
        
        switch (tempObj.getTeam1()) {
            case "Samsung":
                imgTeam1.setImageResource(R.drawable.samsung_logo);
                break;
            case "Lotte":
                imgTeam1.setImageResource(R.drawable.lotte_logo);
                break;
            case "NC":
                imgTeam1.setImageResource(R.drawable.nc_logo);
                break;
            case "KIA":
                imgTeam1.setImageResource(R.drawable.kia_logo);
                break;
            case "Hanwha":
                imgTeam1.setImageResource(R.drawable.hanwha_logo);
                break;
            case "LG":
                imgTeam1.setImageResource(R.drawable.lg_logo);
                break;
            case "Doosan":
                imgTeam1.setImageResource(R.drawable.doosan_logo);
                break;
            case "Nexen":
                imgTeam1.setImageResource(R.drawable.nexen_logo);
                break;
            case "SK":
                imgTeam1.setImageResource(R.drawable.sk_logo);
                break;
            case "KT":
                imgTeam1.setImageResource(R.drawable.kt_logo);
                break;
        }
        switch (tempObj.getTeam2()) {
            case "Samsung":
                imgTeam2.setImageResource(R.drawable.samsung_logo);
                break;
            case "Lotte":
                imgTeam2.setImageResource(R.drawable.lotte_logo);
                break;
            case "NC":
                imgTeam2.setImageResource(R.drawable.nc_logo);
                break;
            case "KIA":
                imgTeam2.setImageResource(R.drawable.kia_logo);
                break;
            case "Hanwha":
                imgTeam2.setImageResource(R.drawable.hanwha_logo);
                break;
            case "LG":
                imgTeam2.setImageResource(R.drawable.lg_logo);
                break;
            case "Doosan":
                imgTeam2.setImageResource(R.drawable.doosan_logo);
                break;
            case "Nexen":
                imgTeam2.setImageResource(R.drawable.nexen_logo);
                break;
            case "SK":
                imgTeam2.setImageResource(R.drawable.sk_logo);
                break;
            case "KT":
                imgTeam2.setImageResource(R.drawable.kt_logo);
                break;
        }

        tvResult1.setText(tempObj.getResults()[0]);
        tvResult2.setText(tempObj.getResults()[1]);
        //// LOCK

        btnProb1.setText(tempObj.getProb()[0]);
        btnProb2.setText(tempObj.getProb()[1]);
        btnProb3.setText(tempObj.getProb()[2]);
        btnProb4.setText(tempObj.getProb()[3]);
        btnProb5.setText(tempObj.getProb()[4]);


        final AlertDialog.Builder ab = new AlertDialog.Builder(getContext());
        ab.setMessage(Html.fromHtml("Moneyball 500원이 차감됩니다.<br/> 구매하시겠습니까?"));

        ab.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getContext(), "Unlock Canceled", Toast.LENGTH_SHORT).show();
            }
        });
        ab.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getContext(), "Unlock Completed", Toast.LENGTH_SHORT).show();
            }
        });

        if(tempObj.getResults()[2].compareTo("0")==0) {
            btnResult3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ab.show();
                }
            });
        } else {
            btnResult3.setText(tempObj.getResults()[2]);
            btnResult3.setBackgroundColor(Color.parseColor("#DCDCDC"));
            btnResult3.setTextColor(Color.BLACK);
            btnResult3.setTypeface(Typeface.DEFAULT_BOLD);
            btnResult3.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
        }

        if(tempObj.getResults()[3].compareTo("0")==0) {
            btnResult4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ab.show();
                }
            });
        } else {
            btnResult4.setText(tempObj.getResults()[3]);
            btnResult4.setBackgroundColor(Color.parseColor("#DCDCDC"));
            btnResult4.setTextColor(Color.BLACK);
            btnResult4.setTypeface(Typeface.DEFAULT_BOLD);
            btnResult4.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
        }

        if(tempObj.getResults()[4].compareTo("0")==0) {
            btnResult5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ab.show();
                }
            });
        } else {
            btnResult5.setText(tempObj.getResults()[4]);
            btnResult5.setBackgroundColor(Color.parseColor("#DCDCDC"));
            btnResult5.setTextColor(Color.BLACK);
            btnResult5.setTypeface(Typeface.DEFAULT_BOLD);
            btnResult5.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
        }

        if(new CalculatorItemWrapper().getCalculatorItem().indexOf(matchupPredictionsLists.get(position)) != -1) {
            PlusBt.setBackground(((Activity) getContext()).getResources().getDrawable(R.drawable.wish_plus_checked));
        }
        //if(new CalculatorItemWrapper().getCalculatorItem().indexOf(matchupPredictionsLists.get(position)) == -1) {
            PlusBt.setOnClickListener(new View.OnClickListener() {
                boolean lock = false;
                @Override
                public void onClick(View v) {
                    ////
                    if(new CalculatorItemWrapper().getCalculatorItem().indexOf(matchupPredictionsLists.get(position)) == -1) {
                        if(lock) return;
                        lock = true;
                        CalculatorItemWrapper calculatorItemWrapper = new CalculatorItemWrapper();
                        ArrayList<MatchupPrediction> calculatorItemArrayList;
                        calculatorItemArrayList = calculatorItemWrapper.getCalculatorItem();
                        calculatorItemArrayList.add(matchupPredictionsLists.get(position));
                        calculatorItemWrapper.setCalculatorItem(calculatorItemArrayList);

                        ListView calculatorList;
                        calculatorList = (ListView) ((Activity) getContext()).findViewById(R.id.calculator_list);
                        CalculatorAdapter calculatorAdapter = new CalculatorAdapter((Activity) getContext(), calculatorItemArrayList);
                        calculatorAdapter.notifyDataSetChanged();
                        calculatorList.setAdapter(calculatorAdapter);
                        PlusBt.setBackground(((Activity) getContext()).getResources().getDrawable(R.drawable.wish_plus_checked));
                        Toast.makeText(getContext(), "Match is added to Calculator", Toast.LENGTH_SHORT).show();
                        lock = false;
                    } else {
                        if(lock) return;
                        lock = true;
                        CalculatorItemWrapper calculatorItemWrapper = new CalculatorItemWrapper();
                        ArrayList<MatchupPrediction> calculatorItemArrayList;
                        calculatorItemArrayList = calculatorItemWrapper.getCalculatorItem();
                        calculatorItemArrayList.remove(matchupPredictionsLists.get(position));
                        calculatorItemWrapper.setCalculatorItem(calculatorItemArrayList);

                        ListView calculatorList;
                        calculatorList = (ListView) ((Activity) getContext()).findViewById(R.id.calculator_list);
                        CalculatorAdapter calculatorAdapter = new CalculatorAdapter((Activity) getContext(), calculatorItemArrayList);
                        calculatorAdapter.notifyDataSetChanged();
                        calculatorList.setAdapter(calculatorAdapter);

                        PlusBt.setBackground(((Activity) getContext()).getResources().getDrawable(R.drawable.wish_plus));
                        Toast.makeText(getContext(), "Match is removed from Calculator", Toast.LENGTH_SHORT).show();
                        lock = false;
                    }
                }
            });
        //} else {
        //    PlusBt.setBackground(((Activity) getContext()).getResources().getDrawable(R.drawable.wish_plus_checked));
        //}
        return rowView;
    }

    private class UnLock extends AsyncTask<String, Void, HttpResponse> {
        private Handler mHandler;
        private ProgressDialog dialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mHandler = new Handler();
            dialog = new ProgressDialog(getContext());
            dialog.setMessage("잠시만 기다려 주세요.");
            dialog.setCancelable(false);
            dialog.show();
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(dialog != null && dialog.isShowing()){
                        dialog.dismiss();
                    }
                }
            },5000);
        }

        @Override
        protected HttpResponse doInBackground(String... urls) {
            HttpResponse response = null;
            HttpClient client = getHttpClient();
            //HttpClient client = new DefaultHttpClient();
            HttpConnectionParams.setConnectionTimeout(client.getParams(), 5000);
            HttpGet httpGet = new HttpGet(urls[0]);

            final AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
            alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            try {
                response = client.execute(httpGet);
            }
            catch(ClientProtocolException e){
                e.printStackTrace();
            }
            catch(IOException e) {
                e.printStackTrace();
                alert.setMessage("[로그인 실패]네트워크 연결이 불안정 합니다");
                ((Activity)getContext()).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        alert.show();
                }
                });
            }

            //////
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

                        JSONObject dataObject = (JSONObject)finalResult.get("data");
                        String userid = dataObject.getString("id");
                        int moneyballNow = dataObject.getInt("money");
                        int userNum = dataObject.getInt("userNum");
                    } else {
                        alert.setMessage("아이디 혹은 비밀번호가 틀렸습니다");
                        ((Activity)getContext()).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                alert.show();
                            }
                        });
                    }

                } else{
                    //Closes the connection.
                    alert.setMessage("[로그인 실패]네트워크 연결이 불안정 합니다");
                    ((Activity)getContext()).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            alert.show();
                        }
                    });
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
            if(dialog != null && dialog.isShowing()){
                dialog.dismiss();
            }
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