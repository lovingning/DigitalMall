package com.acchain.community.base;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.acchain.community.BuildConfig;
import com.acchain.community.R;
import com.acchain.community.dagger.component.ActivityComponent;
import com.acchain.community.dagger.component.DaggerActivityComponent;
import com.acchain.community.dagger.module.ActivityModule;
import com.acchain.community.retrofit.HttpInterface;
import com.acchain.community.rxbus.RxBus;
import com.acchain.community.util.ActivityUtil;
import com.acchain.community.util.Const;
import com.alibaba.android.arouter.launcher.ARouter;
import com.jaeger.library.StatusBarUtil;
import com.knowledge.mnlin.methodinject.MethodInjectActivity;
import com.knowledge.mnlin.methodinject_annotations.annotations.ActivityInject;
import com.knowledge.mnlin.methodinject_annotations.annotations.MethodInject;
import com.knowledge.mnlin.methodinject_annotations.enums.LifeCycleMethod;
import com.orhanobut.logger.Logger;

import java.util.Stack;

import javax.inject.Inject;

import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static android.view.KeyEvent.KEYCODE_MENU;

/**
 * Created by Administrator on 17-1-22.
 */
@ActivityInject
public abstract class BaseActivity<T extends BasePresenter> extends MethodInjectActivity implements BaseView {
    //管理RxBus添加的事件队列
    private static CompositeDisposable group;

    public Toolbar toolbar;
    protected ActivityComponent activityComponent;
    @Inject
    protected T mPresenter;

    @Inject
    protected HttpInterface httpInterface;

    protected CharSequence activityTitle;

    /**
     * 使用dagger注入自身
     */
    protected abstract void injectSelf();

    /**
     * @return 获取布局文件
     */
    @LayoutRes
    protected abstract int getContentViewId();

    /**
     * 初始化数据
     */
    protected abstract void initData(Bundle savedInstanceState);

    /**
     * 在子线程加载数据
     */
    protected void initDataInThread(Bundle savedInstanceState) {
    }

