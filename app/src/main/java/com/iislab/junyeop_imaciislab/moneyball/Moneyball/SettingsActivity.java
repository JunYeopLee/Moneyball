package com.iislab.junyeop_imaciislab.moneyball.Moneyball;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.iislab.junyeop_imaciislab.moneyball.R;
import com.iislab.junyeop_imaciislab.moneyball.common.adapter.SettingsListAdapter;

import java.util.ArrayList;

public class SettingsActivity extends ActionBarActivity {
    ListView listSettings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        ArrayList<String> settingsList = new ArrayList<String>();

        settingsList.add("Logout1");
        settingsList.add("Logout2");
        settingsList.add("Logout3");
        settingsList.add("Logout4");
        settingsList.add("Logout5");

        listSettings = (ListView)findViewById(R.id.settings_list);
        SettingsListAdapter settingsListAdapter = new SettingsListAdapter(this,settingsList);
        listSettings.setAdapter(settingsListAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
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
}
