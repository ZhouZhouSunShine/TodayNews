package com.example.fanjinwei_firstpriject.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.fanjinwei_firstpriject.R;
import com.example.fanjinwei_firstpriject.WebViewActivity;
import com.example.fanjinwei_firstpriject.bean.MenuInFo;
import com.limxing.xlistview.view.XListView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by 范晋炜 on 2017/8/15 0015.
 * com.example.fanjinwei_firstpriject.adapter
 * MyBaseAdapter
 */


public class MyBaseAdapter extends BaseAdapter {

    private Context context;
    private List<MenuInFo.ResultBean.DataBean> results;
    private static final int TYPE_0 = 0;
    private static final int TYPE_1 = 1;
    private XListView xLV;
    private LayoutInflater mLayoutInflater;
    private PopupWindow mPopupWindow;
    private TextView deleteView;
    private ImageView closeView;

    public MyBaseAdapter(Context context, List<MenuInFo.ResultBean.DataBean> results, XListView xLV) {
        this.context = context;
        this.results = results;
        this.xLV = xLV;
        mLayoutInflater = LayoutInflater.from(context);
        //调用一个方法
        initPopView();

    }

    public void getImage(String path, ImageView imageView) {
        //自定义配置
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)//让图片进行内存缓存
                .cacheOnDisk(true)//让图片进行sdcard缓存
                .showImageForEmptyUri(R.mipmap.aa)//图片地址有误
                .showImageOnFail(R.mipmap.bb)//当图片加载出现错误的时候显示的图片
                .showImageOnLoading(R.mipmap.cc)//图片正在加载的时候显示的图片
                .build();

