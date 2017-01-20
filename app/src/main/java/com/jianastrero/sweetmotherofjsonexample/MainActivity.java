package com.jianastrero.sweetmotherofjsonexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jianastrero.sweetmotherofjson.Sweet;
import com.jianastrero.sweetmotherofjson.SweetConfig;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SweetConfig.setDomain("192.168.43.33/sweet");

        textView=(TextView)findViewById(R.id.textView);
    }

    public void connect(View v) {
        Connect connect=new Connect("Sweet Mother Of Json");
        connect.setOnConnectionListener(new Sweet.OnConnectionListener() {
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
