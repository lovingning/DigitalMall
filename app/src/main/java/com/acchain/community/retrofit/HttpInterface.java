package com.acchain.community.retrofit;

import com.acchain.community.base.BaseHttpBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 功能----使用retrofit框架与http交换数据
 * <p>
 * Created by ACChain on 2017/9/25.
 */

public interface HttpInterface {
    @GET("getJson/getData")
    Observable<BaseHttpBean> getJson(@Query("username") String username,
                                     @Query("password") String password);

}
