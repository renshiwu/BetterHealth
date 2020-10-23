/*
 * Copyright 2016. chenshufei
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.rsw.http;


import com.google.gson.JsonObject;
import com.rsw.bean.BaseBean;
import com.rsw.bean.CpClassBean;
import com.rsw.bean.CpTwoClassBean;
import com.rsw.bean.HotBean;
import com.rsw.bean.ImageUploadRes;
import com.rsw.bean.ShequBean;
import com.rsw.bean.SplashBean;
import com.rsw.bean.UserInfo;
import com.rsw.bean.YinshiBean;
import com.squareup.okhttp.RequestBody;

import org.json.JSONObject;

import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.Query;
import rx.Observable;


public interface HttpApi {
    @POST("login")
    Observable<BaseBean> login(@Query("loginName") String loginName, @Query("pwd") String pwd);

    @POST("register")
    Observable<BaseBean> register(@Query("loginName") String loginName, @Query("pwd") String pwd);

    @POST("adduser")
    Observable<BaseBean> adduser(@Query("loginName") String loginName, @Query("pwd") String pwd);

    @GET("getYunfuyinshiList")
    Observable<YinshiBean> getYunfuyinshiList(@Query("page") int page, @Query("size") int size, @Query("class_id") int class_id);

    @GET("getYunfuyinshiListBykeyWord")
    Observable<YinshiBean> getYunfuyinshiListBykeyWord(@Query("page") int page, @Query("size") int size, @Query("keyword") String keyword);

    @GET("getBanners")
    Observable<YinshiBean> getBanners(@Query("size") int size);

    @GET("getCaipuList")
    Observable<YinshiBean> getCaipuList(@Query("keyWords") String keyWords, @Query("page") int page, @Query("size") int size);

    @GET("getCaipuClass")
    Observable<CpClassBean> getCaipuClass(@Query("type") int type);

    @GET("getCaipuTwoClass")
    Observable<CpTwoClassBean> getCaipuTwoClass(@Query("classid") int classid);

    @GET("getVersion")
    Observable<JSONObject> getVersion();

    @GET("getHotKeys")
    Observable<HotBean> getHotKeys();

    @GET("getCaipuByClass")
    Observable<YinshiBean> getCaipuByClass(@Query("classid") int classid, @Query("two_classid") int two_classid, @Query("page") int page, @Query("size") int size);

    //    @Multipart
    //    @POST("headImgUpload")
    //    Observable<ImageUploadRes> headImgUpload(@Part("") RequestBody file, @Query("path") String path, @Query("user_id") String user_id);

    @Multipart
    @POST("headImgUpload")
    Observable<ImageUploadRes> headImgUpload(@Part("file\"; filename=\"img.jpg\"") RequestBody img, @Query("path") String path, @Query("user_id") String user_id, @Query("type") int type);


    @GET("getUserInfo")
    Observable<UserInfo> getUserInfo(@Query("user_id") String user_id);

    @POST("saveCollection")
    Observable<JsonObject> saveCollection(@Query("user_id") String user_id, @Query("collect_id") String collect_id);

    @GET("getCollection")
    Observable<YinshiBean> getCollection(@Query("user_id") String user_id, @Query("page") int page, @Query("size") int size);

    @POST("deleteCollection")
    Observable<JsonObject> deleteCollection(@Query("user_id") String user_id, @Query("collect_id") String collect_id);

    @GET("getCollectionState")
    Observable<JsonObject> getCollectionState(@Query("user_id") String user_id, @Query("collect_id") String collect_id);

    @POST("updateNick")
    Observable<JsonObject> updateNick(@Query("user_id") String user_id, @Query("nickName") String nickName);

    @Multipart
    @POST("addShihua")
    Observable<JsonObject> addShihua(@Part("file\"; filename=\"img.jpg\"") RequestBody img, @Query("fileName") String fileName,
                                     @Query("path") String path, @Query("user_id") String user_id, @Query("content") String content);

    @POST("addFeedBack")
    Observable<JsonObject> addFeedBack(@Query("user_id") String user_id, @Query("content") String content, @Query("phone") String phone, @Query("email") String email);


    @GET("getSplashImage")
    Observable<SplashBean> getSplashImage();


    @GET("getShihua")
    Observable<ShequBean> getShihua(@Query("page") int page, @Query("size") int size);

    @GET("getShihuaByuserId")
    Observable<ShequBean> getShihuaByuserId(@Query("user_id") String user_id, @Query("page") int page, @Query("size") int size);
}
