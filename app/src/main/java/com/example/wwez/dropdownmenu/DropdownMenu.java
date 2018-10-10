package com.example.wwez.dropdownmenu;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.wwez.myapplication.R;

import java.util.List;

public class DropdownMenu extends LinearLayout{
    private LinearLayout tabMenuView;
    private FrameLayout containerView;
    private ImageView contentView;
    private View maskView;
    private FrameLayout popupMenuViews;

    private int dividerColor = 0xffcccccc;
    private int textSelectedColor = 0xff890c85;
    private int textUnSelectedColor = 0xff111111;
    private int maskColor = 0x88888888;
    private int menuBackgroundColor = 0xffffffff;
    private int underlineColor = 0xffcccccc;
    private int menuTextSize = 14;
    private Integer menuSelectedIcon;
    private Integer menuUnSelectedIcon;
    private int currentTabPosition = -1;

    public DropdownMenu(Context context) {
        this(context, null);
    }

    public DropdownMenu(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DropdownMenu(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(VERTICAL);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.dropDownMenu);
        underlineColor = a.getColor(R.styleable.dropDownMenu_underlineColor, underlineColor);
        dividerColor = a.getColor(R.styleable.dropDownMenu_dividerColor, dividerColor);
        textSelectedColor = a.getColor(R.styleable.dropDownMenu_textSelectedColor, textSelectedColor);
        textUnSelectedColor = a.getColor(R.styleable.dropDownMenu_textUnSelectedColor, textUnSelectedColor);
        menuBackgroundColor = a.getColor(R.styleable.dropDownMenu_menuBackgroundColor, menuBackgroundColor);
        maskColor = a.getColor(R.styleable.dropDownMenu_maskColor, maskColor);
        menuTextSize = a.getDimensionPixelSize(R.styleable.dropDownMenu_menuTextSize, menuTextSize);
        menuSelectedIcon =R.drawable.ic_drop_down_selected; //a.getResourceId(R.styleable.dropDownMenu_menuSelectedIcon, menuSelectedIcon);
        menuUnSelectedIcon =R.drawable.ic_drop_down_unselected; //a.getResourceId(R.styleable.dropDownMenu_menuUnSelectedIcon, menuUnSelectedIcon);
        a.recycle();

        initViews(context);
    }

    private void initViews(Context context) {
        //创建顶部菜单
        tabMenuView = new LinearLayout(context);
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        tabMenuView.setOrientation(HORIZONTAL);
        tabMenuView.setLayoutParams(lp);
        addView(tabMenuView, 0);
        //创建水平下划线
        View underlineView = new View(context);
        underlineView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, dp2px(1.0f)));
        underlineView.setBackgroundColor(underlineColor);
        addView(underlineView, 1);
        //初始化containerView
        containerView = new FrameLayout(context);
        containerView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        addView(containerView, 2);
    }

    private int dp2px(float v) {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, v, dm);
    }

    public void setDropdownMenu(List<String> tabTexts, List<View> popupViews, ImageView mcontentView){
        contentView = mcontentView;
        if(tabTexts.size() != popupViews.size()){
            throw new IllegalArgumentException("params error");
        }
        for(int i=0; i<tabTexts.size(); i++){
            addTab(tabTexts, i);
        }
        containerView.addView(contentView, 0);
        maskView = new View(getContext());
        maskView.setLayoutParams(
                new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
        );
        maskView.setBackgroundColor(maskColor);
        maskView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                closeMenu();
            }
        });
        maskView.setVisibility(View.GONE);
        containerView.addView(maskView, 1);

        popupMenuViews = new FrameLayout(getContext());
        popupMenuViews.setVisibility(View.GONE);
        for(int i=0; i<popupViews.size();i++){
            popupViews.get(i).setLayoutParams(
                    new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                            FrameLayout.LayoutParams.WRAP_CONTENT)
            );
            popupMenuViews.addView(popupViews.get(i), i);
        }
        containerView.addView(popupMenuViews, 2);
    }

    private void addTab(List<String> tabTexts, int index) {
        final TextView tab = new TextView(getContext());
        tab.setSingleLine();
        tab.setEllipsize(TextUtils.TruncateAt.END);
        tab.setGravity(Gravity.CENTER);
        tab.setTextSize(TypedValue.COMPLEX_UNIT_PX, menuTextSize);
        tab.setLayoutParams(new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1.0f));
        tab.setTextColor(textUnSelectedColor);
        tab.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(menuUnSelectedIcon), null);
        tab.setText(tabTexts.get(index));
        tab.setPadding(dp2px(5), dp2px(12), dp2px(5), dp2px(12));
        tab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                switchMenu(tab);
            }
        });
        tabMenuView.addView(tab);
        //添加tab的分割线
        if(index < tabTexts.size() -1) {
            View splitView = new View(getContext());
            splitView.setLayoutParams(new LayoutParams(dp2px(0.5f), LayoutParams.MATCH_PARENT));
            splitView.setBackgroundColor(dividerColor);
            tabMenuView.addView(splitView);
        }
    }
    private void switchMenu(TextView targetView) {
        for(int i=0; i<tabMenuView.getChildCount(); i=i+2){
            if(targetView == tabMenuView.getChildAt(i)){
                if(currentTabPosition == i){
                    closeMenu();
                } else {
                    if(currentTabPosition == -1){
                        popupMenuViews.setVisibility(View.VISIBLE);
                        popupMenuViews.setAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.dd_menu_in));
                        maskView.setVisibility(View.VISIBLE);
                        maskView.setAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.dd_mask_in));
                        popupMenuViews.getChildAt(i/2).setVisibility(View.VISIBLE);
                    } else {
                        popupMenuViews.getChildAt(i/2).setVisibility(View.VISIBLE);
                    }
                    currentTabPosition = i;
                    ((TextView)(tabMenuView.getChildAt(i))).setTextColor(textSelectedColor);
                    ((TextView)(tabMenuView.getChildAt(i)))
                            .setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(menuSelectedIcon), null);

                }
            } else {  //其他未选中的item
                ((TextView)(tabMenuView.getChildAt(i))).setTextColor(textUnSelectedColor);
                ((TextView)(tabMenuView.getChildAt(i)))
                        .setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(menuUnSelectedIcon), null);
                popupMenuViews.getChildAt(i/2).setVisibility(View.GONE);
            }
        }
    }
    public void closeMenu(){
        if(currentTabPosition != -1){
            popupMenuViews.setVisibility(GONE);
            popupMenuViews.getChildAt(currentTabPosition / 2).setVisibility(GONE);
            TextView tv = (TextView) tabMenuView.getChildAt(currentTabPosition);
            tv.setTextColor(textUnSelectedColor);
            tv.setCompoundDrawablesWithIntrinsicBounds(null, null,
                    getResources().getDrawable(menuUnSelectedIcon), null);
            maskView.setVisibility(GONE);
            maskView.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.dd_mask_out));
            currentTabPosition = -1;
        }
    }

    public void setTabText(int i, String s) {
        TextView tv = (TextView) tabMenuView.getChildAt(i * 2);
        tv.setText(s);
    }

    public void setImageResource(int imageId) {
        contentView.setImageResource(imageId);
    }

    public boolean isShowing() {
        return currentTabPosition != -1;
    }
}