    @Override
    @SuppressWarnings("all")
    final protected void onCreate(Bundle savedInstanceState) {
        if (BaseApplication.isStrictMode) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads()
                    .detectDiskWrites().detectNetwork().penaltyLog().build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects()
                    .detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());
        }
        super.onCreate(savedInstanceState);
        Logger.v(getClass().getSimpleName() + " : " + "onCreate: ");

        //设置支持动画过渡效果
        ActivityUtil.setActivityContentTransitions(this);
        ActivityUtil.setActivitySupportTransitions(this);
        /*getWindow().setExitTransition(new Fade());*/

        //设置内容全屏
        ActivityUtil.setDecorTransparent(this);

        //设置statubar的颜色
        ActivityUtil.setStatusBarColor(this, getResources().getColor(R.color.transparent));

        //添加布局
        setContentView(getContentViewId());
        ButterKnife.bind(this);

        //设置toolbar和startBar颜色;当点击navigation时默认退出活动
        toolbar = (Toolbar) findViewById(com.acchain.community.R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(v -> finish());
        }

        //注入dagger框架
        activityComponent = DaggerActivityComponent.builder().applicationComponent(BaseApplication.getApplicationComponent()).activityModule(new ActivityModule(this)).build();
        injectSelf();

        //注入路由Arouter框架
        ARouter.getInstance().inject(this);

        //绑定presenter和activity
        mPresenter.mView = this;

        //获取title值
        activityTitle = getTitle();

        //初始化内容
        initData(savedInstanceState);

        //子线程数据处理
        Observable.just(true)
                .subscribeOn(Schedulers.single())
                .map(aBoolean -> {
                    initDataInThread(savedInstanceState);
                    return true;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(a -> {
                });
    }

    /**
     * 设置状态栏颜色
     */
    @SuppressWarnings("all")
    protected void setStatusBarColor(int color) {
        if (ActivityUtil.getSDKVersion() >= 21) {
            getWindow().setStatusBarColor(color);
        } else {
            StatusBarUtil.setColor(this, color);
        }
    }

    /**
     * 设置导航栏颜色
     */
    protected void setToolbarColor(int color) {
        toolbar.setBackgroundColor(color);
    }

    /**
     * 将一个已有的颜色值加深
     */
    private int getDeepColor(int color) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        //改变亮度
        hsv[2] = (float) (hsv[2] * 0.8);
        return Color.HSVToColor(hsv);
    }

    /**
     * 更新数据
     */
    @MethodInject(method = LifeCycleMethod.onResume)
    public void refreshData() {

    }

    /**
     * @param msg 需要显示的toast消息
     */
    protected void showToast(String msg) {
        RxBus.getInstance().post(new BaseEvent(Const.SHOW_TOAST, msg == null ? "null" : msg));
    }

    /**
     * 显示snackbar
     */
    protected void showSnackbar(String msg, String button, View.OnClickListener onClickButton) {
        Snackbar singleSnackbar = Snackbar.make(toolbar == null ? findViewById(android.R.id.content) : toolbar, msg, Snackbar.LENGTH_INDEFINITE);
        ((TextView) singleSnackbar.getView().findViewById(android.support.design.R.id.snackbar_text)).setMaxLines(10);
        singleSnackbar.getView().setAlpha(0.9f);
        singleSnackbar.setActionTextColor(getThemeColorAttribute(com.acchain.community.R.style.TextInputLayout_HintTextAppearance_Hacker, android.R.attr.textColor));
        singleSnackbar.setAction(button, onClickButton == null ? (View.OnClickListener) view -> singleSnackbar.dismiss() : onClickButton).show();
    }

    protected void removeDisposable() {
        if (group != null) {
            group.dispose();
        }
    }

    protected void addDisposable(Disposable disposable) {
        if (group == null) {
            group = new CompositeDisposable();
        }
        group.add(disposable);
    }

    /**
     * 当点击menu键时屏蔽任何操作
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KEYCODE_MENU) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void startActivity(Intent intent) {
        if (Build.VERSION.SDK_INT >= 21) {
            super.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        } else {
            super.startActivity(intent);
        }
    }

    @Override
    public void startActivity(Intent intent, @Nullable Bundle options) {
        super.startActivity(Intent.createChooser(intent, "选择一个应用"), options);
    }

    /**
     * 获取drawable
     */
    protected Drawable dispatchGetDrawable(@DrawableRes int resId) {
        if (Build.VERSION.SDK_INT >= 21) {
            return getDrawable(resId);
        } else {
            return getResources().getDrawable(resId);
        }
    }

    /**
     * 获取颜色值
     */
    final public int dispatchGetColor(@ColorRes int resId) {
        if (Build.VERSION.SDK_INT < 23) {
            return getResources().getColor(resId);
        } else {
            return getResources().getColor(resId, null);
        }
    }

    /**
     * 获取系统属性中某个值
     */
    protected int getThemeColorAttribute(int styleRes, int colorId) {
        int defaultColor = dispatchGetColor(com.acchain.community.R.color.transparent);
        int[] attrsArray = {colorId};
        TypedArray typedArray = obtainStyledAttributes(styleRes, attrsArray);
        int color = typedArray.getColor(0, defaultColor);

        typedArray.recycle();
        return color;
    }

    /**
     * 当activity销毁时候,关闭资源
     */
    @Override
    public void  onDestroy() {
        super.onDestroy();
        mPresenter.mView = null;
        removeDisposable();
    }

    /**
     * 管理activity实例
     */
    @MethodInject(method = LifeCycleMethod.onCreate,inject = BuildConfig.INJECT)
    public void manageAddActivity() {
        boolean isExistStack = false;
        for (int i = 0; i < BaseApplication.activityManager.size(); i++) {
            Stack<BaseActivity> temp = BaseApplication.activityManager.get(i);
            if (temp.get(0).getTaskId() == getTaskId()) {
                temp.push(this);
                isExistStack = true;
                break;
            }
        }
        if (!isExistStack) {
            Stack<BaseActivity> stack = new Stack<>();
            stack.push(this);
            BaseApplication.activityManager.add(stack);
        }
    }

    /**
     * 移除需要销毁的activity实例
     */
    @MethodInject(method = LifeCycleMethod.onDestroy,inject = BuildConfig.INJECT)
    public void manageRemoveActivity() {
        for (int i = 0; i < BaseApplication.activityManager.size(); i++) {
            Stack<BaseActivity> temp = BaseApplication.activityManager.get(i);
            if (temp.get(0).getTaskId() == getTaskId()) {
                temp.pop();
                if (temp.size() == 0) {
                    BaseApplication.activityManager.remove(temp);
                }
                break;
            }
        }
    }

    /**
     * 打印活动栈
     */
    protected void logStack() {
        for (int i = 0; i < BaseApplication.activityManager.size(); i++) {
            Stack<BaseActivity> temp = BaseApplication.activityManager.get(i);
            Logger.v(getClass().getSimpleName() + " : " + "\n栈id：" + temp.get(0).getTaskId() + "\n栈底->" + temp.toString() + "栈顶");
        }
    }
}
