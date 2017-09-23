package com.example.fanjinwei_firstpriject;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.fanjinwei_firstpriject.bean.Message;

import org.w3c.dom.Text;
import org.w3c.dom.ls.LSInput;

import java.util.ArrayList;

/**
 * Created by 范晋炜 on 2017/8/17 0017.
 * com.example.fanjinwei_firstpriject
 * DownLoad
 *
 * 离线下载页面
 *
 */


public class DownLoad extends AppCompatActivity{

    private ListView download_list;
    private Button checkAll;
    private Button noCheckAll;
    private MyBaseAdapter adapter;
    private ImageView download_back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //显示当前页面布局
        setContentView(R.layout.download);
        //获取资源ID
        download_list = (ListView) findViewById(R.id.download_list);
        //全选
        checkAll = (Button) findViewById(R.id.download_btn1);
        //撤销
        noCheckAll = (Button) findViewById(R.id.download_btn2);
        //返回
        download_back = (ImageView) findViewById(R.id.download_back);

        download_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        adapter = new MyBaseAdapter();
        download_list.setAdapter(adapter);

        checkAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.checkAll();
            }
        });

        noCheckAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.noCheckAll();
            }
        });
    }


    //适配器
    private class MyBaseAdapter extends BaseAdapter{

        private ArrayList<Message> list = new ArrayList<Message>();

        public MyBaseAdapter(){
            Message message = new Message("推荐");
            Message message1 = new Message("社会");
            Message message2 = new Message("国内");
            Message message3 = new Message("国际");
            Message message4 = new Message("娱乐");
            Message message5 = new Message("体育");
            Message message6 = new Message("军事");
            Message message7 = new Message("科技");
            Message message8 = new Message("财经");
            Message message9 = new Message("时尚");
            Message message10 = new Message("女人");
            Message message11 = new Message("资讯");
            Message message12 = new Message("汽车");
            Message message13 = new Message("全球");
            Message message14 = new Message("综合");
            list.add(message);
            list.add(message1);
            list.add(message2);
            list.add(message3);
            list.add(message4);
            list.add(message5);
            list.add(message6);
            list.add(message7);
            list.add(message8);
            list.add(message9);
            list.add(message10);
            list.add(message11);
            list.add(message12);
            list.add(message13);
            list.add(message14);
        }

        public void checkAll(){
            for (Message msg:list) {
                msg.isCheck = true;
            }
            notifyDataSetChanged();
        }

        public void noCheckAll(){
            for (Message msg:list) {
                msg.isCheck = false;
            }
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if(convertView == null){
                LayoutInflater inflater = LayoutInflater.from(DownLoad.this);
                convertView = inflater.inflate(R.layout.downloadmybase,null);
                holder = new ViewHolder();
                holder.textView = (TextView) convertView.findViewById(R.id.download_text);
                holder.checkBox = (CheckBox) convertView.findViewById(R.id.download_checkBox1);
                convertView.setTag(holder);
            }else{
                holder = (ViewHolder) convertView.getTag();
            }

            final Message msg = list.get(position);
            holder.textView.setText(msg.str);
            holder.checkBox.setChecked(msg.isCheck);
            //注意这里设置的不是onCheckedChangListener，还是值得思考一下的
            holder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(msg.isCheck){
                        msg.isCheck = false;
                    }else{
                        msg.isCheck = true;
                    }
                }
            });

            return convertView;
        }

        class ViewHolder{
            TextView textView;
            CheckBox checkBox;
        }
    }
}
