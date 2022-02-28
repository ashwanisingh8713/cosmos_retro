package com.route.cosmosretro;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonElement;
import com.route.retrofit.MyPojo;
import com.route.retrofit.ServiceFactory;
import com.route.util.Util;

import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity_OLD extends AppCompatActivity {

    String dataBase = "Routes";
    String containerName = "RoutesData";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//

        MyPojo myPojo = new MyPojo();
        //This is the only required field, and does not have to be a UUID.
        myPojo.id = UUID.randomUUID().toString();

        /*try {
            ServiceFactory.getServiceAPIs().addDocument(Util.generateAuthHeader("post",
//                    "docs", "[INSERT COLLECTION ID HERE]", Util.headerDate(),
                    "docs", "RoutesData", Util.headerDate(),
                    MASTER_KEY), Util.headerDate(), myPojo, new Callback<MyPojo>() {
                @Override
                public void onResponse(Call<MyPojo> call, Response<MyPojo> response) {
                    Log.i("CO_ASH", "onResponse");
                }

                @Override
                public void onFailure(Call<MyPojo> call, Throwable t) {
                    Log.i("CO_ASH", "onFailure");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("CO_ASH", "Exception");
        }*/


        try {

//            String MASTER_KEY = "zGZVcvEnFSvNKa61Yr3UeofdlVhYhQlMIIGHkWaMJZnghohRYcv5l1h18JnKS8VixLrKaVcxPLbMxn0xvZuVkQ==";
            String MASTER_KEY = "s0RzhQ3WHPDkQE0ymX4iVGVWsP5UP6kk42zYCQcLyPanEn4QfVYgELl2IH9QKryeyoIbUPx6xWKMuxP3OOybYg==";
            String resourceId = "dbs/" + dataBase + "/colls/" + containerName;

//            "post", "docs", "dbs/RoutesData/colls/Items",
//                    "post", "docs", "RoutesData",
           /* ServiceFactory.getServiceAPIs().addDC(Util.generateAuthHeader(
                    "POST", "docs", resourceId,
                    Util.headerDate(),
                    MASTER_KEY),
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
                    });*/

        } catch (Exception e) {
            e.printStackTrace();
            Log.i("CO_ASH", "Exception");
        }


    }
}