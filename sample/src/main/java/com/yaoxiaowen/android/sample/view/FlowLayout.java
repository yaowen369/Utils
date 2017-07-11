package com.yaoxiaowen.android.sample.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.yaoxiaowen.android.sample.log.LogUtils;

/**
 * Created by Administrator on 2017/7/10.
 */

public class FlowLayout extends ViewGroup{

    private static final String TAG = "FlowLayout";

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

//        ViewGroup.LayoutParams lp = getLayoutParams();

        //如果我想使用  MarginLayoutParams, 则必须 我们自己的att,来获取他
//        ViewGroup.MarginLayoutParams lp = getLayoutParams();


//        final int count = getChildCount();
//        for (int i=0; i<count; i++){
//            final View view = getChildAt(i);
//            if (view.getVisibility() == GONE){
//                continue;
//            }else {
////                measureChildWithMargins(view, widthMeasureSpec, 0, heightMeasureSpec, 0);
//                measureChild(view, widthMeasureSpec, heightMeasureSpec );
//            }
//        }




        measureChildren(widthMeasureSpec, heightMeasureSpec);

        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);

        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);

        //宽度要特殊处理
        int oneLineMaxWidth = 0;    // 所有行当中，最宽的某一行
        int childrenWidthSum = 0;   //某一行 宽度的总和
        int childrenHeightSum = 0;  // 每行加起来高度的总和
        int oneLineMaxHeight = 0;  //某一行的最大高度


        // 计算各个子View在  每行排列当中高度总和，以及最大宽度的情况
        final int count = getChildCount();
        for (int i=0; i<count ;i++){
            final  View child = getChildAt(i);

            if ((childrenWidthSum + child.getMeasuredWidth()) > widthSpecSize){ //这一行 已经满了，需要另起一行
                childrenHeightSum += oneLineMaxHeight;
                childrenWidthSum = 0;
                oneLineMaxHeight = 0;
            }

            childrenWidthSum += child.getMeasuredWidth();
            if (childrenWidthSum > oneLineMaxWidth){
                oneLineMaxWidth = childrenWidthSum;
            }

            if (child.getMeasuredHeight() > oneLineMaxHeight){
                oneLineMaxHeight = child.getMeasuredHeight();
            }
        }
        childrenHeightSum += oneLineMaxHeight;

        StringBuilder calcResult = new StringBuilder();
        calcResult.append("oneLineMaxWidth = " + oneLineMaxWidth)
                .append("\t childrenWidthSum = " + childrenWidthSum)
                .append("\t childrenHeightSum = " + childrenHeightSum)
                .append("\t oneLineMaxHeight= " + oneLineMaxHeight);

        LogUtils.i(TAG, "calcResult = " + calcResult.toString());

        //考虑 wrap_content的情况
        int resultWidth = -1;
        int resultHeight = -1;

        if (widthSpecMode==MeasureSpec.AT_MOST && widthSpecMode==MeasureSpec.AT_MOST){
            resultHeight = childrenHeightSum;
            resultWidth = oneLineMaxWidth;
        }else if (widthSpecMode==MeasureSpec.AT_MOST){
            resultWidth = oneLineMaxWidth;
        }else if (widthSpecMode==MeasureSpec.AT_MOST){
            resultHeight = childrenHeightSum;
        }


        StringBuilder result = new StringBuilder();
        result.append("\t resultHeight=" + MeasureSpec.toString(resultHeight))
                .append("\t resultWidth=" + MeasureSpec.toString(resultWidth));

        LogUtils.i(TAG, "result = " + result.toString());


        if (resultHeight < 0){
            resultHeight = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
        }

        if (resultWidth < 0){
            resultWidth = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        }

        //for test
        StringBuilder sb = new StringBuilder();
        sb.append("widthMeasureSpec=" + MeasureSpec.toString(widthMeasureSpec))
                .append("\t heightMeasureSpec" + MeasureSpec.toString(heightMeasureSpec))
                .append("\t resultHeight=" + MeasureSpec.toString(resultHeight))
                .append("\t resultWidth=" + MeasureSpec.toString(resultWidth));

        LogUtils.i(TAG, sb.toString());

        setMeasuredDimension(resultWidth, resultHeight);

//        setMeasuredDimension(getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec),
//                getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec));

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
