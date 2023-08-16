package API;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import trempApplication.R;
import trempApplication.app.MyApplication;
import trempApplication.app.UnsafeOkHttpClient;

public class UserAPI {
    Retrofit retrofit;
    WebServiceAPI webServiceApi;

    public UserAPI() {
        int clientPort = 8081;
        OkHttpClient okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient(90);

        Gson gson = new GsonBuilder().setLenient().create();
        retrofit = new Retrofit.Builder().baseUrl(MyApplication.context.getString(R.string.BaseUrl))
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        webServiceApi = retrofit.create(WebServiceAPI.class);
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    public WebServiceAPI getWebServiceApi() {
        return webServiceApi;
    }
}

