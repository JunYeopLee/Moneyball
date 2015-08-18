package com.example.junyeop_imaciislab.moneyball.Moneyball;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.vending.billing.IInAppBillingService;
import com.example.junyeop_imaciislab.moneyball.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
public class BuyingMoneyballActivity extends Activity {
    IInAppBillingService mService;
    ArrayList<String> responseList;
    JSONObject[] skuResults = new JSONObject[4];
    Button[] btns = new Button[4];

    ServiceConnection mServiceConn = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService = null;
        }

        @Override
        public void onServiceConnected(ComponentName name,
                                       IBinder service) {
            mService = IInAppBillingService.Stub.asInterface(service);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buying_moneyball);
        (findViewById(R.id.buying_background)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        (findViewById(R.id.buying_background_relative)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        bindService(new Intent("com.android.vending.billing.InAppBillingService.BIND"), mServiceConn, Context.BIND_AUTO_CREATE);
        new getSkuList().execute();
        /*
        btns[0] = (Button)findViewById(R.id.buy1);
        btns[1] = (Button)findViewById(R.id.buy2);
        btns[2] = (Button)findViewById(R.id.buy3);
        btns[3] = (Button)findViewById(R.id.buy4);
        for( int i = 0 ; i < 4 ; i++ ) {
            final String sku;
            final String developerPayload;
            try {
                sku = skuResults[i].getString("productId");
                developerPayload = skuResults[i].getString("developerPayload");
                btns[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            Bundle buyIntentBundle = mService.getBuyIntent(3, getPackageName(), sku, "inapp", developerPayload);
                            PendingIntent pendingIntent = buyIntentBundle.getParcelable("BUY_INTENT");
                            startIntentSenderForResult(pendingIntent.getIntentSender(),
                                    1001, new Intent(), Integer.valueOf(0), Integer.valueOf(0),
                                    Integer.valueOf(0));
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        } catch (IntentSender.SendIntentException e) {
                            e.printStackTrace();
                        }
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        */
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_buying_moneyball, menu);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mService != null) {
            unbindService(mServiceConn);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1001) {
            int responseCode = data.getIntExtra("RESPONSE_CODE", 0);
            String purchaseData = data.getStringExtra("INAPP_PURCHASE_DATA");
            String dataSignature = data.getStringExtra("INAPP_DATA_SIGNATURE");

            if (resultCode == RESULT_OK) {
                try {
                    JSONObject jo = new JSONObject(purchaseData);
                    String sku = jo.getString("productId");
                    Toast.makeText(this,"You have bought the " + sku + ". Excellent choice, adventurer!",Toast.LENGTH_LONG);
                }
                catch (JSONException e) {
                    Toast.makeText(this, "Failed to parse purchase data.",Toast.LENGTH_LONG);
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     *
     * To get Sku List
     *
     * */
    private class getSkuList extends AsyncTask<String, Void, Integer> {
        private Handler mHandler;
        private ProgressDialog dialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mHandler = new Handler();
            dialog = new ProgressDialog(BuyingMoneyballActivity.this);
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
        protected Integer doInBackground(String... urls) {
            Integer response=0;
            ArrayList<String> skuList = new ArrayList<String> ();
            skuList.add("moneyball1");
            skuList.add("moneyball2");
            skuList.add("moneyball3");
            skuList.add("moneyball4");
            Bundle querySkus = new Bundle();
            querySkus.putStringArrayList("ITEM_ID_LIST", skuList);
            try {
                Bundle skuDetails = mService.getSkuDetails(3,getPackageName(), "inapp", querySkus);
                response = skuDetails.getInt("RESPONSE_CODE");
                int indx=0;
                if (response == 0) {
                    responseList = skuDetails.getStringArrayList("DETAILS_LIST");
                    for (String thisResponse : responseList) {
                        skuResults[indx] = new JSONObject(thisResponse);
                        indx++;
                    }
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(Integer response) {
            super.onPostExecute(response);
            if(dialog != null && dialog.isShowing()){
                dialog.dismiss();
            }
        }
    }
}
