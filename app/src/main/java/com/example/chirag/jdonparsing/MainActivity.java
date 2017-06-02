package com.example.chirag.jdonparsing;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;

public class MainActivity extends AppCompatActivity {


    private AppCompatButton mBtGetData, mBtPostData;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBtGetData = (AppCompatButton) findViewById(R.id.bt_get_data);
        mBtPostData = (AppCompatButton) findViewById(R.id.bt_post_data);


        mBtGetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this,RecyclerActivity.class);
                startActivity(intent);

            }
        });
        mBtPostData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

    }


}
