package mrgao.com.androidtest;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by mr.gao on 2018/1/7.
 * Package:    mrgao.com.androidtest
 * Create Date:2018/1/7
 * Project Name:AndroidTest
 * Description:
 */

public interface AndroidAPI {


    @GET("Android/10/{page}")
    Observable<AndroidBeans> getAndroidAPI(@Path("page") int page);
}
