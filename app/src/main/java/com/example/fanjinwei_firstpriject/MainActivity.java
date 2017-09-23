package com.example.fanjinwei_firstpriject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.andy.library.ChannelActivity;
import com.andy.library.ChannelBean;
import com.example.fanjinwei_firstpriject.adapter.MyPagerAdapter;
import com.example.fanjinwei_firstpriject.dao.ChannelDao;
import com.example.fanjinwei_firstpriject.utils.NetUtils;
import com.example.fanjinwei_firstpriject.utils.ThemeUtils;
import com.example.fanjinwei_firstpriject.webview.Activity;
import com.example.fanjinwei_firstpriject.webview.Collect;
import com.example.fanjinwei_firstpriject.webview.QQZone;
import com.example.fanjinwei_firstpriject.webview.Store;
import com.example.fanjinwei_firstpriject.webview.Topic;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.CircleBitmapDisplayer;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 范晋炜  今日头条工程
 */
public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private SlidingMenu slidingMenu;
    private ImageView head_image;       //左侧拉栏
    private ImageView serach_image;     //右侧拉栏
    private ImageView left_qq;          //点击登录QQ
    private ImageView left_phone;       //手机短信验证
    private ImageView left_day;         //日夜间模式切换
    private ImageView left_set;         //设置页面
    private ImageView left_Download;    //离线下载
    private TextView left_qqZone;       //好友动态
    private TextView left_topic;        //话题
    private TabLayout.Tab one;
    private TabLayout.Tab two;
    private TabLayout.Tab three;
    private TabLayout.Tab four;
    private TabLayout.Tab five;
    private TabLayout.Tab six;
    private TabLayout.Tab seven;
    private TabLayout.Tab eight;
    private TabLayout.Tab nine;
    private ConnectivityBroadcastReceiver mConnectivityBroadcastReceiver;
    private long exitTimeMillis = System.currentTimeMillis();       //再按一次  退出应用程序
    private ImageView main_add;             //频道管理点击
    private TextView left_collect;
    private TextView left_activity;
    private TextView left_store;
    private List<ChannelBean> all;          //做频道管理   全部的数据
    private List<ChannelBean> original;      //频道管理  原始的数据
    private ChannelDao dao;
    private MyPagerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //日夜间模式
        //设置对应的主题 ，在ui创建好之后设置主题无效，所以要放到setContentView（）方法前面setTheme()
        ThemeUtils.onActivityCreatedSetTheme(this);
        //显示当前页面布局
        setContentView(R.layout.activity_main);

        //广播实时监听网络状态
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        mConnectivityBroadcastReceiver = new ConnectivityBroadcastReceiver();

        //注册
        registerReceiver(mConnectivityBroadcastReceiver, filter);

        //获取资源ID
        tabLayout = (TabLayout) findViewById(R.id.main_tablayout);
        viewPager = (ViewPager) findViewById(R.id.main_pager);
        //解决分页问题加载不出数据的   很重要
        viewPager.setOffscreenPageLimit(10);
        //头像的资源ID
        head_image = (ImageView) findViewById(R.id.head_image);
        serach_image = (ImageView) findViewById(R.id.serach_image);
        //频道管理
        main_add = (ImageView) findViewById(R.id.main_add);
        main_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChannelActivity.startChannelActivity(MainActivity.this,all);
            }
        });

        //viewPager适配数据
