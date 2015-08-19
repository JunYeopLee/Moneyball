package com.iislab.junyeop_imaciislab.moneyball.common.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.iislab.junyeop_imaciislab.moneyball.Moneyball.LoginActivity;
import com.iislab.junyeop_imaciislab.moneyball.R;
import com.facebook.login.LoginManager;

import java.util.ArrayList;

/**
 * Created by junyeop_imaciislab on 2015. 6. 1..
 */
public class SettingsListAdapter extends ArrayAdapter<String> {
    private final Activity context;
    private ArrayList<String> settingsLists;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public SettingsListAdapter(Activity context, ArrayList<String> settingsLists) {
        super(context, R.layout.score_prediction_item, settingsLists);
        this.context = context;
        this.settingsLists = settingsLists;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.settings_item, null, true);
        TextView textView = (TextView)rowView.findViewById(R.id.settings_item_row);
        textView.setText(settingsLists.get(position));
        if(position==4) {
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sharedPreferences = getContext().getSharedPreferences("login_info", Context.MODE_PRIVATE);
                    editor = sharedPreferences.edit();
                    editor.remove("username");
                    editor.remove("password");
                    editor.remove("userNum");
                    if(sharedPreferences.getBoolean("isfacebook",false)) {
                        LoginManager.getInstance().logOut();
                        editor.remove("isfacebook");
                    } else if(sharedPreferences.getBoolean("isgoogle",false)){
                        editor.remove("isgoogle");
                    } else if(sharedPreferences.getBoolean("istwitter",false)){
                        editor.remove("istwitter");
                    }
                    editor.commit();
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    getContext().startActivity(intent);
                    ((Activity)getContext()).finish();
                }
            });
        }
        return rowView;
    }

}