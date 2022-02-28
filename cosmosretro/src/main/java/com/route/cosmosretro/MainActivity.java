package com.route.cosmosretro;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.gson.JsonElement;
import com.route.retrofit.MyPojo;
import com.route.retrofit.ServiceAPIs;
import com.route.retrofit.ServiceFactory;
import com.route.test.GenAuth;
import com.route.util.Util;

import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    String dataBase = "Routes";
    String containerName = "RoutesData";

//    public static final String resourceId = "dbs/routeme/colls/RouteMeData";
    public static final String resourceId = "dbs/RouteMeData/colls/RoutesData";
    public static final int COSMOS_PORT_NUM = 443;
    public static final String COSMOS_DB_URL = "https://routeme.documents.azure.com";
    public static final String CONNECTION_STR =
            COSMOS_DB_URL + ":" + COSMOS_PORT_NUM;  // https://routeme.documents.azure.com:443/
    public static final String PRIMARY_KEY = "zGZVcvEnFSvNKa61Yr3UeofdlVhYhQlMIIGHkWaMJZnghohRYcv5l1h18JnKS8VixLrKaVcxPLbMxn0xvZuVkQ==";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyPojo myPojo = new MyPojo();
        //This is the only required field, and does not have to be a UUID.
        myPojo.id = UUID.randomUUID().toString();

        /*try {
            ServiceFactory.getServiceAPIs().addDC(Util.generateAuthHeader(
                    "POST", "docs", resourceId,
                    Util.headerDate(),
                    PRIMARY_KEY),
                    Util.headerDate(), myPojo)
                    .enqueue(new Callback<JsonElement>() {
                        @Override
                        public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                            Log.i("CO_ASH", "onResponse");
                            Log.i("CO_ASH", ""+response.code());

                        }

                        @Override
                        public void onFailure(Call<JsonElement> call, Throwable t) {
                            Log.i("CO_ASH", "onFailure");
                        }
                    });

        } catch (Exception e) {
            e.printStackTrace();
            Log.i("CO_ASH", "Exception");
        }*/

        get();

    }



    private void get() {
        String method = "get";
        String UTCstring = Util.headerDate();
        String xms_version = "2016-07-11";
        String url = "https://routeme.documents.azure.com:443/dbs";
        ServiceFactory.getServiceAPIs()
                .addDC(GenAuth.gen(method, UTCstring, url), UTCstring, xms_version)
                .enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        Log.i("GenAuth", ""+response.code());
                    }

                    @Override
                    public void onFailure(Call<JsonElement> call, Throwable t) {
                        Log.i("GenAuth", ""+t.toString());
                    }
                });
    }
}