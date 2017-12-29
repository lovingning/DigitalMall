package com.acchain.community.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.acchain.community.dagger.component.DaggerFragmentComponent;
import com.acchain.community.dagger.component.FragmentComponent;
import com.acchain.community.dagger.module.FragmentModule;
import com.acchain.community.retrofit.HttpInterface;
import com.orhanobut.logger.Logger;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 3/9/17.
 */
public abstract class BaseFragment<T extends BasePresenter> extends Fragment implements BaseView {
	//上下文对象
	@Inject
	protected BaseActivity baseActivity;

	//根部局
	@Inject
	protected ViewGroup rootView;

	@Inject
	protected T mPresenter;

	@Inject
	protected HttpInterface httpInterface;

	protected FragmentComponent fragmentComponent;

	/**
	 * 用于在fragment销毁时接触绑定
	 */
	Unbinder unbinder;


	/**
	 * 数据处理
	 */
	protected  abstract  void initData(Bundle savedInstanceState) ;

	/**
	 * 获取xml布局文件
	 */
	protected abstract @LayoutRes int getContentViewId();

	/**
	 * 使用dagger注入自身
	 */
	protected abstract void injectSelf();

	@Override
	public void onAttach(Context context) {
		Logger.v( "onAttach: " + getClass().getSimpleName());
		super.onAttach(context);
	}

	@Override
	final public void onCreate(@Nullable Bundle savedInstanceState) {
		Logger.v( "onCreate: " + getClass().getSimpleName());
		super.onCreate(savedInstanceState);
	}

	@Nullable
	@Override
	final public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
			Bundle savedInstanceState) {
		Logger.v( "onCreateView: " + getClass().getSimpleName());
		return inflater.inflate(getContentViewId(),null,false);
	}

	@Override
	final public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		Logger.v( "onActivityCreated: " + getClass().getSimpleName());
		super.onActivityCreated(savedInstanceState);

		fragmentComponent= DaggerFragmentComponent.builder().applicationComponent(BaseApplication.getApplicationComponent()).fragmentModule(new FragmentModule(this)).build();
		injectSelf();
		unbinder=ButterKnife.bind(this, rootView);
		initData(savedInstanceState);
	}

	@Override
	public void onStart() {
		Logger.v( "onStart: " + getClass().getSimpleName());
		super.onStart();
	}

	@Override
	public void onResume() {
		Logger.v( "onResume: " + getClass().getSimpleName());
		super.onResume();
	}

	@Override
	public void onPause() {
		Logger.v( "onPause: " + getClass().getSimpleName());
		super.onPause();
	}

	@Override
	public void onStop() {
		Logger.v( "onStop: " + getClass().getSimpleName());
		super.onStop();
	}

	@Override
	public void onDestroyView() {
		Logger.v( "onDestroyView: " + getClass().getSimpleName());
		super.onDestroyView();
	}

	@Override
	public void onDestroy() {
		Logger.v( "onDestroy: " + getClass().getSimpleName());
		super.onDestroy();
		unbinder.unbind();
	}

	@Override
	public void onDetach() {
		Logger.v( "onDetach: " + getClass().getSimpleName());
		super.onDetach();
	}
}
