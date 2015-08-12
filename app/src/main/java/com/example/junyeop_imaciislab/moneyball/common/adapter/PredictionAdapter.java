package com.example.junyeop_imaciislab.moneyball.common.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
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

import com.example.junyeop_imaciislab.moneyball.Moneyball.MainActivity;
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
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

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

        final ArrayList<View> btnResults = new ArrayList<>();
        btnResults.add(rowView.findViewById(R.id.tv_1result));
        btnResults.add(rowView.findViewById(R.id.tv_1result));
        btnResults.add(rowView.findViewById(R.id.tv_2result));
        btnResults.add(rowView.findViewById(R.id.btn_3result));
        btnResults.add(rowView.findViewById(R.id.btn_4result));
        btnResults.add(rowView.findViewById(R.id.btn_5result));

        final ArrayList<View> btnProbs = new ArrayList<>();
        btnProbs.add(rowView.findViewById(R.id.btn_1prob));
        btnProbs.add(rowView.findViewById(R.id.btn_1prob));
        btnProbs.add(rowView.findViewById(R.id.btn_2prob));
        btnProbs.add(rowView.findViewById(R.id.btn_3prob));
        btnProbs.add(rowView.findViewById(R.id.btn_4prob));
        btnProbs.add(rowView.findViewById(R.id.btn_5prob));

        final Button PlusBt = (Button) rowView.findViewById(R.id.btn_wish_plus);
        ((TextView)rowView.findViewById(R.id.item_index)).setText(String.valueOf(position));
        final MatchupPrediction tempObj = matchupPredictionsLists.get(position);
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

        for( int i = 1 ; i <= 5 ; i++ ) {
            final TextView tmpResult = ((TextView)btnResults.get(i));
            final TextView tmpProb = ((TextView)btnProbs.get(i));
            final int index = i;
            tmpProb.setText(tempObj.getProb()[i-1]);
            if(tempObj.getResults()[i-1].compareTo("0")==0) {
                tmpResult.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder ab = new AlertDialog.Builder(getContext());
                        ab.setMessage(Html.fromHtml("Moneyball 500원이 차감됩니다.<br/> 구매하시겠습니까?"));
                        ab.setNegativeButton("cancel", new buyingOnClickListener(tempObj,tempObj.getMatchNum(), index, tmpResult));
                        ab.setPositiveButton("ok", new buyingOnClickListener(tempObj,tempObj.getMatchNum(), index, tmpResult));
                        ab.show();
                    }
                });
            } else {
                tmpResult.setText(tempObj.getResults()[i-1]);
                tmpResult.setBackgroundColor(Color.parseColor("#DCDCDC"));
                tmpResult.setTextColor(Color.BLACK);
                tmpResult.setTypeface(Typeface.DEFAULT_BOLD);
                tmpResult.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
            }
        }

        if(new CalculatorItemWrapper().getCalculatorItem().indexOf(matchupPredictionsLists.get(position)) != -1) {
            PlusBt.setBackground(((Activity) getContext()).getResources().getDrawable(R.drawable.wish_plus_checked));
        }
        PlusBt.setOnClickListener(new View.OnClickListener() {
            boolean lock = false;
            @Override
            public void onClick(View v) {
                ////
                if (new CalculatorItemWrapper().getCalculatorItem().indexOf(matchupPredictionsLists.get(position)) == -1) {
                    if (lock) return;
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
                    if (lock) return;
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
        return rowView;
    }

    private class UnLockTask extends AsyncTask<String, Void, String> {
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
        protected String doInBackground(String... urls) {
            HttpResponse response = null;
            HttpClient client = getHttpClient();
            HttpConnectionParams.setConnectionTimeout(client.getParams(), 5000);
            HttpGet httpGet = new HttpGet(urls[0]);
            String unLockResult = "0";
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
                alert.setMessage("[구매 실패]네트워크 연결이 불안정 합니다");
                ((Activity)getContext()).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        alert.show();
                }
                });
                return unLockResult;
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
                        JSONArray dataArray = (JSONArray)finalResult.getJSONArray("data");
                        int resultMoney = Integer.valueOf(dataArray.getString(0));
                        SharedPreferences sharedPreferences = getContext().getSharedPreferences("login_info", getContext().MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("money", moneyToString(resultMoney));
                        editor.commit();

                        unLockResult = dataArray.getString(1);
                    } else {
                        alert.setMessage("Moneyball이 부족합니다");
                        ((Activity)getContext()).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                alert.show();
                            }
                        });
                    }
                } else{
                    //Closes the connection.
                    alert.setMessage("[구매 실패]네트워크 연결이 불안정 합니다");
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
            return unLockResult;
        }

        @Override
        protected void onPostExecute(String result) {
            if(dialog != null && dialog.isShowing()){
                dialog.dismiss();
            }
        }


        private String moneyToString(int money) {
            StringBuilder sb = new StringBuilder(String.valueOf(money));
            final int limit = 99999;
            if(money>limit) {
                sb = new StringBuilder("99,999+");
            } else {
                for( int index = sb.length()-3 ; index > 0 ; index-=3 ) {
                    sb.insert(index,",");
                }
            }
            return sb.toString();
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

    private class buyingOnClickListener implements DialogInterface.OnClickListener {
        private MatchupPrediction matchObj;
        private int userNum;
        private int matchNum;
        private int unlockNum;
        private TextView view;
        public buyingOnClickListener(MatchupPrediction matchObj,int matchId, int unlockId, TextView view) {
            this.matchObj = matchObj;
            SharedPreferences sharedPreferences = getContext().getSharedPreferences("login_info", getContext().MODE_PRIVATE);
            this.userNum = sharedPreferences.getInt("userNum",-1);
            this.matchNum = matchId;
            this.unlockNum = unlockId;
            this.view = view;
        }

        @Override
        public void onClick(DialogInterface dialog, int which) {
            if(which==-1) { // POSITIVE
                if(userNum!=-1) {
                    Toast.makeText(getContext(), "Unlock Completed " + String.valueOf(which) + " " + userNum + " " + matchNum + " " + unlockNum + " ", Toast.LENGTH_SHORT).show();
                    String query = getContext().getString(R.string.unlock_result_query) + "userNum=" + userNum + "&matchNum=" + matchNum + "&unlockNum=" + unlockNum;
                    try {
                        String result = new UnLockTask().execute(query).get();
                        if(result.compareTo("0")!=0) {
                            view.setText(result);
                            view.setBackgroundColor(Color.parseColor("#DCDCDC"));
                            view.setTextColor(Color.BLACK);
                            view.setTypeface(Typeface.DEFAULT_BOLD);
                            view.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
                            String[] temp = matchObj.getResults();
                            temp[unlockNum-1] = result;
                            matchObj.setResults(temp);
                        }
                        SharedPreferences sharedPreferences = getContext().getSharedPreferences("login_info", getContext().MODE_PRIVATE);
                        TextView moneyballText = (TextView)((MainActivity)getContext()).findViewById(R.id.moneyball_now);
                        moneyballText.setText(sharedPreferences.getString("money","ERROR"));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                } else {
                 // Trouble Shooting
                }
            } else { // NEGATIVE
                Toast.makeText(getContext(), "Unlock Canceled" + String.valueOf(which) + " " + userNum + " " + matchNum + " " + unlockNum + " ", Toast.LENGTH_SHORT).show();
            }
        }
    }
}