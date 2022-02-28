package com.route.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.route.cosmosdb.CosmosDB;
import com.route.cosmosdb.R;

public class MainActivity extends AppCompatActivity {


    private CosmosDB mCosmosDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Thread(() -> {
            // do background stuff here
            try {
                mCosmosDB = CosmosDB.getInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            runOnUiThread(()->{
                // OnPostExecute stuff here
            });
        }).start();



    }






}