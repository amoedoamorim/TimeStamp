package com.amoedoamorim.timestamp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "TimeStamp";

    EditText edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edit = (EditText)findViewById(R.id.edit_text);
    }

    public void publish(View view){
        Log.d(TAG, "publish: ");

        RequestQueue queue = Volley.newRequestQueue(this);

        String arg = edit.getText().toString();

        try {
            arg = URLEncoder.encode(arg, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String url = "http://your-service" + arg;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "onResponse: " + response);
                        Toast.makeText(MainActivity.this, "Sucesso!", Toast.LENGTH_SHORT).show();
                        edit.setText("");
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: ");
                Toast.makeText(MainActivity.this, "Erro comunicando com servidor!", Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(stringRequest);
        timesTamp();
    }

    private void timesTamp(){
        Calendar cal = Calendar.getInstance();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Log.d(TAG, "timesTamp: " + df.format(cal.getTime()));
    }
}
