package API;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import trempApplication.R;
import trempApplication.app.Address;
import trempApplication.app.MyApplication;
import trempApplication.app.UnsafeOkHttpClient;

public class AddressAPI {
    Retrofit retrofit;
    WebServiceAPI webServiceApi;

    public AddressAPI() {
        OkHttpClient okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient(5);

        Gson gson = new GsonBuilder().setLenient().create();
        retrofit = new Retrofit.Builder().baseUrl(MyApplication.context.getString(R.string.BaseUrl))
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        webServiceApi = retrofit.create(WebServiceAPI.class);
    }
}
