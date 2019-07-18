package com.fearefull.todoreminder.ui.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;

import androidx.viewpager.widget.ViewPager;

public class BaseViewPager extends ViewPager {

    private boolean enableSwipe;
    private int height = 0;
    private int decorHeight = 0;
    private int widthMeasuredSpec;

    public BaseViewPager(Context context) {
        super(context);
        init();
    }

    public BaseViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        enableSwipe = true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        // Never allow swiping to switch between pages
        return enableSwipe && super.onInterceptTouchEvent(event);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Never allow swiping to switch between pages
        return enableSwipe && super.onTouchEvent(event);

    }

    public void setEnableSwipe(boolean enableSwipe) {
        this.enableSwipe = enableSwipe;
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        super.setCurrentItem(item, false);
    }

    @Override
    public void setCurrentItem(int item) {
        super.setCurrentItem(item, false);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        widthMeasuredSpec = widthMeasureSpec;
        int mode = MeasureSpec.getMode(heightMeasureSpec);

        if (mode == MeasureSpec.UNSPECIFIED || mode == MeasureSpec.AT_MOST) {
            if (height == 0) {
                // measure vertical decors based on ViewPager implementation
                decorHeight = 0;
                for (int i = 0; i < getChildCount(); i++) {
                    View child = getChildAt(i);
                    LayoutParams lp = (LayoutParams) child.getLayoutParams();
                    if (lp != null) {
                        if (lp.isDecor) {
                            int vgrav = lp.gravity & Gravity.VERTICAL_GRAVITY_MASK;
                            boolean consumeVertical = vgrav == Gravity.TOP || vgrav == Gravity.BOTTOM;
                            if (consumeVertical) {
                                decorHeight += child.getMeasuredHeight();
                            }
                        } else {
                            height = Math.max(height, measureViewHeight(child));
                        }
                    }
                }
            }
            int totalHeight = height + decorHeight + getPaddingBottom() + getPaddingTop();
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(totalHeight, MeasureSpec.EXACTLY);
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private int measureViewHeight(View view) {
        view.measure(getChildMeasureSpec(widthMeasuredSpec,
                getPaddingLeft() + getPaddingRight(),
                view.getLayoutParams().width),
                MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        return view.getMeasuredHeight();
    }
}