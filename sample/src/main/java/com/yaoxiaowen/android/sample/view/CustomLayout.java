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
 * YaoWen(43194) create at tongcheng work pc,
 * time:  2017/7/10 17:14  qq:2669932513
 *
 *  参考:
 *  http://blog.csdn.net/xmxkf/article/details/51500304
 *
 *  但是依旧没有解决我的问题，如果该类使用了padding.那么就不对了
 */
public class CustomLayout extends ViewGroup{

    private static final String TAG = "FlowLayout";

    private Context mContext;

    public CustomLayout(Context context) {
        super(context);
        mContext = context;
    }

    public CustomLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public CustomLayout(Context context, AttributeSet attrs, int defStyleAttr) {
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
                int marginWidth = cWidth + params.leftMargin + params.rightMargin;
                layoutWidth = (layoutWidth > marginWidth ? layoutWidth : marginWidth);
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
                int marginHeight = cHeight + params.topMargin + params.bottomMargin;
                layoutHeight = (marginHeight>layoutHeight) ? marginHeight : layoutHeight;
            }
        }

        setMeasuredDimension(layoutWidth, layoutHeight);
    }

    //http://blog.csdn.net/xmxkf/article/details/51500304
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
                        left = (getWidth() - childMeasureWidth) / 2 - params.rightMargin + params.leftMargin;
                        top = (getHeight() - childMeasureHeight) / 2 + params.topMargin - params.bottomMargin;
                    break;
                case FlowLayoutParams.POSITION_LEFT:  //左上
                        left = 0 + params.leftMargin;
                        top=0 + params.topMargin;
                    break;
                case FlowLayoutParams.POSITION_RIGHT: //右上
                        left = getWidth() - childMeasureWidth - params.rightMargin;
                        top = 0 + params.topMargin;
                    break;
                case FlowLayoutParams.POSITION_BOTTOM: //左下
                        left = 0 + params.leftMargin;
                        top = getHeight() - childMeasureHeight - params.bottomMargin;
                    break;
                case FlowLayoutParams.POSTION_RIGHT_AND_BOTTOM:  //右下角
                        left = getWidth() - childMeasureWidth - params.rightMargin;
                        top = getHeight() - childMeasureHeight - params.bottomMargin;
                    break;
                default:
                    LogUtils.e(TAG, "onLayout() switch错误的出现了默认值");
            }

            child.layout(left, top, left+childMeasureWidth, top+childMeasureHeight);
        }

//        final int count = getChildCount();
//        int childMeasureWidth = 0;
//        int childMeasureHeight = 0;
//
//        int layoutWidth = 0; //容器已经占据的宽度
//        int layoutHeight = 0;  //容器已经占据的高度
//
//        int maxChildHeightOneItem = 0;  //一行中子控件最高的高度, 用于决定下一行高度应该在目前的基础上累加多少
//        for (int i=0; i<count; i++){
//            View child = getChildAt(i);
//            childMeasureWidth = child.getMeasuredWidth();
//            childMeasureHeight = child.getMeasuredHeight();
//
//            if (layoutWidth < getWidth()) {
//                //一行没有排满，继续往右排列
//                left = layoutWidth;
//                right = left + childMeasureWidth;
//                top = layoutHeight;
//                bottom = top + childMeasureHeight;
//            }else {
//                //排满后换行
//                layoutWidth = 0;
//                layoutHeight += maxChildHeightOneItem;
//                maxChildHeightOneItem = 0;
//
//                left = layoutWidth;
//                right = left + childMeasureWidth;
//                top = layoutHeight;
//                bottom = top + childMeasureHeight;
//            }
//
//            layoutWidth += childMeasureWidth;  //宽度累加
//            if (childMeasureHeight > maxChildHeightOneItem){
//                maxChildHeightOneItem = childMeasureHeight;
//            }
//
//            child.layout(left, top, right, bottom);
//        }
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
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomLayout);
            // 获取在子控件上的 位置属性
            position = a.getInt(R.styleable.CustomLayout_layout_position, position);
            a.recycle();
        }

        public FlowLayoutParams(@Px int width, @Px int height) {
            super(width, height);
        }

        public FlowLayoutParams(LayoutParams source) {
            super(source);
        }
    }

}