        //ImageLoader.getInstance().loadImage(path,options,new Ima);//加载图片
        //参数1：加载的图片地址
        //参数2：将图片设置到那个图片控件上面
        //参数3：加载图片配置选项，意思是指明对这张图片的是否进行缓存(内存、sdcard)
        ImageLoader.getInstance().displayImage(path, imageView, options);
    }

    //加载更多数据的方法
    public void loadMore(Context context, List<MenuInFo.ResultBean.DataBean> data, boolean flag) {
        for (MenuInFo.ResultBean.DataBean bean : data) {
            if (flag) {
                results.add(0, bean);
            } else {
                results.add(bean);
            }
        }
    }

    @Override
    public int getCount() {
        return results != null ? results.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return results.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        if (position % 2 == 0) {
            return TYPE_0;
        } else {
            return TYPE_1;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        int type = getItemViewType(position);

        ViewHolder1 holder1 = null;
        ViewHolder2 holder2 = null;

        switch (type) {
            case 0: {
                if (convertView == null) {
                    convertView = mLayoutInflater.inflate(R.layout.item1_type1, null);
                    holder1 = new ViewHolder1();
                    /*holder1.newItem1_text = (TextView) convertView.findViewById(R.id.newItem1_text);
                    holder1.newItem1_image1 = (ImageView) convertView.findViewById(R.id.newItem1_image1);
                    holder1.newItem1_image2 = (ImageView) convertView.findViewById(R.id.newItem1_image2);
                    holder1.newItem1_more = (ImageView) convertView.findViewById(R.id.newItem1_more);
                    holder1.newItem1_name = (TextView) convertView.findViewById(R.id.newItem1_name);
                    holder1.newItem1_data = (TextView) convertView.findViewById(R.id.newItem1_data);*/
                    holder1.finally1_title = (TextView) convertView.findViewById(R.id.finally1_title);
                    holder1.finally1_author_name = (TextView) convertView.findViewById(R.id.finally1_author_name);
                    holder1.finally1_category = (TextView) convertView.findViewById(R.id.finally1_category);
                    holder1.finally1_data = (TextView) convertView.findViewById(R.id.finally1_data);
                    holder1.finally1_image = (ImageView) convertView.findViewById(R.id.finally1_image);
                    holder1.finally1_pop = (ImageView) convertView.findViewById(R.id.finally1_pop);
                    convertView.setTag(holder1);
                } else {
                    holder1 = (ViewHolder1) convertView.getTag();
                }
                /*holder1.newItem1_text.setText(results.get(position).getTitle());
                holder1.newItem1_name.setText(results.get(position).getAuthor_name());
                holder1.newItem1_data.setText(results.get(position).getDate());*/
                /*//popupwindow的
                holder1.newItem1_more.setOnClickListener(new PopAction(position));
                getImage(results.get(position).getThumbnail_pic_s(), holder1.newItem1_image1);
                getImage(results.get(position).getThumbnail_pic_s02(), holder1.newItem1_image2);*/
                holder1.finally1_title.setText(results.get(position).getTitle());
                holder1.finally1_author_name.setText(results.get(position).getAuthor_name());
                holder1.finally1_data.setText(results.get(position).getDate());
                holder1.finally1_category.setText(results.get(position).getCategory());
                holder1.finally1_pop.setOnClickListener(new PopAction(position));
                getImage(results.get(position).getThumbnail_pic_s(),holder1.finally1_image);
            }
            break;

            case 1: {
                if (convertView == null) {
                    convertView = convertView.inflate(context, R.layout.item_type3, null);
                    holder2 = new ViewHolder2();
                    /*holder2.newItem2_text = (TextView) convertView.findViewById(R.id.newItem2_text);
                    holder2.newItem2_name = (TextView) convertView.findViewById(R.id.newItem2_name);
                    holder2.newItem2_data = (TextView) convertView.findViewById(R.id.newItem2_data);
                    holder2.newItem2_image1 = (ImageView) convertView.findViewById(R.id.newItem2_image1);
                    holder2.newItem2_image2 = (ImageView) convertView.findViewById(R.id.newItem2_image2);
                    holder2.newItem2_image3 = (ImageView) convertView.findViewById(R.id.newItem2_image3);
                    holder2.newItem2_more = (ImageView) convertView.findViewById(R.id.newItem2_more);*/
                    holder2.finally3_author_name = (TextView) convertView.findViewById(R.id.finally3_author_name);
                    holder2.finally3_category = (TextView) convertView.findViewById(R.id.finally3_category);
                    holder2.finally3_data = (TextView) convertView.findViewById(R.id.finally3_data);
                    holder2.finally3_title = (TextView) convertView.findViewById(R.id.finally3_title);
                    holder2.finally3_image1 = (ImageView) convertView.findViewById(R.id.finally3_image1);
                    holder2.finally3_image2 = (ImageView) convertView.findViewById(R.id.finally3_image2);
                    holder2.finally3_image3 = (ImageView) convertView.findViewById(R.id.finally3_image3);
                    holder2.finally3_pop = (ImageView) convertView.findViewById(R.id.finally3_pop);
                    convertView.setTag(holder2);
                } else {
                    holder2 = (ViewHolder2) convertView.getTag();
                }
                /*holder2.newItem2_text.setText(results.get(position).getTitle());
                holder2.newItem2_name.setText(results.get(position).getAuthor_name());
                holder2.newItem2_data.setText(results.get(position).getDate());
                //popupwindow的
                holder2.newItem2_more.setOnClickListener(new PopAction(position));
                getImage(results.get(position).getThumbnail_pic_s(), holder2.newItem2_image1);
                getImage(results.get(position).getThumbnail_pic_s02(), holder2.newItem2_image2);
                getImage(results.get(position).getThumbnail_pic_s03(), holder2.newItem2_image3);*/
                holder2.finally3_author_name.setText(results.get(position).getAuthor_name());
                holder2.finally3_category.setText(results.get(position).getCategory());
                holder2.finally3_data.setText(results.get(position).getDate());
                holder2.finally3_title.setText(results.get(position).getTitle());
                getImage(results.get(position).getThumbnail_pic_s(),holder2.finally3_image1);
                getImage(results.get(position).getThumbnail_pic_s02(),holder2.finally3_image2);
                getImage(results.get(position).getThumbnail_pic_s03(),holder2.finally3_image3);
                holder2.finally3_pop.setOnClickListener(new PopAction(position));
            }
            break;
        }
        xLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context, WebViewActivity.class);
                intent.putExtra("url", results.get(position - 1).getUrl());
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    class ViewHolder1{
        TextView finally1_title,finally1_category,finally1_author_name,finally1_data;
        ImageView finally1_image,finally1_pop;
    }

    class ViewHolder2{
        TextView finally3_title,finally3_category,finally3_author_name,finally3_data;
        ImageView finally3_image1,finally3_image2,finally3_image3,finally3_pop;
    }

    class ViewHolder3{

    }

//    class ViewHolder1 {
//        TextView newItem1_text, newItem1_name,newItem1_data;
//        ImageView newItem1_image1, newItem1_image2,newItem1_more;
//    }

//    class ViewHolder2 {
//        TextView newItem2_text, newItem2_name,newItem2_data;
//        ImageView newItem2_image1, newItem2_image2,newItem2_image3,newItem2_more;
//    }

    class PopAction implements View.OnClickListener {

        private int position;

        public PopAction(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            //操作对应position的数据
            int[] array = new int[2];
            v.getLocationOnScreen(array);
            int x = array[0];
            int y = array[1];
            //调用方法
            showPop(v, position, x, y);
        }
    }

    private void initPopView() {
        View popwindowLayout = mLayoutInflater.inflate(R.layout.popupwindow, null);
        mPopupWindow = new PopupWindow(popwindowLayout, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#000000")));
        mPopupWindow.setAnimationStyle(R.style.popWindowAnimation);

        //知道popwindow中间的控件 ,去做点击
        deleteView = (TextView) popwindowLayout.findViewById(R.id.pop_delete);
        closeView = (ImageView) popwindowLayout.findViewById(R.id.pop_close);
    }


    private void showPop(View parent, final int position, int x, int y) {

        //根据view的位置显示popupwindow的位置
        mPopupWindow.showAtLocation(parent, 0, x, y);

        //根据view的位置popupwindow将显示到他的下面 , 可以通过x ,y 参数修正这个位置
        // mPopupWindow.showAsDropDown(parent,0,-50);

        //设置popupwindow可以获取焦点,不获取焦点的话 popupwiondow点击无效
        mPopupWindow.setFocusable(true);

        //点击popupwindow的外部,popupwindow消失
        mPopupWindow.setOutsideTouchable(true);

        deleteView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                results.remove(position);
                notifyDataSetChanged();
                if (mPopupWindow.isShowing()) {
                    mPopupWindow.dismiss();
                }
            }
        });

        closeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPopupWindow.isShowing()) {
                    mPopupWindow.dismiss();
                }
            }
        });


    }


}
