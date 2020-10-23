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
import com.rsw.constants.Constant;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


public class HttpInterface {
    private final HttpApi mHttpApi;
    private static final HttpInterface sNetworkDateSource = new HttpInterface();

    static class Creator {
        public static HttpApi newHttpApi() {
            OkHttpClient client = new OkHttpClient();
            CookieManager cookieManager = new CookieManager();
            cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
            client.setReadTimeout(1000 * 30, TimeUnit.MILLISECONDS);
            client.setConnectTimeout(1000 * 30, TimeUnit.MILLISECONDS); //120s 失败
            client.setCookieHandler(cookieManager);
            client.interceptors().add(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request();
                    System.out.println("request=============" + request);
                    Request newRequest = request.newBuilder()
                            .build();
                    Headers headers = request.headers();
                    Map<String, List<String>> headerListMap = headers.toMultimap();
                    for (Map.Entry<String, List<String>> entry : headerListMap.entrySet()) {
                        List<String> values = entry.getValue();
                        StringBuffer sb = new StringBuffer();
                        for (String value : values) {
                            sb.append(value).append(" ");
                        }
                    }
                    Response proceed = chain.proceed(newRequest);
                    return proceed;
                }
            });
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constant.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(client)
                    .build();
            return retrofit.create(HttpApi.class);
        }

    }


    private HttpInterface() {
        mHttpApi = Creator.newHttpApi();
    }

    public static HttpInterface getInstance() {
        return sNetworkDateSource;
    }

    // 不强制 T extends BaseBean 了
    private <T> Subscription doSubscription(Observable<T> observable, Action1<T> action1, Action1<Throwable> onError) {
        if (null != onError) {
            return observable.observeOn(AndroidSchedulers.mainThread()) //处理的结果 给 订阅者返回是在主线程的
                    .subscribeOn(Schedulers.io()) //被观察者observable是在io线程里处理的，
                    .subscribe(action1, onError);
        } else {
            return observable.observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(action1);
        }
    }

    /**
     * 登录
     *
     * @param username
     * @param password
     * @param action1
     * @return
     */
    public Subscription login(String username, String password, Action1<BaseBean> action1, Action1<Throwable> onError) {
        return doSubscription(mHttpApi.login(username, password), action1, onError);
    }

    public Subscription register(String username, String password, Action1<BaseBean> action1, Action1<Throwable> onError) {
        return doSubscription(mHttpApi.register(username, password), action1, onError);
    }

    public Subscription adduser(String username, String password, Action1<BaseBean> action1, Action1<Throwable> onError) {
        return doSubscription(mHttpApi.adduser(username, password), action1, onError);
    }

    public Subscription getYunfuyinshiList(int page, int size, int type, Action1<YinshiBean> action1, Action1<Throwable> onError) {


        return doSubscription(mHttpApi.getYunfuyinshiList(page, size, type), action1, onError);
    }

    public Subscription getYunfuyinshiListBykeyWord(int page, int size, String keyword, Action1<YinshiBean> action1, Action1<Throwable> onError) {
        return doSubscription(mHttpApi.getYunfuyinshiListBykeyWord(page, size, keyword), action1, onError);
    }


    public Subscription getBanners(int size, Action1<YinshiBean> action1, Action1<Throwable> onError) {
        return doSubscription(mHttpApi.getBanners(size), action1, onError);
    }

    public Subscription getCaipuList(String keyWords, int page, int size, Action1<YinshiBean> action1, Action1<Throwable> onError) {
        return doSubscription(mHttpApi.getCaipuList(keyWords, page, size), action1, onError);
    }

    public Subscription getCaipuClass(int type, Action1<CpClassBean> action1, Action1<Throwable> onError) {
        return doSubscription(mHttpApi.getCaipuClass(type), action1, onError);
    }

    public Subscription getCaipuTwoClass(int classid, Action1<CpTwoClassBean> action1, Action1<Throwable> onError) {
        return doSubscription(mHttpApi.getCaipuTwoClass(classid), action1, onError);
    }

    public Subscription getHotKeys(Action1<HotBean> action1, Action1<Throwable> onError) {
        return doSubscription(mHttpApi.getHotKeys(), action1, onError);
    }

    public Subscription getCaipuByClass(int classid, int two_classid, int page, int size, Action1<YinshiBean> action1, Action1<Throwable> onError) {
        return doSubscription(mHttpApi.getCaipuByClass(classid, two_classid, page, size), action1, onError);
    }

    //上传文件
    public Subscription headImgUpload(byte[] fileDatas, String path, String user_id, int type, Action1<ImageUploadRes> action1, Action1<Throwable> onError) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), fileDatas);
        return doSubscription(mHttpApi.headImgUpload(requestBody, path, user_id, type), action1, onError);
    }

    public Subscription getUserInfo(String user_id, Action1<UserInfo> action1, Action1<Throwable> onError) {
        return doSubscription(mHttpApi.getUserInfo(user_id), action1, onError);
    }

    public Subscription saveCollection(String user_id, String collect_id, Action1<JsonObject> action1, Action1<Throwable> onError) {
        return doSubscription(mHttpApi.saveCollection(user_id, collect_id), action1, onError);
    }

    public Subscription getCollection(String user_id, int page, int size, Action1<YinshiBean> action1, Action1<Throwable> onError) {
        return doSubscription(mHttpApi.getCollection(user_id, page, size), action1, onError);
    }

    public Subscription deleteCollection(String user_id, String collect_id, Action1<JsonObject> action1, Action1<Throwable> onError) {
        return doSubscription(mHttpApi.deleteCollection(user_id, collect_id), action1, onError);
    }

    public Subscription getCollectionState(String user_id, String collect_id, Action1<JsonObject> action1, Action1<Throwable> onError) {
        return doSubscription(mHttpApi.getCollectionState(user_id, collect_id), action1, onError);
    }

    public Subscription updateNick(String user_id, String nickName, Action1<JsonObject> action1, Action1<Throwable> onError) {
        return doSubscription(mHttpApi.updateNick(user_id, nickName), action1, onError);
    }

    //上传文件
    public Subscription addShihua(byte[] fileDatas, String fileName, String path, String user_id, String content, Action1<JsonObject> action1, Action1<Throwable> onError) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), fileDatas);
        return doSubscription(mHttpApi.addShihua(requestBody, fileName, path, user_id, content), action1, onError);
    }

    public Subscription addFeedBack(String user_id, String content, String phone, String email, Action1<JsonObject> action1, Action1<Throwable> onError) {
        return doSubscription(mHttpApi.addFeedBack(user_id, content, phone, email), action1, onError);
    }

    public Subscription getSplashImage(Action1<SplashBean> action1, Action1<Throwable> onError) {
        return doSubscription(mHttpApi.getSplashImage(), action1, onError);
    }

    public Subscription getShihua(int page, int size, Action1<ShequBean> action1, Action1<Throwable> onError) {
        return doSubscription(mHttpApi.getShihua(page, size), action1, onError);
    }

    public Subscription getShihuaByuserId(String user_id, int page, int size, Action1<ShequBean> action1, Action1<Throwable> onError) {
        return doSubscription(mHttpApi.getShihuaByuserId(user_id, page, size), action1, onError);
    }

}
