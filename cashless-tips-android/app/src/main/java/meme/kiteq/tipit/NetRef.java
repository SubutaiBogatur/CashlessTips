package meme.kiteq.tipit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class NetRef {
    private static NetRef instance;
    private Net impl;
    private NetRef() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create())
                .baseUrl("http://evarand.rocks/api/")
                .build();

        impl = retrofit.create(Net.class);
    };

    public static NetRef getInstance() {
        if (instance == null) {
            synchronized (NetRef.class) {
                if (instance == null) {
                    instance = new NetRef();
                }
            }
        }

        return instance;
    }

    public static Net getNet() {
        return getInstance().impl;
    }
}
