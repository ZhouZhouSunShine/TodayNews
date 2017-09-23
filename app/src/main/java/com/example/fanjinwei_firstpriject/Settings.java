package com.example.fanjinwei_firstpriject;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fanjinwei_firstpriject.utils.MyApplication;
import com.example.fanjinwei_firstpriject.utils.NetUtils;

import java.io.File;
import java.math.BigDecimal;

/**
 * Created by 范晋炜 on 2017/8/16 0016.
 * com.example.fanjinwei_firstpriject
 * Settings
 */


public class Settings extends AppCompatActivity implements View.OnClickListener {

    private ImageView set_back;
    private ImageView set_wifi;
    private TextView set_clear;   //清理缓存
    private TextView set_size;    //缓存大小

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //显示当前页面布局
        setContentView(R.layout.settings);
        set_back = (ImageView) findViewById(R.id.set_back);
        set_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        set_wifi = (ImageView) findViewById(R.id.set_wifi);
        set_wifi.setOnClickListener(this);
        set_clear = (TextView) findViewById(R.id.set_clear);
        set_clear.setOnClickListener(this);
        set_size = (TextView) findViewById(R.id.set_size);
        //缓存大小
        try {
            String totalCacheSize = getTotalCacheSize();
            set_size.setText(totalCacheSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //省流模式
            case R.id.set_wifi:

                //做一个alertDialog
                String[] strings = {"最佳效果", "较省流量", "极省流量"};
                int mode = MyApplication.getAppContext().getSharedPreferences(NetUtils.SP_NAME, Context.MODE_PRIVATE).getInt(NetUtils.PICTURE_LOAD_MODE_KEY, 0);
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("非wifi网络流量");
                builder.setSingleChoiceItems(strings, mode, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //要保存我们的图片加载模式
                        MyApplication.getAppContext().getSharedPreferences(NetUtils.SP_NAME, Context.MODE_PRIVATE).edit().putInt(NetUtils.PICTURE_LOAD_MODE_KEY, which).commit();
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("取消", null);
                builder.create().show();

                break;

            case R.id.set_clear:

                clearAllCache(Settings.this);
                String totalCacheSize = null;
                try {
                    totalCacheSize = getTotalCacheSize();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Toast.makeText(this, "清除完成", Toast.LENGTH_SHORT).show();
                set_size.setText(totalCacheSize);
                break;

            case R.id.set_size:


                break;

        }
    }

    /**
     * 计算app的缓存大小其实计算的是 getCacheDir()这个file和getExternalCacheDir()这个file大小的和
     */
    public String getTotalCacheSize() throws Exception {
        long cacheSize = getFolderSize(this.getCacheDir());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            cacheSize += getFolderSize(this.getExternalCacheDir());
        }
//        File cacheDir = StorageUtils.getOwnCacheDirectory(this, "universalimageloader/Cache");
        return getFormatSize(cacheSize);
    }

    /**
     * 计算文件夹的大小
     */
    public static long getFolderSize(File file) throws Exception {
        if (!file.exists()) {
            return 0;
        }
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                // 如果下面还有文件
                if (fileList[i].isDirectory()) {
                    size = size + getFolderSize(fileList[i]);
                } else {
                    size = size + fileList[i].length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     * 格式化得到的总大小 默认是byte,  然后根据传入的大小,自动转化成合适的大小单位
     *
     * @param size
     * @return
     */
    public static String getFormatSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return "0K";
        }
        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "KB";
        }
        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "MB";
        }
        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
                + "TB";
    }

    /**
     * 清理app的缓存 其实是清除的getCacheDir 和getExternalCacheDir这两个文件
     *
     * @param context
     */
    public static void clearAllCache(Context context) {
        deleteDir(context.getCacheDir());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            deleteDir(context.getExternalCacheDir());
        }
    }

    /**
     * 删除一个文件夹
     *
     * @param dir
     * @return
     */
    private static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }

}
