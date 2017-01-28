package com.jianastrero.sweetmotherofjsonexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jianastrero.sweetmotherofjson.SweetJson;
import com.jianastrero.sweetmotherofjson.SweetJsonConfig;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SweetJsonConfig.setDomain("192.168.43.33/sweet");

        textView=(TextView)findViewById(R.id.textView);
    }

    public void connect(View v) {
        Connect connect=new Connect("SweetJson Mother Of Json");
        connect.setOnConnectionListener(new SweetJson.OnConnectionListener() {
            @Override
            public void onBeforeConnectionStart() {
                textView.setText("Connecting to server...");
            }

            @Override
            public void onAfterConnectionStopped(String result, JSONObject jsonObject, JSONArray jsonArray) {
                textView.setText(""+result);
            }
        });
        connect.submit();
    }
}
