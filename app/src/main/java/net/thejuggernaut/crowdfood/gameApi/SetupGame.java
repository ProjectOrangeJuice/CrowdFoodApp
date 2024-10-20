package net.thejuggernaut.crowdfood.gameApi;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.Serializable;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;

public class SetupGame implements Serializable {
    public static final String BASE_URL = "http://192.168.1.102:8000";

    public static GameApi getRetro(Context c){
        Gson gson = new GsonBuilder()
                .setLenient()
                .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                .create();
//        Auth0 auth0 = new Auth0(c);
//        SecureCredentialsManager credentialsManager =
//                new SecureCredentialsManager(c, new AuthenticationAPIClient(auth0), new SharedPreferencesStorage(c));
        SharedPreferences pref = c.getSharedPreferences("AccountInfo",MODE_PRIVATE);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
                                      @Override
                                      public Response intercept(Chain chain) throws IOException {
                                          Request original = chain.request();

                                          Request request = original.newBuilder()
                                                  .addHeader("Authorization", "Bearer " + pref.getString("Token",""))
                                                  .method(original.method(), original.body())
                                                  .build();

                                          return chain.proceed(request);
                                      }
                                  });

                OkHttpClient client = httpClient.build();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        GameApi foodieAPI = retrofit.create(GameApi.class);

        return foodieAPI;
    }
}
