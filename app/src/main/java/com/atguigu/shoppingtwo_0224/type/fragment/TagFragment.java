package com.atguigu.shoppingtwo_0224.type.fragment;

import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.atguigu.shoppingtwo_0224.base.BaseFragment;

/**
 * 作者：田学伟 on 2017/6/16 16:06
 * QQ：93226539
 * 作用：
 */

public class TagFragment extends BaseFragment{
    private TextView textView;

    @Override
    public View initView() {
        textView = new TextView(mContext);
        textView.setTextSize(20);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.RED);
        return textView;
    }

    /**
     * 1.把数据绑定到控件上的时候，重新该方法
     * 2.联网请求，把得到的数据绑定到视图上
     */
    @Override
    public void initData() {
        super.initData();
        Log.e("TAG", "Tag的数据被初始化了...");
        textView.setText("Tag内容");
    }
}


