package mrgao.com.retrofitText.retrofit;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mr.gao on 2018/1/7.
 * Package:    mrgao.com.androidtest
 * Create Date:2018/1/7
 * Project Name:AndroidTest
 * Description:
 */

public class RetrofitUtils {

    private static volatile RetrofitUtils mRetrofitUtils;
    private Retrofit mRetrofit;
    private OkHttpClient mOkHttpClient;


    private RetrofitUtils() {

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        mOkHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .addInterceptor(loggingInterceptor)
                .build();

        mRetrofit = new Retrofit.Builder()
                .baseUrl(ServerUrls.BASE_URL)
                .client(mOkHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

    }

    /**
     * Author: MrGao
     * CreateTime: 2018/1/7 12:34
     * MethodName:
     * Des：单例设计模式
     * Params：
     * Return:
     **/
    public static RetrofitUtils getInstance() {
        if (mRetrofitUtils == null) {
            synchronized (RetrofitUtils.class) {
                if (mRetrofitUtils == null) {
                    mRetrofitUtils = new RetrofitUtils();
                }
            }
        }
        return mRetrofitUtils;
    }

    /**
    *Author: MrGao
    *CreateTime: 2018/1/7 12:37
    *MethodName: 
    *Des：
    *Params：
    *Return: 
    **/
    public <T> T create(Class<T> tClass) {
        return mRetrofit.create(tClass);
    }


}
