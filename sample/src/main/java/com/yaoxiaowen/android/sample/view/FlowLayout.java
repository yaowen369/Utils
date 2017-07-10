package com.yaoxiaowen.android.sample.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2017/7/10.
 */

public class FlowLayout extends ViewGroup{

    private Context mContext;

    //在水平方向上,以及在竖直方向上,所使用的长度
    private int mHorizonticalLength = 0;
    private int mVerticalLength = 0;

    //代表该View所受到的约束,在水平和竖直上,他最大能占有的长度
    private int mHorizontalMaxLength=0;
    private int mVerticalMaxLegnth = 0;

    public FlowLayout(Context context) {
        super(context);
        mContext = context;
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

//        final LayoutParams mLp = getLayoutParams();

        int count = getChildCount();
        for (int i=0; i<count; i++){

        }

        measureChildren(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec),
                getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec));

    }

    //http://blog.csdn.net/xmxkf/article/details/51500304
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        final int count = getChildCount();

        int childMeasureWidth = 0;
        int childMeasureHeight = 0;

        int layoutWidth = 0; //容器已经占据的宽度
        int layoutHeight = 0; //容器已经占据的 高度

        int maxChildHeight = 0; //一行中子控件最高的高度,用来决定下一行在目前的基础上累加多少

        for (int i=0; i<count; i++){
            View child = getChildAt(i);
            childMeasureWidth = child.getMeasuredWidth();
            childMeasureHeight = child.getMeasuredHeight();

            // 这个地方是写错了吗？为什么是  getWidth,而不是getMeasuredWidth
            if (layoutWidth < getWidth()){
                //如果一行没有排满, 则继续向右排列
                left = layoutWidth;
                right = left + childMeasureWidth;
                top = layoutHeight;
                bottom = top + childMeasureHeight;
            }else {
                //排满后换行
                layoutWidth = 0;
                layoutHeight += maxChildHeight;
                maxChildHeight = 0;

                left = 0;
                right = left + childMeasureWidth;
                top = layoutHeight;
                bottom = top + childMeasureHeight;
            }

            layoutWidth += childMeasureWidth;
            if (childMeasureHeight > maxChildHeight){
                maxChildHeight = childMeasureHeight;
            }
            child.layout(left, top, right, bottom);
        }

    }
}
