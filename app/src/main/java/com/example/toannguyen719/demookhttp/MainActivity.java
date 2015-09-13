package com.example.toannguyen719.demookhttp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String URL_IMAGE = "http://54.255.214.47/api/product/getimage";

    private Button btnGetMethod;
    private Button btnPostMethod;
    private TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnGetMethod = (Button) findViewById(R.id.btnGetMethod);
        btnPostMethod = (Button) findViewById(R.id.btnPostMethod);
        tvResult = (TextView) findViewById(R.id.tvResult);

        btnGetMethod.setOnClickListener(this);
        btnPostMethod.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btnGetMethod:
                GetOkHttp getOkHttp = new GetOkHttp();
                getOkHttp.execute(URL_IMAGE);
                break;

            case R.id.btnPostMethod:

                break;
        }
    }

    private class GetOkHttp extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            String url = params[0];

            try {

                return GetMethod(url);

            } catch (IOException e) {
                Log.e("GetOkHttp", e.getMessage());
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            tvResult.setText(s);
        }

        private String GetMethod(String url) throws IOException {

            String result;

            OkHttpClient okHttpClient = new OkHttpClient();
            Request request = new Request.Builder().url(url).build();
            Response response = okHttpClient.newCall(request).execute();
            result = response.body().string();

            return result;
        }
    }

}
