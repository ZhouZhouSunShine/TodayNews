package com.example.fanjinwei_firstpriject.utils;

import android.app.Application;
import android.content.Context;
import android.os.Environment;

import com.mob.MobSDK;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import org.xutils.x;

import java.io.File;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by 范晋炜 on 2017/8/10 0010.
 * utils
 * MyApplication
 */


public class MyApplication extends Application {

    {
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
    }

    private static MyApplication myApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        //xUtils的初始化
        x.Ext.init(this);
        //第三方登录
        UMShareAPI.get(this);
        Config.DEBUG = true;
        //省流模式
        myApplication = this;
        //极光推送
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        //短信验证
        MobSDK.init(this, "1ff5d68f78003", "7577d8d6c059c2d367a4a40c05712f1d");
//        String path = Environment.getExternalStorageDirectory().getPath() + "/Application";
        String path = this.getCacheDir().getPath() + "/imageLoader";

        File file = new File(path);

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .memoryCacheExtraOptions(200, 200)//配置内存缓存图片的尺寸
                .memoryCacheSize(2 * 1024 * 1024)//配置内存缓存的大小
                .threadPoolSize(3)//配置加载图片的线程数
                .threadPriority(1000)//配置线程的优先级
                .diskCache(new UnlimitedDiskCache(file))//UnlimitedDiskCache 限制这个图片的缓存路径
                .diskCacheFileCount(50)//配置sdcard缓存文件的数量
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())//MD5这种方式生成缓存文件的名字
                .diskCacheSize(50 * 1024 * 1024)//在sdcard缓存50MB
                .build();//完成

        ImageLoader.getInstance().init(config);
    }

    public static Context getAppContext(){
        return myApplication;
    }
}
