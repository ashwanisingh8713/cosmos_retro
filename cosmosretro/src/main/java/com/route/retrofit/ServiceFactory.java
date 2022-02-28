package com.route.retrofit;



import com.route.cosmosretro.MainActivity;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;




public class ServiceFactory {


    private static ServiceAPIs sServiceAPIs;

    public static ServiceAPIs getServiceAPIs() {
        if(sServiceAPIs == null) {
            sServiceAPIs = createServiceAPIs();
        }
        return sServiceAPIs;
    }

    private ServiceFactory() {
    }


    private static ServiceAPIs createServiceAPIs() {
        final Retrofit retrofit = createRetrofit();
        return retrofit.create(ServiceAPIs.class);
    }

    /**
     * This creates OKHttpClient
     */
    private static OkHttpClient createOkHttpClient() {
        final OkHttpClient.Builder httpClient =
                new OkHttpClient.Builder();
        httpClient.readTimeout(40, TimeUnit.SECONDS);
        httpClient.connectTimeout(40, TimeUnit.SECONDS);
        return httpClient.build();
    }

    /**
     * Creates a pre configured Retrofit instance
     */
    private static Retrofit createRetrofit() {
        String baseUrl = "https://[INSERT DB NAME HERE].documents.azure.com";
//        String host = "https://routeme.documents.azure.com";
//        String host = "https://vinylpixels-routeme-test.documents.azure.com";
//        String host = "https://3569c573-86aa-4895-9555-26a2ca2736cb.documents.azure.com:443/";

        // url = "https://routeme.documents.azure.com:443/dbs";
        String base = "https://routeme.documents.azure.com:443";
        return new Retrofit.Builder()
                .baseUrl(MainActivity.CONNECTION_STR)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // <- add this
                .client(createOkHttpClient())
                .build();
    }
}
