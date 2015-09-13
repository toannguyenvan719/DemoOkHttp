package com.example.toannguyen719.demookhttp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String URL_IMAGE = "http://54.255.214.47/api/product/getimage";
    public static final String URL_LOGIN = "http://54.255.214.47/users/login";
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

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
                String json = "{ \n" +
                        "    \"username\":\"phuoc\",\n" +
                        "    \"password\":\"12345\"\n" +
                        "}";

                PostOkHttp postOkHttp = new PostOkHttp();
                postOkHttp.execute(URL_LOGIN, json);
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

    private class PostOkHttp extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            String url = params[0];
            String json = params[1];

            try {

                return PostMethod(url, json);

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        private String PostMethod(String url, String json) throws IOException {

            String result;

            OkHttpClient okHttpClient = new OkHttpClient();
            RequestBody requestBody = RequestBody.create(JSON, json);
            Request request = new Request.Builder().url(url).post(requestBody).build();
            Response response = okHttpClient.newCall(request).execute();
            result = response.body().string();

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            tvResult.setText(s);
        }
    }
}
