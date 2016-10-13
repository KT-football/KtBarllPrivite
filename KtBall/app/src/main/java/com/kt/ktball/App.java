package com.kt.ktball;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.preference.PreferenceManager;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.baidu.mapapi.SDKInitializer;
import com.frame.app.utils.SharedPreferencesUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kt.ktball.db.THDatabaseLoader;
import com.kt.ktball.entity.UserLogin;
import com.ktfootball.app.Constants;
import com.yolanda.nohttp.NoHttp;
import com.youku.player.YoukuPlayerBaseConfiguration;

/**
 * Created by jy on 16/5/27.
 */
public class App extends MultiDexApplication {

    public static YoukuPlayerBaseConfiguration configuration;
    private static Context mContext;
    private static App sInstance;
    public static OkHttpClient httpClient;
    public static final String USER_INFO = "userinfo";
    private static UserLogin user;

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        MultiDex.install(this);
        SDKInitializer.initialize(this);
        NoHttp.initialize(this);
        configuration = new YoukuPlayerBaseConfiguration(this) {
            @Override
            public Class<? extends Activity> getCachingActivityClass() {
                return null;
            }

            @Override
            public Class<? extends Activity> getCachedActivityClass() {
                return null;
            }

            @Override
            public String configDownloadPath() {
                return null;
            }
        };
        mContext = getApplicationContext();
        sInstance = this;
        initUtils();
        initDB();
    }

    private void initDB() {
        THDatabaseLoader.init();
    }

    private void initUtils() {
        httpClient = new OkHttpClient();
    }

    public static App getInstance() {
        return sInstance;
    }

    public static Context getContext() {
        return mContext;
    }

    public static long getUserId() {
        return PreferenceManager.getDefaultSharedPreferences(mContext).getLong("user_id", 0);
    }

    public static void setLocalUserInfo(String userinfo) {
        user = new Gson().fromJson(userinfo, new TypeToken<UserLogin>() {
        }.getType());
        SharedPreferencesUtils.saveString(getContext(), USER_INFO, userinfo);
    }

    public  static UserLogin getUserLogin() {
        if (user == null) {
            String str = SharedPreferencesUtils.getString(getContext(), USER_INFO, "");
            user = new Gson().fromJson(str, new TypeToken<UserLogin>() {
            }.getType());
        }
        return user;
    }


    public static String getImageUrl(String url){
        return Constants.HOST + url;
    }
}
