package com.iislab.junyeop_imaciislab.moneyball.Moneyball;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.iislab.junyeop_imaciislab.moneyball.R;
import com.iislab.junyeop_imaciislab.moneyball.util.Constants;
import com.iislab.junyeop_imaciislab.moneyball.util.IabHelper;
import com.iislab.junyeop_imaciislab.moneyball.util.IabResult;
import com.iislab.junyeop_imaciislab.moneyball.util.Inventory;

import org.json.JSONObject;

import java.util.ArrayList;
public class BuyingMoneyballActivity extends Activity {
    ArrayList<String> responseList;
    JSONObject[] skuResults = new JSONObject[4];
    Button[] btns = new Button[4];
    IabHelper mHelper;

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
        String base64EncodedPublicKey = Constants.IAB.getKey1()+Constants.IAB.getKey2()+Constants.IAB.getKey3()+Constants.IAB.getKey4()+Constants.IAB.getKey5()+Constants.IAB.getKey6()+Constants.IAB.getKey7()+Constants.IAB.getKey8();
        mHelper = new IabHelper(this, base64EncodedPublicKey);
        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            public void onIabSetupFinished(IabResult result) {
                if (!result.isSuccess()) {
                    // Oh noes, there was a problem.
                    Log.d("BUY", "Problem setting up In-app Billing: " + result);
                }
                responseList = new ArrayList<>();
                responseList.add(getString(R.string.item1));
                responseList.add(getString(R.string.item2));
                mHelper.queryInventoryAsync(true, responseList, mQueryFinishedListener);
                // Hooray, IAB is fully set up!
            }
        });

        //new getSkuList().execute(getString(R.string.item1),getString(R.string.item2));
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
        if (mHelper != null) mHelper.dispose();
        mHelper = null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("BUY","requestCode : "+requestCode+" resultCode : " +resultCode+" data : "+data);
        if(!mHelper.handleActivityResult(requestCode,resultCode,data)) {
            super.onActivityResult(requestCode,resultCode,data);
        } else {

        }
        /*
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
        */
    }


    private IabHelper.QueryInventoryFinishedListener mQueryFinishedListener = new IabHelper.QueryInventoryFinishedListener() {
        @Override
        public void onQueryInventoryFinished(IabResult result,Inventory inventory) {
            if(result.isFailure()) {
                return;
            }
            String item1Price = inventory.getSkuDetails(getResources().getString(R.string.item1)).getPrice();
            String item2Price = inventory.getSkuDetails(getResources().getString(R.string.item2)).getPrice();
            Toast.makeText(getApplicationContext(),item1Price+" "+item2Price,Toast.LENGTH_LONG);
        }
    };
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
        }

        @Override
        protected Integer doInBackground(String... items) {
            Integer response=0;
            responseList = new ArrayList<>();
            responseList.add(items[0]);
            responseList.add(items[1]);

            Handler mHandler = new Handler(Looper.getMainLooper());
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mHelper.queryInventoryAsync(true, responseList,mQueryFinishedListener);

                }
            }, 0);

            return response;
        }

        @Override
        protected void onPostExecute(Integer response) {
            super.onPostExecute(response);

        }
    }
}
