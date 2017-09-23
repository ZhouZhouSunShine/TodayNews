package com.example.fanjinwei_firstpriject.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fanjinwei_firstpriject.R;
import com.example.fanjinwei_firstpriject.adapter.MyBaseAdapter;
import com.example.fanjinwei_firstpriject.bean.MenuInFo;
import com.google.gson.Gson;
import com.limxing.xlistview.view.XListView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

/**
 * Created by 范晋炜 on 2017/8/9 0009.
 * com.example.fanjinwei_firstpriject
 * MyFragment
 */


public class ShiShangFragment extends Fragment implements XListView.IXListViewListener {

    private TextView textView;
    private String text;
    private XListView xLV;
    private int index = 1;
    private boolean flag;
    private MyBaseAdapter adapter;
    private List<MenuInFo.ResultBean.DataBean> results;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //传值注释掉
        /*Bundle arguments = getArguments();
        text = arguments.getString("text");*/
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.myfragment,container,false);
        //传值标题的资源ID
        /*textView = (TextView) view.findViewById(R.id.fragment_textView);

        textView.setText(text);*/

        return view;
    }

    //获取视图  里面有逻辑的操作

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //得到视图
        View view = getView();
        xLV = (XListView) view.findViewById(R.id.fragment_list);
        xLV.setPullLoadEnable(true);
        xLV.setXListViewListener(this);
        //调用添加数据的方法
        getData();
    }


    //上拉刷新
    @Override
    public void onRefresh() {
        ++index;
        flag = true;
        getData();
        xLV.stopRefresh(true);
    }
    //下拉加载
    @Override
    public void onLoadMore() {
        ++index;
        flag = false;
        getData();
        xLV.stopLoadMore();
    }

    //加载数据的方法
    public void getData(){
        RequestParams params = new RequestParams("http://v.juhe.cn/toutiao/index");
        params.addBodyParameter("key","da161b40c11f8792fa176785dca00c0f");
        params.addBodyParameter("type","shishang");
        //请求数据
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                MenuInFo menuInFo = gson.fromJson(result, MenuInFo.class);
                List<MenuInFo.ResultBean.DataBean> results = menuInFo.getResult().getData();
                //适配数据
                if(adapter == null){
                    adapter = new MyBaseAdapter(getActivity(),results,xLV);
                    xLV.setAdapter(adapter);
                }else{
                    adapter.loadMore(getActivity(),results,flag);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }
}
