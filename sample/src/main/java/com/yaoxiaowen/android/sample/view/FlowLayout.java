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
 * @see CustomLayout
 * 根据该类修改而来
 */
public class FlowLayout extends ViewGroup{

    private static final String TAG = "FlowLayout";

    private Context mContext;

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

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);

        int layoutWidth = 0;
        int layoutHeight = 0;


        int cWidth = 0;
        int cHeight = 0;
        final int count = getChildCount();

        for (int i=0; i<count; i++){
            View child = getChildAt(i);
            measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, 0);
        }

        FlowLayoutParams params = null;
        //处理宽度
        if (widthMode == MeasureSpec.EXACTLY){
            layoutWidth = sizeWidth;
        }else {
            for (int i=0; i<count; i++){
                View child = getChildAt(i);
                cWidth = child.getMeasuredWidth();
                params = (FlowLayoutParams)child.getLayoutParams();
                int marginWidth = cWidth + params.leftMargin + params.rightMargin + getPaddingLeft() + getPaddingRight();
                layoutWidth = (layoutWidth > marginWidth ? layoutWidth : marginWidth);

                layoutWidth = Math.min(layoutWidth, sizeWidth);
            }
        }

        //处理 高度
        if (heightMode == MeasureSpec.EXACTLY){
            layoutHeight = sizeHeight;
        }else {
            for (int i=0; i<count; i++){
                View child = getChildAt(i);
                cHeight = child.getMeasuredHeight();
                params = (FlowLayoutParams)child.getLayoutParams();
                int marginHeight = cHeight + params.topMargin + params.bottomMargin + getPaddingTop() + getPaddingBottom();
                layoutHeight = (marginHeight>layoutHeight) ? marginHeight : layoutHeight;

                layoutHeight = Math.min(layoutHeight, sizeHeight);
            }
        }
        StringBuilder debugInfo = new StringBuilder();
        debugInfo.append("widthMeasureSpec = " + MeasureSpec.toString(widthMeasureSpec))
                .append("\t heightMeasureSpec = " + MeasureSpec.toString(heightMeasureSpec))
                .append("\t layoutWidth=" + layoutWidth)
                .append("\t layoutHeight=" + layoutHeight)
                .append("\t getPaddingTop=" + getPaddingTop())
                .append("\t getPaddingBottom" + getPaddingBottom())
                .append("\t getPaddingLeft=" + getPaddingLeft())
                .append("\t getPaddingRight" + getPaddingRight());

        LogUtils.i(TAG, debugInfo.toString());
        setMeasuredDimension(layoutWidth, layoutHeight);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {

        final int count = getChildCount();
        int childMeasureWidth = 0;
        int childMeasureHeight = 0;
        FlowLayoutParams params = null;



        for (int i=0; i<count; i++){
            View child = getChildAt(i);
            childMeasureWidth = child.getMeasuredWidth();
            childMeasureHeight = child.getMeasuredHeight();

            params = (FlowLayoutParams)child.getLayoutParams();
            switch (params.position){
                case FlowLayoutParams.POSITION_MIDDLE:  //中间
                    left = (getWidth() - childMeasureWidth) / 2 - params.rightMargin + params.leftMargin + getPaddingLeft();
                    top = (getHeight() - childMeasureHeight) / 2 + params.topMargin - params.bottomMargin + getPaddingTop();
                    break;
                case FlowLayoutParams.POSITION_LEFT:  //左上
                    left = 0 + params.leftMargin + getPaddingLeft();
                    top=0 + params.topMargin + getPaddingTop();
                    break;
                case FlowLayoutParams.POSITION_RIGHT: //右上
                    left = getWidth() - childMeasureWidth - params.rightMargin - getPaddingRight();
                    top = 0 + params.topMargin + getPaddingTop();
                    break;
                case FlowLayoutParams.POSITION_BOTTOM: //左下
                    left = 0 + params.leftMargin - getPaddingLeft();
                    top = getHeight() - childMeasureHeight - params.bottomMargin - getPaddingBottom();
                    break;
                case FlowLayoutParams.POSTION_RIGHT_AND_BOTTOM:  //右下角
                    left = getWidth() - childMeasureWidth - params.rightMargin - getPaddingRight();
                    top = getHeight() - childMeasureHeight - params.bottomMargin - getPaddingBottom();
                    break;
                default:
                    LogUtils.e(TAG, "onLayout() switch错误的出现了默认值");
            }

            StringBuilder debugInfo = new StringBuilder();
            debugInfo.append(getPosition(params.position)  + "\t");
            debugInfo.append("left = " + left)
                     .append("\t top=" + top)
                    .append("\t childMeasureWidth= " + childMeasureWidth)
                    .append("\t childMeasureHeight=" + childMeasureHeight);

            debugInfo.append("\t getPaddingTop=" + getPaddingTop())
                    .append("\t getPaddingBottom" + getPaddingBottom())
                    .append("\t getPaddingLeft=" + getPaddingLeft())
                    .append("\t getPaddingRight" + getPaddingRight());

            LogUtils.i(TAG, debugInfo.toString());
            child.layout(left, top, left+childMeasureWidth, top+childMeasureHeight);
        }
    }


    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new FlowLayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new FlowLayoutParams(p);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new FlowLayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }


    @Override
    protected boolean checkLayoutParams(LayoutParams p) {
        return p instanceof  FlowLayoutParams;
    }

    public static class FlowLayoutParams extends MarginLayoutParams{
        public static final int POSITION_MIDDLE = 0;
        public static final int POSITION_LEFT = 1;
        public static final int POSITION_RIGHT = 2;
        public static final int POSITION_BOTTOM = 3;
        public static final int POSTION_RIGHT_AND_BOTTOM = 4;


        public int position = POSITION_LEFT;

        public FlowLayoutParams(Context context, AttributeSet attrs) {
            super(context, attrs);
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FlowLayout);
            // 获取在子控件上的 位置属性
            position = a.getInt(R.styleable.FlowLayout_flow_layout_position, position);
            a.recycle();
        }

        public FlowLayoutParams(@Px int width, @Px int height) {
            super(width, height);
        }

        public FlowLayoutParams(LayoutParams source) {
            super(source);
        }
    }


    public static String getPosition(int posisiton){
        switch (posisiton){
            case FlowLayoutParams.POSITION_MIDDLE:
                return "中间";
            case FlowLayoutParams.POSITION_LEFT:
                return "左上";
            case FlowLayoutParams.POSITION_RIGHT:
                return "右上";
            case FlowLayoutParams.POSITION_BOTTOM:
                return "左下";
            case FlowLayoutParams.POSTION_RIGHT_AND_BOTTOM:
                return "右下";
            default:
                return "出现null";
        }
    }
}
