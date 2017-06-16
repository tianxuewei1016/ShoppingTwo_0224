package com.atguigu.shoppingtwo_0224.shoppingcart.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.atguigu.shoppingtwo_0224.R;
import com.atguigu.shoppingtwo_0224.activity.MainActivity;
import com.atguigu.shoppingtwo_0224.activity.MyApplication;
import com.atguigu.shoppingtwo_0224.base.BaseFragment;
import com.atguigu.shoppingtwo_0224.home.bean.GoodsBean;
import com.atguigu.shoppingtwo_0224.shoppingcart.adapter.ShoppingCartAdapter;
import com.atguigu.shoppingtwo_0224.shoppingcart.utils.CartStorage;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 作者：田学伟 on 2017/6/11 15:17
 * QQ：93226539
 * 作用：购物车
 */

public class ShoppingCartFragment extends BaseFragment {


    @InjectView(R.id.tv_shopcart_edit)
    TextView tvShopcartEdit;
    @InjectView(R.id.recyclerview)
    RecyclerView recyclerview;
    @InjectView(R.id.checkbox_all)
    CheckBox checkboxAll;
    @InjectView(R.id.tv_shopcart_total)
    TextView tvShopcartTotal;
    @InjectView(R.id.btn_check_out)
    Button btnCheckOut;
    @InjectView(R.id.ll_check_all)
    LinearLayout llCheckAll;
    @InjectView(R.id.cb_all)
    CheckBox cbAll;
    @InjectView(R.id.btn_delete)
    Button btnDelete;
    @InjectView(R.id.btn_collection)
    Button btnCollection;
    @InjectView(R.id.ll_delete)
    LinearLayout llDelete;
    @InjectView(R.id.iv_empty)
    ImageView ivEmpty;
    @InjectView(R.id.tv_empty_cart_tobuy)
    TextView tvEmptyCartTobuy;
    @InjectView(R.id.ll_empty_shopcart)
    LinearLayout llEmptyShopcart;
    private ArrayList<GoodsBean> datas;
    private ShoppingCartAdapter adapter;

    //编辑状态
    private static final int ACTION_EDIT = 1;
    //完成状态
    private static final int ACTION_COMPLETE = 2;

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_shopping_cart, null);
        ButterKnife.inject(this, view);
        //设置tag
        tvShopcartEdit.setTag(ACTION_EDIT);
        return view;
    }

    /**
     * 1.把数据绑定到控件上的时候，重新该方法
     * 2.联网请求，把得到的数据绑定到视图上
     */
    @Override
    public void initData() {
        super.initData();
        showData();
    }

    private void showData() {
        datas = CartStorage.getInstance(MyApplication.getContext()).getAllData();
        if (datas != null && datas.size() > 0) {
            //有数据,布局隐藏
            llEmptyShopcart.setVisibility(View.GONE);
            //设置适配器
            adapter = new ShoppingCartAdapter(mContext, datas, checkboxAll, tvShopcartTotal, cbAll);
            recyclerview.setAdapter(adapter);
            //设置布局管理器
            recyclerview.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        } else {
            //没有数据-空布局显示
            llEmptyShopcart.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @OnClick({R.id.tv_shopcart_edit, R.id.checkbox_all, R.id.btn_check_out, R.id.cb_all, R.id.btn_delete, R.id.btn_collection, R.id.tv_empty_cart_tobuy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_shopcart_edit:
//                Toast.makeText(mContext, "编辑", Toast.LENGTH_SHORT).show();
                int tag = (int) tvShopcartEdit.getTag();
                if (tag == ACTION_EDIT) {//编辑
                    //切换完成状态
                    showDelete();
                } else {
                    hideDelete();
                }

                break;
            case R.id.checkbox_all:
//                Toast.makeText(mContext, "全选", Toast.LENGTH_SHORT).show();
                boolean checked = checkboxAll.isChecked();
                //设置是否选择
                adapter.checkAll_none(checked);
                //重新计算价格

                break;
            case R.id.btn_check_out:
                Toast.makeText(mContext, "去结算", Toast.LENGTH_SHORT).show();
                break;
            case R.id.cb_all:
//                Toast.makeText(mContext, "删除的全选", Toast.LENGTH_SHORT).show();
                boolean isCheckedAll = cbAll.isChecked();
                //设置是否选择
                adapter.checkAll_none(isCheckedAll);
                break;
            case R.id.btn_delete:
//                Toast.makeText(mContext, "删除", Toast.LENGTH_SHORT).show();
                adapter.deleteData();

                adapter.checkAll();
                showEempty();
                break;
            case R.id.btn_collection:
                Toast.makeText(mContext, "收藏", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_empty_cart_tobuy:
//                Toast.makeText(mContext, "去逛逛", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContext, MainActivity.class);
                intent.putExtra("checkid",R.id.rb_home);
                startActivity(intent);
                break;
        }
    }

    private void showEempty() {
        if (datas == null || datas.size() == 0) {
            llEmptyShopcart.setVisibility(View.VISIBLE);
        }
    }

    private void showDelete() {


        tvShopcartEdit.setText("完成");
        tvShopcartEdit.setTag(ACTION_COMPLETE);
        //隐藏结算
        llCheckAll.setVisibility(View.GONE);
        //显示删除按钮部分
        llDelete.setVisibility(View.VISIBLE);

        adapter.checkAll_none(false);

        adapter.checkAll();

        adapter.showTotalPrice();
    }

    private void hideDelete() {
        tvShopcartEdit.setText("编辑");
        tvShopcartEdit.setTag(ACTION_EDIT);
        llCheckAll.setVisibility(View.VISIBLE);
        llDelete.setVisibility(View.GONE);

        adapter.checkAll_none(true);

        adapter.checkAll();

        adapter.showTotalPrice();
    }

    @Override
    public void onResume() {
        super.onResume();
        showData();
    }
}
