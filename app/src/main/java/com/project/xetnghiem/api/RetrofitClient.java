package com.project.xetnghiem.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lucky on 13-Sep-17.
 */

public class RetrofitClient {
    private static Retrofit retrofit = null;
//    private static String baseUrl = "http://192.168.1.11:52406";
    private static String baseUrl = "http://eltms-capstone.azurewebsites.net/";
    private static String accessToken = "null";
//    private static String baseUrl = "http://10.0.2.2:8000";
//    private static String baseUrl = "http://10.0.2.2:52406";

    public static Retrofit getClient() {
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS);//.addInterceptor(interceptor).build();
        //add header in case of authorization
        clientBuilder.addInterceptor((chain) -> {
                    Request original = chain.request();
                    Request.Builder reqBuilder = original.newBuilder()
//                            .addHeader("Authorization", "Bearer " + accessToken)
                            //localhost+
//                            .addHeader("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6IjVkZTUxYTExOTVhZWNjZDg5Y2Q2OWQyNmY2N2Y4NTNmN2RlODBmYWMxNGE4OWE0YjRmZjVkYTZhMjg1M2Q3ZTc3OTkzZDBiZWUxMzFiMjZhIn0.eyJhdWQiOiIzIiwianRpIjoiNWRlNTFhMTE5NWFlY2NkODljZDY5ZDI2ZjY3Zjg1M2Y3ZGU4MGZhYzE0YTg5YTRiNGZmNWRhNmEyODUzZDdlNzc5OTNkMGJlZTEzMWIyNmEiLCJpYXQiOjE1MzI3ODAyNjYsIm5iZiI6MTUzMjc4MDI2NiwiZXhwIjoxNTY0MzE2MjY2LCJzdWIiOiIwMTI3OTAxMTA5NyIsInNjb3BlcyI6W119.OaUoJ9BUNwCeRXy3DHW-0VF_Pn5Beq9fmkV2zRSsOsBS_Bbx5iYlmBUDzpM0upxHvu0GIO4Om2d-JXrima0hdiugI7AtWT3QN0Bpt7Jsi2-_F5PQgqvynC3OwPIdIT8XnruO3lGSp42KiIBwhStddRc4cS8WZ9QqI7rVxMMsS3_VQ_2heRGsaHO4JpQ-CgxZLYou8Y5WdUgVlHEed2gPPB8lysyMJesc_KycBTBZ_czxOk-J0BXdY_7aIfot0jC4Fggv3sBaL-LsxMvlR3D4qBDOL1PP_aK803ZOzA2xAmwmktELaQ6pUB-KOs3hcspH19jJAEcXfqeZPvBcoqsb17wsxFG-rglAqLQ48z2LRuHaH0quGpBSnO2DNp9dZmM4juGczHO99uGd3Q64ZBf9TIWeA85TouRnmGj-cEHa15zn807WnNK3-JbWNUYcreU_XY4iS7OF0iy2mxEbFjMlhWdt4Vb1rH_RLiK6hRGjq95d-c-vyipP4aetA-boeFy8moeWhHfQDZVtOeSs_kcNJryoqRX4s1iKzr4D7zN3bLm607ipuay23MMOL_neVriU363WD55Hv-aWXxp8RJTpq1NDwUj8zw6JMhqeYq9SuHjJFomeB53iC3oqh-YXRQoq_q1IBT5DoV3SJPXZEXEjbW9UCHjqg7cN54xKwylmhNw")
                            //server
//                            .addHeader("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6IjgwNjc0NGQzMjBmYjI1M2M5YTcxYTljODg1Y2U0ZjkzOTdhM2Y5NTEzM2Q3OTk2ZTg4NDk1ODMyNDQ3ZTdjNzMzYWMyYjQ1ZjZiZDU5NzBjIn0.eyJhdWQiOiIzIiwianRpIjoiODA2NzQ0ZDMyMGZiMjUzYzlhNzFhOWM4ODVjZTRmOTM5N2EzZjk1MTMzZDc5OTZlODg0OTU4MzI0NDdlN2M3MzNhYzJiNDVmNmJkNTk3MGMiLCJpYXQiOjE1MzI3ODMzNjUsIm5iZiI6MTUzMjc4MzM2NSwiZXhwIjoxNTY0MzE5MzY1LCJzdWIiOiIwMTI3OTAxMTA5NyIsInNjb3BlcyI6W119.x1IZML8aFb7Mo-kSfGqUYjp2L4iqa46kjjjGg3Aboi6djsy0-pR7lHuLoRgOjEPAd9_VHv1xiTg0HAKJj84guz6bJyKk6sWOg2JYZIvi59cI5tWfdDAdUZvGFmq3yZhLjv0mpP2Pb8YGbPtUuxFNDzagqFDI7IfapNzq5qy0QnNv5gHvG_XgRZhSwhGqMBOYujacCBLUL_VFBZoB48Sohorry6NmFX5KRt1qZZnnIM6mmD8EVqPrt-D0Vo8qVGoHkck69aCUGTJ4CseuPmmnLst8ddQLW3VyctKxr7sH7wo6QPWYt8mWUrHieIl_gWGuV8A2HjfKeo4TEt0nI2dHsO6sMiWqsk2JN1hHe-2AkNNDcuLkuv6QXn_eKuAV4SErpxpNlvQ9IfgqA0gY6DoBT2z6MMoY16tmdZulJAA4PQ-J499DxPoYBcN0x3pLdEetlabObxhkKs2nkOe-AioYYzesAI8tTBh_piSuTvLSSqUvGBdWouiYtZ5wSFnsgZ303cRBv5BmQ1kLb7dUcTqL7zctt5o9Jfdg5hocZxajSGfHDy4P9OqHCw10ifbaCxmv0FmHM9mZZp7Xn5zdIpYmRejbE1DeGTpnzDhFn4WYgth3Q8-kTFmbABOUEDwSFDyTo0Wq4RbPCdSQ_oobDOiRqwSWOjakVb_fed6ZclTj2ow")
                            .addHeader("Accept", "application/json");
                    Request request = reqBuilder.build();
                    return chain.proceed(request);
                }
        );
        //add log for retrofit
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        clientBuilder.addInterceptor(interceptor);
        OkHttpClient client = clientBuilder.build();
        //Gson builder
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(client)
                    .build();
        }
        return retrofit;
    }

    public static void setAccessToken(String token) {
        accessToken = token;
    }

    public static void setBaseUrl(String mBaseUrl) {
        baseUrl = mBaseUrl;
    }

    public static Retrofit getClient(String url) {
        setBaseUrl(url);
        return getClient();
    }
}
