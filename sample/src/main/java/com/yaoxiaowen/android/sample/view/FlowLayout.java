package com.yaoxiaowen.android.sample.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Px;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.yaoxiaowen.android.sample.R;
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

        final int childCount = getChildCount();

        //只有一个child,暂且这样计算

        View childView = null;
        for (int i=0; i<childCount; i++){
            childView = getChildAt(i);
            FlowLayoutParams tmpParams = (FlowLayoutParams) getChildAt(0).getLayoutParams();
            measureChildWithMargins(childView, widthMeasureSpec, tmpParams.leftMargin+tmpParams.rightMargin,
                    heightMeasureSpec, tmpParams.topMargin+tmpParams.bottomMargin);
        }

        FlowLayoutParams childParams = (FlowLayoutParams) getChildAt(0).getLayoutParams();
//
//        FlowLayoutParams thisParams = (FlowLayoutParams) getLayoutParams();
        int actualWidth =  childView.getMeasuredWidth() + childParams.leftMargin + childParams.rightMargin;
        int actualHeight = childView.getMeasuredHeight() + childParams.topMargin + childParams.bottomMargin;

        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSepcMode = MeasureSpec.getMode(heightMeasureSpec);

        //debug  信息

        int resultWidth = -1;
        int resultHeight = -1;

        if (widthSpecMode==MeasureSpec.AT_MOST && heightSepcMode==MeasureSpec.AT_MOST){
            resultWidth = actualWidth;
            resultHeight = actualHeight;
        }else if (widthSpecMode==MeasureSpec.AT_MOST){
            resultWidth = actualWidth;
        }else if (heightSepcMode==MeasureSpec.AT_MOST){
            resultHeight = actualHeight;
        }

        if (resultWidth < 0){
            resultWidth = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        }
        if (resultHeight < 0){
            resultHeight = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
        }


        StringBuilder sb = new StringBuilder();
        sb.append("widthMeasureSpec = " + MeasureSpec.toString(widthMeasureSpec))
                .append("\t heightMeasureSpec=" + MeasureSpec.toString(heightMeasureSpec))
                .append("\t resultWidth=" + MeasureSpec.toString(resultWidth))
                .append("\t resultHeight=" + MeasureSpec.toString(resultHeight));

        LogUtils.i(TAG, sb.toString());

        StringBuilder childSb = new StringBuilder();
        childSb.append("childView.getMeasuredWidth() = " + childView.getMeasuredWidth())
                .append("\t childView.getMeasuredHeight()= " + childView.getMeasuredHeight())
                .append("\t childParams.leftMargin=" + childParams.leftMargin)
                .append("\t childParams.rightMargin = " + childParams.rightMargin)
                .append("\t childParams.topMargin=" + childParams.topMargin)
                .append("\t childParams.bottomMargin=" + childParams.bottomMargin);
        LogUtils.i(TAG, "childSb = " + childSb.toString());

        setMeasuredDimension(resultWidth, resultHeight);