//        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));

        //将tablayout和viewPager关联起来
        tabLayout.setupWithViewPager(viewPager);

        //各Fragment的设置
        one = tabLayout.getTabAt(0);
        two = tabLayout.getTabAt(1);
        three = tabLayout.getTabAt(2);
        four = tabLayout.getTabAt(3);
        five = tabLayout.getTabAt(4);
        six = tabLayout.getTabAt(5);
        seven = tabLayout.getTabAt(6);
        eight = tabLayout.getTabAt(7);

        //点击监听
        head_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSlidingMenu_LeFT();
                slidingMenu.toggle();

            }
        });


        serach_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSlidingMenu_RIGHT();
                slidingMenu.toggle();
            }
        });

        //调用频道管理的方法
        indata();
    }

    //测试一下
    public void onTestBaseUrl(View v) {
        Toast.makeText(MainActivity.this, NetUtils.getInstance().getBASE_URL(), Toast.LENGTH_SHORT).show();
    }

    public class ConnectivityBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
                //省流模式
                boolean isMobileConnectivity = true;

                //如果能走到这，说明网络已经发生变化
                ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetworkInfo = manager.getActiveNetworkInfo();
                if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
                    if (ConnectivityManager.TYPE_WIFI == activeNetworkInfo.getType()) {
                        Toast.makeText(MainActivity.this, "当前是WIFI网络,请放心使用", Toast.LENGTH_SHORT).show();
                        //省流模式
                        isMobileConnectivity = false;
                    } else if (ConnectivityManager.TYPE_MOBILE == activeNetworkInfo.getType()) {
                        Toast.makeText(MainActivity.this, "现在是移动网络，注意流量消耗", Toast.LENGTH_SHORT).show();
                        //省流模式
                        isMobileConnectivity = true;
                    } else {
                        Toast.makeText(MainActivity.this, "网络不可用，请检查网络", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(MainActivity.this, "网络不可用，请检查网络", Toast.LENGTH_SHORT).show();
                }

                //改变一下网络状态
                NetUtils.getInstance().changeNetState(isMobileConnectivity);

            }
        }
    }

    //销毁广播

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mConnectivityBroadcastReceiver);
    }

    public void setSlidingMenu_LeFT() {
        //创建侧拉列表
        slidingMenu = new SlidingMenu(this);
        //设置侧拉列表的位置
        slidingMenu.setMode(SlidingMenu.LEFT);
        //设置触摸的区域               //触摸边距时弹出侧拉列表
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        //设置菜单打开时  内容区域宽度
        slidingMenu.setBehindOffset(300);
        //设置菜单打开、关闭时的褪色效果
        slidingMenu.setFadeDegree(1f);
        //将侧拉列表和Activity关联起来
        slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        //创建slidingmenu的布局
        slidingMenu.setMenu(R.layout.slidingmenu_left);
        //第三方登录QQ
        left_qq = (ImageView) slidingMenu.findViewById(R.id.left_QQ);
        left_qq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UMShareAPI.get(MainActivity.this).getPlatformInfo(MainActivity.this, SHARE_MEDIA.QQ, umAuthListener);
            }
        });
        //手机短信验证
        left_phone = (ImageView) slidingMenu.findViewById(R.id.left_phone);
        left_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MessageVerification.class);
                startActivity(intent);
            }
        });

        //好友动态
        left_qqZone = (TextView) findViewById(R.id.left_QQZone);
        left_qqZone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, QQZone.class);
                startActivity(intent);
            }
        });

        //我的话题
        left_topic = (TextView) findViewById(R.id.left_topic);
        left_topic.setOnClickListener(new View.OnClickListener() {
            private Intent intent;

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Topic.class);
                startActivity(intent);
            }
        });

        //收藏
        left_collect = (TextView) findViewById(R.id.left_collect);
        left_collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Collect.class);
                startActivity(intent);
            }
        });

        //活动
        left_activity = (TextView) findViewById(R.id.left_activity);
        left_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Activity.class);
                startActivity(intent);
            }
        });

        //商城
        left_store = (TextView) findViewById(R.id.left_store);
        left_store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Store.class);
                startActivity(intent);
            }
        });

        //日夜间模式切换
        left_day = (ImageView) slidingMenu.findViewById(R.id.left_Day);
        left_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThemeUtils.ChangeCurrentTheme(MainActivity.this);
            }
        });


        //设置界面
        left_set = (ImageView) slidingMenu.findViewById(R.id.left_set);
        left_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Settings.class);
                startActivity(intent);
            }
        });
        left_Download = (ImageView) findViewById(R.id.left_Download);
        //离线下载
        left_Download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DownLoad.class);
                startActivity(intent);
            }
        });
    }

    public void setSlidingMenu_RIGHT() {
        //创建侧拉列表
        slidingMenu = new SlidingMenu(this);
        //设置侧拉列表的位置
        slidingMenu.setMode(SlidingMenu.RIGHT);
        //设置触摸的区域               //触摸边距时弹出侧拉列表
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        //设置菜单打开时  内容区域宽度
        slidingMenu.setBehindOffset(300);
        //设置菜单打开、关闭时的褪色效果
        slidingMenu.setFadeDegree(1f);
        //将侧拉列表和Activity关联起来
        slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        //创建slidingmenu的布局
        slidingMenu.setMenu(R.layout.slidingmenu_right);
    }

    /**
     * 第三方登录集成
     */

    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            //授权开始的回调
        }

        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            Toast.makeText(getApplicationContext(), "Authorize succeed", Toast.LENGTH_SHORT).show();
            String iconurl = data.get("iconurl");
            //自定义配置
            DisplayImageOptions options = new DisplayImageOptions.Builder()
                    .cacheInMemory(true)//让图片进行内存缓存
                    .cacheOnDisk(true)//让图片进行sdcard缓存
                    .displayer(new CircleBitmapDisplayer())
                    .build();

            //ImageLoader.getInstance().loadImage(path,options,new Ima);//加载图片
            //参数1：加载的图片地址
            //参数2：将图片设置到那个图片控件上面
            //参数3：加载图片配置选项，意思是指明对这张图片的是否进行缓存(内存、sdcard)
            ImageLoader.getInstance().displayImage(iconurl, left_qq, options);
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Toast.makeText(getApplicationContext(), "Authorize fail", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText(getApplicationContext(), "Authorize cancel", Toast.LENGTH_SHORT).show();
        }
    };

    //频道管理
    public void indata(){
        all = new ArrayList<>();
        original = new ArrayList<>();
        dao = new ChannelDao(this);
        List<ChannelBean> select = dao.select();
        if (select==null||select.size()<1) {
            ChannelBean channelBean1 = new ChannelBean("推荐", true);
            ChannelBean channelBean2 = new ChannelBean("社会", true);
            ChannelBean channelBean3 = new ChannelBean("国内", true);
            ChannelBean channelBean4 = new ChannelBean("国际", true);
            ChannelBean channelBean5 = new ChannelBean("娱乐", true);
            ChannelBean channelBean6 = new ChannelBean("体育", true);
            ChannelBean channelBean7 = new ChannelBean("军事", false);//false为可以添加的频道
            ChannelBean channelBean8 = new ChannelBean("科技",false);
            ChannelBean channelBean9 = new ChannelBean("财经",false);
            ChannelBean channelBean10 = new ChannelBean("时尚",false);

            //我的频道
            original.add(channelBean1);
            original.add(channelBean2);
            original.add(channelBean3);
            original.add(channelBean4);
            original.add(channelBean5);
            original.add(channelBean6);
            //全部频道
            all.add(channelBean1);
            all.add(channelBean2);
            all.add(channelBean3);
            all.add(channelBean4);
            all.add(channelBean5);
            all.add(channelBean6);
            all.add(channelBean7);
            all.add(channelBean8);
            all.add(channelBean9);
            all.add(channelBean10);

            dao.add(all);
        }else{
            all.addAll(select);
            List<ChannelBean> tiaojianselect = dao.tiaojianselect();
            original.addAll(tiaojianselect);
        }
        adapter = new MyPagerAdapter(getSupportFragmentManager(),original);
        viewPager.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //第三方登录
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
        //频道方法
        if (requestCode == ChannelActivity.REQUEST_CODE && resultCode == ChannelActivity.RESULT_CODE) {
            String stringExtra = data.getStringExtra(ChannelActivity.RESULT_JSON_KEY);
            if (TextUtils.isEmpty(stringExtra)) {
                return;
            }
            List<ChannelBean> list = new Gson().fromJson(stringExtra, new TypeToken<List<ChannelBean>>() {
            }.getType());
            if (list == null || list.size() < 1) {
                return;
            }
            all.clear();
            original.clear();

            //将返回的数据,添加到我们的集合中
            all.addAll(list);
            for (ChannelBean channelBean : list) {
                boolean select = channelBean.isSelect();
                if (select) {
                    original.add(channelBean);
                }
            }

            adapter.notifyDataSetChanged();


            //保存数据库
            dao.delete();
            dao.add(all);

            FragmentManager supportFragmentManager = getSupportFragmentManager();
            List<Fragment> fragments = supportFragmentManager.getFragments();
            FragmentTransaction transaction = supportFragmentManager.beginTransaction();
            for (Fragment f : fragments
                    ) {
                transaction.remove(f);
            }
            transaction.commitAllowingStateLoss();
            recreate();

        }
    }

    //再按一次退出应用程序
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - exitTimeMillis == 0 || currentTime - exitTimeMillis > 1500) {
                exitTimeMillis = System.currentTimeMillis();
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                return false;
            } else {
                finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
