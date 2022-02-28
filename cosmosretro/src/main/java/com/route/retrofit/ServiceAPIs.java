package com.route.retrofit;


import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;



public interface ServiceAPIs {


    @POST("/dbs/[db_id]/colls/[collection_id]/docs")
    void addDocument(@Header("authorization") String authorization,
                     @Header("x-ms-date") String date,
                     @Body MyPojo myPojo,
                     Callback<MyPojo> cb);


    //https://vinylpixels-routeme-test.documents.azure.com/dbs/Routes/colls/RoutesData/docs
//    @POST("/dbs/RoutesData/colls/Items/docs")
//    @POST("/dbs/RouteMe/colls/RoutesData/docs")
//    @POST("/dbs/routeme/colls/RouteMeData/docs")
//    @POST("/dbs/RouteMeData/colls/RoutesData/docs")
    //url = "https://routeme.documents.azure.com:443/dbs";
    @GET("/dbs")
    Call<JsonElement> addDC(@Header("authorization") String authorization,
                            @Header("x-ms-date") String date,
                            @Header("x-ms-version") String xms_version);

}