////        final LayoutParams mLp = getLayoutParams();
//
////        ViewGroup.LayoutParams lp = getLayoutParams();
//
//        //如果我想使用  MarginLayoutParams, 则必须 我们自己的att,来获取他
////        ViewGroup.MarginLayoutParams lp = getLayoutParams();
//
//
//        final int childCount = getChildCount();
//        for (int i=0; i<childCount; i++){
//            final View view = getChildAt(i);
//            if (view.getVisibility() == GONE){
//                continue;
//            }else {
//                measureChildWithMargins(view, widthMeasureSpec, 0, heightMeasureSpec, 0);
//            }
//        }
//
//
//
//
////        measureChildren(widthMeasureSpec, heightMeasureSpec);
//
//        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
//        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
//
//        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
//        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
//
//        //宽度要特殊处理
//        int oneLineMaxWidth = 0;    // 所有行当中，最宽的某一行
//        int childrenWidthSum = 0;   //某一行 宽度的总和
//        int childrenHeightSum = 0;  // 每行加起来高度的总和
//        int oneLineMaxHeight = 0;  //某一行的最大高度
//
//
//        // 计算各个子View在  每行排列当中高度总和，以及最大宽度的情况
//
//        int childMeasureWidth = 0;
//        int childMeasureHeight = 0;
//        FlowLayoutParams params = null;
//
//        final int count = getChildCount();
//        for (int i=0; i<count ;i++){
//            final  View child = getChildAt(i);
//
//            params = (FlowLayoutParams)child.getLayoutParams();
//
//
//            childMeasureWidth = child.getMeasuredWidth() + params.leftMargin + params.rightMargin;
//            childMeasureHeight = child.getMeasuredHeight() + params.topMargin + params.bottomMargin;
//
//            if ((childrenWidthSum + childMeasureWidth) > widthSpecSize){ //这一行 已经满了，需要另起一行
//                childrenHeightSum += oneLineMaxHeight;
//                childrenWidthSum = 0;
//                oneLineMaxHeight = 0;
//            }
//
//            childrenWidthSum += childMeasureWidth;
//            if (childrenWidthSum > oneLineMaxWidth){
//                oneLineMaxWidth = childrenWidthSum;
//            }
//
//            if (childMeasureHeight > oneLineMaxHeight){
//                oneLineMaxHeight = childMeasureHeight;
//            }
//        }
//        childrenHeightSum += oneLineMaxHeight;
//
//        StringBuilder calcResult = new StringBuilder();
//        calcResult.append("oneLineMaxWidth = " + oneLineMaxWidth)
//                .append("\t childrenWidthSum = " + childrenWidthSum)
//                .append("\t childrenHeightSum = " + childrenHeightSum)
//                .append("\t oneLineMaxHeight= " + oneLineMaxHeight);
//
//        LogUtils.i(TAG, "calcResult = " + calcResult.toString());
//
//        //考虑 wrap_content的情况
//        int resultWidth = -1;
//        int resultHeight = -1;
//
//        if (widthSpecMode==MeasureSpec.AT_MOST && widthSpecMode==MeasureSpec.AT_MOST){
//            resultHeight = childrenHeightSum;
//            resultWidth = oneLineMaxWidth;
//        }else if (widthSpecMode==MeasureSpec.AT_MOST){
//            resultWidth = oneLineMaxWidth;
//        }else if (widthSpecMode==MeasureSpec.AT_MOST){
//            resultHeight = childrenHeightSum;
//        }
//
//
//        StringBuilder result = new StringBuilder();
//        result.append("\t resultHeight=" + MeasureSpec.toString(resultHeight))
//                .append("\t resultWidth=" + MeasureSpec.toString(resultWidth));
//
//        LogUtils.i(TAG, "result = " + result.toString());
//
//
//        if (resultHeight < 0){
//            resultHeight = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
//        }
//
//        if (resultWidth < 0){
//            resultWidth = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
//        }
//
//        //for test
//        StringBuilder sb = new StringBuilder();
//        sb.append("widthMeasureSpec=" + MeasureSpec.toString(widthMeasureSpec))
//                .append("\t heightMeasureSpec" + MeasureSpec.toString(heightMeasureSpec))
//                .append("\t resultHeight=" + MeasureSpec.toString(resultHeight))
//                .append("\t resultWidth=" + MeasureSpec.toString(resultWidth));
//
//        LogUtils.i(TAG, sb.toString());
//
//        setMeasuredDimension(resultWidth, resultHeight);
//
////        setMeasuredDimension(getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec),
////                getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec));

    }

    //http://blog.csdn.net/xmxkf/article/details/51500304
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {

        final int count = getChildCount();
        FlowLayoutParams flowLp = null;
        for (int i = 0; i< count; i++){
            View child = getChildAt(i);
            flowLp = (FlowLayoutParams)child.getLayoutParams();

            child.layout(left+flowLp.leftMargin, top+flowLp.topMargin, child.getMeasuredWidth(), child.getMeasuredHeight());
        }

//        final int count = getChildCount();
//
//        int childMeasureWidth = 0;
//        int childMeasureHeight = 0;
//
//        int layoutWidth = 0; //容器已经占据的宽度
//        int layoutHeight = 0; //容器已经占据的 高度
//
//        int maxChildHeight = 0; //一行中子控件最高的高度,用来决定下一行在目前的基础上累加多少
//
//        FlowLayoutParams params = null;
//
//        for (int i=0; i<count; i++){
//            View child = getChildAt(i);
//            params = (FlowLayoutParams)child.getLayoutParams();
//
//
//            childMeasureWidth = child.getMeasuredWidth() + params.leftMargin + params.rightMargin;
//            childMeasureHeight = child.getMeasuredHeight() + params.topMargin + params.bottomMargin;
//
//            // 这个地方是写错了吗？为什么是  getWidth,而不是getMeasuredWidth
//            if (layoutWidth < getWidth()){
//                //如果一行没有排满, 则继续向右排列
//                left = layoutWidth;
//                right = left + childMeasureWidth;
//                top = layoutHeight;
//                bottom = top + childMeasureHeight;
//            }else {
//                //排满后换行
//                layoutWidth = 0;
//                layoutHeight += maxChildHeight;
//                maxChildHeight = 0;
//
//                left = 0;
//                right = left + childMeasureWidth;
//                top = layoutHeight;
//                bottom = top + childMeasureHeight;
//            }
//
//            layoutWidth += childMeasureWidth;
//            if (childMeasureHeight > maxChildHeight){
//                maxChildHeight = childMeasureHeight;
//            }
//            child.layout(left, top,left+childMeasureWidth, top+childMeasureHeight);
//        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
//        return super.generateLayoutParams(attrs);
        LogUtils.i(TAG, "generateLayoutParams(AttributeSet attrs)");
        return new FlowLayoutParams(mContext, attrs);
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
//        return super.generateLayoutParams(p);
        LogUtils.i(TAG, "generateLayoutParams(LayoutParams p)");
        return new FlowLayoutParams(p);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
//        return super.generateDefaultLayoutParams();
        LogUtils.i(TAG, "generateDefaultLayoutParams()");
        return new FlowLayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }


    @Override
    protected boolean checkLayoutParams(LayoutParams p) {
        boolean result =  (p!=null && (p instanceof  FlowLayoutParams));
        LogUtils.i(TAG, "checkLayoutParams() 方法被调用 -> p=" + p + "\t result=" + result);
        return result;
    }


    public static class FlowLayoutParams extends MarginLayoutParams{
        public FlowLayoutParams(Context context, AttributeSet attrs) {
            super(context, attrs);
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FlowLayout);

            a.recycle();
        }

        public FlowLayoutParams(@Px int width, @Px int height) {
            super(width, height);
        }

        public FlowLayoutParams(MarginLayoutParams source) {
            super(source);
        }

        public FlowLayoutParams(LayoutParams source) {
            super(source);
        }
    }
}
