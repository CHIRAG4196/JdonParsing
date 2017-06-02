package com.example.chirag.jdonparsing;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.chirag.jdonparsing.adapter.UserAdapter;
import com.example.chirag.jdonparsing.model.MainResponse;
import com.example.chirag.jdonparsing.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RecyclerActivity extends AppCompatActivity {

    public static final String BASE_URL = "https://reqres.in/api";
    private RecyclerView mRvUser;
    private ArrayList<User> userArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        mRvUser = (RecyclerView) findViewById(R.id.rv_user);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRvUser.setLayoutManager(layoutManager);
        EmployeeLoadingTask employeeLoadingTask = new EmployeeLoadingTask();
        employeeLoadingTask.execute(BASE_URL, "/users?page=2");


    }
    public class EmployeeLoadingTask extends AsyncTask<String, Void, MainResponse> {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(RecyclerActivity.this, "Data Loading", "");
        }

        @Override
        protected MainResponse doInBackground(String... strings) {
            MainResponse mainResponse = new MainResponse();
            try {
                String json = getJsonData(strings[0] + strings[1]);

                Log.e("TEST", "" + json);
                try {
                    JSONObject mainJson = new JSONObject(json);
                    userArrayList = new ArrayList<>();
                    JSONArray dataArray = mainJson.getJSONArray("data");
                    for (int i = 0; i < dataArray.length(); i++) {
                        JSONObject obj = dataArray.getJSONObject(i);
                        String firstName = obj.getString("first_name");
                        String lastName = obj.getString("last_name");
                        String thumb = obj.getString("avatar");
                        Log.e("TEST", "First Name : " + firstName);
                        Log.e("TEST", "Last Name : " + lastName);
                        User user = new User();
                        user.setFirstName(firstName);
                        user.setLastName(lastName);
                        user.setThumb(thumb);
                        userArrayList.add(user);
                    }
                    mainResponse.setUsers(userArrayList);

                } catch (JSONException e) {
                    Log.e("TEST", "JSONException : " + e.getLocalizedMessage());
                }
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
            return mainResponse;
        }

        @Override
        protected void onPostExecute(MainResponse mainResponse) {
            super.onPostExecute(mainResponse);

            progressDialog.dismiss();

            if (mainResponse == null) {
                return;
            }
            userArrayList = mainResponse.getUsers();
            Log.e("array", "onPostExecute: "+userArrayList.size() );
            UserAdapter userAdapter = new UserAdapter(RecyclerActivity.this,userArrayList);
            mRvUser.setAdapter(userAdapter);
            //   textview.setText(mainResponse.getTotal());

        }
    }

    public String getJsonData(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }
}
