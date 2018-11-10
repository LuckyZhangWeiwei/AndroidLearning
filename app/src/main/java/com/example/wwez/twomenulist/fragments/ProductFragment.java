package com.example.wwez.twomenulist.fragments;


import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SectionIndexer;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wwez.myapplication.R;
import com.example.wwez.twomenulist.adapter.LeftAdapter;
import com.example.wwez.twomenulist.adapter.RightAdapter;
import com.example.wwez.twomenulist.entity.Product;
import com.example.wwez.twomenulist.entity.ViewBus;
import com.example.wwez.twomenulist.utils.Parser;
import com.example.wwez.twomenulist.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductFragment extends Fragment implements SectionIndexer , View.OnClickListener
{
    private ListView rightListView;          //右侧商品listview
    private ListView leftListView;   //左侧--商品类型listview
    private RightAdapter rightAdapter;   //右侧adapter
    private LeftAdapter leftAdapter;  //左侧adapter
    private ImageView shopCart;//购物车
    private View cartFrame;
    private TextView buyNumView;//购物车上的数量标签
    private TextView mPriceSumView;
//    private View cartView;
    private TextView selectedView;
    private View titleLayout;
    private TextView title;
//    private ListView popuListView;
    private int lastFirstVisibleItem = -1; //上次第一个可见元素，用于滚动时记录标识
    private List<Product> mProductList;
    private ViewBus mViewBus = new ViewBus();

    /**
     * 在这里虚构个商家id，其主要目的是不想让大家走更多的弯路
     */
//    private int businessId = 0;
    /**
     * 上次选中的左侧菜单
     */
    private View lastView;

    private PopupWindow popupWindow;

    public ProductFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product, container, false);
        initView(view);
        initClick();
        initData();
        return view;
    }

    private void initView(View view) {
        titleLayout = view.findViewById(R.id.title_layout);
        title = view.findViewById(R.id.title_layout_catalog);
        rightListView = view.findViewById(R.id.menu_lvmenu);
        shopCart = view.findViewById(R.id.iv_add_cart);//购物车
        cartFrame = view.findViewById(R.id.cart_frame);
        buyNumView = view.findViewById(R.id.tv_count_price);//购物车上的数量标签
        mPriceSumView = view.findViewById(R.id.price_sum_view);
        selectedView = view.findViewById(R.id.selected_view);
        leftListView = view.findViewById(R.id.side_menu_lv);

        mViewBus.setBuyNumView(buyNumView);
        mViewBus.setmPriceSumView(mPriceSumView);
        mViewBus.setSelectedView(selectedView);
        mViewBus.setCartFrame(cartFrame);
        mViewBus.setmShopCart(shopCart);
        mViewBus.setmFragmentActivity(getActivity());
    }

    private void initClick() {
        cartFrame.setOnClickListener(this);
        selectedView.setOnClickListener(this);
        leftListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(lastView != null) {
                    lastView.setBackgroundColor(getResources().getColor(R.color.frament_tab_color));
                }
                //设置选中颜色为白色
                view.setBackgroundColor(getResources().getColor(R.color.white));
                //点击左侧，右侧滚动到对应的位置
                TextView section_tv = view.findViewById(R.id.section_tv);
                int location = rightAdapter.getPositionForSection((Integer.parseInt(section_tv.getText().toString())));
                if (location != -1) {
                    rightListView.setSelection(location);
                }
                lastView = view;
            }
        });
    }

    private void initData() {
        mProductList = Parser.getCateProductList(getActivity());
        rightAdapter = new RightAdapter(getActivity(), mProductList, mViewBus);
        leftAdapter = new LeftAdapter(getActivity(), mProductList);
        rightListView.setAdapter(rightAdapter);
        leftListView.setAdapter(leftAdapter);
        rightListView.setOnScrollListener(mOnScrollListener);
    }

    /********************SectionIndexer interface********************************/
    @Override
    public Object[] getSections() {
        return null;
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        for(int i = 0; i < mProductList.size(); i++) {
            int section = mProductList.get(i).getSeleteId();
            if(section == sectionIndex) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int getSectionForPosition(int position) {
        return mProductList.get(position).getSeleteId();
    }
    /********************SectionIndexer interface********************************/

    /**********************click interface***************************************/
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cart_frame:
                initPopWindow();
                break;
            case R.id.iv_add_cart:
                initPopWindow();
                break;
        }
    }
    /**********************click interface***************************************/

    private void initPopWindow() {
        View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.popu_cart, null);
        int height = Utils.getScreenHeight(getActivity()) - (cartFrame.getHeight()*2) + 10;
        popupWindow = new PopupWindow(contentView, Utils.getScreenWidth(getActivity()),
                height);
        popupWindow.setContentView(contentView);

        popupWindow.setFocusable(true);
        popupWindow.setTouchInterceptor(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
        View empView = contentView.findViewById(R.id.content_view);
        empView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        ColorDrawable dw = new ColorDrawable(Color.parseColor("#b0000000"));
        popupWindow.setBackgroundDrawable(dw);
        popupWindow.getBackground().setAlpha(100);
        popupWindow.setAnimationStyle(R.style.mypopwindow_anim_style);
        popupWindow.showAsDropDown(cartFrame);

        mViewBus.setPopupWindow(popupWindow);
    }
    private AbsListView.OnScrollListener mOnScrollListener = new AbsListView.OnScrollListener(){

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {

        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            try{
                int section = getSectionForPosition(firstVisibleItem);  //通过该item的位置得到索引的位置
                int nextSection = 0;
                if(mProductList.size() > 1) {
                    nextSection = getSectionForPosition(firstVisibleItem + 1);
                }
                int nextSecPosition = getPositionForSection(nextSection);  //根据分类列的索引号获得该序列的首个位置
                if(firstVisibleItem != lastFirstVisibleItem) {
                    ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams)titleLayout.getLayoutParams();
                    params.topMargin = 0;
                    titleLayout.setLayoutParams(params);
                    title.setText(mProductList.get(getPositionForSection(section)).getType());
                    if (lastView != null) {
                        lastView.setBackgroundColor(getResources().getColor(R.color.frament_tab_color));
                    }
                    lastView = leftListView.getChildAt(section);
                    lastView.setBackgroundColor(getResources().getColor(R.color.white));
                }
                if(nextSecPosition == firstVisibleItem + 1) {
                    View childView = view.getChildAt(0);
                    if (childView != null) {
                        int titleHeight = titleLayout.getHeight();
                        int bottom = childView.getBottom();
                        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams)titleLayout.getLayoutParams();
                        if (bottom < titleHeight) {
                            float pushedDistance = bottom - titleHeight;
                            params.topMargin = (int) pushedDistance;
                            titleLayout.setLayoutParams(params);
                        } else {
                            if (params.topMargin != 0) {
                                params.topMargin = 0;
                                titleLayout.setLayoutParams(params);
                            }
                        }
                    }
                }
                lastFirstVisibleItem = firstVisibleItem;
            }catch (NullPointerException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
}
