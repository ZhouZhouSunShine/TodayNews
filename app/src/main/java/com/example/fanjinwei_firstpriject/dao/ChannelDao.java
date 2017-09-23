package com.example.fanjinwei_firstpriject.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.andy.library.ChannelBean;
import com.example.fanjinwei_firstpriject.sql.MySQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 范晋炜 on 2017/8/19 0019.
 * com.example.fanjinwei_firstpriject.dao
 * ChannelDao
 */


public class ChannelDao {

    private SQLiteDatabase db;

    public ChannelDao(Context context){
        MySQLiteOpenHelper helper = new MySQLiteOpenHelper(context);
        db = helper.getWritableDatabase();
    }

    public void add(List<ChannelBean> channelBeenlist){
        if (channelBeenlist==null||channelBeenlist.size()<0){
            return;
        }
        for (ChannelBean channelbean:channelBeenlist) {
            ContentValues values=new ContentValues();
            values.put("name",channelbean.getName());
            values.put("selected",channelbean.isSelect());
            db.insert("channel",null,values);
        }
    }
    public List<ChannelBean> select(){
        Cursor cursor = db.query("channel", null, null, null, null, null,null);
        List<ChannelBean> list=new ArrayList<>();
        while (cursor.moveToNext()){
            String name = cursor.getString(cursor.getColumnIndex("name"));
            int selected = cursor.getInt(cursor.getColumnIndex("selected"));
            list.add(new ChannelBean(name,selected==0?false:true));
        }
        cursor.close();
        return list;
    }
    public List<ChannelBean> tiaojianselect(){
        Cursor cursor = db.query("channel", null, "selected=?", new String[]{"1"}, null, null, null);
        List<ChannelBean> list=new ArrayList<>();
        while (cursor.moveToNext()){
            String name = cursor.getString(cursor.getColumnIndex("name"));
            int selected = cursor.getInt(cursor.getColumnIndex("selected"));
            list.add(new ChannelBean(name,selected==0?false:true));
        }
        cursor.close();
        return list;
    }
    public void delete(){
        db.delete("channel",null,null);

    }

}
