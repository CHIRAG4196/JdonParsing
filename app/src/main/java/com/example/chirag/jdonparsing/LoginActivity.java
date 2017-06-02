package com.example.chirag.jdonparsing;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.chirag.jdonparsing.model.MainResponse;
import com.example.chirag.jdonparsing.model.UserDataPost;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

    public static final String BASE_URL = "https://reqres.in/api/users";
    private AppCompatEditText mEtName, mEtJob;
    private AppCompatButton mBtLogin;
    private AppCompatTextView mTvFirstName, mTvJob, mTvCreated, mTvUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mEtName = (AppCompatEditText) findViewById(R.id.et_name);
        mEtJob = (AppCompatEditText) findViewById(R.id.et_job);
        mTvFirstName = (AppCompatTextView) findViewById(R.id.tv_first_name);
        mTvJob = (AppCompatTextView) findViewById(R.id.tv_job);
        mTvCreated = (AppCompatTextView) findViewById(R.id.tv_created);
        mTvUserId = (AppCompatTextView) findViewById(R.id.tv_user_id);
        mBtLogin = (AppCompatButton) findViewById(R.id.bt_login);

        mBtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name, job;
                name = mEtName.getText().toString();
                job = mEtJob.getText().toString();
                if (TextUtils.isEmpty(name) && TextUtils.isEmpty(job)) {
                    Toast.makeText(LoginActivity.this, "Enter the Details", Toast.LENGTH_SHORT).show();
                    return;
                }
                mTvUserId.setVisibility(View.VISIBLE);
                mTvFirstName.setVisibility(View.VISIBLE);
                mTvJob.setVisibility(View.VISIBLE);
                mTvCreated.setVisibility(View.VISIBLE);
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("name", mEtName.getText().toString());
                    jsonObject.put("job", mEtJob.getText().toString());


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                EmployeeLoadingTask employeeLoadingTask = new EmployeeLoadingTask();
                employeeLoadingTask.execute(BASE_URL, jsonObject.toString());
            }
        });
    }

    public class EmployeeLoadingTask extends AsyncTask<String, Void, UserDataPost> {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(LoginActivity.this, "Data Loading", "");
        }

        @Override
        protected UserDataPost doInBackground(String... params) {
            //  UserDataPost userDataPost = new UserDataPost();
            try {
                String json = postJsonData(params[0], params[1]);
                try {
                    return parseJsonUsingGson(json);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.i("POST", "post :" + json);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        private UserDataPost parseJsonUsingGson(String json) throws JSONException {
            Gson gson = new Gson();
            UserDataPost userDataPost = gson.fromJson(json, UserDataPost.class);
            return userDataPost;
        }

        @Override
        protected void onPostExecute(UserDataPost userDataPost) {
            super.onPostExecute(userDataPost);

            progressDialog.dismiss();

            if (userDataPost == null) {
                return;
            }

            mTvFirstName.setText(userDataPost.getName());
            mTvJob.setText(userDataPost.getJob());
            mTvUserId.setText(userDataPost.getId());
            mTvCreated.setText(userDataPost.getCreated());

            //   textview.setText(mainResponse.getTotal());


        }
    }


    public String postJsonData(String url, String json) throws IOException {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, json);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public class xyz extends AsyncTask<String, Void, MainResponse> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected MainResponse doInBackground(String... params) {
            try {
                String json = getJsonData(params[0], params[1]);
                try {
                   return JsonUsingGson(json);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            catch(IOException e){
                e.printStackTrace();
            }
            return null;
        }

        private MainResponse JsonUsingGson(String json) throws JSONException {
            Gson gson = new Gson();
            MainResponse mainResponse = gson.fromJson(json, MainResponse.class);
            return mainResponse;
        }

        @Override
        protected void onPostExecute(MainResponse mainResponse) {
            super.onPostExecute(mainResponse);
        }
    }

    public String getJsonData(String url, String json) throws IOException {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, json);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }
}
