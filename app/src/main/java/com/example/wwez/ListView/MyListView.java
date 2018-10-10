package com.example.wwez.ListView;

import android.content.Context;
import android.text.format.Time;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.wwez.myapplication.R;

public class MyListView extends ListView implements AbsListView.OnScrollListener{
    private View mview;
    private int mtotalItemCount;
    private int mlastItemCount;
    private boolean check;
    private LoadMoreListener iloadmoreListener;
    private IRefreshListener iRefreshlistener;

    private View header;
    int headerHeight;
    int firstVisibleItem;
    boolean isFlag;
    int startY;
    int topPadding;
    int scrollState;
    final int NONE = 0;
    final int PULL = 1;
    final int RELEASE = 2;
    final int REFRESH = 3;

    public MyListView(Context context) {
        super(context);
        initView(context);
    }

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public MyListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }
    private void initView(Context context) {
        mview = View.inflate(context, R.layout.list_loadmore_footer, null);
        mview.findViewById(R.id.ll).setVisibility(View.GONE);
        addFooterView(mview);

        LayoutInflater inflater = LayoutInflater.from(context);
        header = inflater.inflate(R.layout.list_header, null);
        measureView(header);
        headerHeight  = header.getMeasuredHeight();
        topPadding(-headerHeight);
        addHeaderView(header);
        this.setOnScrollListener(this);
    }

    private void topPadding(int topPadding) {
        header.setPadding(header.getPaddingLeft(), topPadding, header.getPaddingRight(), header.getPaddingBottom());
        header.invalidate();
    }

    private void measureView(View view){
        //LayoutParams are used by views to tell their parents
        //how they want to be laid out.
        //LayoutParams被view用来告诉它们的父布局它们应该被怎样安排
        ViewGroup.LayoutParams p = view.getLayoutParams();
        if(p==null){
            //两个参数:width,height
            p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        //getChildMeasureSpec:获取子View的widthMeasureSpec、heightMeasureSpec值
        int width = ViewGroup.getChildMeasureSpec(0, 0, p.width);
        int height;
        int tempHeight = p.height;
        if(tempHeight>0){
            height = MeasureSpec.makeMeasureSpec(tempHeight, MeasureSpec.EXACTLY);
        }else{
            height = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        }
        view.measure(width, height);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if(mtotalItemCount == mlastItemCount && scrollState == SCROLL_STATE_IDLE){
            if(!check) {
                check = true;
                mview.findViewById(R.id.ll).setVisibility(View.VISIBLE);
                iloadmoreListener.loadMore();
            }
        }
        this.scrollState = scrollState;
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        this.mtotalItemCount = totalItemCount;
        this.mlastItemCount = firstVisibleItem + visibleItemCount;

        this.firstVisibleItem = firstVisibleItem;
    }

    public void setLastItemHiden() {
        if (check){
            check=false;
            mview.findViewById(R.id.ll).setVisibility(View.GONE);
        }
    }

    public interface LoadMoreListener {
        void loadMore();
    }

    public interface IRefreshListener{
        void onRefresh();
    }

    public void setLoadMoreListener(LoadMoreListener loadMoreListener){
        iloadmoreListener = loadMoreListener;
    }

    public void setRefreshListener(IRefreshListener listener){
        iRefreshlistener = listener;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(firstVisibleItem == 0) {
                    isFlag = true;
                    startY = (int)ev.getY();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                onMove(ev);
                break;
            case MotionEvent.ACTION_UP:
                if(scrollState == RELEASE) {
                    scrollState = REFRESH;
                    refreshViewByState();
                    iRefreshlistener.onRefresh();
                } else if(scrollState == PULL){
                    scrollState=NONE;
                    isFlag=false;
                    refreshViewByState();
                }

                break;
        }
        return super.onTouchEvent(ev);
    }

    private void onMove(MotionEvent ev) {
        if(!isFlag){
            return;
        }
        int currentY = (int)ev.getY();
        int space = currentY - startY;
        topPadding = space - headerHeight;
        switch (scrollState) {
            case NONE:
                if(space > 0) {
                    scrollState=PULL;
                    refreshViewByState();
                }
                break;
            case PULL:
                topPadding(topPadding);
                if(space > headerHeight + 30
                        &&
                        scrollState == SCROLL_STATE_TOUCH_SCROLL
                        ){
                    scrollState=RELEASE;
                    refreshViewByState();
                }
                break;
            case RELEASE:
                topPadding(topPadding);
                if(space < headerHeight+30){
                    scrollState = PULL;
                    refreshViewByState();
                } else if(space < 0){
                    scrollState = NONE;
                    isFlag = false;
                    refreshViewByState();
                }
                break;
        }
    }

    private void refreshViewByState(){
        //提示
        TextView tips = header.findViewById(R.id.tips);
        //箭头
        ImageView arrow = header.findViewById(R.id.arrow);
        //进度条
        ProgressBar progress = header.findViewById(R.id.progress);
        //箭头的动画效果1，由0度转向180度，即箭头由朝下转为朝上
        RotateAnimation animation1 = new RotateAnimation(0, 180,
                RotateAnimation.RELATIVE_TO_SELF,0.5f,
                RotateAnimation.RELATIVE_TO_SELF,0.5f);
        animation1.setDuration(500);
        animation1.setFillAfter(true);
        //箭头的动画效果2，由180度转向0度，即箭头由朝上转为朝下
        RotateAnimation animation2 = new RotateAnimation(180, 0,
                RotateAnimation.RELATIVE_TO_SELF,0.5f,
                RotateAnimation.RELATIVE_TO_SELF,0.5f);
        animation2.setDuration(500);
        animation2.setFillAfter(true);

        switch (scrollState) {
            case NONE:                     //正常状态
                arrow.clearAnimation();    //清除箭头动画效果
                topPadding(-headerHeight); //设置header距离顶部的距离
                break;

            case PULL:                                //下拉状态
                arrow.setVisibility(View.VISIBLE);    //箭头设为可见
                progress.setVisibility(View.GONE);    //进度条设为不可见
                tips.setText("下拉可以刷新");           //提示文字设为"下拉可以刷新"
                arrow.clearAnimation();               //清除之前的动画效果，并将其设置为动画效果2
                arrow.setAnimation(animation2);
                break;

            case RELEASE:                            //下拉状态
                arrow.setVisibility(View.VISIBLE);   //箭头设为可见
                progress.setVisibility(View.GONE);   //进度条设为不可见
                tips.setText("松开可以刷新");          //提示文字设为"松开可以刷新"
                arrow.clearAnimation();              //清除之前的动画效果，并将其设置为动画效果2
                arrow.setAnimation(animation1);
                break;

            case REFRESH:                             //更新状态
                topPadding(headerHeight);                       //距离顶部的距离设置为50
                arrow.setVisibility(View.GONE);       //箭头设为不可见
                progress.setVisibility(View.VISIBLE); //进度条设为可见
                tips.setText("正在刷新...");            //提示文字设为""正在刷新..."
                arrow.clearAnimation();                //清除动画效果
                break;
        }
    }

    public void refreshComplete(){
        scrollState = NONE;   //状态设为正常状态
        isFlag = false; //标志设为false
        refreshViewByState();
        //设置提示更新时间间隔
//        Time t = new Time();
//        t.setToNow();
//        time = t.hour*60+t.minute-updateTime;
//        updateTime = t.hour*60+t.minute;
        TextView lastUpdateTime = findViewById(R.id.time);
        lastUpdateTime.setText("2分钟前更新");
    }
}
