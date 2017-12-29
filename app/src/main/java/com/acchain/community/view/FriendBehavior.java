package com.acchain.community.view;

import android.content.Context;
import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.acchain.community.R;
import com.blankj.utilcode.util.ScreenUtils;

/**
 * @author 小任
 * @date 2017/12/25
 * version 1.0
 * 描述:
 */

public class FriendBehavior extends CoordinatorLayout.Behavior<LinearLayout> {
    private int defaultY;
    private int minY;
    private int minW;
    private int maxW;
    private int minH;
    private int maxH;
    private int scollHeight;
    private int searchMaxHeight;
    private int searchMaxWidth;
    private float textSize;
    private final Animation showAnimation;
    private final Animation hideAnimation;
    private boolean isShow = true;
    private View childAt;


    public FriendBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        minY = 0;
        minW = ScreenUtils.getScreenWidth() / 2;
        maxW = ScreenUtils.getScreenWidth();
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.actionBarSize, typedValue, true);
        minH = TypedValue.complexToDimensionPixelSize(typedValue.data, context.getResources().getDisplayMetrics());

        showAnimation = AnimationUtils.loadAnimation(
                context, android.support.design.R.anim.abc_fade_in);
        showAnimation.setDuration(200);
        showAnimation.setInterpolator(new LinearOutSlowInInterpolator());
        hideAnimation = AnimationUtils.loadAnimation(
                context, android.support.design.R.anim.abc_fade_out);
        hideAnimation.setDuration(200);
        hideAnimation.setInterpolator(new FastOutLinearInInterpolator());
        hideAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                childAt.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        showAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                childAt.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, LinearLayout child, View dependency) {
        return dependency instanceof AppBarLayout;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, LinearLayout child, View dependency) {
        if (defaultY == 0) {
            defaultY = dependency.getHeight() - child.getHeight();
            child.setY(defaultY);
            maxH = child.getHeight();
            scollHeight = dependency.getHeight() - minH;
            childAt = child.getChildAt(0);
            searchMaxHeight = childAt.getHeight();
            searchMaxWidth = childAt.getWidth();
        }
        float v = (dependency.getHeight() - Math.abs(dependency.getY()) - minH) / scollHeight;
        ViewGroup.LayoutParams layoutParams = child.getLayoutParams();
        layoutParams.width = (int) (maxW - ((maxW - minW) * (1 - v)));
        layoutParams.height = (int) (maxH - ((maxH - minH) * (1 - v)));
        child.setLayoutParams(layoutParams);
        float y = defaultY - defaultY * (1 - v);
        child.setY(y);
        if (y == 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                child.setZ(20);
            }
        }
        ViewGroup.LayoutParams layoutParams1 = childAt.getLayoutParams();
        layoutParams1.height = (int) (searchMaxHeight * v);
        childAt.setLayoutParams(layoutParams1);
        if (v < 1) {
            if (!isShow) {
                return true;
            }
            childAt.startAnimation(hideAnimation);
            isShow = false;
        } else {
            if (isShow) {
                return true;
            }
            childAt.startAnimation(showAnimation);
            isShow = true;
        }
        return true;
    }

}
